package com.example.instagramclone.model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.instagramclone.R;
import com.google.android.material.switchmaterial.SwitchMaterial;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

public class CollegeAdapter extends  RecyclerView.Adapter<CollegeAdapter.ViewHolder> {
    private Context mcontext;
    private List<College> collegeList;

    public CollegeAdapter(Context mcontext,List<College> collegeList) {
        this.mcontext = mcontext;
        this.collegeList = collegeList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mcontext).inflate(R.layout.college_list_item,viewGroup,false);
        return new CollegeAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        final College college = collegeList.get(i);
        viewHolder.collegeName.setText(college.getCollegeName());
        viewHolder.collegeAddress.setText(college.getCollegeAddress());
        viewHolder.collegeID.setText(college.getCollegeId());
        if(college.isHideSwitch()){
            viewHolder.isSelectedSwitch.setVisibility(View.GONE);
        }
        viewHolder.isSelectedSwitch.setSelected(college.isSelected);

    }

    @Override
    public int getItemCount() {
        return collegeList.size();
    }

    public  class ViewHolder extends RecyclerView.ViewHolder {
        public TextView collegeName;
        public  TextView collegeAddress;
        public  TextView collegeID;
        public SwitchMaterial isSelectedSwitch;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            collegeName = itemView.findViewById(R.id.collgeName);
            collegeAddress = itemView.findViewById(R.id.collegeAddress);
            collegeID = itemView.findViewById(R.id.collegeId);
            isSelectedSwitch = itemView.findViewById(R.id.collegeSelectedSwitch);

        }
    }
}
