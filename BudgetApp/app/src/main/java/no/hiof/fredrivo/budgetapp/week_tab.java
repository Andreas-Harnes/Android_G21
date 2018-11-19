package no.hiof.fredrivo.budgetapp;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import no.hiof.fredrivo.budgetapp.Adapter.DayTabAdapter;
import no.hiof.fredrivo.budgetapp.Adapter.WeekTabAdapter;
import no.hiof.fredrivo.budgetapp.R;
import no.hiof.fredrivo.budgetapp.classes.Expenses;

public class week_tab extends Fragment {

    private static final String TAG = "Tab2frag";

    private static List<Expenses> expensesArrayList = new ArrayList<>();

    private DatabaseReference mDatabaseRef;
    private GoogleSignInAccount account;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mDatabaseRef = FirebaseDatabase.getInstance().getReference();

        account = GoogleSignIn.getLastSignedInAccount(getContext());

        Toast.makeText(getContext(), account.getEmail(), Toast.LENGTH_SHORT).show();

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
        for(DataSnapshot ds : dataSnapshot.child(account.getId()).child("Expenses").getChildren()) {

            Expenses x = ds.getValue(Expenses.class);

            if(validData(x)){

            }

            if(validData(ds.getValue(Expenses.class))){
                Expenses userExpense = new Expenses();
                userExpense.setSum(ds.getValue(Expenses.class).getSum());
                userExpense.setDate(ds.getValue(Expenses.class).getDate());
                userExpense.setLocation(ds.getValue(Expenses.class).getLocation());
                userExpense.setDescription(ds.getValue(Expenses.class).getDescription());
                userExpense.setCategory(ds.getValue(Expenses.class).getCategory());

                expensesArrayList.add(userExpense);
            }


        }
    }


    // Sjekker om datoen på expens objektet er fra denne uken
    private boolean validData(Expenses data){

        String regex;

        int fBoolean = 0;

        //gets todays date from calendar object
        Calendar calendar = Calendar.getInstance();
        int intYear = calendar.get(Calendar.YEAR);
        int intMonth = calendar.get(Calendar.MONTH) + 1;
        int intDay = calendar.get(Calendar.DAY_OF_MONTH);

        String day = "";
        String month = "";
        String year = "";


        // Setter day variabelen
        if(intDay < 10){
            day = "0" + String.valueOf(intDay);
        } else {
            day = String.valueOf(intDay);
        }


        // Setter month variabelen
        if(intMonth < 10){
            month = "0" + String.valueOf(intMonth);
        } else {
            month = String.valueOf(intMonth);
        }

        year = String.valueOf(intYear);

        // TODO: Må fikse slik at regex sjekker uke istedenfor dag
        regex = "((" + day.substring(0,1) + ")(" + day.substring(1) + "))(/)(("
                + month.substring(0,1) + ")(" + month.substring(1) +
                "))(/)((" + year.substring(0,1) + ")(" + year.substring(1,2) + ")(" +
                year.substring(2,3) + ")(" + year.substring(3) + "))";

//        Toast.makeText(getContext(), data.getDate(), Toast.LENGTH_SHORT).show();

        if(data.getDate().matches(regex)){
            fBoolean = 1;
        }

        if(fBoolean == 1){
            return true;
        } else {
            return false;
        }

        //Toast.makeText(getContext(), data, Toast.LENGTH_LONG).show();

    }





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_week_tab, container, false);

        //setter opp RecyclerView, LayoutManager og adapter
        RecyclerView WeekTabRecyclerView = root.findViewById(R.id.weekTabRecyclerView);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        WeekTabRecyclerView.setLayoutManager(layoutManager);

        //TODO: ta bort TestData når vi har funksjonalitet
        WeekTabRecyclerView.setAdapter(new WeekTabAdapter(Expenses.TestData()));

        return root;
    }

}
