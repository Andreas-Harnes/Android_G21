package no.hiof.fredrivo.budgetapp;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


import no.hiof.fredrivo.budgetapp.classes.Expenses;

public class ChartActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private DrawerLayout draw;

    private static ArrayList<Expenses> expensesArrayList = new ArrayList<>();
    private DatabaseReference mDatabaseRef;
    private GoogleSignInAccount account;

    private List<Expenses> chartDataList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart);

        expensesArrayList.clear();

        mDatabaseRef = FirebaseDatabase.getInstance().getReference();
        account = GoogleSignIn.getLastSignedInAccount(this);

        Toolbar toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu);

        draw = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,draw,toolbar,R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        draw.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        pieChart = findViewById(R.id.pieChartLayout);

        pieChartList = new ArrayList<>();
        
    }

        mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                showData(dataSnapshot);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        /*Liste med alle expenses som sorteres på kategori
        List<Expenses> expensesList = Expenses.expensesSortedCategory((ArrayList<Expenses>) DetailActivity.getExpensesList());
        int moneyLeft = ProfilActivity.getIncome();*/

        //TestData
        List<Expenses> expensesList = Expenses.TestData();
        int moneyLeft = 10000;

        //Legger til expense objekter som entries som pieChartData, Fra https://github.com/PhilJay/MPAndroidChart
        for (Expenses values : expensesList) {
            pieChartList.add(new PieEntry(values.getSum(), values.getCategory()));
            moneyLeft = moneyLeft - values.getSum();


        }

        //Bruker samme kode/litt inspirert fra https://github.com/PhilJay/MPAndroidChart/wiki/Setting-Data
        //Hvis income er mindre en 0 blir den satt til 0 fordi det er ikke hennsiktsmessig
        // å vise negaative penger igjen.
        if (moneyLeft < 0){
            moneyLeft = 0;
            PieEntry moneyLeftEntry = new PieEntry(moneyLeft, "Income left");
            pieChartList.add(moneyLeftEntry);
        }
        else {
            PieEntry moneyLeftEntry = new PieEntry(moneyLeft, "Income left");
            pieChartList.add(moneyLeftEntry);
        }

        PieDataSet dataSet = new PieDataSet(pieChartList, "");
        dataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        dataSet.setValueTextSize(16f);

        Legend legend = pieChart.getLegend();
        legend.setTextSize(16f);
        legend.setWordWrapEnabled(true);
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        legend.setOrientation(Legend.LegendOrientation.HORIZONTAL);

        PieData pieData = new PieData(dataSet);

        pieChart.setData(pieData);
        pieChart.getDescription().setEnabled(false);
        pieChart.invalidate();
    }

    private void showData(DataSnapshot dataSnapshot) {
        expensesArrayList.clear();
        for(DataSnapshot ds : dataSnapshot.child(account.getId()).child("Expenses").getChildren()) {

            //Expenses x = ds.getValue(Expenses.class);

            if(validData(ds.getValue(Expenses.class))){
                Expenses userExpense = new Expenses();
                userExpense.setSum(ds.getValue(Expenses.class).getSum());
                userExpense.setDate(ds.getValue(Expenses.class).getDate());
                userExpense.setLocation(ds.getValue(Expenses.class).getLocation());
                userExpense.setDescription(ds.getValue(Expenses.class).getDescription());
                userExpense.setCategory(ds.getValue(Expenses.class).getCategory());

                expensesArrayList.add(userExpense);

            }


        }

        //chartDataList = Expenses.expensesSortedCategory(expensesArrayList);

    }

    private boolean validData(Expenses data){

        String regex;

        int fBoolean = 0;

        //gets todays date from calendar object
        Calendar calendar = Calendar.getInstance();
        int intYear = calendar.get(Calendar.YEAR);
        int intMonth = calendar.get(Calendar.MONTH) + 1;
        int intDay = calendar.get(Calendar.DAY_OF_MONTH);

        String day = "";
        String month = "";
        String year = "";


        // Setter day variabelen
        if(intDay < 10){
            day = "0" + String.valueOf(intDay);
        } else {
            day = String.valueOf(intDay);
        }


        // Setter month variabelen
        if(intMonth < 10){
            month = "0" + String.valueOf(intMonth);
        } else {
            month = String.valueOf(intMonth);
        }

        year = String.valueOf(intYear);


        regex = "((.)(.))(/)(("
                + month.substring(0,1) + ")(" + month.substring(1) +
                "))(/)((" + year.substring(0,1) + ")(" + year.substring(1,2) + ")(" +
                year.substring(2,3) + ")(" + year.substring(3) + "))";

//        Toast.makeText(getContext(), data.getDate(), Toast.LENGTH_SHORT).show();

        if(data.getDate().matches(regex)){
            fBoolean = 1;
        }

        if(fBoolean == 1){
            return true;
        } else {
            return false;
        }

        //Toast.makeText(getContext(), data, Toast.LENGTH_LONG).show();

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int id = menuItem.getItemId();

        if (id == R.id.overview) {
            Intent intent = new Intent(this, overview.class);
            //intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            startActivity(intent);

        } else if (id == R.id.profile) {
            Intent intent = new Intent(this, ProfilActivity.class);
            //intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            startActivity(intent);

        } else if (id == R.id.detail) {
            Intent intent = new Intent(this, DetailActivity.class);
            //intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            startActivity(intent);

        } else if (id == R.id.chart) {
            draw.closeDrawers();
        }

        draw.closeDrawer(GravityCompat.START);
        return true;
    }
}
