package com.example.vicework;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    Button btnaddjob,btnsearchjob,btnlogin,btnregister;
    public static String greetings="Main";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActionBar bar = getSupportActionBar();
        if(bar != null) {
            bar.hide();
        }


        btnaddjob = findViewById(R.id.btnAddJob);
        btnsearchjob = findViewById(R.id.btnSearchJob);
        btnlogin=findViewById(R.id.btnlogin);
        btnregister=findViewById(R.id.register);

        btnsearchjob.setOnClickListener(v -> {
          functionsearchjob();

        });

        btnaddjob.setOnClickListener(v -> {

          functionaddjob();

        });

        btnlogin.setOnClickListener(v -> {

            functionlogin();

        });

        btnregister.setOnClickListener(v -> {

            functionregister();

        });

    }

    private void functionregister() {

        Intent intent = new Intent(this,Register.class);
        startActivity(intent);

    }

    @Override
    public void onBackPressed()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("EXIT");
        builder.setMessage("Are you sure you want to exit?");

        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {

                moveTaskToBack(true);
                android.os.Process.killProcess(android.os.Process.myPid());
                System.exit(1);
            }
        });

        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                // Do nothing
                dialog.dismiss();
            }
        });

        AlertDialog alert = builder.create();
        alert.show();


    }
    private void functionlogin() {
        Intent intent = new Intent(this,Login.class);
        startActivity(intent);

    }

    private void functionsearchjob() {
        Intent intent = new Intent(this,SearchJob.class);
        startActivity(intent);

    }

    private void functionaddjob() {
        Intent intent = new Intent(this,AddJob.class);
        startActivity(intent);
    }




}