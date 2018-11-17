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

        //finds views from xml file
        drpSettingsCat = findViewById(R.id.drpSettingsCat);
        txtMonthlyEx = findViewById(R.id.txtProfilSettingsMonthlyEx);
        txtIncome = findViewById(R.id.txtProfilSettingsIncome);
        txtSavePrMonth = findViewById(R.id.txtProfilSettingsSave);
        txtCategoriesForSaving = findViewById(R.id.txtProfilCategories);

        //adapter to put categories from arraylist into dropdown menu
        ArrayAdapter<String> adapterSettingsCat = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, Categories.getUserCategories());
        adapterSettingsCat.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        drpSettingsCat.setAdapter(adapterSettingsCat);

        //sets toolbar for this activity
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //finds button for adding category from xml
        Button btnAddCategory = findViewById(R.id.btnAddCatSettings);

        //sets onclicklistener to category button
        btnAddCategory.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                //current item in dropdown menu
                String fillCategories = drpSettingsCat.getSelectedItem().toString();

                //adds current item to arraylist, if it is not already added
                if (saveCategoriesList.contains(fillCategories)) {
                    Toast.makeText(ProfilSettingsActivity.this, "Category already added", Toast.LENGTH_SHORT).show();
                }
                else {
                    saveCategoriesList.add(fillCategories);
                }

                //creates stringbuilder to contain chosen categories and append new ones
                StringBuilder builder = new StringBuilder();

                //creates index to check which element it is
                int i = 0;

                //for each loop to add elements from arraylist to textview
                for (String s : saveCategoriesList) {

                    //checks if there are more than 4 elements in the list, gives toast if true
                    if (i == 4) {
                        Toast.makeText(getApplicationContext(), "Maximum 4 categories", Toast.LENGTH_SHORT).show();
                    }

                    //checks if this is the first element, adds element without comma
                    else if (i == 0) {
                        builder.append(s);
                    }

                    //adds element with comma
                    else {
                        String s2 = ", " + s;
                        builder.append(s2);
                    }

                    i++;
                }

                //sets content of textview to be string in stringbuilder
                txtCategoriesForSaving.setText(builder);
            }
        });

        Context context = this.getApplicationContext();

        //finds button for saving changes from xml
        Button saveBtn = findViewById(R.id.saveBtn);

        //new intent to ProfilActivity
        intentSaveChanges = new Intent(context, ProfilActivity.class);

        //sets onclicklistener to button for saving changes
        saveBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                //gets text from views
                int monthly = Integer.parseInt(txtMonthlyEx.getText().toString());
                int income = Integer.parseInt(txtIncome.getText().toString());
                int save = Integer.parseInt(txtSavePrMonth.getText().toString());
                String category = txtCategoriesForSaving.getText().toString();

                //creates bundle for transferring info to ProfilActivity
                Bundle bundle = new Bundle();

                //puts info into bundle
                bundle.putInt("income", income);
                bundle.putInt("save", save);
                bundle.putInt("monthly", monthly);
                bundle.putString("category", category);

                //puts bundle into extra
                intentSaveChanges.putExtras(bundle);

                //starts intent with result ok
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
