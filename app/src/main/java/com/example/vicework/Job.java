package com.example.vicework;

public class Job {
    private String jobname;
    private String startdate;
    private String jobprice;
    private String numberofpeople;
    private String jobgiver;
    private String phonenumber;
    private String amountper;
    private String picture;

    public Job(String jobname,String startdate,String jobprice,String numberofpeople,String jobgiver,String phonenumber,String amountper,String picture)
    {
        this.jobname = jobname;
        this.startdate = startdate;
        this.jobprice = jobprice;
        this.numberofpeople = numberofpeople;
        this.jobgiver = jobgiver;
        this.phonenumber = phonenumber;
        this.amountper = amountper;
        this.picture = picture;

    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getJobname() {
        return jobname;
    }

    public void setJobname(String jobname) {
        this.jobname = jobname;
    }

    public String getStartdate() {
        return startdate;
    }

    public void setStartdate(String startdate) {
        this.startdate = startdate;
    }

    public String getJobprice() {
        return jobprice;
    }

    public void setJobprice(String jobprice) {
        this.jobprice = jobprice;
    }

    public String getNumberofpeople() {
        return numberofpeople;
    }

    public void setNumberofpeople(String numberofpeople) {
        this.numberofpeople = numberofpeople;
    }

    public String getJobgiver() {
        return jobgiver;
    }

    public void setJobgiver(String jobgiver) {
        this.jobgiver = jobgiver;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getAmountper() {
        return amountper;
    }

    public void setAmountper(String amountper) {
        this.amountper = amountper;
    }
}
