package no.hiof.fredrivo.budgetapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import no.hiof.fredrivo.budgetapp.Adapter.DetailActivityAdapter;
import no.hiof.fredrivo.budgetapp.classes.Expenses;

public class DetailActivity extends AppCompatActivity {

    private static List<Expenses> expensesArrayList = new ArrayList<>();

    private DatabaseReference mDatabaseRef;
    private GoogleSignInAccount account;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        account = GoogleSignIn.getLastSignedInAccount(this);
        setContentView(R.layout.activity_detail);

        Toolbar toolbar = findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);
        
        RecyclerView detailRecyclerView = findViewById(R.id.detailRecyclerView);
        detailRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        Toast.makeText(this, account.getDisplayName(), Toast.LENGTH_SHORT).show();

//        mDatabaseRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                showData(dataSnapshot);
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });



        detailRecyclerView.setAdapter(new DetailActivityAdapter(this, expensesArrayList));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_detail, menu);
        return true;
    }


//        private void showData(DataSnapshot dataSnapshot) {
//        for(DataSnapshot ds : dataSnapshot.child(account.getId()).child("Expenses").getChildren()) {
//            Expenses userExpense = new Expenses();
//            userExpense.setSum(ds.getValue(Expenses.class).getSum());
//            userExpense.setDate(ds.getValue(Expenses.class).getDate());
//            userExpense.setLocation(ds.getValue(Expenses.class).getLocation());
//            userExpense.setDescription(ds.getValue(Expenses.class).getDescription());
//            userExpense.setCategory(ds.getValue(Expenses.class).getCategory());
//
//
//            expensesArrayList.add(userExpense);
//        }
//    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.overview) {
            Intent intent = new Intent(this, overview.class);
            startActivity(intent);
            return true;
        }
        if (id == R.id.profile) {
            Intent intent = new Intent(this, ProfilActivity.class);
            startActivity(intent);
            return true;
        }
        if (id == R.id.detail) {
            Intent intent = new Intent(this, DetailActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
