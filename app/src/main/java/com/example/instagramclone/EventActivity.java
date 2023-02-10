package com.example.instagramclone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.instagramclone.model.College;
import com.example.instagramclone.model.CollegeAdapter;
import com.example.instagramclone.model.Post;
import com.example.instagramclone.model.PostAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class EventActivity extends AppCompatActivity {
    List<College>collegeList=new ArrayList<>();
    RecyclerView recycler_view;
    CollegeAdapter clgAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);

        collegeList=new ArrayList<>();
        recycler_view=findViewById(R.id.collegeRecyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setStackFromEnd(true);
        linearLayoutManager.setReverseLayout(true);

        recycler_view.setLayoutManager(linearLayoutManager);
        clgAdapter = new CollegeAdapter(this,collegeList);
        recycler_view.setAdapter(clgAdapter);
        fetchColleges();


    }

    private void fetchColleges() {
        FirebaseDatabase.getInstance().getReference().child("college").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                collegeList.clear();
                for (DataSnapshot np : snapshot.getChildren()) {
                    collegeList.add(np.getValue(College.class));
                }
                clgAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
