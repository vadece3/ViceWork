package com.example.vicework;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class single_item_Adapter extends ArrayAdapter<Job> {
    //storing all the names in the list
    private List<Job> job;

    //context object
    private Context context;

    //constructor
    public single_item_Adapter(Context context, int resource, List<Job> job) {
        super(context, resource, job);
        this.context = context;
        this.job = job;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

    //getting the layoutinflater
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        //getting listview items
        View listViewItem = inflater.inflate(R.layout.single_item, null, true);
        TextView textViewjobname = listViewItem.findViewById(R.id.textViewjobname);
        TextView textViewstartdate = listViewItem.findViewById(R.id.textViewstartdate);
        TextView textViewprice = listViewItem.findViewById(R.id.textViewprice);
        TextView textViewamountper = listViewItem.findViewById(R.id.textViewamountper);
        TextView textViewnumberofpeople = listViewItem.findViewById(R.id.textViewnumberofpeople);
        TextView textViewjobgiver = listViewItem.findViewById(R.id.textViewjobgiver);
        TextView textViewphonenumber = listViewItem.findViewById(R.id.textViewphonenumber);

        ///////apply for a job//////////
        Button btnapplyy=listViewItem.findViewById(R.id.btnapply);
        btnapplyy.setOnClickListener(v -> {

            Job jobs = job.get(position);
            String jobname=jobs.getJobname();
            String startdate=jobs.getStartdate();
            String jobgiver=jobs.getJobgiver();

           //geting user name
            TopMainActivity tma = new TopMainActivity();
            String name= "JOB SEARCHER: "+tma.name;
        if(tma.name!=null) {
            StringRequest stringrequest = new StringRequest(Request.Method.POST, Constants.URL_APPLY,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                Log.i("tagconvertstr", "[" + response + "]");
                                JSONObject jsonObject = new JSONObject(response);
                                Toast.makeText(getContext(), jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                                //if(jsonObject.getString("message").equals("SEND:SUCCESS")){


                                //}

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
                    params.put("jobname", jobname);
                    params.put("startdate", startdate);
                    params.put("jobgiver", jobgiver);
                    params.put("name", name);

                    return params;

                }
            };

            RequestQueue requestQueue = Volley.newRequestQueue(getContext());
            requestQueue.add(stringrequest);


        }
        else{

            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

            builder.setTitle("CONDITION");
            builder.setMessage("TO APPLY YOU HAVE TO LOGIN.. DO YOU WANT TO LOGIN?");

            builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

                public void onClick(DialogInterface dialog, int which) {

                    Intent intent= new Intent(getContext(),Login.class);
                    context.startActivity(intent);
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
        });



        //getting the current region
        Job jobs = job.get(position);

        //setting the region to textview
        textViewamountper.setText(jobs.getAmountper());
        textViewjobgiver.setText(jobs.getJobgiver());
        textViewjobname.setText(jobs.getJobname());
        textViewnumberofpeople.setText(jobs.getNumberofpeople());
        textViewphonenumber.setText(jobs.getPhonenumber());
        textViewprice.setText(jobs.getJobprice());
        textViewstartdate.setText(jobs.getStartdate());

        ImageView imageView = listViewItem.findViewById(R.id.imageViewuserimagesingleview);
        imageView.setImageBitmap(Convertbitmaptoimage(jobs.getPicture()));


        LinearLayout layout = listViewItem.findViewById(R.id.viewprofilesingleactivity);
        layout.setOnClickListener(v -> {

            //geting user name
            TopMainActivity tma = new TopMainActivity();
            String name= "JOB SEARCHER: "+tma.name;
            if(tma.name!=null) {
                if(jobs.getJobgiver().equals("JOB GIVER NAME: ANONYMOS")){
                    Toast.makeText(getContext(), "THIS USER IS ANONYMOS", Toast.LENGTH_SHORT).show();

                }
                else {
                    Intent intent = new Intent(getContext(), ViewUserProfile.class);
                    String username=textViewjobgiver.getText().toString();
                    ///geting just the username
                    String word = "JOB GIVER NAME: ";
                    String names = username.replaceAll(word, "");
                    intent.putExtra("key", names);
                    context.startActivity(intent);
                }
            }
            else{

                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

                builder.setTitle("CONDITION");
                builder.setMessage("TO VIEW THIS PROFILE YOU NEED TO LOGIN. DO YOU WANT TO LOGIN?");

                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {

                        Intent intent= new Intent(getContext(),Login.class);
                        context.startActivity(intent);

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

        });


        return listViewItem;
    }

    public Bitmap Convertbitmaptoimage(String bitmaptext){

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        byte[] byteFormat = stream.toByteArray();
        byteFormat = Base64.decode(bitmaptext, Base64.NO_WRAP);
        Bitmap decodedImage = BitmapFactory.decodeByteArray(byteFormat, 0, byteFormat.length);
       return decodedImage;


    }

}
