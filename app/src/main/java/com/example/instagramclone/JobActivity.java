
package com.example.instagramclone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.instagramclone.model.Job;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class JobActivity extends AppCompatActivity implements
        AdapterView.OnItemSelectedListener {

    ImageView close;
    TextView post;
    TextView jobRole;
    TextView companyName;
    TextView jobDescription;
    String[] jobDomains = { "SDE", "Web", "ML", "Android", "Backend", "Security"};
    String selectedJobDomain = jobDomains[0];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job);
        close = findViewById(R.id.close);
        post = findViewById(R.id.post);
        jobRole = findViewById(R.id.jobRole);
        companyName = findViewById(R.id.companyName);
        jobDescription = findViewById(R.id.jobDescription);

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(JobActivity.this, Start_activity.class));
                finish();
            }
        });
        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkValidFields()){
                    saveJobInFirebase();
                }else{
                    Toast.makeText(JobActivity.this, "please fill empty field",
                            Toast.LENGTH_SHORT).show();
                }

            }
        });
        Spinner spin = (Spinner) findViewById(R.id.spinner);
        spin.setOnItemSelectedListener(this);

        //Creating the ArrayAdapter instance having the country list
        ArrayAdapter aa = new ArrayAdapter(this,android.R.layout.simple_spinner_item,jobDomains);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        spin.setAdapter(aa);

    }

    private Boolean checkValidFields() {
        if(jobRole.getText().toString().isEmpty())
            return false;
        if(companyName.getText().toString().isEmpty())
            return false;
        if(jobDescription.getText().toString().isEmpty())
            return false;
        return true;

    }

    private void saveJobInFirebase() {

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Posts");
        String PostId = ref.push().getKey();
        HashMap<String, Object> map = new HashMap<>();
        map.put("PostId", PostId);
        map.put("ImageUrl", "https://firebasestorage.googleapis.com/v0/b/instagram-clone-70f2c.appspot.com/o/Posts%2F1667497119355.null?alt=media&token=f754b5e1-963b-4e9b-8732-1bfa0fff9452");
        map.put("Description", "");
        map.put("postType", "JOB");
        map.put("publisher", FirebaseAuth.getInstance().getCurrentUser().getUid());
        Job job = new Job();
        job.setCompanyName(companyName.getText().toString());
        job.setJobDescription(jobDescription.getText().toString());
        job.setJobDomain(selectedJobDomain);
        job.setJobRole(jobRole.getText().toString());
        map.put("jobData", job);
        ref.child(PostId).setValue(map);
        Toast.makeText(JobActivity.this, "Job Upload Sucessful",
                Toast.LENGTH_SHORT).show();
        startActivity(new Intent(JobActivity.this, Start_activity.class));
        finish();
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        selectedJobDomain = jobDomains[i];
        Toast.makeText(this, jobDomains[i] + " Selected", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}