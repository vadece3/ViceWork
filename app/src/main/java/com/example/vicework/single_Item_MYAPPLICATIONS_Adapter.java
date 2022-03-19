package com.example.vicework;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class single_Item_MYAPPLICATIONS_Adapter extends ArrayAdapter<Job_myapplications> {
    //storing all the names in the list
    private List<Job_myapplications> job_myapplications;

    //context object
    private Context context;
    TextView textViewjobname;
    TextView textViewstartdate;
    TextView textViewjobgiver;
    Button btnViewAnswer;
    public static String answer;

    //constructor
    public single_Item_MYAPPLICATIONS_Adapter(Context context, int resource, List<Job_myapplications> job_myapplications) {
        super(context, resource, job_myapplications);
        this.context = context;
        this.job_myapplications = job_myapplications;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        //getting the layoutinflater
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        //getting listview items
        View listViewItem = inflater.inflate(R.layout.single_item_myapplications, null, true);
        textViewjobname = listViewItem.findViewById(R.id.textViewjobnameviewmyapp);
        textViewstartdate = listViewItem.findViewById(R.id.textViewstartdateviewmyapp);
        textViewjobgiver = listViewItem.findViewById(R.id.textViewjobgiverviewmyapp);
        TextView textViewposition = listViewItem.findViewById(R.id.textViewpositionmyapp);

        //getting the current region
        Job_myapplications jobs = job_myapplications.get(position);

        //setting the region to textview
        textViewjobgiver.setText(jobs.getJobgiver());
        textViewjobgiver.setOnClickListener(v -> {

            Intent intent = new Intent(getContext(), ViewUserProfile.class);
            String username=textViewjobgiver.getText().toString();
            ///geting just the username
            String word = "JOB GIVER NAME: ";
            String name = username.replaceAll(word, "");
            intent.putExtra("key", name);
            context.startActivity(intent);

        });
        textViewjobname.setText(jobs.getJobname());
        textViewstartdate.setText(jobs.getStartdate());
        textViewposition.setText(jobs.getJobposition());
        answer = jobs.getAcceptence_status();

        btnViewAnswer = listViewItem.findViewById(R.id.btnviewanswermyapp);
        btnViewAnswer.setOnClickListener(v -> {

            functionViewAnswer();

        });

        return listViewItem;
    }

    private void functionViewAnswer() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        builder.setTitle("ANSWER");
        builder.setMessage(answer);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                // Do nothing
                dialog.dismiss();
            }
        });

        AlertDialog alert = builder.create();
        alert.show();

    }

}
