package com.example.instagramclone.model;


import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONObject;

import java.io.Serializable;


public class Post implements Parcelable {
    private  String PostId;
    private  String ImageUrl;
    private  String Description;
    private  String publisher;
    private  String postType;
    private String collegeId;
    private Job jobData;

    public Post() {
    }

    protected Post(Parcel in) {
        PostId = in.readString();
        ImageUrl = in.readString();
        Description = in.readString();
        publisher = in.readString();
        postType = in.readString();
        collegeId = in.readString();
    }

    public static final Creator<Post> CREATOR = new Creator<Post>() {
        @Override
        public Post createFromParcel(Parcel in) {
            return new Post(in);
        }

        @Override
        public Post[] newArray(int size) {
            return new Post[size];
        }
    };

    public String getCollegeId() {
        return collegeId;
    }

    public void setCollegeId(String collegeId) {
        this.collegeId = collegeId;
    }

    public Post(String postId, String imageUrl, String description, String publisher, String postType, String collegeId, Job jobData) {
        PostId = postId;
        ImageUrl = imageUrl;
        Description = description;
        this.publisher = publisher;
        this.postType = postType;
        this.collegeId = collegeId;
        this.jobData = jobData;
    }

    public Post(String postId, String imageUrl, String description, String publisher, String postType, Job jobData) {
        PostId = postId;
        ImageUrl = imageUrl;
        Description = description;
        this.publisher = publisher;
        this.postType = postType;
        this.jobData = jobData;
    }

    public String getPostType() {
        return postType;
    }

    public void setPostType(String postType) {
        this.postType = postType;
    }

    public Job getJobData() {
        return jobData;
    }

    public void setJobData(Job jobData) {
        this.jobData = jobData;
    }

    public Post(String postId, String imageUrl, String description, String publisher) {
        PostId = postId;
        ImageUrl = imageUrl;
        Description = description;
        this.publisher = publisher;
    }

    public String getPostId() {
        return PostId;
    }

    public void setPostId(String postId) {
        PostId = postId;
    }

    public String getImageUrl() {
        return ImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        ImageUrl = imageUrl;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    @Override
    public String toString() {
        return "Post{" +
                "PostId='" + PostId + '\'' +
                ", ImageUrl='" + ImageUrl + '\'' +
                ", Description='" + Description + '\'' +
                ", publisher='" + publisher + '\'' +
                ", postType='" + postType + '\'' +
                ", jobData=" + jobData +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(PostId);
        parcel.writeString(ImageUrl);
        parcel.writeString(Description);
        parcel.writeString(publisher);
        parcel.writeString(postType);
        parcel.writeString(collegeId);
    }
}
