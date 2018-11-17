package no.hiof.fredrivo.budgetapp;

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
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import no.hiof.fredrivo.budgetapp.Adapter.DayTabAdapter;
import no.hiof.fredrivo.budgetapp.classes.Expenses;

public class day_tab extends Fragment {


    private static final String TAG = "Tab1frag";

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
            Expenses userExpense = new Expenses();
            userExpense.setSum(ds.getValue(Expenses.class).getSum());
            userExpense.setDate(ds.getValue(Expenses.class).getDate());
            userExpense.setLocation(ds.getValue(Expenses.class).getLocation());
            userExpense.setDescription(ds.getValue(Expenses.class).getDescription());
            userExpense.setCategory(ds.getValue(Expenses.class).getCategory());

            expensesArrayList.add(userExpense);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_day_tab, container, false);

        //setter opp RecyclerView, LayoutManager og adapter

        RecyclerView dayTabRecyclerView = root.findViewById(R.id.dayTabRecyclerView);

        final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        dayTabRecyclerView.setLayoutManager(layoutManager);

        dayTabRecyclerView.setAdapter(new DayTabAdapter(expensesArrayList));

        return root;
    }
}
