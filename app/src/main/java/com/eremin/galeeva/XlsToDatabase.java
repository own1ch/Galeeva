package com.eremin.galeeva;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.widget.Toast;

import com.ajts.androidmads.library.ExcelToSQLite;

import java.io.File;

public class XlsToDatabase {

    Context context;

    public XlsToDatabase(Context context) {
        this.context = context;
    }

    public void fromXlsToDatabase() {
        String directory = Environment.getExternalStorageDirectory().getPath() + "/input/users.xls";
        //String directory = "/storage/emulated/legacy/input/";
        File file = new File(directory);
        if (!file.exists()) {
            Toast.makeText(context, "Файл не найден!", Toast.LENGTH_SHORT).show();
            return;
        }
        Database database = new Database(context);
        Database.DBHelper dbHelper = new Database.DBHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        //db.beginTransaction();
        // Is used to import data from excel without dropping table
        // ExcelToSQLite excelToSQLite = new ExcelToSQLite(getApplicationContext(), DBHelper.DB_NAME);

        // if you want to add column in excel and import into DB, you must drop the table
        ExcelToSQLite excelToSQLite = new ExcelToSQLite(context, Database.DbName, true);
        // Import EXCEL FILE to SQLite
        excelToSQLite.importFromFile(directory, new ExcelToSQLite.ImportListener() {
            @Override
            public void onStart() {

            }

            @Override
            public void onCompleted(String dbName) {
                Toast.makeText(context, "Excel imported into " + Database.DbName, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onError(Exception e) {
                Toast.makeText(context, "Error : " + e.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
        updateDatabase();
        //db.endTransaction();
    }

    private void updateDatabase() {
        Database database = new Database(context);
        database.upgradeTable();
    }
}
