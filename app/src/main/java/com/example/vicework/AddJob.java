package com.example.vicework;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
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
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class AddJob extends AppCompatActivity {

    EditText a,b,c,d,e,f;
    Spinner g;
    Button h;
    ImageView imvdatep;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_job);

        a=findViewById(R.id.editTextTopJobName);
        b=findViewById(R.id.editTextTopStartDate);
        c=findViewById(R.id.editTextTopNumberJobPrice);
        d=findViewById(R.id.editTextTopNumberPeopleNeeded);
        //e=findViewById(R.id.editTextTopJobGiver);
        f=findViewById(R.id.editTextTopNumberPhone);
        g=findViewById(R.id.TopamountPer);

        String[] semester = {"SELECT_PAYMENT_RANGE","per_hour","per_day",
                "per_month","per_year"};
        ArrayAdapter aass = new ArrayAdapter(this,android.R.layout.simple_spinner_item,semester);
        aass.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        g.setAdapter(aass);

        h=findViewById(R.id.btnTopAdd);
        h.setOnClickListener(v -> {
            if (a.getText().toString().trim().equalsIgnoreCase(""))
                Snackbar.make(v, " PLEASE FILL ALL THE FIELDS ", Snackbar.LENGTH_LONG).show();
            else if (b.getText().toString().trim().equalsIgnoreCase(""))
                Snackbar.make(v, " PLEASE FILL ALL THE FIELDS ", Snackbar.LENGTH_LONG).show();
            else if (c.getText().toString().trim().equalsIgnoreCase(""))
                Snackbar.make(v, " PLEASE FILL ALL THE FIELDS ", Snackbar.LENGTH_LONG).show();
            else if (c.getText().toString().trim().equalsIgnoreCase(""))
                Snackbar.make(v, " PLEASE FILL ALL THE FIELDS ", Snackbar.LENGTH_LONG).show();
            //else if (e.getText().toString().trim().equalsIgnoreCase(""))
                //Snackbar.make(v, " PLEASE FILL ALL THE FIELDS ", Snackbar.LENGTH_LONG).show();
            else if (f.getText().toString().trim().equalsIgnoreCase(""))
                Snackbar.make(v, " PLEASE FILL ALL THE FIELDS ", Snackbar.LENGTH_LONG).show();
            else if (g.getSelectedItem().toString().trim().equalsIgnoreCase("SELECT_PAYMENT_RANGE"))
                Snackbar.make(v, " PLEASE SELECT_PAYMENT_RANGE ", Snackbar.LENGTH_LONG).show();
            else {

                functionAdd();
            }
        });

        imvdatep = findViewById(R.id.imageViewTopdatepicker);
        imvdatep.setOnClickListener(v -> {

            Calendar cal = Calendar.getInstance();
            int mDate = cal.get(Calendar.DATE);
            int mMonth = cal.get(Calendar.MONTH);
            int mYear = cal.get(Calendar.YEAR);
            DatePickerDialog datePickerDialog = new DatePickerDialog(this, android.R.style.Theme_DeviceDefault_Dialog, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker datePicker, int year, int month, int date) {
                    b.setText(date+"/"+month+"/"+year);
                }
            },mYear,mMonth,mDate);
            datePickerDialog.show();

        });
    }


    private void functionAdd() {
        String aa = "JOB: "+a.getText().toString();
        String bb = "START DATE: "+b.getText().toString();
        String cc = "AMOUNT: "+c.getText().toString()+"FCFA";
        String dd = "People Needed: "+d.getText().toString();
        String ee = "JOB GIVER NAME: ANONYMOS";
        String ff = "PHONE NUMBER: "+f.getText().toString();
        String gg = g.getSelectedItem().toString();
        String hh = loadDefaultImage(this);

        StringRequest stringrequest = new StringRequest(Request.Method.POST, Constants.URL_ADD_DATA,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.i("tagconvertstr", "[" + response + "]");
                            JSONObject jsonObject = new JSONObject(response);
                            Toast.makeText(getApplicationContext(), jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                            if (jsonObject.getString("message").equals("SEND:SUCCESS")) {

                                Intent intent = new Intent(getApplicationContext(), SearchJob.class);
                                startActivity(intent);
                                finish();

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
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("aa", aa);
                params.put("bb", bb);
                params.put("cc", cc);
                params.put("dd", dd);
                params.put("ee", ee);
                params.put("ff", ff);
                params.put("gg", gg);
                params.put("hh", hh);


                return params;

            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringrequest);
        

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

    //encode Defaultimage to base64 string
    private static String loadDefaultImage(Context contexts) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        Bitmap bitmap = BitmapFactory.decodeResource(contexts.getResources(), R.drawable.default_profile_picture);
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        byte[] imageBytes = stream.toByteArray();
        String imageString = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return imageString;
    }

}