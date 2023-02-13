package com.example.instagramclone.model;

public class College {
    String collegeName;
    String collegeId;
    String collegeAddress;
    boolean isSelected=false;
    boolean hideSwitch=false;

    public boolean isHideSwitch() {
        return hideSwitch;
    }

    public void setHideSwitch(boolean hideSwitch) {
        this.hideSwitch = hideSwitch;
    }

    public College(String collegeName, String collegeId, String collegeAddress, boolean isSelected) {
        this.collegeName = collegeName;
        this.collegeId = collegeId;
        this.collegeAddress = collegeAddress;
        this.isSelected = isSelected;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

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

    @Override
    public String toString() {
        return "College{" +
                "collegeName='" + collegeName + '\'' +
                ", collegeId='" + collegeId + '\'' +
                ", collegeAddress='" + collegeAddress + '\'' +
                ", isSelected=" + isSelected +
                ", hideSwitch=" + hideSwitch +
                '}';
    }

    public void setCollegeAddress(String collegeAddress) {
        this.collegeAddress = collegeAddress;
    }
}
