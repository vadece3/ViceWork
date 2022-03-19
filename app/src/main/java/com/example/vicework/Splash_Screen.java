package com.example.vicework;

import android.content.Intent;
import android.os.Handler;
import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.vicework.MainActivity;

public class Splash_Screen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash__screen);
        ActionBar bar = getSupportActionBar();
        if(bar != null) {
            bar.hide();
        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(Splash_Screen.this, MainActivity.class);
                startActivity(i);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                finish();
            }
        }, 2000);

    }
}