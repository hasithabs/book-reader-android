package com.example.enters.book_reader.Helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;
import android.util.Log;

import com.example.enters.book_reader.book.Book;

import java.util.ArrayList;

/**
 * Created by EnTeRs on 3/25/2018.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String TAG = DatabaseHelper.class.getName();

    private static DatabaseHelper dbInstance;

    private static String DB_NAME = "book_reader.sqlite";
    private static String DB_PATH = "";
    private static String TABLE_NAME = "books";
    private static int BOOK_COLUMN_ID = 0;
    private static int BOOK_COLUMN_TITLE = 1;
    private static int BOOK_COLUMN_AUTHOR = 2;
    private static int BOOK_COLUMN_IMGPATH = 3;
    private static int BOOK_COLUMN_FILEPATH = 4;
    private static int BOOK_COLUMN_TYPE = 5;
    private static int BOOK_COLUMN_PAGECOUNT = 6;
    private static int BOOK_COLUMN_CURRENTPAGE = 7;
    private static int BOOK_COLUMN_LANGUAGE = 8;
    private static String TABLE_CREATE = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " ( " +
            "`id` INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE, " +
            "`title` TEXT NOT NULL, " +
            "`author` TEXT NOT NULL, " +
            "`imgPath` TEXT NOT NULL, " +
            "`filePath` TEXT NOT NULL, " +
            "`type` INTEGER, " +
            "`pageCount` INTEGER, " +
            "`currentPage` INTEGER," +
            "`language` TEXT NOT NULL " +
            ");";

    private SQLiteDatabase bookDatabase;
    private final Context bookDBContext;

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, 1);
        DB_PATH = Environment.getDataDirectory() + "/data/" + context.getPackageName() + "/databases/";
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

    public boolean checkDatabase() {
        SQLiteDatabase checkDB = null;
        String myPath = DB_PATH + DB_NAME;
        checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);
        if (checkDB != null) {
            checkDB.close();
        }
        return checkDB != null ? true : false;
    }

    @Override
    public synchronized void close() {
        if (bookDatabase != null) {
            bookDatabase.close();
        }
        super.close();
    }

    public Cursor QueryData(String query) {
        openDatabase();
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
        bookDatabase.execSQL("DROP TABLE IF EXISTS '" + TABLE_NAME + "'");
    }

    public int getDataCount() {
        Cursor cursor = QueryData("SELECT COUNT(*) FROM " + TABLE_NAME);
        cursor.moveToFirst();
        int count = cursor.getInt(0);
        cursor.close();
        return count;
    }

    public boolean insertBook (int _id, String _title, String _author, String _imgPath, String _filePath,
                               int _type, int _PageCount, int _currentPage, String _language) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("id", _id);
        contentValues.put("title", _title);
        contentValues.put("author", _author);
        contentValues.put("imgPath", _imgPath);
        contentValues.put("filePath", _filePath);
        contentValues.put("type", _type);
        contentValues.put("pageCount", _PageCount);
        contentValues.put("currentPage", _currentPage);
        contentValues.put("language", _language);
        bookDatabase.insert(TABLE_NAME, null, contentValues);
        return true;
    }

    public void insertData() {
        if (getDataCount() == 3) {
            Log.d(TAG, "Data exist in book table");
        } else {
            Log.d(TAG, "Adding data to book table");
            clearTable();
            insertBook(2, "Algorithms", "Jeff Edmonds", "cover/Algorithms.jpg",
                    "pdf/Algorithms.pdf", 1, 464, 0, "English");
            insertBook(3, "Certified Ethical Hacker", "Sean-Philip Oriyano", "cover/CertifiedEthicalHacker.jpg",
                    "pdf/CertifiedEthicalHacker.pdf", 0, 507, 20, "English");
            insertBook(4, "Game Engine Architecture", "Jason Gregory", "cover/GameArch.jpg",
                    "pdf/GameArch.pdf", 0, 853, 0, "English");
            insertBook(5, "How Successful People Think", "John C. Maxwell", "cover/HowSuccessful.jpg",
                    "pdf/HowSuccessful.pdf", 0, 80, 0, "English");
            insertBook(6, "How To Win Every Argument", "Madsen Pirie", "cover/WinEveryArgument.jpg",
                    "pdf/WinEveryArgument.pdf", 0, 196, 0, "English");
            insertBook(7, "Inorganic Chemistry", "James E. House", "cover/InorganicChemistry.jpg",
                    "pdf/InorganicChemistry.pdf", 0, 865, 0, "English");
            insertBook(8, "Power Up Your Mind", "Bill Lucas", "cover/PowerUpYourMind.jpg",
                    "pdf/PowerUpYourMind.pdf", 0, 273, 0, "English");
            insertBook(9, "Artificial Intelligence", "Russell Peter", "cover/PrenticeHall.jpg",
                    "pdf/PrenticeHall.pdf", 0, 1152, 0, "English");
            insertBook(10, "The 7 Habits of Effective People", "Stephen R. Covey", "cover/TheSevenHabits.jpg",
                    "pdf/TheSevenHabits.pdf", 0, 219, 0, "English");
            insertBook(11, "Wings of Fire", "A P J Abdul Kalam", "cover/WingsOfFire.jpg",
                    "pdf/WingsOfFire.pdf", 0, 219, 0, "English");
        }
    }

    public ArrayList<Book> getAllFavoriteBooks() {
        Cursor data = QueryData("SELECT * FROM " + TABLE_NAME + " WHERE type = 1");
        Log.d(TAG, "Getting all favorite books");

        ArrayList<Book> bookList = new ArrayList<>();

        while (data.moveToNext()) {
            bookList.add(new Book(data.getInt(BOOK_COLUMN_ID),
                    data.getString(BOOK_COLUMN_TITLE),
                    data.getString(BOOK_COLUMN_AUTHOR),
                    data.getString(BOOK_COLUMN_IMGPATH),
                    data.getString(BOOK_COLUMN_FILEPATH),
                    data.getInt(BOOK_COLUMN_TYPE),
                    data.getInt(BOOK_COLUMN_PAGECOUNT),
                    data.getInt(BOOK_COLUMN_CURRENTPAGE),
                    data.getString(BOOK_COLUMN_LANGUAGE)
            ));
        }
        close();

        return bookList;
    }

    public ArrayList<Book> getAllBooks() {
        Cursor data = QueryData("SELECT * FROM " + TABLE_NAME);
        Log.d(TAG, "Get all books");

        ArrayList<Book> bookList = new ArrayList<>();

        while (data.moveToNext()) {
            bookList.add(new Book(data.getInt(BOOK_COLUMN_ID),
                    data.getString(BOOK_COLUMN_TITLE),
                    data.getString(BOOK_COLUMN_AUTHOR),
                    data.getString(BOOK_COLUMN_IMGPATH),
                    data.getString(BOOK_COLUMN_FILEPATH),
                    data.getInt(BOOK_COLUMN_TYPE),
                    data.getInt(BOOK_COLUMN_PAGECOUNT),
                    data.getInt(BOOK_COLUMN_CURRENTPAGE),
                    data.getString(BOOK_COLUMN_LANGUAGE)
            ));
        }
        close();

        return bookList;
    }

    public void changeBookType(int id, int type) {
        Log.d(TAG, "Changing book type in table");
        QueryData("UPDATE " + TABLE_NAME + " SET type = " + type + " WHERE id = " + id).moveToFirst();
        Log.d(TAG, "Changed book type in table");
    }

    public boolean removeBook(int id) {
        Log.d(TAG, "Removing item from table");
        return bookDatabase.delete(TABLE_NAME, "id = " + id, null) > 0;
    }

    public void changeBookPage(int id, int currentPage) {
        Log.d(TAG, "Changing current page in table book");
        QueryData("UPDATE " + TABLE_NAME + " SET currentPage = " + currentPage + " WHERE id = " + id).moveToFirst();
    }
}
