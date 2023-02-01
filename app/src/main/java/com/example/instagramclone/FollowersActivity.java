package com.example.instagramclone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.instagramclone.model.User;
import com.example.instagramclone.model.UserAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FollowersActivity extends AppCompatActivity {
    private  String personId;
    private  String title;
    private List<String> idlist;
    private RecyclerView recyclerView_followers;
    private UserAdapter userAdapter;
    private  List<User> mUsers;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_followers);
        Intent intent = getIntent();
      personId = intent.getStringExtra("ID");
        title = intent.getStringExtra("TITLE");
//        personId = FirebaseAuth.getInstance().getCurrentUser().getUid();
//        title = "following";
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        recyclerView_followers = findViewById(R.id.recycler_viewfollwers);
        recyclerView_followers.setHasFixedSize(true);
        recyclerView_followers.setLayoutManager(new LinearLayoutManager(this));
        mUsers = new ArrayList<>();
        userAdapter = new UserAdapter(this, mUsers, true);
        recyclerView_followers.setAdapter(userAdapter);

        idlist = new ArrayList<>();

        switch (title) {
            case "followers" :
                getFollowers();
                break;

            case "followings":
                getFollowings();
                break;

            case "likes":
                getLikes();
                break;
        }

    }

    private void getLikes() {
        FirebaseDatabase.getInstance().getReference().child("Likes").child(personId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                idlist.clear();
                for (DataSnapshot snapshot1 : snapshot.getChildren())
                {
                    idlist.add(snapshot1.getKey());

                }
                showUsers();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void getFollowings() {
        FirebaseDatabase.getInstance().getReference().child("Follow").child(personId).child("Following").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                idlist.clear();
                for (DataSnapshot snapshot1 : snapshot.getChildren())
                {
                    idlist.add(snapshot1.getKey());

                }
               showUsers();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void getFollowers() {
        FirebaseDatabase.getInstance().getReference().child("Follow").child(personId).child("Followers").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                idlist.clear();
                for (DataSnapshot snapshot1 : snapshot.getChildren())
                {
                    idlist.add(snapshot1.getKey());

                }
               showUsers();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void showUsers() {
//        mUsers.clear();
//       // for (String id :idlist)
//            FirebaseDatabase.getInstance().getReference().child("Users").child(id).addValueEventListener(new ValueEventListener() {
//                @Override
//                public void onDataChange(@NonNull DataSnapshot snapshot) {
//                    User user =snapshot.getValue(User.class);
//                    mUsers.add(user);
//                    Log.d("list of followers", mUsers.toString());
//                    userAdapter.notifyDataSetChanged();
//
//                }
//
//                @Override
//                public void onCancelled(@NonNull DatabaseError error) {
//
//                }
//            });

        FirebaseDatabase.getInstance().getReference().child("Users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mUsers.clear();
                for (DataSnapshot snapshot1:snapshot.getChildren()){
                    User user = snapshot1.getValue(User.class);
                    {

                        for (String id :idlist)
                        {
                            if (user.getUserId().equals(id)){
                                mUsers.add(user);
                            }
                        }
                    }
                }
                Log.d("list of followers", mUsers.toString());
                userAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}