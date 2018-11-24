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


import java.util.ArrayList;
import java.util.List;


import no.hiof.fredrivo.budgetapp.classes.Expenses;

public class ChartActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private DrawerLayout draw;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart);

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

        PieChart pieChart = findViewById(R.id.pieChartLayout);

        List<PieEntry> pieChartList = new ArrayList<>();

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
