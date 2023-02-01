package com.example.instagramclone.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.instagramclone.MessageActivity;
import com.example.instagramclone.PostActivity;
import com.example.instagramclone.R;
import com.example.instagramclone.Start_activity;
import com.example.instagramclone.model.Post;
import com.example.instagramclone.model.PostAdapter;
import com.example.instagramclone.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment {
    RecyclerView recyclerviewpost;
    private PostAdapter postAdapter;
    private List<Post> postList;
    private List<String> followinglist;
    private ImageView camera;
    private  ImageView gotomessage;
    // commented code

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        recyclerviewpost = view.findViewById(R.id.recyclerview_posts);
        camera = view.findViewById(R.id.camera);
        gotomessage = view.findViewById(R.id.messages);
        recyclerviewpost.setHasFixedSize(true);
       LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
       linearLayoutManager.setStackFromEnd(true);
       linearLayoutManager.setReverseLayout(true);
       recyclerviewpost.setLayoutManager(linearLayoutManager);
      postList = new ArrayList<>();
      postAdapter = new PostAdapter(getContext(),postList);
      recyclerviewpost.setAdapter(postAdapter);

      followinglist = new ArrayList<>();
      checkfollowingusers();
      camera.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              startActivity( new Intent(getContext(), PostActivity.class));
          }
      });
      gotomessage.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              startActivity(new Intent(getContext(), MessageActivity.class));
          }
      });


        return view;
    }

    private void checkfollowingusers() {
        FirebaseDatabase.getInstance().getReference().child("Follow").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .child("Following").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                followinglist.clear();
                for (DataSnapshot npsnapshot : snapshot.getChildren()) {
                    followinglist.add(npsnapshot.getKey());

            }
                readposts();


        }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
    });
}

    private void readposts() {
        FirebaseDatabase.getInstance().getReference().child("Posts").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                postList.clear();
                for (DataSnapshot npsnapshot : snapshot.getChildren()) {
                    Post post = npsnapshot.getValue(Post.class);
                    for (String id : followinglist){
                        if (post.getPublisher().equals(id)){
                            postList.add(post);
                        }
                    }
                }
                postAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void SetPostTypeinList() {
        for(Post post: postList){

        }
    }
}