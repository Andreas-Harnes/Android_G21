package no.hiof.fredrivo.budgetapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import no.hiof.fredrivo.budgetapp.Adapter.DetailActivityAdapter;
import no.hiof.fredrivo.budgetapp.classes.Expenses;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        RecyclerView detailRecyclerView = findViewById(R.id.detailRecyclerView);
        detailRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        detailRecyclerView.setAdapter(new DetailActivityAdapter(this, Expenses.getExpenseList()));

        //setUpRecylcleView();
    }

    /*private void setUpRecylcleView() {
        detailRecyclerView = findViewById(R.id.detailRecyclerView);
        detailRecyclerView.setAdapter(new DetailActivityAdapter(this, Expenses.getExpenseList()));
        //detailRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        detailRecyclerView.setLayoutManager(linearLayoutManager);

        Log.i("RecycleView", "Recycleview set up");
    }*/
}
