package no.hiof.fredrivo.budgetapp;
import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import no.hiof.fredrivo.budgetapp.classes.Expenses;
import no.hiof.fredrivo.budgetapp.classes.Profile;

public class ProfilActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private TextView txtProfilIncome;
    private TextView txtProfilSave;
    private TextView txtProfilMonthlyEx;
    private TextView txtProfilCategories;
    private DrawerLayout draw;
    private GoogleSignInAccount account;
    private DatabaseReference mDatabaseRef;

    private static int income;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);

        // Get GUI elementer
        txtProfilIncome = findViewById(R.id.txtProfilIncome);
        txtProfilSave = findViewById(R.id.txtProfilSave);
        txtProfilMonthlyEx = findViewById(R.id.txtProfilMonthlyEx);
        txtProfilCategories = findViewById(R.id.txtProfilCategories);

        // Google login
        mDatabaseRef = FirebaseDatabase.getInstance().getReference();
        account = GoogleSignIn.getLastSignedInAccount(this);





        // Sette profilbildet
        ImageView imgProfilePicture = findViewById(R.id.imageView);
        Picasso.get().load(account.getPhotoUrl()).into(imgProfilePicture);

        // Setter navnet til navnet på profilen
        TextView txtProfileName = findViewById(R.id.txtProfileName);
        txtProfileName.setText(account.getDisplayName());




        //Toolbar og navigationDrawer start:
        Toolbar toolbar = findViewById(R.id.profiltoolbar);
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
        // slutt for navi drawer


        mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

//                showData(dataSnapshot);

                if(dataSnapshot.child(account.getId()).hasChild("Profile")){
                    showData(dataSnapshot);
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }



    private void showData(DataSnapshot dataSnapshot) {

        txtProfilIncome.setText(dataSnapshot.child(account.getId()).child("Profile").child("incomePerMonth").getValue().toString());
        txtProfilSave.setText(dataSnapshot.child(account.getId()).child("Profile").child("savePerMonth").getValue().toString());
        txtProfilMonthlyEx.setText(dataSnapshot.child(account.getId()).child("Profile").child("expensesPerMonth").getValue().toString());
        txtProfilCategories.setText(dataSnapshot.child(account.getId()).child("Profile").child("categoryToSave").getValue().toString());

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_profile_settings, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.settings) {
            Intent intent = new Intent(this, ProfilSettingsActivity.class);
            startActivityForResult(intent, 1);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int id = menuItem.getItemId();

        if (id == R.id.overview) {
            Intent intent = new Intent(this, overview.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            startActivity(intent);

        } else if (id == R.id.profile) {
            draw.closeDrawers();

        } else if (id == R.id.detail) {
            Intent intent = new Intent(this,DetailActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            startActivity(intent);

        } else if (id == R.id.chart) {
            if (ProfilActivity.getIncome() == 0){
                draw.closeDrawers();
                Toast.makeText(this, "Pleas fill out profile settings", Toast.LENGTH_SHORT).show();
            }
            else {
                Intent intent = new Intent(this,ChartActivity.class);
                startActivity(intent);

            }

        }

        draw.closeDrawer(GravityCompat.START);
        return true;
    }

    public static int getIncome(){
        return income;
    }
}
