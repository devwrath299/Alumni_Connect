package com.example.instagramclone.model;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.instagramclone.R;
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

public class ChatAdapter extends  RecyclerView.Adapter<ChatAdapter.ViewHolder> {
    private Context mcontext;
    private  String uniquenode;
    boolean isMe;
    private List<Chat> mcomments;
    private FirebaseUser firebaseUser ;

    public ChatAdapter(Context mcontext, List<Chat> mcomments,String node) {
        this.mcontext = mcontext;
        this.mcomments = mcomments;
        this.uniquenode =node;
    }

    @NonNull


    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        final Chat chat = mcomments.get(position);
        holder.comment.setText(chat.getMessage());
       // holder.imageprofile.setVisibility(View.INVISIBLE);
        isMe = chat.getPublisher().equals(firebaseUser.getUid());
        setChatRowAppearance(isMe, holder);




        FirebaseDatabase.getInstance().getReference().child("Users").child(chat.getPublisher()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);
                holder.username.setText(user.getUsername());
//                if(user.getImageUrl().equals("default")){
//                    holder.imageprofile.setImageResource(R.drawable.unkown_person_24);
//                }else{
//                    Picasso.get().load(user.getImageUrl()).into(holder.imageprofile);}


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                if (chat.getPublisher().endsWith(firebaseUser.getUid())){
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
                            FirebaseDatabase.getInstance().getReference().child("Messages").child(uniquenode).child(chat.getMessageId())
                                    .removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()){
                                        Toast.makeText(mcontext, "Message Deleted Sucessfully", Toast.LENGTH_SHORT).show();
                                        ad.dismiss();
                                    }
                                    else{
                                        Toast.makeText(mcontext, task.getException()+"", Toast.LENGTH_SHORT).show();
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
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mcontext).inflate(R.layout.user_message,parent,false);
       // final  ChatAdapter.ViewHolder holder =new ChatAdapter.ViewHolder(view);
      //  view.setTag(holder);
        //ChatAdapter.ViewHolder holder1 = (ChatAdapter.ViewHolder) view.getTag();



       // setChatRowAppearance(isMe, holder);


        return new ChatAdapter.ViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return mcomments.size();
    }

    public  class  ViewHolder extends RecyclerView.ViewHolder {
           // public CircleImageView imageprofile;
            public TextView username;
            public  TextView comment;
        LinearLayout.LayoutParams params;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
           // imageprofile = itemView.findViewById(R.id.profile_image);
            username = itemView.findViewById(R.id.username);
            comment = itemView.findViewById(R.id.comment);
            params = (LinearLayout.LayoutParams) username.getLayoutParams();


        }
    }

    private void setChatRowAppearance(boolean isItMe, ViewHolder holder) {

        if (isItMe) {
            holder.params.gravity = Gravity.END;
            holder.username.setTextColor(Color.GREEN);

            // If you want to use colours from colors.xml
            // int colourAsARGB = ContextCompat.getColor(mActivity.getApplicationContext(), R.color.yellow);
            // holder.authorName.setTextColor(colourAsARGB);

            holder.comment.setBackgroundResource(R.drawable.message_background);
        } else {
            holder.params.gravity = Gravity.START;
            holder.username.setTextColor(Color.BLUE);
            holder.comment.setBackgroundResource(R.drawable.message_background);
        }

        holder.username.setLayoutParams(holder.params);
        holder.comment.setLayoutParams(holder.params);

    }
}
