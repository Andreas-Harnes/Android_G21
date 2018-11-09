package no.hiof.fredrivo.budgetapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import no.hiof.fredrivo.budgetapp.InputActivity;
import no.hiof.fredrivo.budgetapp.R;
import no.hiof.fredrivo.budgetapp.classes.Categories;

public class NewCategoryActivity extends AppCompatActivity {

    private Intent intentAddCategory;
    private String newCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_category);

        intentAddCategory = new Intent (this,InputActivity.class);

        Button btnAddCat = findViewById(R.id.btnAddCat);
        btnAddCat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText txtNewCat = findViewById(R.id.txtNewCat);
                newCategory = txtNewCat.getText().toString();
                new Categories(newCategory);
                startActivity(intentAddCategory);
            }
        });
    }
}
