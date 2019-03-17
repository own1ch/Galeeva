package com.eremin.galeeva;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Database {
    public static final String LOG_TAG = "ads";
    public static final String DbName = "Dantists";
    public static final String NAME = "name";
    public static final String EMAIL = "email";
    public static final String NUMBER = "number";
    public static final String IN = "come_in";
    public static final String OUT = "come_out";

    private String lastIn;
    private String lastOut;

    private Context context;
    DBHelper dbHelper;
    SQLiteDatabase db;

    public Database(Context context) {
        this.context = context;
        dbHelper = new DBHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    public void updateLastInto(int id) {
        readDb(String.valueOf(id));
        ContentValues cv = new ContentValues();
        String time = getCurrentTimeUsingCalendar();
        if(lastIn == null) {
            cv.put(IN, time);
        } else cv.put(OUT, time);
        db.update(DbName, cv, "id = ?", new String[] {String.valueOf(id)});
    }

    public String getCurrentTimeUsingCalendar() {
        Calendar cal = Calendar.getInstance();
        Date date=cal.getTime();
        DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
        return dateFormat.format(date);
        //System.out.println("Current time of the day using Calendar - 24 hour format: "+ formattedDate);
    }

    public void insertIntoDatabase() {
        ContentValues cv = new ContentValues();
        //cv.put("id", 0);
        cv.put(NAME, "Еремин");
        cv.put(EMAIL, "xzi123mail.ru");
        cv.put(NUMBER, "89030891743");
        //db.insert(DbName, null, cv);
        /*db.execSQL(String.format("INSERT INTO %s (%s, %s, %s) VALUES(%s,%s,%s);",
                DbName,NAME,EMAIL,NUMBER, cv.get(NAME), cv.get(EMAIL), cv.get(NUMBER)));*/
        //readDb();
    }

    public void readDb(String id) {
        Cursor c;
        try {
            c = db.query(DbName, null, "id=?", new String[] {id}, null, null, null);
        } catch (SQLiteException e) {
            Toast.makeText(context, "Заполните базу!", Toast.LENGTH_LONG);
            return;
        }

        // ставим позицию курсора на первую строку выборки
        // если в выборке нет строк, вернется false
        if (c.moveToFirst()) {

            // определяем номера столбцов по имени в выборке
            int inColIndex = c.getColumnIndex("come_in");
            int outColIndex = c.getColumnIndex("come_out");

            do {
                lastIn = c.getString(inColIndex);
                lastOut = c.getString(outColIndex);
            } while (c.moveToNext());
        } else
            Log.d(LOG_TAG, "0 rows");
        c.close();
    }

    public void upradeTable() {
        dbHelper.onUpgrade(db, 0, 1);
    }

    static class DBHelper extends SQLiteOpenHelper {

        public DBHelper(Context context) {
            // конструктор суперкласса
            super(context, DbName, null, 1);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            Log.d(DbName, "--- onCreate database ---");
            // создаем таблицу с полями
            db.execSQL("create table Dantists ("
                    + "id integer primary key autoincrement,"
                    + "name text,"
                    + "email text,"
                    + "number text,"
                    + "income date,"
                    + "outcome date);");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("ALTER TABLE Dantists ADD COLUMN come_in text");
            db.execSQL("ALTER TABLE Dantists ADD COLUMN come_out text");
        }
    }
}
