package no.hiof.fredrivo.budgetapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.BreakIterator;
import java.util.ArrayList;

import no.hiof.fredrivo.budgetapp.classes.Categories;
import no.hiof.fredrivo.budgetapp.classes.Profile;


public class ProfilSettingsActivity extends AppCompatActivity {
    // TODO: Legge til funksjonalitet for profil settings

    private Intent intentSaveChanges;
    private Spinner drpSettingsCat;

    private EditText txtMonthlyEx;
    private EditText txtIncome;
    private EditText txtSavePrMonth;
    private TextView txtCategoriesForSaving;
    private ArrayList<String> saveCategoriesList = new ArrayList<>();

    private DatabaseReference mDatabaseRef;
    private GoogleSignInAccount account;
    private TextView txtProfilSettingsIncome;
    private TextView txtProfilSettingsSave;
    private TextView txtProfilSettingsMonthlyEx;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil_settings);

        // Får tak i GUI elementer
        txtProfilSettingsIncome = findViewById(R.id.txtProfilSettingsIncome);
        txtProfilSettingsSave = findViewById(R.id.txtProfilSettingsSave);
        txtProfilSettingsMonthlyEx = findViewById(R.id.txtProfilSettingsMonthlyEx);

        mDatabaseRef = FirebaseDatabase.getInstance().getReference();

        // TODO: Legg til feil håndtering
        try {
            // Google login
            account = GoogleSignIn.getLastSignedInAccount(this);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //finner views fra xml
        drpSettingsCat = findViewById(R.id.drpSettingsCat);
        txtMonthlyEx = findViewById(R.id.txtProfilSettingsMonthlyEx);
        txtIncome = findViewById(R.id.txtProfilSettingsIncome);
        txtSavePrMonth = findViewById(R.id.txtProfilSettingsSave);
        txtCategoriesForSaving = findViewById(R.id.txtProfilCategories);

        //adapter for å putte kategorier fra arraylist inni dropdownmeny
        ArrayAdapter<String> adapterSettingsCat = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, Categories.getUserCategories());
        adapterSettingsCat.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        drpSettingsCat.setAdapter(adapterSettingsCat);

        //setter toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //onclicklistener på bakgrunn for å gjemme tastatur
        findViewById(R.id.profilSettingsBackground).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                //kaller metode som gjemmer tastatur
                hideSoftKeyboard();
                return true;
            }
        });

        //finner knapp for å legge til kategori
        Button btnAddCategory = findViewById(R.id.btnAddCatSettings);

        //setter onClickListener på kategoriknapp
        btnAddCategory.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                //nåværende objekt i dropdownmeny
                String fillCategories = drpSettingsCat.getSelectedItem().toString();

                //legger til nåværende objekt i arraylist, hvis det ikke er det allerede
                if (saveCategoriesList.contains(fillCategories)) {
                    Toast.makeText(ProfilSettingsActivity.this, "Category already added", Toast.LENGTH_SHORT).show();
                }
                else {
                    saveCategoriesList.add(fillCategories);
                }

                //lager stringbuilder for å holde på valgte kategorier og legge på nye
                StringBuilder builder = new StringBuilder();

                //lager index for å sjekke hvilket element vi er på
                int i = 0;

                //for each for å legge til elementer fra arraylist til textview
                for (String s : saveCategoriesList) {

                    //sjekker om der er mer enn 4 elementer i lista, sier ifra med toast
                    if (i == 4) {
                        Toast.makeText(getApplicationContext(), "Maximum 4 categories", Toast.LENGTH_SHORT).show();
                    }

                    //sjekker om dette er det første elementet, legger til kategori uten komma
                    else if (i == 0) {
                        builder.append(s);
                    }

                    //legger til kategori med komma
                    else {
                        String s2 = ", " + s;
                        builder.append(s2);
                    }

                    i++;
                }

                //setter innholder til textview til å være stringen i stringbuilder
                txtCategoriesForSaving.setText(builder);
            }
        });

        Context context = this.getApplicationContext();

        //finner knapp for å lagre endringer
        Button saveBtn = findViewById(R.id.saveBtn);

        //intent for å gå til ProfilActivity
        intentSaveChanges = new Intent(context, ProfilActivity.class);

        //setter onClickListener på knapp for å lagre endringer
        saveBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {



                //henter ut tekst fra views
                String id = mDatabaseRef.push().getKey();
                int monthly = Integer.parseInt(txtMonthlyEx.getText().toString());
                int income = Integer.parseInt(txtIncome.getText().toString());
                int save = Integer.parseInt(txtSavePrMonth.getText().toString());
                String category = txtCategoriesForSaving.getText().toString();

                //lager bundle for å overføre info til ProfilActivity
                Bundle bundle = new Bundle();

                //putter info inn i bundle
                bundle.putInt("income", income);
                bundle.putInt("save", save);
                bundle.putInt("monthly", monthly);
                bundle.putString("category", category);

                //putter bundle inn i extra
                intentSaveChanges.putExtras(bundle);

                Profile userProfile = new Profile(id, income, save, monthly, category);
                mDatabaseRef.child(account.getId()).child("Profile") .setValue(userProfile);

                //starter intent med result ok
                setResult(Activity.RESULT_OK, intentSaveChanges);
                finish();

            }
        });


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

        txtProfilSettingsIncome.setText(dataSnapshot.child(account.getId()).child("Profile").child("incomePerMonth").getValue().toString());
        txtProfilSettingsSave.setText(dataSnapshot.child(account.getId()).child("Profile").child("savePerMonth").getValue().toString());
        txtProfilSettingsMonthlyEx.setText(dataSnapshot.child(account.getId()).child("Profile").child("expensesPerMonth").getValue().toString());

    }


    //metode for å gjemme tastatur
   public void hideSoftKeyboard() {

        try {
            InputMethodManager inputMethodManager = (InputMethodManager)  this.getSystemService(Activity.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), 0);
        } catch (Exception e) {
            e.printStackTrace();
        }

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
