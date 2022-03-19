package com.example.vicework;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.widget.ImageView;
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

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

public class ViewUserProfile extends AppCompatActivity {

    TextView tusername,tuserfullname,tusertown,tuserphonenumber,tuseremail;
    ImageView iuserpicture;
    public static String uname,uDateOfBirth,uuser,upassword,utown,uphone,uemail,upicture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_user_profile);

        tusername=findViewById(R.id.tusernameviewuser);
        tusertown=findViewById(R.id.tusertownviewuser);
        tuseremail=findViewById(R.id.tuseremailviewuser);
        tuserfullname = findViewById(R.id.tuserfullnameviewuser);
        tuserphonenumber=findViewById(R.id.tuserphonenumberuserview);

        iuserpicture=findViewById(R.id.imuserpictureviewuser);

        Intent in= getIntent();
        String name = in.getStringExtra("key");

        ////////geting all user data
        getinguserdata(name);
        ////////
        tusername.setText(name);


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

                                String picture = jsonObject.getString("picture");
                                iuserpicture.setImageBitmap(Convertbitmaptoimage(picture));

                                tusertown.setText(jsonObject.getString("town"));
                                tuseremail.setText(jsonObject.getString("email"));
                                tuserfullname.setText(jsonObject.getString("name"));
                                tuserphonenumber.setText(jsonObject.getString("phone"));


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

    public Bitmap Convertbitmaptoimage(String bitmaptext){

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        byte[] byteFormat = stream.toByteArray();
        byteFormat = Base64.decode(bitmaptext, Base64.NO_WRAP);
        Bitmap decodedImage = BitmapFactory.decodeByteArray(byteFormat, 0, byteFormat.length);
        return decodedImage;


    }

}