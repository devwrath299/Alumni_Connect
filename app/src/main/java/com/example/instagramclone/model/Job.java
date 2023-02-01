package com.example.instagramclone.model;

public class Job {
    private  String companyName;
    private  String jobDescription;
    private  String jobDomain;
    private String jobRole;

    public Job() {
    }

    public Job(String companyName, String jobDescription, String jobDomain, String jobRole) {
        this.companyName = companyName;
        this.jobDescription = jobDescription;
        this.jobDomain = jobDomain;
        this.jobRole = jobRole;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getJobDescription() {
        return jobDescription;
    }

    public void setJobDescription(String jobDescription) {
        this.jobDescription = jobDescription;
    }

    public String getJobDomain() {
        return jobDomain;
    }

    public void setJobDomain(String jobDomain) {
        this.jobDomain = jobDomain;
    }

    public String getJobRole() {
        return jobRole;
    }

    public void setJobRole(String jobRole) {
        this.jobRole = jobRole;
    }
}
