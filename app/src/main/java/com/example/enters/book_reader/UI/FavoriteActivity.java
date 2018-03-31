package com.example.enters.book_reader.UI;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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

    ArrayList<Book> bookList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);
        setTitle("Favorite Books");

        bookDB = new DatabaseHelper(this);
        populateListView();
    }

    public void populateListView() {
        bookList = new ArrayList<>();

        Cursor favoriteBooks = bookDB.getAllFavoriteBooks();
        System.out.println(favoriteBooks.getCount());
        System.out.println("--------------------------");

        if (favoriteBooks.getCount() == 0) {
            Toast.makeText(FavoriteActivity.this, "You don't have favorite books", Toast.LENGTH_LONG);
        } else {
            while (favoriteBooks.moveToNext()) {
                bookList.add(new Book(favoriteBooks.getInt(0),
                        favoriteBooks.getString(1),
                        favoriteBooks.getString(2),
                        favoriteBooks.getString(3),
                        favoriteBooks.getString(4),
                        favoriteBooks.getInt(5)));

                bookAdapter = new BookAdapter(this, bookList);

                recyclerView = findViewById(R.id.FavRecyclerView);
                recyclerView.setHasFixedSize(true);
                recyclerView.setLayoutManager(new LinearLayoutManager(this));
                recyclerView.setAdapter(bookAdapter);
            }
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(this, ReaderActivity.class);
        intent.putExtra("bookId", bookList.get(position).getId());
        intent.putExtra("bookPath", bookList.get(position).getFilePath());
        FavoriteActivity.this.startActivity(intent);
    }
}
