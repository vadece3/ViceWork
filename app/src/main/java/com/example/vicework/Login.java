package com.example.vicework;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
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

public class Login extends AppCompatActivity {

        EditText euser,epass;
        Button li,register;
        public static String user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ActionBar bar = getSupportActionBar();
        if(bar != null) {
            bar.hide();
        }

        euser=findViewById(R.id.user);
        epass=(EditText) findViewById(R.id.password);
        li=findViewById(R.id.login);
        li.setOnClickListener(v -> {

            functionLogin();

        });

        register=findViewById(R.id.btnregisterInlogin);
        register.setOnClickListener(v -> {

            functionRegister();

        });
    }

    private void functionRegister() {
        Intent intent = new Intent(this,Register.class);
        startActivity(intent);
        finish();

    }

    private void functionLogin() {
        user = euser.getText().toString();
        String password = epass.getText().toString();

        if (user.equals("") || password.equals("")) {
            Toast.makeText(getApplicationContext(), "PLEASE FILL ALL THE FIELDS", Toast.LENGTH_SHORT).show();
        }
        else{
            StringRequest stringrequest = new StringRequest(Request.Method.POST, Constants.URL_GET_LOGIN,
                    new Response.Listener<String>() {

                        @Override
                        public void onResponse(String response) {
                            try {
                                Log.i("tagconvertstr", "[" + response + "]");
                                JSONObject jsonObject = new JSONObject(response);
                                Toast.makeText(getApplicationContext(), jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                                if (jsonObject.getString("message").equals("LOGIN SUCCESSFUL")) {

                                    Intent intent = new Intent(getApplicationContext(), TopMainActivity.class);
                                    intent.putExtra("key",user);

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
                            // Toast.makeText(getApplicationContext(),error.getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("user", user);
                    params.put("password", password);

                    return params;
                }
            };

            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(stringrequest);

        }
    }


}