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

        List<Entry> entries = new ArrayList<Entry>();


    }
}
