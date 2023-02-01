package com.example.instagramclone.model;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.instagramclone.R;
import com.example.instagramclone.Start_activity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class CommentsAdapter extends  RecyclerView.Adapter<CommentsAdapter.ViewHolder>{
    private Context mcontext;

    private List<Comments> mcomments;
    private FirebaseUser firebaseUser ;

    public CommentsAdapter(Context mcontext, List<Comments> mcomments) {
        this.mcontext = mcontext;
        this.mcomments = mcomments;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mcontext).inflate(R.layout.comment_item,parent,false);
        return new CommentsAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        final Comments comments = mcomments.get(position);
        holder.comment.setText(comments.getComment());
        FirebaseDatabase.getInstance().getReference().child("Users").child(comments.getPublisher()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);
                holder.username.setText(user.getUsername());
                if(user.getImageUrl().equals("default")){
                    holder.imageprofile.setImageResource(R.drawable.unkown_person_24);
                }else{
                    Picasso.get().load(user.getImageUrl()).into(holder.imageprofile);}


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
//        holder.comment.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(mcontext, Start_activity.class);
//                intent.putExtra("PublisherId",comments.getPublisher());
//                mcontext.startActivity(intent);
//            }
//        });
        holder.username.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mcontext, Start_activity.class);
                intent.putExtra("PublisherId",comments.getPublisher());
                mcontext.startActivity(intent);
            }
        });
        holder.imageprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mcontext, Start_activity.class);
                intent.putExtra("PublisherId",comments.getPublisher());
                mcontext.startActivity(intent);
            }
        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                if (comments.getPublisher().endsWith(firebaseUser.getUid())){
                  final AlertDialog ad =  new AlertDialog.Builder(mcontext).create();
                           ad.setTitle("Do you want to delete this comment");
                           ad.setButton(AlertDialog.BUTTON_NEUTRAL, "No", new DialogInterface.OnClickListener() {
                               @Override
                               public void onClick(DialogInterface dialogInterface, int i) {
                                   dialogInterface.dismiss();
                               }
                           });
                           ad.setButton(AlertDialog.BUTTON_POSITIVE, "Yes", new DialogInterface.OnClickListener() {
                               @Override
                               public void onClick(DialogInterface dialogInterface, int i) {
                                   FirebaseDatabase.getInstance().getReference().child("Comments").child(comments.getPostId()).child(comments.getCommentid())
                                           .removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                       @Override
                                       public void onComplete(@NonNull Task<Void> task) {
                                           if (task.isSuccessful()){
                                               Toast.makeText(mcontext, "Comment Deleted Sucessfully", Toast.LENGTH_SHORT).show();
                                               ad.dismiss();
                                           }
                                       }
                                   });
                               }
                           });
                           ad.show();
                }
                return true;
            }
        });


    }

    @Override
    public int getItemCount() {
        return mcomments.size();
    }

    public  class ViewHolder extends RecyclerView.ViewHolder {
        public CircleImageView imageprofile;
        public TextView username;
        public  TextView comment;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageprofile = itemView.findViewById(R.id.profile_image);
            username = itemView.findViewById(R.id.username);
            comment = itemView.findViewById(R.id.comment);
        }
    }
}
