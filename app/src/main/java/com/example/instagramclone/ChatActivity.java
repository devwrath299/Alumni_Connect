package com.example.instagramclone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.instagramclone.model.Chat;
import com.example.instagramclone.model.ChatAdapter;
import com.example.instagramclone.model.Comments;
import com.example.instagramclone.model.CommentsAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class ChatActivity extends AppCompatActivity {
    private ImageButton sendbutton;
    private  EditText inputmessage;
    String fullname;
    String userId;
    String singlenode;
    FirebaseUser firebaseUser;

    private RecyclerView recyclerView_messages;
    private ChatAdapter commentsAdapter;
    private List<Chat> chatList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        sendbutton = findViewById(R.id.sendButton);
        inputmessage =findViewById(R.id.messageInput);
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        Intent intent = getIntent();
        fullname = intent.getStringExtra("name");
        userId = intent.getStringExtra("recieverId");
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
       getSupportActionBar().setTitle(fullname);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        final ArrayList<String> newArrayList = new ArrayList<>();

        newArrayList.add(userId);

        newArrayList.add( firebaseUser.getUid());

        Collections.sort(newArrayList);

        singlenode = newArrayList.get(0)+"_"+newArrayList.get(1);

        recyclerView_messages = findViewById(R.id.recyclerview_Message);
        recyclerView_messages.setHasFixedSize(true);
        recyclerView_messages.setLayoutManager( new LinearLayoutManager(this));
        chatList = new ArrayList<>();
        commentsAdapter = new ChatAdapter(this,chatList,singlenode);
        recyclerView_messages.setAdapter(commentsAdapter);





        sendbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(inputmessage.getText().toString())){
                    Toast.makeText(ChatActivity.this, "Please write something", Toast.LENGTH_SHORT).show();

                }
                else{
                    putmessage();
                }
            }
        });
       getmessages();

    }

    private void getmessages() {

        FirebaseDatabase.getInstance().getReference().child("Messages").child(singlenode).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                chatList.clear();
                for (DataSnapshot npsnapshot : snapshot.getChildren()) {
                    Chat comment =npsnapshot.getValue(Chat.class);
                    chatList.add(comment);
                }
                commentsAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void putmessage() {

        HashMap<String,Object> map = new HashMap<>();
        DatabaseReference ref= FirebaseDatabase.getInstance().getReference().child("Messages").child(singlenode);
        String messageId = ref.push().getKey();
        map.put("message",inputmessage.getText().toString());
        map.put("publisher",firebaseUser.getUid());
        map.put("reciver",userId);
        map.put("MessageId",messageId);
        ref.child(messageId).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    Toast.makeText(ChatActivity.this, "Message Sent", Toast.LENGTH_SHORT).show();

                    inputmessage.setText("");
                }else{
                    Toast.makeText(ChatActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}