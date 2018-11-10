package no.hiof.fredrivo.budgetapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import no.hiof.fredrivo.budgetapp.classes.Profile;


public class ProfilSettingsActivity extends AppCompatActivity {
    // TODO: Legge til funksjonalitet for profil settings

    private Intent intentSaveChanges;
    private Spinner drpSettingsCat;

    private EditText txtMonthlyEx;
    private EditText txtIncome;
    private EditText txtSavePrMonth;
    private TextView txtCategoriesForSaving;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil_settings);

        drpSettingsCat = findViewById(R.id.drpCategory);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        intentSaveChanges = new Intent(this, ProfilActivity.class);

        txtMonthlyEx = findViewById(R.id.txtProfilSave);
        txtIncome = findViewById(R.id.txtProfilIncome);
        txtSavePrMonth = findViewById(R.id.txtProfilMonthlyEx);
        txtCategoriesForSaving = findViewById(R.id.txtProfilCategories);

        Button saveBtn = findViewById(R.id.saveBtn);
        saveBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                int monthly = Integer.parseInt(txtMonthlyEx.toString());
                int income = Integer.parseInt(txtIncome.toString());
                int save = Integer.parseInt(txtSavePrMonth.toString());
                String category = txtCategoriesForSaving.toString();

                Bundle bundle = new Bundle();

                bundle.putInt("income", income);
                bundle.putInt("save", save);
                bundle.putInt("monthly", monthly);
                bundle.putString("category", category);

                intentSaveChanges.putExtras(bundle);

                startActivity(intentSaveChanges);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_overview, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
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

        return super.onOptionsItemSelected(item);
    }
}
