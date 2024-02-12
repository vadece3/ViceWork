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

import java.util.HashMap;
import java.util.Map;

public class TopMainActivity extends AppCompatActivity {

    Button btnaddjob,btnsearchjob,btnlogout;
    TextView tvgreatings;
    public static String name;
    public static String uname,uDateOfBirth,uuser,upassword,utown,uphone,uemail,upicture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_main);

        tvgreatings=findViewById(R.id.textViewTopMain);
        Intent in= getIntent();
        String name = in.getStringExtra("key");
        this.name= in.getStringExtra("key");
        ////////geting all user data
        getinguserdata(name);
        ////////


        tvgreatings.setText("Hi,  "+ name);

        btnaddjob = findViewById(R.id.btnTopAddJob);
        btnsearchjob = findViewById(R.id.btnTopSearchJob);
        btnlogout=findViewById(R.id.buttonLogOutMain);

        btnsearchjob.setOnClickListener(v -> {
            functionsearchjob();

        });

        btnaddjob.setOnClickListener(v -> {

            functionaddjob();

        });

        btnlogout.setOnClickListener(v -> {

            functionlogout();

        });

    }

    @Override
    public void onBackPressed()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("EXIT");
        builder.setMessage("Are you sure you want to LogOut and Exit?");

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
    private void functionlogout() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("LOGOUT");
        builder.setMessage("Are you sure you want to LogOut?");

        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {

                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
                name=null;
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

    }

    private void functionsearchjob() {
        Intent intent = new Intent(this,TopSearchJob.class);
        Intent in= getIntent();
        String name = in.getStringExtra("key");
        intent.putExtra("key",name);
        startActivity(intent);

    }

    private void functionaddjob() {
        Intent intent = new Intent(this,TopAddJob.class);
        Intent in= getIntent();
        String name = in.getStringExtra("key");
        intent.putExtra("key",name);
        startActivity(intent);
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
                return true;

            case R.id.myjobs:
                Intent imj = new Intent(this,MyJobs.class);
                Intent intentimj= getIntent();
                String nameimj = intentimj.getStringExtra("key");
                imj.putExtra("key",nameimj);
                startActivity(imj);
                return true;

            case R.id.myprofile:
                Intent imp = new Intent(this,UserProfile.class);
                Intent intentimp= getIntent();
                String nameimp = intentimp.getStringExtra("key");
                imp.putExtra("key",nameimp);
                startActivity(imp);
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