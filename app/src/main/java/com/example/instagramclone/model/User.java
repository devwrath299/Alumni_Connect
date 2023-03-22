package com.example.instagramclone.model;

public class User {
     private String Username;
    private String Name;
    private String Email;
    private String Password;
    private String UserId;
    private String collegeId;
    private String bio;
    private String ImageUrl;
    private String jobRole;
    private String companyName;

    public String getJobRole() {
        return jobRole;
    }

    public void setJobRole(String jobRole) {
        this.jobRole = jobRole;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public User() {
    }

    public User(String username, String name, String email, String password, String userId, String collegeId, String bio, String imageUrl, String jobRole, String companyName) {
        Username = username;
        Name = name;
        Email = email;
        Password = password;
        UserId = userId;
        this.collegeId = collegeId;
        this.bio = bio;
        ImageUrl = imageUrl;
        this.jobRole = jobRole;
        this.companyName = companyName;
    }

    public String getCollegeId() {
        return collegeId;
    }

    public User(String username, String name, String email, String password, String userId, String collegeId, String bio, String imageUrl) {
        Username = username;
        Name = name;
        Email = email;
        Password = password;
        UserId = userId;
        this.collegeId = collegeId;
        this.bio = bio;
        ImageUrl = imageUrl;
    }

    public void setCollegeId(String collegeId) {
        this.collegeId = collegeId;
    }

    public User(String username, String name, String email, String password, String userId, String bio, String imageUrl) {
        Username = username;
        Name = name;
        Email = email;
        Password = password;
        UserId = userId;
        this.bio = bio;
        ImageUrl = imageUrl;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getImageUrl() {
        return ImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        ImageUrl = imageUrl;
    }
}
