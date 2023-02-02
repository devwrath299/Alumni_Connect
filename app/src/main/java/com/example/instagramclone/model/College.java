package com.example.instagramclone.model;

public class College {
    String collegeName;
    String collegeId;
    String collegeAddress;

    public College() {

    }

    public College(String collegeName, String collegeId, String collegeAddress) {
        this.collegeName = collegeName;
        this.collegeId = collegeId;
        this.collegeAddress = collegeAddress;
    }

    public String getCollegeName() {
        return collegeName;
    }

    public void setCollegeName(String collegeName) {
        this.collegeName = collegeName;
    }

    public String getCollegeId() {
        return collegeId;
    }

    public void setCollegeId(String collegeId) {
        this.collegeId = collegeId;
    }

    public String getCollegeAddress() {
        return collegeAddress;
    }

    public void setCollegeAddress(String collegeAddress) {
        this.collegeAddress = collegeAddress;
    }
}
