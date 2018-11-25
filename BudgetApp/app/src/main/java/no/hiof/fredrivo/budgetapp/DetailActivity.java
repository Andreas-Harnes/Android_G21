package no.hiof.fredrivo.budgetapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import no.hiof.fredrivo.budgetapp.Adapter.DayTabAdapter;
import no.hiof.fredrivo.budgetapp.Adapter.DetailActivityAdapter;
import no.hiof.fredrivo.budgetapp.classes.Expenses;

public class DetailActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private static ArrayList<Expenses> expensesArrayList = new ArrayList<>();

    private DatabaseReference mDatabaseRef;
    private GoogleSignInAccount account;
    private DrawerLayout draw;
    private DetailActivityAdapter detailActivityAdapter;
    private ArrayList<Expenses> list = new ArrayList<>();
    private DataSnapshot ds;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        account = GoogleSignIn.getLastSignedInAccount(this);
        mDatabaseRef = FirebaseDatabase.getInstance().getReference();


        expensesArrayList.clear();

        setContentView(R.layout.activity_detail);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu);


        // implementering av navigation drawer!
        draw = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,draw,toolbar,R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        draw.addDrawerListener(toggle);
        toggle.syncState();



        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        
        RecyclerView detailRecyclerView = findViewById(R.id.detailRecyclerView);
        detailRecyclerView.setLayoutManager(new LinearLayoutManager(this));


        detailActivityAdapter = new DetailActivityAdapter(this, expensesArrayList);
        detailRecyclerView.setAdapter(detailActivityAdapter);


        mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                ds = dataSnapshot;
                showData(dataSnapshot);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        TextView txtDrawerProfileName = (TextView) navigationView.getHeaderView(0).findViewById(R.id.nav_header_textView);
        txtDrawerProfileName.setText(account.getDisplayName());

        ImageView imgDrawerPicture = (ImageView) navigationView.getHeaderView(0).findViewById(R.id.imageView);
        Picasso.get().load(account.getPhotoUrl()).into(imgDrawerPicture);
    }


    private void showData(DataSnapshot dataSnapshot) {
        expensesArrayList.clear();
        for(DataSnapshot DS : dataSnapshot.child(account.getId()).child("Expenses").getChildren()) {

            Expenses userExpense = new Expenses();
            userExpense.setSum(DS.getValue(Expenses.class).getSum());
            userExpense.setDate(DS.getValue(Expenses.class).getDate());
            userExpense.setLocation(DS.getValue(Expenses.class).getLocation());
            userExpense.setDescription(DS.getValue(Expenses.class).getDescription());
            userExpense.setCategory(DS.getValue(Expenses.class).getCategory());

            expensesArrayList.add(userExpense);


        }


        // Det er denne som oppdaterer viewet
        detailActivityAdapter.notifyDataSetChanged();
    }



    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {


        int id = menuItem.getItemId();

        if (id == R.id.overview) {
            draw.closeDrawers();

        } else if (id == R.id.profile) {
            Intent intent = new Intent(this, ProfilActivity.class);
            startActivity(intent);

        } else if (id == R.id.detail) {
            Intent intent = new Intent(this,DetailActivity.class);
            startActivity(intent);

        } else if (id == R.id.chart) {
            if (Integer.parseInt(ds.child(account.getId()).child("Profile").child("incomePerMonth").getValue().toString()) != 0 ||
                    ds.child(account.getId()).hasChild("Profile")){

                Intent intent = new Intent(this, ChartActivity.class);
                startActivity(intent);
            }
            else {
                Toast.makeText(this, "Please fill out profile settings", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(this, ProfilActivity.class);
                startActivity(intent);

            }

        }

        draw.closeDrawer(GravityCompat.START);
        return true;
    }

    public static List<Expenses> getExpensesList() {
        return expensesArrayList;
    }
}
