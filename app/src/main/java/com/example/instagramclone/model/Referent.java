package com.example.instagramclone.model;

public class Referent {
    String coverLetter;
    String postId;
    String publisher;
    String resumeLetter;
    String userId;

    public Referent() {

    }

    @Override
    public String toString() {
        return "Referent{" +
                "coverLetter='" + coverLetter + '\'' +
                ", postId='" + postId + '\'' +
                ", publisher='" + publisher + '\'' +
                ", resumeLetter='" + resumeLetter + '\'' +
                ", userId='" + userId + '\'' +
                '}';
    }

    public Referent(String coverLetter, String postId, String publisher, String resumeLetter, String userId) {
        this.coverLetter = coverLetter;
        this.postId = postId;
        this.publisher = publisher;
        this.resumeLetter = resumeLetter;
        this.userId = userId;
    }

    public String getCoverLetter() {
        return coverLetter;
    }

    public void setCoverLetter(String coverLetter) {
        this.coverLetter = coverLetter;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getResumeLetter() {
        return resumeLetter;
    }

    public void setResumeLetter(String resumeLetter) {
        this.resumeLetter = resumeLetter;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
