package no.hiof.fredrivo.budgetapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class InputActivity extends AppCompatActivity {

    private Intent intentOverview;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input);


        intentOverview = new Intent(this, overview.class);

        Button btnAdd = findViewById(R.id.btnRegister);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intentOverview);
            }
        });
    }
}
