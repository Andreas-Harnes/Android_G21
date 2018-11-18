package no.hiof.fredrivo.budgetapp;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import no.hiof.fredrivo.budgetapp.Adapter.DayTabAdapter;
import no.hiof.fredrivo.budgetapp.Adapter.MonthTabAdapter;
import no.hiof.fredrivo.budgetapp.classes.Expenses;


public class month_tab extends Fragment {
    private static final String TAG = "Tab3frag";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_month_tab, container, false);

        //setter opp RecyclerView, LayoutManager og adapter
        RecyclerView monthTabRecyclerView = root.findViewById(R.id.monthTabRecyclerView);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        monthTabRecyclerView.setLayoutManager(layoutManager);

        //TODO: ta bort TestData når vi har funksjonalitet
        monthTabRecyclerView.setAdapter(new MonthTabAdapter(Expenses.TestData()));

        return root;
    }








}
