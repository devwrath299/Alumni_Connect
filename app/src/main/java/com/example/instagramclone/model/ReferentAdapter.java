package com.example.instagramclone.model;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.instagramclone.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ReferentAdapter extends  RecyclerView.Adapter<ReferentAdapter.ViewHolder>{
    private Context mcontext;

    private List<Referent> referentList;

    public ReferentAdapter(Context mcontext, List<Referent> referentList) {
        this.mcontext = mcontext;
        this.referentList = referentList;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mcontext).inflate(R.layout.referral_list_item,parent,false);
        return new ReferentAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        Referent referent = referentList.get(position);
        holder.tvCoverLetter.setText("Cover Letter:\n"+referent.getCoverLetter());
        holder.tvResumeLink.setText("Resume Link: \n"+ referent.getResumeLink());
        FirebaseDatabase.getInstance().getReference().child("Users").child(referent.getPublisher()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);
                if(user.getImageUrl().equals("default")){
                    holder.imageprofile.setImageResource(R.drawable.unkown_person_24);
                }else{
                    Picasso.get().load(user.getImageUrl()).into(holder.imageprofile);}
                holder.username.setText(user.getUsername());
                holder.tvFullName.setText(user.getName());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }



    @Override
    public int getItemCount() {
        return referentList.size();
    }

    public  class ViewHolder extends RecyclerView.ViewHolder {
        public CircleImageView imageprofile;
        public TextView username;
        public TextView tvFullName;
        public TextView tvCoverLetter;
        public TextView tvResumeLink;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageprofile = itemView.findViewById(R.id.profile_image);
            tvFullName = itemView.findViewById(R.id.author);
            username = itemView.findViewById(R.id.username);
            tvCoverLetter = itemView.findViewById(R.id.tvCoverLetter);
            tvResumeLink = itemView.findViewById(R.id.tvResumeLink);

        }
    }
}

