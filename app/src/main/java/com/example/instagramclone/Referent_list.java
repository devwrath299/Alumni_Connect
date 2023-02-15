package com.example.instagramclone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.example.instagramclone.model.Referent;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Referent_list extends AppCompatActivity {
    List<Referent>referents;
    public String postid="NOEbyXK_3iNhtGYBz9c";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_referent_list);
        referents=new ArrayList<>();
        fetch_referents();
    }

    private void fetch_referents() {
        FirebaseDatabase.getInstance().getReference().child("Referrals").child(postid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot snp:snapshot.getChildren())
                {
                    for(DataSnapshot sp:snp.getChildren())
                    {
                        Referent rf=sp.getValue(Referent.class);
                        Toast.makeText(Referent_list.this, "", Toast.LENGTH_SHORT).show();
                        referents.add(rf);
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}