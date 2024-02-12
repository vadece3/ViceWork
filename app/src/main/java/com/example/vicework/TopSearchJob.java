package com.example.vicework;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
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

public class TopSearchJob extends AppCompatActivity {

    Button btnhome,btnrefresh;
    ListView l;
    private List<Job> jobss;
    private single_item_Adapter jobAdapter;
   // TextView tvgreatings;
    Button lout;
    public static String uname,uDateOfBirth,uuser,upassword,utown,uphone,uemail,upicture;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_search_job);



        //btnhome=findViewById(R.id.btnTophome);

       // tvgreatings=findViewById(R.id.textViewTopSeartchJob);
        Intent in= getIntent();
        String name = in.getStringExtra("key");
        ////////geting all user data
        getinguserdata(name);
        ////////
       // tvgreatings.setText("Hi,  "+ name);

        lout=findViewById(R.id.LOGOUTSearchJob);
        lout.setOnClickListener(v -> {

            AlertDialog.Builder builder = new AlertDialog.Builder(this);

            builder.setTitle("LOGOUT");
            builder.setMessage("Are you sure you want to LogOut?");

            builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

                public void onClick(DialogInterface dialog, int which) {

                    Intent i = new Intent(getApplicationContext(),MainActivity.class);
                    startActivity(i);
                    finish();
                    TopMainActivity tma = new TopMainActivity();
                    tma.name=null;
                    uname=null;
                    uDateOfBirth=null;
                    uuser=null;
                    upassword=null;
                    utown=null;
                    uphone=null;
                    uemail=null;
                    upicture=null;

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

        });

        jobss = new ArrayList<>();
        l = findViewById(R.id.listviewTopJobs);

        /*btnhome.setOnClickListener(v -> {

            Intent intent = new Intent(this,TopMainActivity.class);
            intent.putExtra("key",name);
            intent.putExtra("uname",uname);
            intent.putExtra("uDateOfBirth",uDateOfBirth);
            intent.putExtra("uuser",uuser);
            intent.putExtra("upassword",upassword);
            intent.putExtra("utown",utown);
            intent.putExtra("uphone",uphone);
            intent.putExtra("uemail",uemail);
            intent.putExtra("upicture",upicture);
            startActivity(intent);

        });*/

        btnrefresh=findViewById(R.id.btnToprefresh);
        btnrefresh.setOnClickListener(v -> {

            Intent intent = new Intent(this,TopSearchJob.class);
           // intent.putExtra("key",name);//
            intent.putExtra("uname",uname);
            intent.putExtra("uDateOfBirth",uDateOfBirth);
            intent.putExtra("uuser",uuser);
            intent.putExtra("upassword",upassword);
            intent.putExtra("utown",utown);
            intent.putExtra("uphone",uphone);
            intent.putExtra("uemail",uemail);
            intent.putExtra("upicture",upicture);
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

        Intent intent = new Intent(this,TopMainActivity.class);
        intent.putExtra("key",uuser);
        intent.putExtra("uname",uname);
        intent.putExtra("uDateOfBirth",uDateOfBirth);
        intent.putExtra("uuser",uuser);
        intent.putExtra("upassword",upassword);
        intent.putExtra("utown",utown);
        intent.putExtra("uphone",uphone);
        intent.putExtra("uemail",uemail);
        intent.putExtra("upicture",upicture);
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
                Intent ima = new Intent(this,MyApplications.class);
                Intent intentima= getIntent();
                String nameima = intentima.getStringExtra("key");
                ima.putExtra("key",nameima);
                startActivity(ima);
                finish();
                return true;

            case R.id.myjobs:
                Intent imj = new Intent(this,MyJobs.class);
                Intent intentimj= getIntent();
                String nameimj = intentimj.getStringExtra("key");
                imj.putExtra("key",nameimj);
                startActivity(imj);
                finish();
                return true;

            case R.id.myprofile:
                Intent imp = new Intent(this,UserProfile.class);
                Intent intentimp= getIntent();
                String nameimp = intentimp.getStringExtra("key");
                imp.putExtra("key",nameimp);
                imp.putExtra("uname",uname);
                imp.putExtra("uDateOfBirth",uDateOfBirth);
                imp.putExtra("uuser",uuser);
                imp.putExtra("upassword",upassword);
                imp.putExtra("utown",utown);
                imp.putExtra("uphone",uphone);
                imp.putExtra("uemail",uemail);
                imp.putExtra("upicture",upicture);
                startActivity(imp);
                finish();
                return true;


        }
        return super.onOptionsItemSelected(item);
    }

    private void getinguserdata(String username) {

        StringRequest stringrequest = new StringRequest(Request.Method.POST, Constants.URL_GET_DATA_USER,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.i("tagconvertstr", "[" + response + "]");
                            JSONArray jsonarray = new JSONArray(response);
                            ///set code to get job details
                            for (int i=0 ; i<jsonarray.length() ; i++){

                                JSONObject jsonObject = jsonarray.getJSONObject(i);

                                uname = jsonObject.getString("name");
                                uDateOfBirth = jsonObject.getString("DateOfBirth");
                                uuser = jsonObject.getString("user");
                                upassword = jsonObject.getString("password");
                                utown = jsonObject.getString("town");
                                uphone = jsonObject.getString("phone");
                                uemail = jsonObject.getString("email");
                                upicture = jsonObject.getString("picture");
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
                params.put("username", username);

                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringrequest);

    }


}