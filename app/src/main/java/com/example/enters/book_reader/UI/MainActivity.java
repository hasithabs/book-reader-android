package com.example.enters.book_reader.UI;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.enters.book_reader.R;

public class MainActivity extends AppCompatActivity {

    private static int SPLASH_TIME_OUT = 4000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent homeIntend = new Intent(MainActivity.this, HomeActivity.class);
                startActivity(homeIntend);
                finish();
            }
        },SPLASH_TIME_OUT);
    }
}
