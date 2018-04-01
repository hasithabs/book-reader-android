package com.example.enters.book_reader.UI;

import android.content.Intent;
import android.hardware.ConsumerIrManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.enters.book_reader.Helper.DatabaseHelper;
import com.example.enters.book_reader.R;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {

    DatabaseHelper bookDB;
    private CardView searchCard;
    private CardView favoriteCard;

    private int groupMenuId = 1;

    int createId = Menu.FIRST;
    int dropId = Menu.FIRST +1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        searchCard = (CardView) findViewById(R.id.cardViewSearch);
        favoriteCard =(CardView) findViewById(R.id.cardViewFavorite);
        searchCard.setOnClickListener(this);
        favoriteCard.setOnClickListener(this);
        bookDB = DatabaseHelper.getDbInstance(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if(id == R.id.cardViewSearch){
            Intent intent = new Intent(HomeActivity.this, SearchBookActivity.class);
            HomeActivity.this.startActivity(intent);
        }
        if(id == R.id.cardViewFavorite){
            Intent intent = new Intent(HomeActivity.this, FavoriteActivity.class);
            HomeActivity.this.startActivity(intent);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(groupMenuId, createId, createId, "Add Table");
        menu.add(groupMenuId, dropId, dropId, "Drop Table");

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case 1:
                bookDB.createTable();
                bookDB.insertData();
                Toast.makeText(this, "Create table & add data", Toast.LENGTH_LONG);
                return true;

            case 2:
                bookDB.dropTable();
                Toast.makeText(this, "Drop table", Toast.LENGTH_LONG);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
