package no.hiof.fredrivo.budgetapp;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import org.w3c.dom.Text;

import no.hiof.fredrivo.budgetapp.classes.Profile;

public class ProfilActivity extends AppCompatActivity {

    private TextView txtProfilIncome;
    private TextView txtProfilSave;
    private TextView txtProfilMonthlyEx;
    private TextView txtProfilCategories;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);

        txtProfilIncome = findViewById(R.id.txtProfilIncome);
        txtProfilSave = findViewById(R.id.txtProfilSave);
        txtProfilMonthlyEx = findViewById(R.id.txtProfilMonthlyEx);
        txtProfilCategories = findViewById(R.id.txtProfilCategories);

        setProfileInfo();

        Toolbar toolbar = findViewById(R.id.profiltoolbar);
        setSupportActionBar(toolbar);
    }

    public void setProfileInfo() {

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();

        if (extras != null) {

            int income = intent.getIntExtra("income", -1000);
            int save = intent.getIntExtra("income", -1000);
            int monthly = intent.getIntExtra("income", -1000);
            String category = intent.getStringExtra("category");

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
    }

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
            startActivity(intent);
            return true;
        }

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

        return super.onOptionsItemSelected(item);
    }
}
