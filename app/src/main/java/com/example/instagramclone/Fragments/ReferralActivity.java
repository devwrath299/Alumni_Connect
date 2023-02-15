package com.example.instagramclone.Fragments;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.instagramclone.PostActivity;
import com.example.instagramclone.R;
import com.example.instagramclone.Start_activity;
import com.example.instagramclone.model.Post;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class ReferralActivity extends AppCompatActivity {
    Post post;
    Button btnSubmit;
    EditText etCoverLetter, etResumeLink;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_referral);
        post= getIntent().getExtras().getParcelable("postItem");
        btnSubmit = findViewById(R.id.btnSubmit);
        etCoverLetter = findViewById(R.id.etCoverletter);
        etResumeLink = findViewById(R.id.etResumeLink);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validateFields()){
                    String coverLetter= etCoverLetter.getText().toString();
                    String resumeLink= etResumeLink.getText().toString();
                    String userId= FirebaseAuth.getInstance().getCurrentUser().getUid();

                    HashMap<String,Object> map = new HashMap<>();
                    map.put("postId",post.getPostId());
                    map.put("coverLetter",coverLetter);
                    map.put("resumeLetter",resumeLink);
                    map.put("userId",userId);
                    map.put("publisher",post.getPublisher());
                    FirebaseDatabase.getInstance().getReference().child("Referrals").child(post.getPostId()).child(userId).setValue(map);
                    Toast.makeText(ReferralActivity.this, "Referral Submitted Successful", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });


    }

    private boolean validateFields() {
        if(etCoverLetter.getText().toString().isEmpty()){
            Toast.makeText(this, "Why are you fit for this role cannot be empty", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(etResumeLink.getText().toString().isEmpty()){
            Toast.makeText(this, "Resume Link cannot be empty", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;

    }
}
