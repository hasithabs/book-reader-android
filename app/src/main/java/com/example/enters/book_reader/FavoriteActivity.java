package com.example.enters.book_reader;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

public class FavoriteActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "FavoriteActivity";

    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;
    String[] title, author;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this, ReaderActivity.class);
        intent.putExtra("bookId", "06FgsmilUXAC");
        FavoriteActivity.this.startActivity(intent);
    }
}
