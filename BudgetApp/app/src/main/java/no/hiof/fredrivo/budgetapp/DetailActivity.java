package no.hiof.fredrivo.budgetapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import no.hiof.fredrivo.budgetapp.Adapter.DetailActivityAdapter;
import no.hiof.fredrivo.budgetapp.classes.Expenses;

public class DetailActivity extends AppCompatActivity {

    private RecyclerView detailRecyclerView;
    //private ArrayList<Expenses> testlist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        /*Expenses test1 = new Expenses(300, new Date(2018,11,21), "Rema 1000", "desc,", "Food");
        Expenses test2 = new Expenses(200, new Date(2018,11,21), "Rema 1000", "desc,", "Food");
        Expenses test3 = new Expenses(100, new Date(2018,11,21), "Rema 1000", "desc,", "Food");

        testlist.add(test1);
        testlist.add(test2);
        testlist.add(test3);*/

        setUpRecylcleView();
    }

    private void setUpRecylcleView() {
        detailRecyclerView = findViewById(R.id.detailRecyclerView);
        detailRecyclerView.setAdapter(new DetailActivityAdapter(this, Expenses.getExpenseList()));
        //detailRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        detailRecyclerView.setLayoutManager(linearLayoutManager);
    }
}
