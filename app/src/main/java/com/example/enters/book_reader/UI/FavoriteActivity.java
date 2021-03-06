package com.example.enters.book_reader.UI;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.example.enters.book_reader.Adapter.BookAdapter;
import com.example.enters.book_reader.Helper.DatabaseHelper;
import com.example.enters.book_reader.R;
import com.example.enters.book_reader.book.Book;

import java.util.ArrayList;

public class FavoriteActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private static final String TAG = "FavoriteActivity";

    DatabaseHelper bookDB;

    RecyclerView recyclerView;
    BookAdapter bookAdapter;

    ArrayList<Book> favoriteBooks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);
        setTitle("Favorite Books");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        bookDB = DatabaseHelper.getDbInstance(this);
        populateListView();
    }

    public void populateListView() {
        favoriteBooks = bookDB.getAllFavoriteBooks();

        if (favoriteBooks.size() == 0) {
            Toast msg1 = Toast.makeText(this, "You don't have favorite books", Toast.LENGTH_LONG);
            msg1.show();
        } else {
            bookAdapter = new BookAdapter(this, favoriteBooks);

            recyclerView = findViewById(R.id.FavRecyclerView);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setAdapter(bookAdapter);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        bookAdapter.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                int bookId = data.getExtras().getInt("bookId");

                for(Book b : favoriteBooks){
                    if(b.getId() == bookId) {
                        int _index = favoriteBooks.indexOf(b);
                        favoriteBooks.get(_index).setCurrentPage(data.getExtras().getInt("currentPage"));
                        Log.d(TAG, "Setting current page to book");
                        bookAdapter.notifyDataSetChanged();
                    }
                }
            }
        }
    }

}
