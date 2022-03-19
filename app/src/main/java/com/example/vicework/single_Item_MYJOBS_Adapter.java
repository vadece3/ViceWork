package com.example.vicework;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class single_Item_MYJOBS_Adapter extends ArrayAdapter<Job_myjobs> {
    //storing all the names in the list
    private List<Job_myjobs> job_myjobs;

    //context object
    private Context context;

    TextView textViewjobname;
    TextView textViewstartdate;
    TextView textViewjobsearcher,textViewconfirm,textViewreject;

    //constructor
    public single_Item_MYJOBS_Adapter(Context context, int resource, List<Job_myjobs> job_myjobs) {
        super(context, resource, job_myjobs);
        this.context = context;
        this.job_myjobs = job_myjobs;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        //getting the layoutinflater
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        //getting listview items
        View listViewItem = inflater.inflate(R.layout.single_item_myjobs, null, true);
        textViewjobname = listViewItem.findViewById(R.id.textViewjobnameviewmyjob);
        textViewstartdate = listViewItem.findViewById(R.id.textViewstartdateviewmyjob);
        textViewjobsearcher = listViewItem.findViewById(R.id.textViewjobsearcherviewmyjob);

        TextView textViewposition = listViewItem.findViewById(R.id.textViewpositionmyjobs);

        //getting the current region
        Job_myjobs jobs = job_myjobs.get(position);

        //setting the region to textview
        textViewjobsearcher.setText(jobs.getJobsearcher());
        textViewjobname.setText(jobs.getJobname());
        textViewstartdate.setText(jobs.getStartdate());
        textViewposition.setText(jobs.getJobposition());
        textViewjobsearcher.setOnClickListener(v -> {

            Intent intent = new Intent(getContext(), ViewUserProfile.class);
            String username=textViewjobsearcher.getText().toString();
            String word = "JOB SEARCHER: ";
            String name = username.replaceAll(word, "");

            intent.putExtra("key", name);
            context.startActivity(intent);

        });

        textViewconfirm = listViewItem.findViewById(R.id.textViewconfirmmyjob);
        textViewconfirm.setOnClickListener(v -> {

            functionconfirm();

        });
        textViewreject=listViewItem.findViewById(R.id.textViewrejectmyapp);
        textViewreject.setOnClickListener(v -> {
            functionreject();

        });


        return listViewItem;
    }

    private void functionreject() {

        String confirm = "SORRY. YOU ARE NOT QUALIFIED FOR THE JOB";
        String jobsearcher = textViewjobsearcher.getText().toString();
        String jobname= textViewjobname.getText().toString();
        String startdate = textViewstartdate.getText().toString();

        StringRequest stringrequest = new StringRequest(Request.Method.POST, Constants.URL_ACCEPTENCE,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.i("tagconvertstr", "[" + response + "]");
                            JSONObject jsonObject = new JSONObject(response);
                            Toast.makeText(getContext(), jsonObject.getString("message"), Toast.LENGTH_SHORT).show();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getContext(), "SERVER NOT RESPONDING: no connection", Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("confirm", confirm);
                params.put("jobsearcher", jobsearcher);
                params.put("jobname", jobname);
                params.put("startdate", startdate);

                return params;

            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringrequest);



    }



    private void functionconfirm() {

        String confirm = "YOU ARE QUALIFIED FOR THE JOB. PLEASE CONTACT ME";
        String jobsearcher = textViewjobsearcher.getText().toString();
        String jobname= textViewjobname.getText().toString();
        String startdate = textViewstartdate.getText().toString();

        StringRequest stringrequest = new StringRequest(Request.Method.POST, Constants.URL_ACCEPTENCE,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.i("tagconvertstr", "[" + response + "]");
                            JSONObject jsonObject = new JSONObject(response);
                            Toast.makeText(getContext(), jsonObject.getString("message"), Toast.LENGTH_SHORT).show();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getContext(), "SERVER NOT RESPONDING: no connection", Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("confirm", confirm);
                params.put("jobsearcher", jobsearcher);
                params.put("jobname", jobname);
                params.put("startdate", startdate);

                return params;

            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringrequest);



    }

}
