package com.example.vicework;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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

public class MyApplications extends AppCompatActivity {

    ListView l;
    private List<Job_myapplications> jobss;
    private single_Item_MYAPPLICATIONS_Adapter jobAdapter;
    public static String jobsearcher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_applications);

        jobss = new ArrayList<>();
        l = findViewById(R.id.listviewmyapplications);

        getDataFromInternet();

        Intent in= getIntent();
        jobsearcher = "JOB SEARCHER: "+in.getStringExtra("key");
    }

    private void getDataFromInternet() {
        StringRequest stringrequest = new StringRequest(Request.Method.POST, Constants.URL_GET_MYAPPLICATIONS_DATA,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.i("tagconvertstr", "[" + response + "]");
                            JSONArray jsonarray = new JSONArray(response);
                            ///set code to get job details
                            int tempposition=1;
                            for (int i=0 ; i<jsonarray.length() ; i++){

                                JSONObject jsonObject = jsonarray.getJSONObject(i);

                                String jobname = jsonObject.getString("jobname");
                                String startdate = jsonObject.getString("startdate");
                                String jobsearcher = jsonObject.getString("jobsearcher");
                                String jobgiver = jsonObject.getString("jobgiver");
                                String acceptence_status = jsonObject.getString("acceptence_status");
                                String position = String.valueOf(tempposition);
                                tempposition++;

                                loadJobs(jobgiver,jobname,startdate,jobsearcher,position,acceptence_status);
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
                params.put("jobsearcher", jobsearcher);

                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringrequest);

    }

    private void loadJobs(String a,String b,String c,String d,String e,String f) {
        Job_myapplications job_myapplications = new Job_myapplications(

                a,
                b,
                c,
                d,
                e,
                f

        );
        jobss.add(job_myapplications);

        jobAdapter = new single_Item_MYAPPLICATIONS_Adapter(this, R.layout.single_item_myapplications, jobss);
        l.setAdapter(jobAdapter);

    }
}