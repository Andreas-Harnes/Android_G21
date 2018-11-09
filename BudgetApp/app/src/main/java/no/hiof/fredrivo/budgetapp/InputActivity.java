package no.hiof.fredrivo.budgetapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import no.hiof.fredrivo.budgetapp.classes.Categories;
import no.hiof.fredrivo.budgetapp.classes.Expenses;


public class InputActivity extends AppCompatActivity {

    //variabler for items
    private Intent intentOverview;
    private Intent intentNewCategory;
    private EditText numPrice;
    private EditText datePurchaseDate;
    private EditText txtLocation;
    private EditText txtDescription;

    private Spinner drpCategory;

    private Spinner drpDateDay;
    private Spinner drpDateMonth;
    private Spinner drpDateYear;

    private int price;
    private String date;
    private String location;
    private String description;
    private String category;


//    private FirebaseDatabase dataBase;
    private DatabaseReference myRef;


    // TODO: Legge til funksjonalitet for inputActivity


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input);

        numPrice = findViewById(R.id.numPrice);
        drpDateDay = findViewById(R.id.drpDateDay);
        drpDateMonth = findViewById(R.id.drpDateMonth);
        drpDateYear = findViewById(R.id.drpDateYear);
        txtLocation = findViewById(R.id.txtLocation);
        txtDescription = findViewById(R.id.txtDescription);
        drpCategory = findViewById(R.id.drpCategory);


        myRef = FirebaseDatabase.getInstance().getReference("Expenses");

        ArrayAdapter<String> adapterCategories = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, Categories.getUserCategories());

        adapterCategories.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        drpCategory.setAdapter(adapterCategories);

        // Dropdown menus for date
        // TODO: Integrere på en sikkelig måte
        // Fyller opp dag, måned og års arrayene med data
        List<Integer> arrayDays =  new ArrayList<>();
        for (int x = 1; x<31; x++) {
            arrayDays.add(x);
        }

        List<Integer> arrayMonths =  new ArrayList<>();
        for (int x = 1; x<13; x++) {
            arrayMonths.add(x);
        }

        List<Integer> arrayYears =  new ArrayList<>();
        for (int x = 2018; x>=1970; x--) {
            arrayYears.add(x);
        }


        // Lager 3 adaptere for dropdown menyene
        // Dette er for at dataen i arrayen kan brukes i en spinner
        ArrayAdapter<Integer> adapterDays = new ArrayAdapter<Integer>(
                this, android.R.layout.simple_spinner_item, arrayDays);

        ArrayAdapter<Integer> adapterMonths = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item, arrayMonths);

        ArrayAdapter<Integer> adapterYears = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item, arrayYears);



        // Legger dataene til i dropdown menyene
        adapterDays.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        drpDateDay.setAdapter(adapterDays);

        adapterMonths.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        drpDateMonth.setAdapter(adapterMonths);

        adapterYears.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        drpDateYear.setAdapter(adapterYears);

        intentOverview = new Intent(this, overview.class);
        finish();

        Button btnAdd = findViewById(R.id.btnRegister);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               String id = myRef.push().getKey();
               price = Integer.parseInt(numPrice.getText().toString());
               date = drpDateDay.getSelectedItem().toString() + "/" + drpDateMonth.getSelectedItem().toString() + "/" + drpDateYear.getSelectedItem().toString();
               location = txtLocation.getText().toString();
               description = txtDescription.getText().toString();
               category = drpCategory.getSelectedItem().toString();



                myRef.child(id).setValue(new Expenses(price, date, location, description, category));

                startActivity(intentOverview);
            }
        });

        intentNewCategory = new Intent (this, NewCategoryActivity.class);

        Button btnNewCategory = findViewById(R.id.btnAddCategory);
        btnNewCategory.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                startActivity(intentNewCategory);
            }
        });
    }

    /*public List<String> categories(String newCat) {

        arrayCategories.add(newCat);
        return arrayCategories;
    }*/
}
