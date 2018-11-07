package no.hiof.fredrivo.budgetapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import no.hiof.fredrivo.budgetapp.classes.Expenses;


public class InputActivity extends AppCompatActivity {

    //variabler for items
    private Intent intentOverview;
    private EditText numPrice;
    private EditText datePurchaseDate;
    private EditText txtLocation;
    private EditText txtDescription;
    
    private Spinner drpCategory;

    private int price;
    private LocalDate date;
    private String location;
    private String description;
    private String category;

    // TODO: Legge til funksjonalitet for inputActivity
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input);

        numPrice = findViewById(R.id.numPrice);
        datePurchaseDate = findViewById(R.id.datePurchaseDate);
        txtLocation = findViewById(R.id.txtLocation);
        txtDescription = findViewById(R.id.txtDescription);
        drpCategory = findViewById(R.id.drpCategory);

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
                category = drpCategory.getSelectedItem().toString();

                new Expenses(price, date, location, description, category);

                startActivity(intentOverview);
            }
        });
    }
}
