package com.example.instagramclone.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.instagramclone.R;
import com.example.instagramclone.Start_activity;
import com.example.instagramclone.model.JobAdapter;
import com.example.instagramclone.model.Post;
import com.example.instagramclone.model.PostAdapter;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;

public class JobListFragment extends Fragment {
    RecyclerView recyclerviewpost;
    private JobAdapter postAdapter;
    private List<Post> postList;
    public String currentCollegeId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_job_list, container, false);
        recyclerviewpost = view.findViewById(R.id.recyclerview_posts);
        recyclerviewpost.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setStackFromEnd(true);
        linearLayoutManager.setReverseLayout(true);
        recyclerviewpost.setLayoutManager(linearLayoutManager);
        postList = new ArrayList<>();
        postAdapter = new JobAdapter(getContext(),postList);
        recyclerviewpost.setAdapter(postAdapter);
        currentCollegeId= ((Start_activity)getActivity()).currentCollegeId;
        readposts();
        ChipGroup chipGroup = view.findViewById(R.id.chipGroup);

        chipGroup.setOnCheckedChangeListener(new ChipGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(ChipGroup chipGroup, int i) {

                Chip chip = chipGroup.findViewById(i);
                chipGroup.getCheckedChipId();

                if (chip != null)
                    filterAdapterListNew(chipGroup.getCheckedChipId());


            }
        });

        return view;
    }

    private void filterAdapterListNew(int checkedChipId) {
        List<Post> tempList = new ArrayList<>();
        switch (checkedChipId){
            case R.id.chipAll: {
                postAdapter = new JobAdapter(getContext(),postList);
                recyclerviewpost.setAdapter(postAdapter);
                break;
            }
            case R.id.chipAndroid: {
                for (Post post: postList){
                    if(post.getJobData().getJobDomain().equals("Android")){
                        tempList.add(post);
                    }
                }
                postAdapter = new JobAdapter(getContext(),tempList);
                recyclerviewpost.setAdapter(postAdapter);
                break;
            }
            case R.id.chipMl: {
                for (Post post: postList){
                    if(post.getJobData().getJobDomain().equals("ML")){
                        tempList.add(post);
                    }
                }
                postAdapter = new JobAdapter(getContext(),tempList);
                recyclerviewpost.setAdapter(postAdapter);
                break;
            }
            case R.id.chipWeb: {
                for (Post post: postList){
                    if(post.getJobData().getJobDomain().equals("Web")){
                        tempList.add(post);
                    }
                }
                postAdapter = new JobAdapter(getContext(),tempList);
                recyclerviewpost.setAdapter(postAdapter);
                break;
            }
            case R.id.chipSecurity: {
                for (Post post: postList){
                    if(post.getJobData().getJobDomain().equals("Security")){
                        tempList.add(post);
                    }
                }
                postAdapter = new JobAdapter(getContext(),tempList);
                recyclerviewpost.setAdapter(postAdapter);
                break;
            }
            case R.id.chipBackend: {
                for (Post post: postList){
                    if(post.getJobData().getJobDomain().equals("Backend")){
                        tempList.add(post);
                    }
                }
                postAdapter = new JobAdapter(getContext(),tempList);
                recyclerviewpost.setAdapter(postAdapter);
                break;
            }
            case R.id.chipSde: {
                for (Post post: postList){
                    if(post.getJobData().getJobDomain().equals("SDE")){
                        tempList.add(post);
                    }
                }
                postAdapter = new JobAdapter(getContext(),tempList);
                recyclerviewpost.setAdapter(postAdapter);
                break;
            }
            default: {
                postAdapter = new JobAdapter(getContext(),postList);
                recyclerviewpost.setAdapter(postAdapter);
            }
        }
    }


    private void readposts() {
        FirebaseDatabase.getInstance().getReference().child("Posts").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                postList.clear();
                for (DataSnapshot npsnapshot : snapshot.getChildren()) {
                    Post post = npsnapshot.getValue(Post.class);
//                    Log.d("codeUpdate", post.toString());
                        if (post.getPublisher().equals("g0kgJ050EUSLtimY3CMe1gp0moY2") && post.getCollegeId().equals(currentCollegeId)) {
                            if(post.getJobData()!=null){
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
}