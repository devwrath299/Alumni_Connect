package com.example.instagramclone.model;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;


import com.example.instagramclone.Fragments.ProfileFragment;
import com.example.instagramclone.R;
import com.example.instagramclone.Start_activity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserAdapter  extends  RecyclerView.Adapter<UserAdapter.ViewHolder>{
    private Context mcontext;
    private  List<User> mUsers;
    private  boolean isfragment;
    private FirebaseUser firebaseUser;



    public UserAdapter(Context montext, List<User> mUsers, boolean isfragment) {
        this.mcontext = montext;
        this.mUsers =  mUsers;
        this.isfragment = isfragment;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view  = LayoutInflater.from(mcontext).inflate(R.layout.user_item,parent,false);
        return  new UserAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        final User user = mUsers.get(position);

        holder.btnfollow.setVisibility(View.VISIBLE);
        holder.username.setText(user.getUsername());
        holder.name.setText(user.getName());
        Picasso.get().load(user.getImageUrl()).placeholder(R.drawable.unkown_person_24).into(holder.profileimage);
        isfollowed(user.getUserId(),holder.btnfollow);
        if (user.getUserId().equals(firebaseUser.getUid())){
            holder.btnfollow.setVisibility(View.GONE);
        }
        holder.btnfollow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (holder.btnfollow.getText().toString().equals("Follow")){
                    FirebaseDatabase.getInstance().getReference().child("Follow").child(firebaseUser.getUid()).child("Following").child(user.getUserId())
                            .setValue(true);
                    FirebaseDatabase.getInstance().getReference().child("Follow").child(user.getUserId()).child("Followers")
                            .child(firebaseUser.getUid()).setValue(true);
                    addNotification(firebaseUser.getUid(),user.getUserId(),"has started following you");


                }else {
                    FirebaseDatabase.getInstance().getReference().child("Follow").child(firebaseUser.getUid()).child("Following").child(user.getUserId())
                            .removeValue();
                    FirebaseDatabase.getInstance().getReference().child("Follow").child(user.getUserId()).child("Followers")
                            .child(firebaseUser.getUid()).removeValue();
                    addNotification(firebaseUser.getUid(),user.getUserId(),"has unfollowed you");

                }
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mcontext,Start_activity.class);
                intent.putExtra("PublisherId",user.getUserId());
                mcontext.startActivity(intent);
//                if (isfragment){
//                    mcontext.getSharedPreferences("Profile",Context.MODE_PRIVATE).edit().putString("ProfileId",user.getUserId()).apply();
////                    Intent intent = new Intent(mcontext, Start_activity.class);
////                    intent.putExtra("PublisherId",user.getUserId());
////                    mcontext.startActivity(intent);
//                    ((FragmentActivity)mcontext).getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new ProfileFragment())
//                         .commit();
//
//                }else {
//                    Intent intent = new Intent(mcontext,Start_activity.class);
//                    intent.putExtra("PublisherId",user.getUserId());
//                    mcontext.startActivity(intent);
//                }
            }
        });

    }
    private void addNotification(  String publisher,String postpersonid,String text) {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Notification");
        String notificationId= ref.push().getKey();

        HashMap<String,Object> map = new HashMap<>();
        map.put("userid",publisher);//who is liking the image postperson is person whose image is liked
        map.put("text",text);
        map.put("postid","not a post");
        map.put("isPost",false);
        map.put("NotificationId",notificationId);
        FirebaseDatabase.getInstance().getReference().child("Notification").child(postpersonid).child(notificationId).setValue(map);

    }

    private void isfollowed(final String userId, final Button btnfollow) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Follow").child(firebaseUser.getUid())
                .child("Following");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.child(userId).exists()){
                    btnfollow.setText("Following");
                }else
                    btnfollow.setText("Follow");

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    @Override
    public int getItemCount() {
        return  mUsers.size();
    }

    public  class  ViewHolder extends  RecyclerView.ViewHolder {
        public CircleImageView profileimage;
        public TextView username;
        public  TextView name;
        public Button btnfollow;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            profileimage = itemView.findViewById(R.id.profile_image);
            username = itemView.findViewById(R.id.username);
            name = itemView.findViewById(R.id.fullname);
            btnfollow = itemView.findViewById(R.id.btnfollow);



        }
    }
}
