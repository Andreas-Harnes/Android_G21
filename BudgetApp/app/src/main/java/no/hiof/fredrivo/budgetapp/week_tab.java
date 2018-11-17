package no.hiof.fredrivo.budgetapp;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import no.hiof.fredrivo.budgetapp.Adapter.DayTabAdapter;
import no.hiof.fredrivo.budgetapp.Adapter.WeekTabAdapter;
import no.hiof.fredrivo.budgetapp.R;
import no.hiof.fredrivo.budgetapp.classes.Expenses;

public class week_tab extends Fragment {

    private static final String TAG = "Tab2frag";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_week_tab, container, false);

        //setter opp RecyclerView, LayoutManager og adapter
        RecyclerView WeekTabRecyclerView = root.findViewById(R.id.weekTabRecyclerView);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        WeekTabRecyclerView.setLayoutManager(layoutManager);

        WeekTabRecyclerView.setAdapter(new WeekTabAdapter(Expenses.getExpenseList()));

        return root;
    }

}
