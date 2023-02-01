package com.example.instagramclone.model;

public class Post {
    private  String PostId;
    private  String ImageUrl;
    private  String Description;
    private  String publisher;
    private  String postType;
    private Job jobData;

    public Post() {
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
}
