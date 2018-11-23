package no.hiof.fredrivo.budgetapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Objects;

import no.hiof.fredrivo.budgetapp.Adapter.DayTabAdapter;
import no.hiof.fredrivo.budgetapp.classes.Categories;
import no.hiof.fredrivo.budgetapp.classes.Expenses;

public class day_tab extends Fragment {


    private static final String TAG = "Tab1frag";

    private DatabaseReference mDatabaseRef;

    private static ArrayList<Expenses> expensesArrayList = new ArrayList<>();
    private ArrayList<Expenses> dayCategoryList = new ArrayList<>();

    private GoogleSignInAccount account;
    private TextView txtDaySum;

    private DayTabAdapter dayTabAdapter;

    private int totalSpent;
    private Intent dayDetailIntent;



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        expensesArrayList.clear();

        mDatabaseRef = FirebaseDatabase.getInstance().getReference();

        account = GoogleSignIn.getLastSignedInAccount(getContext());

//        Toast.makeText(getContext(), account.getDisplayName(), Toast.LENGTH_SHORT).show();

        Toast.makeText(getContext(), account.getEmail(), Toast.LENGTH_SHORT).show();



        //dayCategoryList = Expenses.expensesSortedCategory(expensesArrayList);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_day_tab, container, false);

        //setter opp RecyclerView, LayoutManager og adapter
        RecyclerView dayTabRecyclerView = root.findViewById(R.id.dayTabRecyclerView);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        dayTabRecyclerView.setLayoutManager(layoutManager);

        dayDetailIntent = new Intent(getContext(), CategoryDetailActivity.class);

        //ArrayList<Expenses> dayCategoryList = Expenses.expensesSortedCategory(expensesArrayList);
        dayTabAdapter = new DayTabAdapter(dayCategoryList, new DayTabAdapter.DayViewClickListener() {
            @Override
            public void onClick(int position) {
                String category = dayCategoryList.get(position).getCategory();
                ArrayList<Expenses> detailDayList = DetailDayList(expensesArrayList, category);
                Bundle b = new Bundle();
                b.putSerializable("list", detailDayList);
                dayDetailIntent.putExtra("bundle", b);

                startActivity(dayDetailIntent);
            }
        });
        dayTabRecyclerView.setAdapter(dayTabAdapter);

        txtDaySum = root.findViewById(R.id.txtDaySum);

        mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                showData(dataSnapshot);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



        return root;
    }

    //legger sammen alle dagens utgifter til en sum nederst
    private int daySum(ArrayList<Expenses> expenses) {
        int total = 0;

        for (Expenses i : expenses) {
            total += i.getSum();
        }

        return total;
    }


    private void showData(DataSnapshot dataSnapshot) {
        expensesArrayList.clear();
        dayCategoryList.clear();
        for(DataSnapshot ds : dataSnapshot.child(account.getId()).child("Expenses").getChildren()) {

            Expenses x = ds.getValue(Expenses.class);

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
        ArrayList<Expenses> tempList = Expenses.expensesSortedCategory(expensesArrayList);

        dayCategoryList.addAll(tempList);

        changeTotalSpent(dayCategoryList, txtDaySum);

        // Det er denne som oppdaterer viewet
        dayTabAdapter.notifyDataSetChanged();
    }

    public ArrayList<Expenses> DetailDayList(ArrayList<Expenses> dayList, String category) {
        ArrayList<Expenses> temp = new ArrayList<>();

        for (Expenses e : dayList) {
            if (e.getCategory().equals(category)) {
                temp.add(e);
            }
        }

        return temp;
    }

    // Sjekker om datoen på expens objektet er fra dags dato
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

        regex = "((" + day.substring(0,1) + ")(" + day.substring(1) + "))(/)(("
                + month.substring(0,1) + ")(" + month.substring(1) +
                "))(/)((" + year.substring(0,1) + ")(" + year.substring(1,2) + ")(" +
                year.substring(2,3) + ")(" + year.substring(3) + "))";


        if(data.getDate().matches(regex)){
            fBoolean = 1;
        }

        if(fBoolean == 1){
            return true;
        } else {
            return false;
        }


    }


    private void changeTotalSpent(ArrayList<Expenses> arrayList, TextView textField){

        int sum = daySum(arrayList);
        String s = "Today's total: " + Integer.toString(sum) + ",-";
        textField.setText(s);

    }
}
