package com.example.vicework;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.DatePickerDialog;
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
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
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
import com.squareup.picasso.Picasso;

import com.theartofdev.edmodo.cropper.CropImageView;
import com.theartofdev.edmodo.cropper.CropImage;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class Register extends AppCompatActivity {
    EditText ename,edob,euserr,epasswordr,etown,ephonenumber,eemail;
    Button btnregister;
    ImageView imvdob,imvprofilepicture;
    public static Uri imageuri;
    TextView epicture;
    public static String picture;




    private static final int CAMERA_REQUEST = 100;
    private static final int STORAGE_REQUEST = 200;
    String cameraPermission[];
    String storagePermission[];
    String readPermission[];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ActionBar bar = getSupportActionBar();
        if(bar != null) {
            bar.hide();
        }


//        Toast.makeText(this,  "hellooo"+imvprofilepicture.getDrawable().toString(), Toast.LENGTH_LONG).show();


        // allowing permissions of gallery and camera
        cameraPermission = new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        storagePermission = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};
        readPermission = new String[]{Manifest.permission.READ_EXTERNAL_STORAGE};

        ename=findViewById(R.id.Name);
        edob=findViewById(R.id.dateofbirth);
        euserr=findViewById(R.id.userregister);
        epasswordr=findViewById(R.id.passwordregister);
        etown=findViewById(R.id.town);
        ephonenumber=findViewById(R.id.phone);
        eemail=findViewById(R.id.email);
        epicture=findViewById(R.id.picture);
        imvprofilepicture=findViewById(R.id.imageViewuserprofilepicutre);
        btnregister=findViewById(R.id.btnregister);

        epicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showImagePicDialog();
            }

        });
        btnregister.setOnClickListener(v -> {

            functionregister();

        });

        imvdob = findViewById(R.id.imageViewdateofbirth);
        imvdob.setOnClickListener(v -> {

            Calendar cal = Calendar.getInstance();
            int mDate = cal.get(Calendar.DATE);
            int mMonth = cal.get(Calendar.MONTH);
            int mYear = cal.get(Calendar.YEAR);
            DatePickerDialog datePickerDialog = new DatePickerDialog(this, android.R.style.Theme_DeviceDefault_Dialog, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker datePicker, int year, int month, int date) {
                    edob.setText(date+"/"+month+"/"+year);
                }
            },mYear,mMonth,mDate);
            datePickerDialog.show();

        });
    }


    ///code to get image

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                imageuri = result.getUri();
                Picasso.with(this).load(imageuri).into(imvprofilepicture);
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
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
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
        CropImage.activity().start(Register.this);
    }



    private void functionregister() {


        // Get the Base64 string
        if(imvprofilepicture.getDrawable()==null) {
            picture =loadDefaultImage(this);
        }
        else{
            String a = imageuri.getPath();
            picture =loadselectedImage(this,a);

        }
        String user = euserr.getText().toString();
        String password = epasswordr.getText().toString();
        String dateofbirth = edob.getText().toString();
        String name = ename.getText().toString();
        String town = etown.getText().toString();
        String phone = ephonenumber.getText().toString();
        String email = eemail.getText().toString();

        if (user.equals("") || password.equals("") || dateofbirth.equals("") || name.equals("") || town.equals("") || phone.equals("") || email.equals("")) {
            Toast.makeText(getApplicationContext(), "PLEASE FILL ALL THE FIELDS WHICH HAS **", Toast.LENGTH_SHORT).show();
        }
        else{
            StringRequest stringrequest = new StringRequest(Request.Method.POST, Constants.URL_GET_REGISTER,
                    new Response.Listener<String>() {

                        @Override
                        public void onResponse(String response) {
                            try {
                                Log.i("tagconvertstr", "[" + response + "]");
                                JSONObject jsonObject = new JSONObject(response);
                                Toast.makeText(getApplicationContext(), jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                                if (jsonObject.getString("message").equals("REGISTERED SUCCESSFULLY")) {

                                    Intent intent = new Intent(getApplicationContext(), TopMainActivity.class);
                                    intent.putExtra("key",user);
                                    intent.putExtra("upassword",password);
                                    intent.putExtra("uDateOfBirth",dateofbirth);
                                    intent.putExtra("uname",name);
                                    intent.putExtra("utown",town);
                                    intent.putExtra("uphone",phone);
                                    intent.putExtra("uemail",email);
                                    intent.putExtra("upicture",picture);

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
                    params.put("dateofbirth", dateofbirth);
                    params.put("name", name);
                    params.put("town", town);
                    params.put("phone", phone);
                    params.put("email", email);
                    params.put("picture", picture);

                    return params;
                }
            };

            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(stringrequest);

        }
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