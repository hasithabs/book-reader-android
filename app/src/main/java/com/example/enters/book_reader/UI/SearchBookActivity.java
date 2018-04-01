package com.example.enters.book_reader.UI;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.enters.book_reader.Adapter.BookAdapter;
import com.example.enters.book_reader.Helper.DatabaseHelper;
import com.example.enters.book_reader.R;
import com.example.enters.book_reader.book.Book;

import java.util.ArrayList;

public class SearchBookActivity extends AppCompatActivity implements SearchView.OnQueryTextListener, View.OnClickListener {

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
        bookDB = DatabaseHelper.getDbInstance(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        bookList = bookDB.getAllBooks();

        if (bookList.size() == 0) {
            //Toast.makeText(FavoriteActivity.this, "You don't have favorite books", Toast.LENGTH_LONG);
        } else {
            bookAdapter = new BookAdapter(this, bookList);

            recyclerView = findViewById(R.id.SearchRecyclerView);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setAdapter(bookAdapter);

            searchView = findViewById(R.id.search_bar);
            searchView.setOnQueryTextListener(this);
        }


    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String text) {
        ArrayList<Book> searchBookList = new ArrayList<>();
        for (Book book : bookList){
            String bookTitle = book.getTitle().toLowerCase();
            if(bookTitle.contains(text)){
                searchBookList.add(book);
            }
        }
        bookAdapter.setFilter(searchBookList);
        return true;
    }

    @Override
    public void onClick(View view) {
    }
}
