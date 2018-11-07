package no.hiof.fredrivo.budgetapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import no.hiof.fredrivo.budgetapp.classes.Expenses;


public class InputActivity extends AppCompatActivity {

    //variabler for items
    private Intent intentOverview;
    private EditText numPrice;
    private EditText datePurchaseDate;
    private EditText txtLocation;
    private EditText txtDescription;

    private Spinner drpCategory;

    private Spinner drpDateDay;
    private Spinner drpDateMonth;
    private Spinner drpDateYear;

    private int price;
    private LocalDate date;
    private String location;
    private String description;
    private String category;

    // TODO: Legge til funksjonalitet for inputActivity

    // TODO: Det er en bug som gjør at appen stopper når man trykker på add expenses for andre gang

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input);

        numPrice = findViewById(R.id.numPrice);
        datePurchaseDate = findViewById(R.id.datePurchaseDate);
        txtLocation = findViewById(R.id.txtLocation);
        txtDescription = findViewById(R.id.txtDescription);
        drpCategory = findViewById(R.id.drpCategory);
        drpDateDay = findViewById(R.id.drpDateDay);
        drpDateMonth = findViewById(R.id.drpDateMonth);
        drpDateYear = findViewById(R.id.drpDateYear);


        // Dropdown menus for date
        // TODO: Integrere på en sikkelig måte
        // Fyller opp dag, måned og års arrayene med data
        List<Integer> arrayDays =  new ArrayList<Integer>();
        for (int x = 1; x<32; x++) {
            arrayDays.add(x);
        }

        List<Integer> arrayMonths =  new ArrayList<Integer>();
        for (int x = 1; x<13; x++) {
            arrayMonths.add(x);
        }

        List<Integer> arrayYears =  new ArrayList<Integer>();
        for (int x = 1970; x<2018; x++) {
            arrayYears.add(x);
        }


        // Lager 3 adaptere for dropdown menyene
        ArrayAdapter<Integer> adapterDays = new ArrayAdapter<Integer>(
                this, android.R.layout.simple_spinner_item, arrayDays);

        ArrayAdapter<Integer> adapterMonths = new ArrayAdapter<Integer>(
                this, android.R.layout.simple_spinner_item, arrayMonths);

        ArrayAdapter<Integer> adapterYears = new ArrayAdapter<Integer>(
                this, android.R.layout.simple_spinner_item, arrayYears);



        // Legger dataene til i dropdown menyene
        adapterDays.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        drpDateDay.setAdapter(adapterDays);

        adapterMonths.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        drpDateMonth.setAdapter(adapterMonths);

        adapterYears.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        drpDateYear.setAdapter(adapterYears);



        // test kode slutter


        // Kategori spinner

        List<String> arrayCatergories =  new ArrayList<String>();
        arrayCatergories.add("Mat");
        arrayCatergories.add("Klær");
        arrayCatergories.add("Transport");
        arrayCatergories.add("Diverse");

        ArrayAdapter<String> adapterCategories = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, arrayCatergories);

        adapterCategories.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        drpCategory.setAdapter(adapterCategories);





        intentOverview = new Intent(this, overview.class);

        Button btnAdd = findViewById(R.id.btnRegister);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");

                price = Integer.parseInt(numPrice.getText().toString());
                date = LocalDate.parse(datePurchaseDate.getText().toString(), formatter);
                location = txtLocation.getText().toString();
                description = txtDescription.getText().toString();
//                category = drpCategory.getSelectedItem().toString();
                category = "test";

                new Expenses(price, date, location, description, category);
//                new Expenses(price, location, description, category);



                startActivity(intentOverview);
            }
        });
    }
}
