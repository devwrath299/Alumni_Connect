package com.example.instagramclone.model;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.instagramclone.Fragments.PostDetailsFragment;
import com.example.instagramclone.Fragments.ProfileFragment;
import com.example.instagramclone.R;
import com.example.instagramclone.Start_activity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.Collections;
import java.util.List;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHolder> {
    private Context mContext;
    private List<Notification> notification_list;

    public NotificationAdapter(Context mContext, List<Notification> notification_list) {
        this.mContext = mContext;
        this.notification_list = notification_list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.notification_item, parent, false);
        return  new  NotificationAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        final Notification notification =notification_list.get(position);
        getuser(holder.profile_image,holder.username,notification.getUserid());
        holder.comment.setText(notification.getText());
        if (!notification.getPostid().equals("not a post")){
            holder.postimage.setVisibility(View.VISIBLE);
            getPostImage(holder.postimage,notification.getPostid());
        }else {
            holder.postimage.setVisibility(View.GONE);
        }
        CheckBox cb;


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!notification.getPostid().equals("not a post")) {
                    mContext.getSharedPreferences("PREFS", Context.MODE_PRIVATE)
                            .edit().putString("postid", notification.getPostid()).apply();

                    ((FragmentActivity)mContext).getSupportFragmentManager()
                            .beginTransaction().replace(R.id.fragment_container, new PostDetailsFragment()).commit();
                } else {
                    Intent intent = new Intent(mContext, Start_activity.class);
                    intent.putExtra("PublisherId",notification.getUserid());
                    mContext.startActivity(intent);
//                    mContext.getSharedPreferences("PROFILE", Context.MODE_PRIVATE)
//                            .edit().putString("profileId", notification.getUserid()).apply();
//
//                    ((FragmentActivity)mContext).getSupportFragmentManager()
//                            .beginTransaction().replace(R.id.fragment_container, new ProfileFragment()).commit();
                }
            }
        });










//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
////                mContext.getSharedPreferences("PREFS", Context.MODE_PRIVATE).edit().putString("postid",notification.getPostid()).apply();
////                ((FragmentActivity)mContext).getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new PostDetailsFragment())
////                        .commit();
////                mContext.getSharedPreferences("PREFS", Context.MODE_PRIVATE).edit().putString("postid",notification.getPostid()).apply();
////                ((FragmentActivity)mContext).getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new PostDetailsFragment())
////                        .commit();
//
//                if (notification.isPost()){
//                    mContext.getSharedPreferences("PREFS", Context.MODE_PRIVATE).edit().putString("postid", notification.getPostid()).apply();
//                    ((FragmentActivity)mContext).getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new PostDetailsFragment())
//                            .commit();
//                }else {
//                    mContext.getSharedPreferences("Profile",Context.MODE_PRIVATE).edit().putString("ProfileId",notification.getUserid())
//                            .apply();
//                    ((FragmentActivity)mContext).getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new ProfileFragment())
//                            .commit();
//                }
//
//            }
//        });


    }

    private void getPostImage(final ImageView post_image, String postid) {
        FirebaseDatabase.getInstance().getReference().child("Posts").child(postid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Post post = snapshot.getValue(Post.class);
                Picasso.get().load(post.getImageUrl()).placeholder(R.drawable.loadingimage).into(post_image);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void getuser(final ImageView profile_image, final TextView username, String userid) {
        FirebaseDatabase.getInstance().getReference().child("Users").child(userid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);
                if (user.getImageUrl().equals("defaults")){
                    profile_image.setImageResource(R.drawable.unkown_person_24);
                }
                else{
                    Picasso.get().load(user.getImageUrl()).placeholder(R.drawable.unkown_person_24).into(profile_image);

                }

                username.setText(user.getUsername());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return notification_list.size();
    }

    public class  ViewHolder extends RecyclerView.ViewHolder {
        public ImageView profile_image;
        public ImageView postimage;
        public TextView username;
        public  TextView comment;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            profile_image = itemView.findViewById(R.id.image_profile);
            postimage = itemView.findViewById(R.id.post_image);
            username = itemView.findViewById(R.id.username);
            comment = itemView.findViewById(R.id.comment);
        }
    }
}
