package no.hiof.fredrivo.budgetapp;

import android.graphics.Color;
import android.graphics.Paint;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;


import java.util.ArrayList;
import java.util.List;


import no.hiof.fredrivo.budgetapp.classes.Expenses;

public class ChartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart);

        PieChart pieChart = findViewById(R.id.pieChartLayout);

        List<PieEntry> pieChartList = new ArrayList<>();

        //TestData
        List<Expenses> TestData = new ArrayList<>();
        TestData.add(new Expenses(100, "11/04/2017", "Halden", "Sulten","Mat"));
        TestData.add(new Expenses(200, "04/04/2018", "Halden", "bleh","Bil"));
        TestData.add(new Expenses(150, "03/11/2017", "Halden", "Mat?","Drikke"));
        TestData.add(new Expenses(400, "17/08/2017", "Halden", "namm","Pølse"));

        for (Expenses values : TestData) {

            pieChartList.add(new PieEntry(values.getSum(), values.getCategory()));

        }

        PieDataSet dataSet = new PieDataSet(pieChartList, "");
        dataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        dataSet.setValueTextSize(16f);

        //Description
        Description pieChartDescription = new Description();
        pieChart.setDescription(pieChartDescription);
        pieChartDescription.setText("Your total expenses");
        pieChartDescription.setTextSize(16f);
        pieChartDescription.setPosition(750, 150);

        PieData pieData = new PieData(dataSet);

        pieChart.setData(pieData);
        pieChart.invalidate();



    }
}
