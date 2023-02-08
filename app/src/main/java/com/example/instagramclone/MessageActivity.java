package com.example.instagramclone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.widget.AutoCompleteTextView;

import com.example.instagramclone.model.MessageAdapter;
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

public class MessageActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private AutoCompleteTextView usermessagesearch;
    private List<User> messagelist;
    private MessageAdapter userAdapter;
    private String currentCollegeId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        recyclerView = findViewById(R.id.recyclerview_users);
        usermessagesearch = findViewById(R.id.searchmessage);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        messagelist = new ArrayList<>();
        setCurrentCollegeId();
        readmessage();
        usermessagesearch.addTextChangedListener(new TextWatcher() {
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
    }

    private void setCurrentCollegeId() {
        SharedPreferences sh = getSharedPreferences("MySharedPref", Context.MODE_PRIVATE);
       currentCollegeId  = sh.getString("currentCollegeId", "ABC");
    }

    private void searchuser(String s) {
        Query query= FirebaseDatabase.getInstance().getReference().child("Users")
                .orderByChild("Username").startAt(s).endAt(s+"\uf8ff");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                messagelist.clear();
                for (DataSnapshot npsnapshot : snapshot.getChildren()) {
                    User user = npsnapshot.getValue(User.class);
                    if(user.getCollegeId().equals(currentCollegeId)) {
                        messagelist.add(user);
                    }

                }

                userAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void readmessage() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Users");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (TextUtils.isEmpty(usermessagesearch.getText().toString()))
                    if (snapshot.exists()) {
                        for (DataSnapshot npsnapshot : snapshot.getChildren()) {
                            User user = npsnapshot.getValue(User.class);
                            if(user.getCollegeId().equals(currentCollegeId)) {
                                messagelist.add(user);
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
        userAdapter = new MessageAdapter(this, messagelist, false);
        recyclerView.setAdapter(userAdapter);
    }
}