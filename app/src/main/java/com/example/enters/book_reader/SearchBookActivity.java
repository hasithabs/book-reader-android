package com.example.enters.book_reader;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.enters.book_reader.Adapter.BookAdapter;
import com.example.enters.book_reader.book.Book;

import java.util.ArrayList;
import java.util.List;

public class SearchBookActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    DatabaseHelper bookDB;
    RecyclerView recyclerView;
    BookAdapter bookAdapter;
    ArrayList<Book> bookList;
    SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_book);
        setTitle("Search Books");

        bookDB = new DatabaseHelper(this);

        bookList = new ArrayList<>();

        Cursor Books = bookDB.getAllBooks();

        if (Books.getCount() == 0) {
            //Toast.makeText(FavoriteActivity.this, "You don't have favorite books", Toast.LENGTH_LONG);
        } else {
            while (Books.moveToNext()) {
                bookList.add(new Book(Books.getInt(0),
                        Books.getString(1),
                        Books.getString(2),
                        Books.getString(3),
                        Books.getString(4)));
            }
        }

        bookAdapter = new BookAdapter(this, bookList);

        recyclerView = findViewById(R.id.SearchRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(bookAdapter);

        //searchView = findViewById(R.id.search_bar);
        //searchView.findViewById();
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String text) {
        text = text.toLowerCase();
        ArrayList<Book> searchBookList = new ArrayList<>();
        for (Book book : bookList){
            String bookTitle = book.getTitle().toLowerCase();
            if(bookTitle.contains(text)){
                searchBookList.add(book);
            }
        }

        bookAdapter = new BookAdapter(this, bookList);
        recyclerView = findViewById(R.id.SearchRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(bookAdapter);
        return true;
    }






}
