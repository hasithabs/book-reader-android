package com.example.enters.book_reader.Helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;
import android.util.Log;

/**
 * Created by EnTeRs on 3/25/2018.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String TAG = DatabaseHelper.class.getName();

    private static DatabaseHelper dbInstance;

    private static String DB_NAME = "book_reader.sqlite";
    private static String DB_PATH = "";
    private static String TABLE_NAME = "books";
    private static String TABLE_CREATE = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " ( " +
            "`id` INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE, " +
            "`title` TEXT NOT NULL, " +
            "`author` TEXT NOT NULL, " +
            "`imgPath` TEXT NOT NULL, " +
            "`filePath` TEXT NOT NULL, " +
            "`type` INTEGER " +
            ");";

    private SQLiteDatabase bookDatabase;
    private final Context bookDBContext;

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, 1);
//        if (Build.VERSION.SDK_INT >= 15) {
//            DB_PATH = context.getApplicationInfo().dataDir = "/databases/";
//        } else {
            DB_PATH = Environment.getDataDirectory() + "/data/" + context.getPackageName() + "/databases/";
//        }
        getWritableDatabase();
        openDatabase();
        createTable();
        insertData();
        this.bookDBContext = context;
    }

    public static DatabaseHelper getDbInstance(Context context) {
        if (dbInstance == null) {
            dbInstance = new DatabaseHelper(context.getApplicationContext());
        }
        return dbInstance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d(TAG, "Upgrading database from version " + oldVersion + " to " + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void openDatabase() {
        String myPath = DB_PATH + DB_NAME;
        bookDatabase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);
    }

//    public void checkAndCopyDatabase() {
//        boolean dbExist = checkDatabase();
//        if (dbExist) {
//            Log.d(TAG, "Database is already exist");
//        } else {
//            this.getReadableDatabase();
//        }
//
//        try {
//            copyDatabase();
//        } catch (IOException ex) {
//            ex.printStackTrace();
//            Log.e(TAG, "Error copy database");
//        }
//    }

    public boolean checkDatabase() {
        SQLiteDatabase checkDB = null;
        String myPath = DB_PATH + DB_NAME;
        checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);
        if (checkDB != null) {
            checkDB.close();
        }
        return checkDB != null ? true : false;
    }
//
//    public void copyDatabase() throws IOException {
//        InputStream dbInput = bookDBContext.getAssets().open(DB_NAME);
//        String saveFileName = DB_PATH + DB_NAME;
//        OutputStream dbOutput = new FileOutputStream(saveFileName);
//        byte[] buffer = new byte[1024];
//        int dataLen;
//        while ((dataLen = dbInput.read(buffer)) > 0) {
//            dbOutput.write(buffer, 0, dataLen);
//        }
//        dbOutput.flush();
//        dbOutput.close();
//        dbInput.close();
//    }

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

    public void createTable() {
        if (checkDatabase() && checkTableExist()) {
            Log.d(TAG, "Create book table");
            bookDatabase.execSQL(TABLE_CREATE);
        } else {
            Log.d(TAG, "Book table exists");
        }
    }

    public boolean checkTableExist() {
        Cursor cursor = QueryData("SELECT name FROM sqlite_master WHERE type='table' AND name='" + TABLE_NAME + "'");
        cursor.moveToFirst();
        int count = cursor.getCount();
        cursor.close();
        return count == 0;
    }

    public void clearTable() {
        bookDatabase.execSQL("delete from " + TABLE_NAME);
    }

    public void dropTable() {
        bookDatabase.execSQL("DROP TABLE IF EXISTS" + TABLE_NAME, null);
    }

    public int getDataCount() {
        Cursor cursor = QueryData("SELECT COUNT(*) FROM " + TABLE_NAME);
        cursor.moveToFirst();
        int count = cursor.getInt(0);
        cursor.close();
        return count;
    }

    public boolean insertBook (int _id, String _title, String _author, String _imgPath, String _filePath, int _type) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("id", _id);
        contentValues.put("title", _title);
        contentValues.put("author", _author);
        contentValues.put("imgPath", _imgPath);
        contentValues.put("filePath", _filePath);
        contentValues.put("type", _type);
        bookDatabase.insert(TABLE_NAME, null, contentValues);
        return true;
    }

    public void insertData() {
        if (getDataCount() == 3) {
            Log.d(TAG, "Data exist in book table");
        } else {
            Log.d(TAG, "Adding data to book table");
            clearTable();
            insertBook(1, "Sample data", "Authorrr", "broke_img.jpg", "aassdd.pdf", 1);
            insertBook(2, "Book text 3", "Authorrsdg sdg s", "broke_img.jpg", "aassdd.pdf", 1);
            insertBook(3, "Book tesdg s", "Authsdg  gsdg sdg s", "broke_img.jpg", "aassdd.pdf", 0);
        }
    }

    public Cursor getAllFavoriteBooks() {
        Cursor data = QueryData("SELECT * FROM " + TABLE_NAME + " WHERE type = 1");
        Log.d(TAG, "Get all favorite books");
        return data;
    }

    public Cursor getAllBooks() {
        Cursor data = QueryData("SELECT * FROM " + TABLE_NAME );
        Log.d(TAG, "Get all books");
        return data;
    }

    public void changeBookType(int id, int type) {
        QueryData("UPDATE " + TABLE_NAME + " SET type = " + type + " WHERE id = " + id).moveToFirst();
        Log.d(TAG, "Change book type in table");
    }

    public boolean removeBook(int id) {
        Log.d(TAG, "Removing item from table");
        return bookDatabase.delete(TABLE_NAME, "id = " + id, null) > 0;
    }
}
