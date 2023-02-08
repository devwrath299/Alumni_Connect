package com.example.instagramclone.model;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.instagramclone.ChatActivity;
import com.example.instagramclone.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MessageAdapter extends  RecyclerView.Adapter<MessageAdapter.ViewHolder> {
    private Context mcontext;
    private List<User> mUsers;
    private  boolean isfragment;
    private FirebaseUser firebaseUser;

    public MessageAdapter(Context mcontext, List<User> mUsers, boolean isfragment) {
        this.mcontext = mcontext;
        this.mUsers = mUsers;
        this.isfragment = isfragment;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view  = LayoutInflater.from(mcontext).inflate(R.layout.user_item,parent,false);
        return  new MessageAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        final User user = mUsers.get(position);
        holder.username.setText(user.getUsername());
        holder.name.setText(user.getName());
        holder.collegeName.setText(user.getCollegeId());
        Picasso.get().load(user.getImageUrl()).placeholder(R.drawable.unkown_person_24).into(holder.profileimage);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mcontext, ChatActivity.class);
                intent.putExtra("recieverId",user.getUserId());
                intent.putExtra("name", user.getName());
                mcontext.startActivity(intent);


            }
        });

    }

    @Override
    public int getItemCount() {
        return mUsers.size();
    }

    public  class ViewHolder extends RecyclerView.ViewHolder {
        public CircleImageView profileimage;
        public TextView username;
        public  TextView name;
        public TextView collegeName;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            profileimage = itemView.findViewById(R.id.profile_image);
            username = itemView.findViewById(R.id.username);
            name = itemView.findViewById(R.id.fullname);
            collegeName = itemView.findViewById(R.id.collgeName);
        }
    }
}
