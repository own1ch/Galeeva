package com.eremin.galeeva;

import android.content.Context;
import android.os.Environment;
import android.widget.Toast;

import com.ajts.androidmads.library.SQLiteToExcel;

import java.io.File;

public class DatabaseToXls {
    Context context;

    public DatabaseToXls(Context context) {
        this.context = context;
    }

    public void fromDataBaseToXls() {
        String directory = Environment.getExternalStorageDirectory().getPath() + "/output/";
        File file = new File(directory);
        if (!file.exists()) {
            file.mkdirs();
        }
        SQLiteToExcel sqliteToExcel = new SQLiteToExcel(context, Database.DbName, directory);

        sqliteToExcel.exportAllTables("users.xls", new SQLiteToExcel.ExportListener() {
            @Override
            public void onStart() {

            }

            @Override
            public void onCompleted(String filePath) {
                Toast.makeText(context, "Успешно выгружено в файл!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(Exception e) {

            }
        });
    }
}
