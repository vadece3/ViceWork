package com.example.vicework;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SearchJob extends AppCompatActivity {
    Button btnhome,btnrefresh;
    ListView l;
    private List<Job> jobss;
    private single_item_Adapter jobAdapter;


    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_job);
        //btnhome=findViewById(R.id.btnhome);


        jobss = new ArrayList<>();
        l = findViewById(R.id.listviewTopJobs);

        Button btn2=(Button)findViewById(R.id.home);
        Button btn3=(Button)findViewById(R.id.account);

        btn2.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                Intent intent = new Intent(SearchJob.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        btn3.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                Intent intent = new Intent(SearchJob.this,Login.class);
                startActivity(intent);
                finish();
            }
        });

        /*btnhome.setOnClickListener(v -> {

            Intent intent = new Intent(this,MainActivity.class);
            startActivity(intent);

        });*/

        btnrefresh=findViewById(R.id.btnrefresh);
        btnrefresh.setOnClickListener(v -> {

            Intent intent = new Intent(this,SearchJob.class);
            startActivity(intent);
            finish();

        });

        getDataFromInternet();
    }

    private void getDataFromInternet() {

        StringRequest stringrequest = new StringRequest(Request.Method.POST, Constants.URL_GET_DATA,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.i("tagconvertstr", "[" + response + "]");
                            JSONArray jsonarray = new JSONArray(response);
                            ///set code to get job details
                            for (int i=0 ; i<jsonarray.length() ; i++){

                                JSONObject jsonObject = jsonarray.getJSONObject(i);

                                String jobname = jsonObject.getString("jobname");
                                String startdate = jsonObject.getString("startdate");
                                String jobprice = jsonObject.getString("jobprice");
                                String numberofpeople = jsonObject.getString("numberofpeople");
                                String jobgiver = jsonObject.getString("jobgiver");
                                String phonenumber = jsonObject.getString("phonenumber");
                                String amountper = jsonObject.getString("amountper");
                                String picture = jsonObject.getString("picture");
                                loadJobs(jobname,startdate,jobprice,numberofpeople,jobgiver,phonenumber,amountper,picture);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), "SERVER NOT RESPONDING: no connection", Toast.LENGTH_SHORT).show();
                        // Toast.makeText(getApplicationContext(),error.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("post", "post");

                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringrequest);
    }

    private void loadJobs(String a,String b,String c,String d,String e,String f,String g,String h) {
                    Job job = new Job(

                            a,
                            b,
                            c,
                            d,
                            e,
                            f,
                            g,
                            h

                    );
                    jobss.add(job);

        jobAdapter = new single_item_Adapter(this, R.layout.single_item, jobss);
        l.setAdapter(jobAdapter);

    }
    @Override
    public void onBackPressed()
    {

        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
        finish();

    }

    //////set a menu/////
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){

        switch (item.getItemId()){
            case R.id.myapplications:
                Toast.makeText(getApplicationContext(), "PLEASE LOGIN TO VIEW THIS", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.myjobs:
                Toast.makeText(getApplicationContext(), "PLEASE LOGIN TO VIEW THIS", Toast.LENGTH_SHORT).show();
                return true;

            case R.id.myprofile:
                Toast.makeText(getApplicationContext(), "PLEASE LOGIN TO VIEW THIS", Toast.LENGTH_SHORT).show();
                return true;



        }
        return super.onOptionsItemSelected(item);
    }


}