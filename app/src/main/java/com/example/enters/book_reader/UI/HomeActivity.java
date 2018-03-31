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

import com.example.enters.book_reader.R;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {

    private CardView searchCard;
    private CardView favoriteCard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        searchCard = (CardView) findViewById(R.id.cardViewSearch);
        favoriteCard =(CardView) findViewById(R.id.cardViewFavorite);
        searchCard.setOnClickListener(this);
        favoriteCard.setOnClickListener(this);
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

}
