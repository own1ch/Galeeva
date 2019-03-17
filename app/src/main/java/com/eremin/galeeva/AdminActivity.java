package com.eremin.galeeva;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AdminActivity extends AppCompatActivity {

    Button fromDatabaseToXls;
    Button fromXlsToDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        fromDatabaseToXls = findViewById(R.id.fromDataBaseToXls);
        fromXlsToDatabase = findViewById(R.id.fromXlsToDataBase);
        init();
    }

    private void init() {
        initDatabaseToXls();
        initXlsToDatabase();
    }

    private void initXlsToDatabase() {
        fromXlsToDatabase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                XlsToDatabase xlsToDatabase = new XlsToDatabase(getApplicationContext());
                xlsToDatabase.fromXlsToDatabase();
            }
        });
    }

    private void initDatabaseToXls() {
        fromDatabaseToXls.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //database.insertIntoDatabase();
                DatabaseToXls databaseToXls = new DatabaseToXls(getApplicationContext());
                databaseToXls.fromDataBaseToXls();
            }
        });
    }
}
