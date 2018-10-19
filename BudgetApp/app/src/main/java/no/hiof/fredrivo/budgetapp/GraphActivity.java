package no.hiof.fredrivo.budgetapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import no.hiof.fredrivo.budgetapp.classes.Expenses;

public class GraphActivity extends AppCompatActivity {

    private PieChart piechart;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);

        piechart = findViewById(R.id.piechart);

        piechart.setUsePercentValues(true);

        //Delete after Iplemented ArrayList for expenses objects
        Expenses ex1 = new Expenses(200, new Date(2018,11,21), "Halden", "desc1", "mat");
        Expenses ex2 = new Expenses(300, new Date(2018,11,21), "Halden", "desc2", "spill");




        List<Entry> entries = new ArrayList<Entry>();

        //entries.add(new Entry(ex1.getSum(), ex1.getCategory()));

        //LineDataSet dataSet = new LineDataSet();


    }
}
