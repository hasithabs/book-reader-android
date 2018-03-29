package com.example.enters.book_reader;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
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
        getMenuInflater().inflate(R.menu.toolbarmenu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id==R.id.search_bID){
            Intent intent = new Intent(this, SearchBookActivity.class);
            HomeActivity.this.startActivity(intent);
            return true;
        }
        if(id==R.id.favorite_bID){
            Intent intent = new Intent(this, FavoriteActivity.class);
            HomeActivity.this.startActivity(intent);
            return true;
        }

        if(id==R.id.menu_bID){
            Intent intent = new Intent(this, HomeActivity.class);
            HomeActivity.this.startActivity(intent);
            return true;
        }
        return true;
    }
}
