package com.example.instagramclone.model;

import static androidx.core.content.ContextCompat.startActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.instagramclone.Comment_Activity;
import com.example.instagramclone.FollowersActivity;
import com.example.instagramclone.Fragments.ReferralActivity;
import com.example.instagramclone.JobActivity;
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

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class JobAdapter extends RecyclerView.Adapter<JobAdapter.ViewHolder> {
    private Context mcontext;
    private List<Post> mPosts;
    private FirebaseUser firebaseUser;


    public JobAdapter(Context mcontext, List<Post> mPosts) {
        this.mcontext = mcontext;
        this.mPosts = mPosts;
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
    }

    public JobAdapter() {

    }

    @NonNull
    @Override
    public JobAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mcontext).inflate(R.layout.job_list_item, parent, false);
        return new JobAdapter.ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull final JobAdapter.ViewHolder holder, final int position) {


        final Post post =  mPosts.get(position);
        holder.companyName.setText(post.getJobData().getCompanyName());
        holder.jobDomain.setText(post.getJobData().getJobDomain());
        holder.jobRole.setText(post.getJobData().getJobRole());
        holder.jobDescription.setText(post.getJobData().getJobDescription());
        holder.applyReferral.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                final Object objSent = new Object();
//                final Bundle bundle = new Bundle();
//                bundle.putSerializable("object_value", post);
                Intent intent =new Intent(mcontext, ReferralActivity.class);
                intent.putExtra("postItem", post);
                mcontext.startActivity(intent);
            }
        });


    }


    @Override
    public int getItemCount() {
        return mPosts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView jobRole;
        public TextView companyName;
        public TextView jobDescription;
        public TextView jobDomain;
        public TextView applyReferral;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            jobRole = itemView.findViewById(R.id.tv_code);
            companyName = itemView.findViewById(R.id.tv_apply);
            jobDescription = itemView.findViewById(R.id.tv_discount_details);
            jobDomain = itemView.findViewById(R.id.tv_min_order);
            applyReferral=itemView.findViewById(R.id.applyReferral);
        }
    }
}
