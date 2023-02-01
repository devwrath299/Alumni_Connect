package com.example.instagramclone.model;

public class Notification {
    private String userid;
    private String text;
    private String postid;
    private boolean isPost;
    private  String NotificationId;

    public Notification() {
    }

    public Notification(String userid, String text, String postid, boolean isPost,String NotificationId) {
        this.userid = userid;
        this.text = text;
        this.postid = postid;
        this.isPost = isPost;
        this.NotificationId = NotificationId;
    }

    public String getNotificationId() {
        return NotificationId;
    }

    public void setNotificationId(String notificationId) {
        NotificationId = notificationId;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getPostid() {
        return postid;
    }

    public void setPostid(String postid) {
        this.postid = postid;
    }

    public boolean isPost() {
        return isPost;
    }

    public void setIsPost(boolean post) {
        isPost = post;
    }
}
