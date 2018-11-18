package no.hiof.fredrivo.budgetapp;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import no.hiof.fredrivo.budgetapp.classes.Categories;
import no.hiof.fredrivo.budgetapp.classes.Expenses;


public class InputActivity extends AppCompatActivity {

    //variabler for views
    private Intent intentOverview;
    private Intent intentNewCategory;
    private EditText numPrice;
    private EditText txtLocation;
    private EditText txtDescription;
    private TextView txtDatePicker;
    private Button btnChangeDate;
    private Spinner drpCategory;

    //variabel for datepickerdialog
    private DatePickerDialog.OnDateSetListener dateDialog;

    private int price;
    private String date;
    private String location;
    private String description;
    private String category;

    private  GoogleSignInAccount account;

    //firebase reference
    private DatabaseReference myRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input);

        try {
            // Google login
            account = GoogleSignIn.getLastSignedInAccount(this);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //Gjør det mulig å hide keyboardet når man trykker på skjermen
        findViewById(R.id.activity_input_background).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                //metoden som gjømmer keyboardet
                hideSoftKeyboard();
                return true;
            }
        });

        numPrice = findViewById(R.id.numPrice);
        txtLocation = findViewById(R.id.txtLocation);
        txtDescription = findViewById(R.id.txtDescription);
        drpCategory = findViewById(R.id.drpCategory);
        txtDatePicker = findViewById(R.id.txtDatePicker);
        btnChangeDate = findViewById(R.id.btnChangeDate);

        myRef = FirebaseDatabase.getInstance().getReference(account.getId());

        //creates calendar object and gets todays date
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        //january is month nr 0, increments with one to correct
        month++;

        //puts todays date into string today and sets it in txtdatepicker
        String today = day + "/" + month + "/" + year;
        txtDatePicker.setText(today);

        //sets onclicklistener to button changedate
        btnChangeDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //gets todays date from calendar object
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                //creates datepickerdialog
                DatePickerDialog dialog = new DatePickerDialog(InputActivity.this, R.style.datepicker, dateDialog, year, month, day);
                dialog.show();
            }
        });

        //when date is picked, put into txtDatePicker
        dateDialog = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month++;
                date = dayOfMonth + "/" + month + "/" + year;
                txtDatePicker.setText(date);
            }
        };

        intentOverview = new Intent(this, overview.class);
        Button btnAdd = findViewById(R.id.btnRegister);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Expenses userExpense = new Expenses();

                String id = myRef.push().getKey();
                price = Integer.parseInt(numPrice.getText().toString());
                location = txtLocation.getText().toString();
                description = txtDescription.getText().toString();
                category = drpCategory.getSelectedItem().toString();


                userExpense.setCategory(category);
                userExpense.setDate(date);
                userExpense.setDescription(description);
                userExpense.setLocation(location);
                userExpense.setSum(price);

                Toast.makeText(InputActivity.this, date, Toast.LENGTH_SHORT).show();

//                myRef.child("Expenses").child(id).setValue(new Expenses(price, date, location, description, category));
                myRef.child("Expenses").child(id).setValue(userExpense);

                startActivity(intentOverview);
                finish();
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

    public void hideSoftKeyboard() {
        InputMethodManager inputMethodManager = (InputMethodManager)  this.getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), 0);
    }
}
