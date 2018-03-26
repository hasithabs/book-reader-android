package com.example.enters.book_reader;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.os.Environment;
import android.util.Log;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by EnTeRs on 3/25/2018.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String TAG = DatabaseHelper.class.getName();

    private static String DB_NAME = "book_reader.sqlite";
    private static String DB_PATH = "";
    private SQLiteDatabase bookDatabase;
    private final Context bookDBContext;

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, 1);
        if (Build.VERSION.SDK_INT >= 15) {
            DB_PATH = context.getApplicationInfo().dataDir = "/databases/";
        } else {
            DB_PATH = Environment.getDataDirectory() + "/data/" + context.getPackageName() + "/databases/";
        }
        this.bookDBContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void checkAndCopyDatabase() {
        boolean dbExist = checkDatabase();
        if (dbExist) {
            Log.d(TAG, "Database is already exist");
        } else {
            this.getReadableDatabase();
        }

        try {
            copyDatabase();
        } catch (IOException ex) {
            ex.printStackTrace();
            Log.e(TAG, "Error copy database");
        }
    }

    public boolean checkDatabase() {
        SQLiteDatabase checkDB = null;
        String myPath = DB_PATH + DB_NAME;
        checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);
        if (checkDB != null) {
            checkDB.close();
        }
        return checkDB != null ? true : false;
    }

    public void copyDatabase() throws IOException {
        InputStream dbInput = bookDBContext.getAssets().open(DB_NAME);
        String saveFileName = DB_PATH + DB_NAME;
        OutputStream dbOutput = new FileOutputStream(saveFileName);
        byte[] buffer = new byte[1024];
        int dataLen;
        while ((dataLen = dbInput.read(buffer)) > 0) {
            dbOutput.write(buffer, 0, dataLen);
        }
        dbOutput.flush();
        dbOutput.close();
        dbInput.close();
    }

    @Override
    public synchronized void close() {
        if (bookDatabase != null) {
            bookDatabase.close();
        }
        super.close();
    }

    public Cursor QueryData(String query) {
        return bookDatabase.rawQuery(query, null);
    }
}
