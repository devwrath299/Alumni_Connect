package com.example.instagramclone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.example.instagramclone.model.JobAdapter;
import com.example.instagramclone.model.Post;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MyPostedJobActivity extends AppCompatActivity {
    RecyclerView recyclerviewpost;
    private JobAdapter postAdapter;
    private List<Post> postList;
    String currentUserid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_posted_job);
        recyclerviewpost = findViewById(R.id.recyclerviewPosts);
        recyclerviewpost.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerviewpost.setLayoutManager(linearLayoutManager);
        postList = new ArrayList<>();
        currentUserid= FirebaseAuth.getInstance().getCurrentUser().getUid();
        postAdapter = new JobAdapter(getApplicationContext(),postList,true);
        recyclerviewpost.setAdapter(postAdapter);
        readposts();

    }
    private void readposts() {
        FirebaseDatabase.getInstance().getReference().child("Posts").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                postList.clear();
                for (DataSnapshot npsnapshot : snapshot.getChildren()) {
                    Post post = npsnapshot.getValue(Post.class);
//                        if (post.getCollegeId().equals(currentCollegeId)) {
                    if(post.getJobData()!=null && post.getPublisher().equals(currentUserid)){
                        postList.add(post);
                    }

//                        }
                }
                postAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}