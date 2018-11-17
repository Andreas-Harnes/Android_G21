package no.hiof.fredrivo.budgetapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import no.hiof.fredrivo.budgetapp.classes.Categories;


public class ProfilSettingsActivity extends AppCompatActivity {
    // TODO: Legge til funksjonalitet for profil settings

    private Intent intentSaveChanges;
    private Spinner drpSettingsCat;

    private EditText txtMonthlyEx;
    private EditText txtIncome;
    private EditText txtSavePrMonth;
    private TextView txtCategoriesForSaving;
    private ArrayList<String> saveCategoriesList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil_settings);

        drpSettingsCat = findViewById(R.id.drpSettingsCat);

        ArrayAdapter<String> adapterSettingsCat = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, Categories.getUserCategories());
        adapterSettingsCat.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        drpSettingsCat.setAdapter(adapterSettingsCat);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        txtMonthlyEx = findViewById(R.id.txtProfilSettingsMonthlyEx);
        txtIncome = findViewById(R.id.txtProfilSettingsIncome);
        txtSavePrMonth = findViewById(R.id.txtProfilSettingsSave);
        txtCategoriesForSaving = findViewById(R.id.txtProfilCategories);

        //TODO: bruk onItemSelected for å fylle kategorier inn i txtCategoriesForSaving
        //TODO: sett et tomt element i toppen av spinner
        //TODO: appende flere kategorier i TextViewet

        Button btnAddCategory = findViewById(R.id.btnAddCatSettings);
        btnAddCategory.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String fillCategories = drpSettingsCat.getSelectedItem().toString();

                if (saveCategoriesList.contains(fillCategories)) {
                    Toast.makeText(ProfilSettingsActivity.this, "Category already added", Toast.LENGTH_SHORT).show();
                }
                else {

                    saveCategoriesList.add(fillCategories);
                }

                StringBuilder builder = new StringBuilder();

                int i = 0;

                for (String s : saveCategoriesList) {

                    if (i == 4) {
                        Toast.makeText(getApplicationContext(), "Maximum 4 categories", Toast.LENGTH_SHORT).show();
                    }

                    else if (i == 0) {
                        builder.append(s);
                    }

                    else {
                        String s2 = ", " + s;
                        builder.append(s2);
                    }

                    i++;
                }
                txtCategoriesForSaving.setText(builder);
            }
        });

        Context context = this.getApplicationContext();
        Button saveBtn = findViewById(R.id.saveBtn);

        intentSaveChanges = new Intent(context, ProfilActivity.class);
        saveBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                int monthly = Integer.parseInt(txtMonthlyEx.getText().toString());
                int income = Integer.parseInt(txtIncome.getText().toString());
                int save = Integer.parseInt(txtSavePrMonth.getText().toString());
                String category = txtCategoriesForSaving.getText().toString();

                Bundle bundle = new Bundle();

                bundle.putInt("income", income);
                bundle.putInt("save", save);
                bundle.putInt("monthly", monthly);
                bundle.putString("category", category);

                intentSaveChanges.putExtras(bundle);

                setResult(Activity.RESULT_OK, intentSaveChanges);
                finish();

            }
        });
    }

    public ArrayList<String> getSaveCategoriesList() { return saveCategoriesList; }

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
