package com.example.vicework;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
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
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

public class UserProfile extends AppCompatActivity {
    TextView tname,tdob,tuser,ttown,tphonenumber,temail,tprofileimage;
    ImageView ivname,ivdob,ivuserr,ivtown,ivphonenumber,ivemail,ivprofileimage;
    Button btnhome,btnchangepassword;
    public static String user;
    public static Uri imageuri;

    private static final int CAMERA_REQUEST = 100;
    private static final int STORAGE_REQUEST = 200;
    String cameraPermission[];
    String storagePermission[];
    String readPermission[];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        // allowing permissions of gallery and camera
        cameraPermission = new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        storagePermission = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};
        readPermission = new String[]{Manifest.permission.READ_EXTERNAL_STORAGE};


        Intent in= getIntent();
        user= in.getStringExtra("key");


        tname=findViewById(R.id.profilename);
        tdob=findViewById(R.id.profiledateofbirth);
        tuser=findViewById(R.id.profileuser);
        ttown=findViewById(R.id.profiletown);
        tphonenumber=findViewById(R.id.profilephone);
        temail=findViewById(R.id.profileemail);
        tprofileimage=findViewById(R.id.textViewchangepicture);

        ivname=findViewById(R.id.imageViewprofilename);
        ivdob=findViewById(R.id.imageViewprofiledateofbirth);
        ivuserr=findViewById(R.id.imageViewprofileuser);
        ivtown=findViewById(R.id.imageViewprofiletown);
        ivphonenumber=findViewById(R.id.imageViewprofilephone);
        ivemail=findViewById(R.id.imageViewprofileemail);
        ivprofileimage=findViewById(R.id.imageViewpicturechanged);


        loaduserdata();

        ivname.setOnClickListener(v -> {

            String a = tuser.getText().toString();
            String column = "name";
            final EditText taskEditText = new EditText(this);
            AlertDialog dialog = new AlertDialog.Builder(this)
                    .setTitle("INPUT")
                    .setMessage("ENTER YOUR NEW NAME AND PRESS 'SAVE'")
                    .setView(taskEditText)
                    .setPositiveButton("SAVE", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            String data = String.valueOf(taskEditText.getText());
                            functionmodif(a,data,column);

                        }
                    })
                    .setNegativeButton("Cancel", null)
                    .create();
            dialog.show();

        });


        ivdob.setOnClickListener(v -> {
            String a = tuser.getText().toString();
            String column = "DateOfBirth";

            final EditText taskEditText = new EditText(this);
            AlertDialog dialog = new AlertDialog.Builder(this)
                    .setTitle("INPUT")
                    .setMessage("ENTER YOUR NEW DATE OF BIRTH AND PRESS 'SAVE'")
                    .setView(taskEditText)
                    .setPositiveButton("SAVE", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            String data = String.valueOf(taskEditText.getText());
                            functionmodif(a,data,column);

                        }
                    })
                    .setNegativeButton("Cancel", null)
                    .create();
            dialog.show();

        });


        ivtown.setOnClickListener(v -> {
            String a = tuser.getText().toString();
            String column = "town";

            final EditText taskEditText = new EditText(this);
            AlertDialog dialog = new AlertDialog.Builder(this)
                    .setTitle("INPUT")
                    .setMessage("ENTER YOUR NEW NAME AND PRESS 'SAVE'")
                    .setView(taskEditText)
                    .setPositiveButton("SAVE", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            String data = String.valueOf(taskEditText.getText());
                            functionmodif(a,data,column);

                        }
                    })
                    .setNegativeButton("Cancel", null)
                    .create();
            dialog.show();

        });

        ivphonenumber.setOnClickListener(v -> {

            String a = tuser.getText().toString();
            String column = "phone";
            final EditText taskEditText = new EditText(this);
            AlertDialog dialog = new AlertDialog.Builder(this)
                    .setTitle("INPUT")
                    .setMessage("ENTER YOUR NEW PHONE NUMBER AND PRESS 'SAVE'")
                    .setView(taskEditText)
                    .setPositiveButton("SAVE", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            String data = String.valueOf(taskEditText.getText());
                            functionmodif(a,data,column);

                        }
                    })
                    .setNegativeButton("Cancel", null)
                    .create();
            dialog.show();

        });

        ivuserr.setOnClickListener(v -> {

            String a = tuser.getText().toString();
            String column = "user";
            final EditText taskEditText = new EditText(this);
            AlertDialog dialog = new AlertDialog.Builder(this)
                    .setTitle("INPUT")
                    .setMessage("ENTER YOUR NEW NAME AND PRESS 'SAVE'")
                    .setView(taskEditText)
                    .setPositiveButton("SAVE", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            String data = String.valueOf(taskEditText.getText());
                            functionmodifuser(a,data,column);

                        }
                    })
                    .setNegativeButton("Cancel", null)
                    .create();
            dialog.show();

        });

        ivemail.setOnClickListener(v -> {

            String a = tuser.getText().toString();
            String column = "email";
            final EditText taskEditText = new EditText(this);
            AlertDialog dialog = new AlertDialog.Builder(this)
                    .setTitle("INPUT")
                    .setMessage("ENTER YOUR NEW NAME AND PRESS 'SAVE'")
                    .setView(taskEditText)
                    .setPositiveButton("SAVE", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            String data = String.valueOf(taskEditText.getText());
                            functionmodif(a,data,column);

                        }
                    })
                    .setNegativeButton("Cancel", null)
                    .create();
            dialog.show();

        });


        tprofileimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showImagePicDialog();
            }

        });

        btnchangepassword=findViewById(R.id.btnchangepasswprd);
        btnchangepassword.setOnClickListener(v -> {

            functionchangepassword();

        });

        btnhome=findViewById(R.id.btnhomeuserprofile);
        btnhome.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), TopMainActivity.class);
            intent.putExtra("key",user);

            startActivity(intent);
            finish();


        });

    }

    private void functionchangepassword() {

        String a = tuser.getText().toString();
        String column = "password";
        final EditText taskEditText = new EditText(this);
        final EditText taskEditText1 = new EditText(this);
        taskEditText.setHint("ENTER OLD PASSWORD");
        taskEditText1.setHint("ENTER NEW PASSWORD");
        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.addView(taskEditText);
        linearLayout.addView(taskEditText1);

        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("INPUT")
                .setMessage("ENTER THE OLD PASSWORD AND THE NEW PASSWORD AND PRESS 'SAVE_CHANGE'")
                .setView(linearLayout)
                .setPositiveButton("SAVE_CHANGE", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                                        String data = String.valueOf(taskEditText1.getText());
                                        String data1 = String.valueOf(taskEditText.getText());
                                        functionmodifPASSWORD(a,data,data1,column);
                    }
                })
                .setNegativeButton("Cancel", null)
                .create();
        dialog.show();


    }

    @Override
    public void onBackPressed()
    {}

    private void functionmodif(String username,String modifieddata,String column) {

        StringRequest stringrequest = new StringRequest(Request.Method.POST, Constants.URL_MODIFY_DATA_USER,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.i("tagconvertstr", "[" + response + "]");
                            JSONObject jsonObject = new JSONObject(response);
                            Toast.makeText(getApplicationContext(), jsonObject.getString("message"), Toast.LENGTH_SHORT).show();

                            if(jsonObject.getString("message").equals("MODIFICATION WAS SUCCESSFULL")){

                                    Intent intent = new Intent(getApplicationContext(), UserProfile.class);
                                    intent.putExtra("key", user);
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
                params.put("username", username);
                params.put("modifieddata", modifieddata);
                params.put("column", column);

                return params;

            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringrequest);




    }

    private void functionmodifPASSWORD(String username,String modifieddata,String oldpassword, String column) {

        StringRequest stringrequest = new StringRequest(Request.Method.POST, Constants.URL_MODIFY_PASSWORD_USER,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.i("tagconvertstr", "[" + response + "]");
                            JSONObject jsonObject = new JSONObject(response);
                            Toast.makeText(getApplicationContext(), jsonObject.getString("message"), Toast.LENGTH_SHORT).show();

                            if(jsonObject.getString("message").equals("PASSWORD WAS SUCCESSFULLY MODIFIED")){

                                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                                startActivity(intent);
                                finish();
                                        TopMainActivity tma = new TopMainActivity();
                                        tma.name=null;
                                        user=null;

                                Toast.makeText(getApplicationContext(), "PLEASE LOGIN TO CONTINUE", Toast.LENGTH_LONG).show();
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
                params.put("username", username);
                params.put("modifieddata", modifieddata);
                params.put("column", column);
                params.put("oldpassword", oldpassword);

                return params;

            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringrequest);




    }

    private void functionmodifuser(String username,String modifieddata,String column) {

        StringRequest stringrequest = new StringRequest(Request.Method.POST, Constants.URL_MODIFY_USER,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.i("tagconvertstr", "[" + response + "]");
                            JSONObject jsonObject = new JSONObject(response);
                            Toast.makeText(getApplicationContext(), jsonObject.getString("message"), Toast.LENGTH_SHORT).show();

                            if(jsonObject.getString("message").equals("MODIFICATION WAS SUCCESSFULL")){

                                            Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                                            startActivity(intent);
                                finish();
                                            TopMainActivity tma = new TopMainActivity();
                                            tma.name=null;
                                            user=null;

                                Toast.makeText(getApplicationContext(), "PLEASE LOGIN TO CONTINUE", Toast.LENGTH_LONG).show();


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
                params.put("username", username);
                params.put("modifieddata", modifieddata);
                params.put("column", column);

                return params;

            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringrequest);




    }

    private void loaduserdata() {

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
                                tname.setText(jsonObject.getString("name"));
                                tdob.setText(jsonObject.getString("DateOfBirth"));
                                tuser.setText(jsonObject.getString("user"));
                                ttown.setText(jsonObject.getString("town"));
                                tphonenumber.setText(jsonObject.getString("phone"));
                                temail.setText(jsonObject.getString("email"));


                                String SIM = jsonObject.getString("picture");
                                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                                byte[] byteFormat = stream.toByteArray();
                                byteFormat = Base64.decode(SIM, Base64.NO_WRAP);
                                Bitmap decodedImage = BitmapFactory.decodeByteArray(byteFormat, 0, byteFormat.length);
                                ivprofileimage.setImageBitmap(decodedImage);

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
                params.put("username", user);

                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringrequest);

    }

    private void functionchangepicture(String username,String modifieddata,String column) {

            StringRequest stringrequest = new StringRequest(Request.Method.POST, Constants.URL_MODIFY_DATA_USER,
                    new Response.Listener<String>() {

                        @Override
                        public void onResponse(String response) {
                            try {
                                Log.i("tagconvertstr", "[" + response + "]");
                                JSONObject jsonObject = new JSONObject(response);
                                Toast.makeText(getApplicationContext(), jsonObject.getString("message"), Toast.LENGTH_SHORT).show();

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
                    params.put("modifieddata", modifieddata);
                    params.put("column", column);

                    return params;
                }
            };

            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(stringrequest);

    }

    //encode Studentimage to base64 string
    private static String loadselectedImage(Context contexts, String imagefile) {

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        Bitmap bitmap = BitmapFactory.decodeFile(imagefile);
        bitmap.compress(Bitmap.CompressFormat.JPEG, 30, stream);
        byte[] imageBytes = stream.toByteArray();
        String imageString = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return imageString;
    }

    ///code to get image

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                imageuri = result.getUri();
                Picasso.with(this).load(imageuri).into(ivprofileimage);
                String a = tuser.getText().toString();
                String column = "picture";
                String path = loadselectedImage(this,imageuri.getPath());
                functionchangepicture(a,path,column);
            }
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case CAMERA_REQUEST: {
                if (grantResults.length > 0) {
                    boolean camera_accepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean writeStorageaccepted = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                    if (camera_accepted && writeStorageaccepted) {
                        pickFromGallery();
                    } else {
                        Toast.makeText(this, "Please Enable Camera and Storage Permissions", Toast.LENGTH_LONG).show();
                    }
                }
            }
            break;
            case STORAGE_REQUEST: {
                if (grantResults.length > 0) {
                    boolean writeStorageaccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    if (writeStorageaccepted) {
                        pickFromGallery();
                    } else {
                        Toast.makeText(this, "Please Enable Storage Permissions", Toast.LENGTH_LONG).show();
                    }
                }
            }
            break;
        }
    }

    // checking camera permissions
    private Boolean checkCameraPermission() {
        boolean result = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == (PackageManager.PERMISSION_GRANTED);
        boolean result1 = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == (PackageManager.PERMISSION_GRANTED);
        return result && result1;
    }

    // Requesting gallery permission
    @RequiresApi(api = Build.VERSION_CODES.M)
    private void requestStoragePermission() {
        requestPermissions(storagePermission, STORAGE_REQUEST);
    }

    // Requesting camera permission
    @RequiresApi(api = Build.VERSION_CODES.M)
    private void requestCameraPermission() {
        requestPermissions(cameraPermission, CAMERA_REQUEST);
    }

    // checking storage permissions
    private Boolean checkStoragePermission() {
        boolean result = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == (PackageManager.PERMISSION_GRANTED);
        return result;
    }
    private void showImagePicDialog() {
        String options[] = {"SELECT YOUR PROFILE PICTURE"};
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(this);
        builder.setTitle("Pick Image From");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which == 0) {
                    if (!checkCameraPermission()) {
                        requestCameraPermission();
                    } else {
                        pickFromGallery();
                    }
                } else if (which == 1) {
                    if (!checkStoragePermission()) {
                        requestStoragePermission();
                    } else {
                        pickFromGallery();
                    }
                }
            }
        });
        builder.create().show();

    }
    // Here we will pick image from gallery or camera
    private void pickFromGallery() {
        CropImage.activity().start(UserProfile.this);
    }

}