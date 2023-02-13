package com.example.instagramclone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.example.instagramclone.model.College;
import com.example.instagramclone.model.CollegeAdapter;
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
    College currentCollege;

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
                    College clg = np.getValue(College.class);
                    if(!clg.getCollegeId().equals(getCurrentCollegeId())){
                        collegeList.add(clg);
                    }else{
                        // current college is on First and switch is not visible
                        clg.setHideSwitch(true);
                        collegeList.add(0,clg);
                        currentCollege= clg;
                    }
                }
                setCollegeSelected();
//                clgAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void setCollegeSelected() {
        for (final College college:collegeList) {
            FirebaseDatabase.getInstance().getReference().child("Events").child(college.getCollegeId()).child("Following").child(getCurrentCollegeId()).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                 if(dataSnapshot.exists()){
                     boolean collegeIdValue= Boolean.TRUE.equals(dataSnapshot.getValue(Boolean.class));
                     college.setSelected(collegeIdValue);
                 }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
            clgAdapter.notifyDataSetChanged();
        }
    }

    private String getCurrentCollegeId() {
        SharedPreferences sh = getSharedPreferences("MySharedPref", Context.MODE_PRIVATE);
        return sh.getString("currentCollegeId", "IIITU");
    }
}
