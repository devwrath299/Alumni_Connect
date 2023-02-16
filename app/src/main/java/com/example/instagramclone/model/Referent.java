package com.example.instagramclone.model;

public class Referent {
    String coverLetter;
    String postId;
    String publisher;
    String resumeLink;
    String userId;

    public Referent() {

    }

    public Referent(String coverLetter, String postId, String publisher, String resumeLink, String userId) {
        this.coverLetter = coverLetter;
        this.postId = postId;
        this.publisher = publisher;
        this.resumeLink = resumeLink;
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

    public String getResumeLink() {
        return resumeLink;
    }

    public void setResumeLink(String resumeLink) {
        this.resumeLink = resumeLink;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
