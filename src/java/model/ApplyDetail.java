/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author shiro
 */
public class ApplyDetail {
    private Apply apply;
    private Candidate candidate;
    private CV cv;
    private JobPost job;


    public ApplyDetail() {
    }

    public ApplyDetail(Apply apply, Candidate candidate, CV cv, JobPost job) {
        this.apply = apply;
        this.candidate = candidate;
        this.cv = cv;
        this.job = job;
    }

    public Apply getApply() {
        return apply;
    }

    public void setApply(Apply apply) {
        this.apply = apply;
    }

    public Candidate getCandidate() {
        return candidate;
    }

    public void setCandidate(Candidate candidate) {
        this.candidate = candidate;
    }

    public CV getCv() {
        return cv;
    }

    public void setCv(CV cv) {
        this.cv = cv;
    }

    public JobPost getJob() {
        return job;
    }

    public void setJob(JobPost job) {
        this.job = job;
    }



    @Override
    public String toString() {
        return "ApplyDetail{" + "apply=" + apply + ", candidate=" + candidate + ", cv=" + cv + ", job=" + job + '}';
    }

}
    

    
