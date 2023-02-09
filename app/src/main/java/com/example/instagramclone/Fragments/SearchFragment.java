package com.example.instagramclone.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;

import com.example.instagramclone.R;
import com.example.instagramclone.Start_activity;
import com.example.instagramclone.model.User;
import com.example.instagramclone.model.UserAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class SearchFragment extends Fragment {
    private RecyclerView recyclerView;
    private AutoCompleteTextView search_bar;
    private List<User> mUsers;
    private UserAdapter userAdapter;
    public String currentCollegeId;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        recyclerView = view.findViewById(R.id.recyclerview_users);
        search_bar = view.findViewById(R.id.searchbar);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mUsers = new ArrayList<>();
        currentCollegeId= ((Start_activity)getActivity()).currentCollegeId;
        readusers();
        search_bar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                searchuser(charSequence.toString());

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        return view;
    }

    private void readusers() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Users");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (TextUtils.isEmpty(search_bar.getText().toString()))
                if (snapshot.exists()) {
                    for (DataSnapshot npsnapshot : snapshot.getChildren()) {
                        User user = npsnapshot.getValue(User.class);

                        if(user.getCollegeId().equals(currentCollegeId)) {
                            mUsers.add(user);
                        }

                    }
//                if (TextUtils.isEmpty(search_bar.getText().toString())){
//                    mUsers.clear();
//                    User user = snapshot.getValue(User.class);
//                    mUsers.add(user);
//                }
                    userAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }


        });
        userAdapter = new UserAdapter(getContext(), mUsers, true);
        recyclerView.setAdapter(userAdapter);
    }
    public  void  searchuser(String s){
        Query query= FirebaseDatabase.getInstance().getReference().child("Users")
                .orderByChild("Username").startAt(s).endAt(s+"\uf8ff");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mUsers.clear();
                for (DataSnapshot npsnapshot : snapshot.getChildren()) {
                    User user = npsnapshot.getValue(User.class);
                    if(user.getCollegeId().equals(currentCollegeId)){
                        mUsers.add(user);
                    }
            }

                userAdapter.notifyDataSetChanged();
        }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
    });
    }
}