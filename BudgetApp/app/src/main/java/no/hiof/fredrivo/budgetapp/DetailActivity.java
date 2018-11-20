package no.hiof.fredrivo.budgetapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import no.hiof.fredrivo.budgetapp.Adapter.DayTabAdapter;
import no.hiof.fredrivo.budgetapp.Adapter.DetailActivityAdapter;
import no.hiof.fredrivo.budgetapp.classes.Expenses;

public class DetailActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private static ArrayList<Expenses> expensesArrayList = new ArrayList<>();

    private DatabaseReference mDatabaseRef;
    private GoogleSignInAccount account;
    private DrawerLayout draw;
    private DetailActivityAdapter detailActivityAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        account = GoogleSignIn.getLastSignedInAccount(this);
        mDatabaseRef = FirebaseDatabase.getInstance().getReference();


        expensesArrayList.clear();

        setContentView(R.layout.activity_detail);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu);


        // implementering av navigation drawer!
        draw = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,draw,toolbar,R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        draw.addDrawerListener(toggle);
        toggle.syncState();



        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        
        RecyclerView detailRecyclerView = findViewById(R.id.detailRecyclerView);
        detailRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        Toast.makeText(this, account.getDisplayName(), Toast.LENGTH_SHORT).show();

        detailActivityAdapter = new DetailActivityAdapter(this, expensesArrayList);

        detailRecyclerView.setAdapter(detailActivityAdapter);


        mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                showData(dataSnapshot);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    private void showData(DataSnapshot dataSnapshot) {
        expensesArrayList.clear();
        for(DataSnapshot ds : dataSnapshot.child(account.getId()).child("Expenses").getChildren()) {

            Expenses userExpense = new Expenses();
            userExpense.setSum(ds.getValue(Expenses.class).getSum());
            userExpense.setDate(ds.getValue(Expenses.class).getDate());
            userExpense.setLocation(ds.getValue(Expenses.class).getLocation());
            userExpense.setDescription(ds.getValue(Expenses.class).getDescription());
            userExpense.setCategory(ds.getValue(Expenses.class).getCategory());

            expensesArrayList.add(userExpense);


        }
//        ArrayList<Expenses> tempList = Expenses.expensesSortedCategory(expensesArrayList);
//
//        dayCategoryList.addAll(tempList);

//        changeTotalSpent(dayCategoryList, txtDaySum);

        // Det er denne som oppdaterer viewet
        detailActivityAdapter.notifyDataSetChanged();
    }

//    private boolean validData(Expenses data){
//
//        String regex;
//
//        int fBoolean = 0;
//
//        //gets todays date from calendar object
//        Calendar calendar = Calendar.getInstance();
//        int intYear = calendar.get(Calendar.YEAR);
//        int intMonth = calendar.get(Calendar.MONTH) + 1;
//        int intDay = calendar.get(Calendar.DAY_OF_MONTH);
//
//        String day = "";
//        String month = "";
//        String year = "";
//
//
//        // Setter day variabelen
//        if(intDay < 10){
//            day = "0" + String.valueOf(intDay);
//        } else {
//            day = String.valueOf(intDay);
//        }
//
//
//        // Setter month variabelen
//        if(intMonth < 10){
//            month = "0" + String.valueOf(intMonth);
//        } else {
//            month = String.valueOf(intMonth);
//        }
//
//        year = String.valueOf(intYear);
//
//        regex = "((" + day.substring(0,1) + ")(" + day.substring(1) + "))(/)(("
//                + month.substring(0,1) + ")(" + month.substring(1) +
//                "))(/)((" + year.substring(0,1) + ")(" + year.substring(1,2) + ")(" +
//                year.substring(2,3) + ")(" + year.substring(3) + "))";
//
//
//        if(data.getDate().matches(regex)){
//            fBoolean = 1;
//        }
//
//        if(fBoolean == 1){
//            return true;
//        } else {
//            return false;
//        }
//
//
//    }




    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int id = menuItem.getItemId();

        if (id == R.id.overview) {
            Intent intent = new Intent(this, overview.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            startActivity(intent);

        } else if (id == R.id.profile) {
            Intent intent = new Intent(this, ProfilActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            startActivity(intent);

        } else if (id == R.id.detail) {
            draw.closeDrawers();

        } else if (id == R.id.chart) {
            Intent intent = new Intent(this,ChartActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            startActivity(intent);

        }

        draw.closeDrawer(GravityCompat.START);
        return true;
    }
}
