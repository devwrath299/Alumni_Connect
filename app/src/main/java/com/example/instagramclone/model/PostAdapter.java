package com.example.instagramclone.model;

import android.content.Context;
import android.content.Intent;
import android.renderscript.Sampler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.instagramclone.Comment_Activity;
import com.example.instagramclone.FollowersActivity;
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

public class PostAdapter  extends  RecyclerView.Adapter<PostAdapter.ViewHolder>{
    private Context mcontext;
    private List<Post> mPosts;
   private  FirebaseUser firebaseUser;



    public PostAdapter(Context mcontext, List<Post> mPosts) {
        this.mcontext = mcontext;
        this.mPosts = mPosts;
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mcontext).inflate(R.layout.post_item,parent,false);
        return  new PostAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {


        final Post post = mPosts.get(position);
        Picasso.get().load(post.getImageUrl()).into(holder.postimage);
        holder.description.setText(post.getDescription());


        FirebaseDatabase.getInstance().getReference().child("Users").child(post.getPublisher()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);



                if(user.getImageUrl().equals("default")){
                    holder.imageprofile.setImageResource(R.drawable.unkown_person_24);
                }else{
                    Picasso.get().load(user.getImageUrl()).into(holder.imageprofile);}
                holder.username.setText(user.getUsername());
                holder.author.setText(user.getName());





//                holder.username.setText(user.getUsername());
//                holder.author.setText(user.getName());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        isliked(post.getPostId(),holder.like);
        nooflikes(post.getPostId(),holder.nooflikes);
        countcomments(post.getPostId(),holder.noofcomments);
        ispostsaved(post.getPostId(),holder.save);
        holder.like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(holder.like.getTag().equals("Like")){
                    FirebaseDatabase.getInstance().getReference().child("Likes").child(post.getPostId()).child(firebaseUser.getUid()).setValue(true);
                   addNotification(post.getPostId(),firebaseUser.getUid(),post.getPublisher(),"liked your post");
                }else{
                    FirebaseDatabase.getInstance().getReference().child("Likes").child(post.getPostId()).child(firebaseUser.getUid()).removeValue();
                    addNotification(post.getPostId(),firebaseUser.getUid(),post.getPublisher(),"removed like from your post");

                   // deletenotification(post.getPostId(),post.getPublisher());
                }
            }
        });
        holder.comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mcontext, Comment_Activity.class);
                intent.putExtra("postId",post.getPostId());
                intent.putExtra("authorId",post.getPublisher());
                mcontext.startActivity(intent);
            }
        });
        holder.noofcomments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mcontext, Comment_Activity.class);
                intent.putExtra("postId",post.getPostId());
                intent.putExtra("authorId",post.getPublisher());
                mcontext.startActivity(intent);

            }
        });
        holder.username.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                persons_searching user= new persons_searching(id);
                Intent intent = new Intent(mcontext, Start_activity.class);
                intent.putExtra("PublisherId",post.getPublisher());
                mcontext.startActivity(intent);

            }
        });
        holder.imageprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                persons_searching user= new persons_searching(id);
                Intent intent = new Intent(mcontext, Start_activity.class);
                intent.putExtra("PublisherId",post.getPublisher());
                mcontext.startActivity(intent);

            }
        });
        holder.author.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                persons_searching user= new persons_searching(id);
                Intent intent = new Intent(mcontext, Start_activity.class);
                intent.putExtra("PublisherId",post.getPublisher());
                mcontext.startActivity(intent);

            }
        });
        holder.save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              if (holder.save.getTag().equals("save")) {
                  FirebaseDatabase.getInstance().getReference().child("Saves").child(firebaseUser.getUid()).child(post.getPostId())
                          .setValue(true);

              }else{
                  FirebaseDatabase.getInstance().getReference().child("Saves").child(firebaseUser.getUid()).child(post.getPostId())
                          .removeValue();

              }
            }
        });
        holder.nooflikes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mcontext, FollowersActivity.class);
                intent.putExtra("ID",post.getPostId());
                intent.putExtra("TITLE","likes");
                mcontext.startActivity(intent);
            }
        });


    }

//    private void deletenotification(final String postId, final String postperonid) {
//        FirebaseDatabase.getInstance().getReference().child("Notification").child(postperonid).addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                for (DataSnapshot snapshot1: snapshot.getChildren()){
//                    Notification notification = snapshot1.getValue(Notification.class);
//                    if (notification.getPostid().equals(postId)){
//                        FirebaseDatabase.getInstance().getReference().child("Notification").child(postperonid).child(notification.getNotificationId()).removeValue();
//                    }
//
//
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//
//
//    }

    private void addNotification( String postId, String publisher,String postpersonid,String text) {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Notification");
        String notificationId= ref.push().getKey();

        HashMap<String,Object> map = new HashMap<>();
        map.put("userid",publisher);//who is liking the image postperson is person ehiose image is liked
        map.put("text",text);
        map.put("postid",postId);
        map.put("isPost",true);
        map.put("NotificationId",notificationId);
        FirebaseDatabase.getInstance().getReference().child("Notification").child(postpersonid).child(notificationId).setValue(map);

    }

    private void ispostsaved(final String postId, final ImageView imageView) {
        FirebaseDatabase.getInstance().getReference().child("Saves").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.child(postId).exists()){
                    imageView.setImageResource(R.drawable.saved_24);
                    imageView.setTag("saved");

                }else
                {
                    imageView.setImageResource(R.drawable.save_24);
                    imageView.setTag("save");

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return mPosts.size();
    }

    public  class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageprofile;
        public ImageView  postimage;
        public ImageView like;
        public ImageView comment;
        public ImageView save;
        public ImageView more;
        public TextView username;
        public TextView nooflikes;
        public TextView noofcomments;
        public TextView author;
        public TextView description;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageprofile =itemView.findViewById(R.id.profile_image);
            postimage =itemView.findViewById(R.id.post_image);
            like =itemView.findViewById(R.id.like);
            comment =itemView.findViewById(R.id.comment);
            save =itemView.findViewById(R.id.save);
            more =itemView.findViewById(R.id.more);
            username =itemView.findViewById(R.id.username);
            nooflikes =itemView.findViewById(R.id.liketext);
            noofcomments =itemView.findViewById(R.id.no_of_comments);
            author =itemView.findViewById(R.id.author);
            description =itemView.findViewById(R.id.description);
        }
    }
    private  void isliked(String postid, final ImageView imageView){
        FirebaseDatabase.getInstance().getReference().child("Likes").child(postid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.child(firebaseUser.getUid()).exists()){
                    imageView.setImageResource(R.drawable.liked_24);
                    imageView.setTag("Liked");

                }else{
                    imageView.setImageResource(R.drawable.ic_baseline_favorite_border_24);
                    imageView.setTag("Like");

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void nooflikes(String postid, final TextView textView){
        FirebaseDatabase.getInstance().getReference().child("Likes").child(postid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                textView.setText(snapshot.getChildrenCount()+" likes");

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private  void countcomments(String postid, final TextView text){
        FirebaseDatabase.getInstance().getReference().child("Comments").child(postid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.getChildrenCount()==0){
                    text.setText("Add Comment");
                }else{
                    text.setText("View All "+snapshot.getChildrenCount()+" comments");

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
