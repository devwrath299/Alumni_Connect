package com.example.instagramclone.model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.instagramclone.Fragments.PostDetailsFragment;
import com.example.instagramclone.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class PhotoAdapter extends RecyclerView.Adapter<PhotoAdapter.ViewHolder> {
    private Context mcontext;
    private List<Post> mpost;

    public PhotoAdapter(Context mcontext, List<Post> mpost) {
        this.mcontext = mcontext;
        this.mpost = mpost;
    }

    @NonNull
    @Override
    public PhotoAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mcontext).inflate(R.layout.photo_item,parent,false);
        return new PhotoAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Post post = mpost.get(position);

        Picasso.get().load(post.getImageUrl()).placeholder(R.drawable.loadingimage).into(holder.postimage);
        holder.postimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mcontext.getSharedPreferences("PREFS", Context.MODE_PRIVATE).edit().putString("postid",post.getPostId()).apply();
                ((FragmentActivity)mcontext).getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new PostDetailsFragment())
                        .commit();

            }


        });

    }



    @Override
    public int getItemCount() {
        return mpost.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView postimage;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            postimage = itemView.findViewById(R.id.post_image);
        }
    }
}
