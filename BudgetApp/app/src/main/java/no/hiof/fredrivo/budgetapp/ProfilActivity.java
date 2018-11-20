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
import android.widget.TextView;
import android.widget.Toast;

import no.hiof.fredrivo.budgetapp.classes.Profile;

public class ProfilActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private TextView txtProfilIncome;
    private TextView txtProfilSave;
    private TextView txtProfilMonthlyEx;
    private TextView txtProfilCategories;
    private DrawerLayout draw;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);

        txtProfilIncome = findViewById(R.id.txtProfilIncome);
        txtProfilSave = findViewById(R.id.txtProfilSave);
        txtProfilMonthlyEx = findViewById(R.id.txtProfilMonthlyEx);
        txtProfilCategories = findViewById(R.id.txtProfilCategories);



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
    }



   /* public void setProfileInfo() {

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();

        int income = intent.getIntExtra("income", -1000);
        int save = intent.getIntExtra("income", -1000);
        int monthly = intent.getIntExtra("income", -1000);
        String category = intent.getStringExtra("category");

        if (income != -1000) {

            Profile newInfo = new Profile(income, save, monthly, category);

            txtProfilIncome.setText(newInfo.getIncomePerMonth());
            txtProfilSave.setText(newInfo.getSavePerMonth());
            txtProfilMonthlyEx.setText(newInfo.getExpensesPerMonth());
            txtProfilCategories.setText(newInfo.getCategoryToSave());
        }

        else {
            txtProfilIncome.setText("Set income");
            txtProfilSave.setText("Set saving");
            txtProfilMonthlyEx.setText("Set expenses");
            txtProfilCategories.setText("Set categories");
        }
    }*/

    // TODO: Items vises ikke i toolbar. får ikke plass i layout. Need Fix
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                Intent i = data;

                int income = i.getIntExtra("income", -1);
                int save = i.getIntExtra("save", -1);
                int monthly = i.getIntExtra("monthly", -1);
                String category = i.getStringExtra("category");
                Profile newInfo = new Profile(income, save, monthly, category);

                txtProfilIncome.setText(String.valueOf(newInfo.getIncomePerMonth() + ",-"));
                txtProfilSave.setText(String.valueOf(newInfo.getSavePerMonth() + ",-"));
                txtProfilMonthlyEx.setText(String.valueOf(newInfo.getExpensesPerMonth() + ",-"));
                txtProfilCategories.setText(newInfo.getCategoryToSave());
            }

            if (resultCode == Activity.RESULT_CANCELED){

                txtProfilIncome.setText("Set income");
                txtProfilSave.setText("Set saving");
                txtProfilMonthlyEx.setText("Set expenses");
                txtProfilCategories.setText("Set categories");
            }
        }
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
            Intent intent = new Intent(this,ChartActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            startActivity(intent);

        }

        draw.closeDrawer(GravityCompat.START);
        return true;
    }
}
