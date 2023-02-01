package com.example.instagramclone.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.instagramclone.EditProfile_activity;
import com.example.instagramclone.FollowersActivity;
import com.example.instagramclone.OptionActivity;
import com.example.instagramclone.R;
import com.example.instagramclone.model.PhotoAdapter;
import com.example.instagramclone.model.Post;
import com.example.instagramclone.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.content.Context.MODE_PRIVATE;


public class ProfileFragment extends Fragment {
    RecyclerView recyclerView_savedphoto;
    private PhotoAdapter photoAdapter_savedphoto;
    private  List<Post> mysaved_posts;
    RecyclerView recyclerView;
    private PhotoAdapter  photoAdapter;
    List<Post> mypostlist;
    private CircleImageView imageprofile;
    private ImageView options;
    private TextView posts;
    private TextView follwers;
    private TextView following;
    private TextView username;
    private TextView fullname;
    private TextView bio;
   private ImageButton myposts;
    private ImageButton savedposts;
    private  TextView followingtext;
    private TextView follwertext;

    private FirebaseUser firebaseUser;
   private String profileId;
   private Button editprofile;


    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_profile, container, false);
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
//        String profile = user.getUserid();
//        if (profile.equals("")){
//           profileId =firebaseUser.getUid();
//        }else {
//            profileId =profile;
//        }

        String data = getContext().getSharedPreferences("Profile", MODE_PRIVATE).getString("ProfileId","");
        if (data.equals("")){
            profileId = firebaseUser.getUid();
        }else{
            profileId = data;
        }


        imageprofile = view.findViewById(R.id.profile_image);
        myposts = view.findViewById(R.id.mypictures);
        savedposts = view.findViewById(R.id.savepictures);

        options = view.findViewById(R.id.options);
        posts = view.findViewById(R.id.posts);
        follwers = view.findViewById(R.id.followers);
        following = view.findViewById(R.id.following);
        username = view.findViewById(R.id.username);
        fullname = view.findViewById(R.id.fullname);
        bio = view.findViewById(R.id.bio);
        followingtext = view.findViewById(R.id.followingtext);
        follwertext = view.findViewById(R.id.follwertext);

        editprofile = view.findViewById(R.id.edit_profile);
        recyclerView = view.findViewById(R.id.recyclerview_pictures);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),3));
        mypostlist = new ArrayList<>();
        photoAdapter = new PhotoAdapter(getContext(),mypostlist);
        recyclerView.setAdapter(photoAdapter);

        recyclerView_savedphoto = view.findViewById(R.id.recyclerview_savedimages);
        recyclerView_savedphoto.setHasFixedSize(true);
        recyclerView_savedphoto.setLayoutManager(new GridLayoutManager(getContext(),3));
        mysaved_posts = new ArrayList<>();
        photoAdapter_savedphoto = new PhotoAdapter(getContext(),mysaved_posts);
        recyclerView_savedphoto.setAdapter(photoAdapter_savedphoto);
        userinfo();
        countfollowersandfollowing();
        countpost();
        myPhotos();
        getsavedposts();
        if (profileId.equals(firebaseUser.getUid())){
            editprofile.setText("EDIT PROFILE");
        }else{
            checkfollowingstatus();
        }
        editprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String buttontext = editprofile.getText().toString();
                if (buttontext.equals("EDIT PROFILE")){
                    startActivity(new Intent(getContext(), EditProfile_activity.class));
                    //gotoeditprofile
                }else {
                    if (editprofile.getText().toString().equals("Follow")){
                        FirebaseDatabase.getInstance().getReference().child("Follow").child(firebaseUser.getUid()).child("Following").child(profileId)
                                .setValue(true);
                        FirebaseDatabase.getInstance().getReference().child("Follow").child(profileId).child("Followers")
                                .child(firebaseUser.getUid()).setValue(true);
                    }else {
                        FirebaseDatabase.getInstance().getReference().child("Follow").child(firebaseUser.getUid()).child("Following").child(profileId)
                                .removeValue();
                        FirebaseDatabase.getInstance().getReference().child("Follow").child(profileId).child("Followers")
                                .child(firebaseUser.getUid()).removeValue();

                    }


//                    if (buttontext.equals("Follow")){
//
//                        FirebaseDatabase.getInstance().getReference().child("Follow").child(firebaseUser.getUid()).child("Follwing").child(profileId)
//                                .setValue(true);
//
//                        FirebaseDatabase.getInstance().getReference().child("Follow").child(profileId).child("Followers").child(firebaseUser.getUid()).setValue(true);
//                        //editprofile.setText("UNFOLLOW");
//                    }else  if (buttontext.equals("UNFOLLOW")){
//                        FirebaseDatabase.getInstance().getReference().child("Follow").child(firebaseUser.getUid()).child("Follwing").child(profileId)
//                                .removeValue();
//
//                        FirebaseDatabase.getInstance().getReference().child("Follow").child(profileId).child("Followers").child(firebaseUser.getUid()).removeValue();
//                       // editprofile.setText("Follow");
//
//                    }


                }
            }
        });

        myposts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recyclerView.setVisibility(View.VISIBLE);
                recyclerView_savedphoto.setVisibility(View.GONE);

            }
        });
        savedposts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recyclerView.setVisibility(View.GONE);
                recyclerView_savedphoto.setVisibility(View.VISIBLE);

            }
        });

        follwers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                getSharedPreferences("Profile",MODE_PRIVATE).edit().putString("ProfileId",profileid).apply();
                Intent intent = new Intent(getContext(), FollowersActivity.class);
                intent.putExtra("ID",profileId);
                intent.putExtra("TITLE","followers");
                startActivity(intent);

            }
        });
        following.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), FollowersActivity.class);
                intent.putExtra("ID",profileId);
                intent.putExtra("TITLE","followings");
                startActivity(intent);

            }
        });
        follwertext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), FollowersActivity.class);
                intent.putExtra("ID",profileId);
                intent.putExtra("TITLE","followers");
                startActivity(intent);

            }
        });
        followingtext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), FollowersActivity.class);
                intent.putExtra("ID",profileId);
                intent.putExtra("TITLE","followings");
                startActivity(intent);

            }
        });
        options.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), OptionActivity.class));
            }
        });



        return view;
    }

    private void getsavedposts() {
        final List<String > savedId = new ArrayList<>();
        FirebaseDatabase.getInstance().getReference().child("Saves").child(profileId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot np : snapshot.getChildren()){
                    savedId.add(np.getKey());

                }
                FirebaseDatabase.getInstance().getReference().child("Posts").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        mysaved_posts.clear();
                        for (DataSnapshot snapshot1: snapshot.getChildren())
                        {
                            Post post = snapshot1.getValue(Post.class);
                            for (String id :savedId){
                                if (post.getPostId().equals(id)){
                                    mysaved_posts.add(post);
                                }
                            }
                        }
                        photoAdapter_savedphoto.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void myPhotos() {
        FirebaseDatabase.getInstance().getReference().child("Posts").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mypostlist.clear();
                for (DataSnapshot np : snapshot.getChildren()){
                    Post post = np.getValue(Post.class);
                    if (post.getPublisher().equals(profileId)){
                        mypostlist.add(post);
                    }
                }
                Collections.reverse(mypostlist);
                photoAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void checkfollowingstatus() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Follow").child(firebaseUser.getUid())
                .child("Following");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.child(profileId).exists()){
                    editprofile.setText("UNFOLLOW");
                }else
                    editprofile.setText("Follow");

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void countpost() {
        FirebaseDatabase.getInstance().getReference().child("Posts").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int counter =0;
                for (DataSnapshot np: snapshot.getChildren()){
                    Post post = np.getValue(Post.class);
//                    try {
//                        if (post.getPublisher().equals(profileId)){
//                            counter++;
//                        }
//
//                    }catch (Exception e){
//                        Log.d("Closing Because",e.getMessage());
//
//                    }
                    if (post.getPublisher().equals(profileId)){
                            counter++;
                        }


                }
                posts.setText(String.valueOf(counter));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void countfollowersandfollowing() {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Follow").child(profileId);
        ref.child("Followers").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                follwers.setText(""+snapshot.getChildrenCount());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        ref.child("Following").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                following.setText(""+snapshot.getChildrenCount());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void userinfo() {
        FirebaseDatabase.getInstance().getReference().child("Users").child(profileId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);

                if(user.getImageUrl().equals("default")){
                    imageprofile.setImageResource(R.drawable.unkown_person_24);
                }else{
                    Picasso.get().load(user.getImageUrl()).placeholder(R.drawable.unkown_person_24).into(imageprofile);}
                username.setText(user.getUsername());
                fullname.setText(user.getName());
                bio.setText(user.getBio());


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}