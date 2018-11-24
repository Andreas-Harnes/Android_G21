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
import android.widget.TextView;
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
import no.hiof.fredrivo.budgetapp.classes.WeekDates;

public class week_tab extends Fragment {

    private static final String TAG = "Tab3frag";
    private ArrayList<Expenses> expensesArrayList = new ArrayList<>();
    private DatabaseReference mDatabaseRef;
    private ArrayList<Expenses> weekCategoryList = new ArrayList<>();
    private GoogleSignInAccount account;
    private TextView txtDaySum;
    private WeekTabAdapter weekTabAdapter;
    private WeekDates c;




    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        expensesArrayList.clear();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference();
        account = GoogleSignIn.getLastSignedInAccount(getContext());
        c = new WeekDates();


//        Toast.makeText(getContext(), account.getEmail(), Toast.LENGTH_SHORT).show();

        //weekCategoryList = Expenses.expensesSortedCategory(expensesArrayList);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_day_tab, container, false);

        //setter opp RecyclerView, LayoutManager og adapter
        RecyclerView dayTabRecyclerView = root.findViewById(R.id.dayTabRecyclerView);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        dayTabRecyclerView.setLayoutManager(layoutManager);

        //ArrayList<Expenses> weekCategoryList = Expenses.expensesSortedCategory(expensesArrayList);
        weekTabAdapter = new WeekTabAdapter(weekCategoryList);
        dayTabRecyclerView.setAdapter(weekTabAdapter);

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

    // Sjekker om datoen på expens objektet er fra denne uken
    private boolean validData(Expenses data, WeekDates weekList){
        for (String x : weekList.getDates()) {
            if(data.getDate().equals(x)){
                return  true;
            }
        }
        return false;
    }

    private void showData(DataSnapshot dataSnapshot) {
        expensesArrayList.clear();
        weekCategoryList.clear();
        for(DataSnapshot ds : dataSnapshot.child(account.getId()).child("Expenses").getChildren()) {

            if(validData(ds.getValue(Expenses.class), c)){
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
        weekCategoryList.addAll(tempList);
        changeTotalSpent(weekCategoryList, txtDaySum);

        // Det er denne som oppdaterer viewet
        weekTabAdapter.notifyDataSetChanged();
    }


    private int daySum(ArrayList<Expenses> expenses) {
        int total = 0;

        for (Expenses i : expenses) {
            total += i.getSum();
        }

        return total;
    }

    private void changeTotalSpent(ArrayList<Expenses> arrayList, TextView textField){

        int sum = daySum(arrayList);
        String s = "Weekly total: " + Integer.toString(sum) + ",-";
        textField.setText(s);

    }


}
