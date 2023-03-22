package com.example.instagramclone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.example.instagramclone.model.PostAdapter;
import com.example.instagramclone.model.Referent;
import com.example.instagramclone.model.ReferentAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ReferentListActivity extends AppCompatActivity {
    List<Referent> referentList;
    public String postid;
    RecyclerView recyclerViewReferent;
    ReferentAdapter referentAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_referent_list);
        postid=getIntent().getStringExtra("postId");
        recyclerViewReferent = findViewById(R.id.recyclerViewReferrals);
        recyclerViewReferent.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerViewReferent.setLayoutManager(linearLayoutManager);
        referentList =new ArrayList<>();
        referentAdapter = new ReferentAdapter(this,referentList);
        recyclerViewReferent.setAdapter(referentAdapter);
        fetch_referents();
    }

    private void fetch_referents() {
        FirebaseDatabase.getInstance().getReference().child("Referrals").child(postid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                referentList.clear();
                for(DataSnapshot snp:snapshot.getChildren())
                {
                        referentList.add(snp.getValue(Referent.class));
                }
                referentAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}