package no.hiof.fredrivo.budgetapp;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import no.hiof.fredrivo.budgetapp.Adapter.DayTabAdapter;
import no.hiof.fredrivo.budgetapp.Adapter.DetailActivityAdapter;
import no.hiof.fredrivo.budgetapp.R;
import no.hiof.fredrivo.budgetapp.classes.Expenses;


/**
 * A simple {@link Fragment} subclass.
 */


public class day_tab extends Fragment {

    private RecyclerView dayTabRecyclerView;




    public day_tab() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_day_tab, container, false);

        dayTabRecyclerView = container.findViewById(R.id.detailRecyclerView);
        dayTabRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        dayTabRecyclerView.setAdapter(new DayTabAdapter(this, Expenses.getExpenseList()));
    }

}
