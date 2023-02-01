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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.instagramclone.model.Comments;
import com.example.instagramclone.model.CommentsAdapter;
import com.example.instagramclone.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class Comment_Activity extends AppCompatActivity {
    private EditText addcomment;
    private CircleImageView imageprofile;
    private TextView post;

    private String postId;
    private String authorId;
    FirebaseUser firebaseUser;
    private RecyclerView recyclerView_comments;
    private CommentsAdapter commentsAdapter;
    private List<Comments> commentsList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment_);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Comments");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        recyclerView_comments = findViewById(R.id.recyclerview_comment);
        recyclerView_comments.setHasFixedSize(true);
        recyclerView_comments.setLayoutManager( new LinearLayoutManager(this));
        commentsList = new ArrayList<>();
        commentsAdapter = new CommentsAdapter(this,commentsList);
        recyclerView_comments.setAdapter(commentsAdapter);

        addcomment = findViewById(R.id.addcomment);
        imageprofile = findViewById(R.id.profile_image);
        post = findViewById(R.id.post);
        Intent intent = getIntent();
        postId = intent.getStringExtra("postId");
        authorId = intent.getStringExtra("authorId");
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        getuserimage();
        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(addcomment.getText().toString())){
                    Toast.makeText(Comment_Activity.this, "No Comment Added", Toast.LENGTH_SHORT).show();

                }
                else{
                    putcomment();
                }
            }
        });
        getcomment();



    }

    private void getcomment() {
        FirebaseDatabase.getInstance().getReference().child("Comments").child(postId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                commentsList.clear();
                for (DataSnapshot npsnapshot : snapshot.getChildren()) {
                    Comments comment =npsnapshot.getValue(Comments.class);
                    commentsList.add(comment);
                    }
                commentsAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void putcomment() {
        HashMap<String,Object> map = new HashMap<>();
        DatabaseReference ref= FirebaseDatabase.getInstance().getReference().child("Comments").child(postId);
        String commentid = ref.push().getKey();
        map.put("comment",addcomment.getText().toString());
        map.put("publisher",firebaseUser.getUid());
        map.put("PostId",postId);
        map.put("CommentId",commentid);
        ref.child(commentid).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    Toast.makeText(Comment_Activity.this, "Comment added Sucessful", Toast.LENGTH_SHORT).show();
                    addNotification(postId,firebaseUser.getUid(),authorId);
                    addcomment.setText("");
                }else{
                    Toast.makeText(Comment_Activity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private void addNotification( String postId, String publisher,String postpersonid) {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Notification");
        String notificationId= ref.push().getKey();

        HashMap<String,Object> map = new HashMap<>();
        map.put("userid",publisher);//who is liking the image postperson is person ehiose image is liked
        map.put("text","commented on your post:-"+addcomment.getText().toString());
        map.put("postid",postId);
        map.put("isPost",true);
        map.put("NotificationId",notificationId);
        FirebaseDatabase.getInstance().getReference().child("Notification").child(postpersonid).child(notificationId).setValue(map);

    }

    private  void  getuserimage(){
        FirebaseDatabase.getInstance().getReference().child("Users").child(firebaseUser.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);
                if(user.getImageUrl().equals("default")){
                    imageprofile.setImageResource(R.drawable.unkown_person_24);
                }else{
                    Picasso.get().load(user.getImageUrl()).into(imageprofile);}


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}