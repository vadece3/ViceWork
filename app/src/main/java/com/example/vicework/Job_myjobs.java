package com.example.vicework;

public class Job_myjobs {
    private String jobgiver;
    private String jobname;
    private String startdate;
    private String jobsearcher;
    private String jobposition;
    private String acceptence_status;

    public Job_myjobs(String jobgiver,String jobname,String startdate,String jobsearcher,String jobposition,String acceptence_status)
    {
        this.jobgiver = jobgiver;
        this.jobname = jobname;
        this.startdate = startdate;
        this.jobsearcher = jobsearcher;
        this.jobposition = jobposition;
        this.acceptence_status=acceptence_status;

    }

    public String getAcceptence_status() {
        return acceptence_status;
    }

    public void setAcceptence_status(String acceptence_status) {
        this.acceptence_status = acceptence_status;
    }

    public String getJobposition() {
        return jobposition;
    }

    public void setJobposition(String jobposition) {
        this.jobposition = jobposition;
    }

    public String getJobgiver() {
        return jobgiver;
    }

    public void setJobgiver(String jobgiver) {
        this.jobgiver = jobgiver;
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

    public String getJobsearcher() {
        return jobsearcher;
    }

    public void setJobsearcher(String jobsearcher) {
        this.jobsearcher = jobsearcher;
    }

}
