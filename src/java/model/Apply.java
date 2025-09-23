/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;


import java.util.Date;

/**
 *
 * @author shiro
 */
public class Apply {
    private int applyId;
    private int jobPostId;
    private int candidateId;
    private int cvId;
    private Date dayCreate;
    private int statusId;
    private float rate; 
    private String note;

    public Apply(int applyId, int jobPostId, int candidateId, int cvId, Date dayCreate, int statusId, float rate, String note) {
        this.applyId = applyId;
        this.jobPostId = jobPostId;
        this.candidateId = candidateId;
        this.cvId = cvId;
        this.dayCreate = dayCreate;
        this.statusId = statusId;
        this.rate = rate;
        this.note = note;
    }
    
    public Apply() {
    }

    public int getApplyId() {
        return applyId;
    }

    public void setApplyId(int applyId) {
        this.applyId = applyId;
    }

    public int getJobPostId() {
        return jobPostId;
    }

    public void setJobPostId(int jobPostId) {
        this.jobPostId = jobPostId;
    }

    public int getCandidateId() {
        return candidateId;
    }

    public void setCandidateId(int candidateId) {
        this.candidateId = candidateId;
    }

    public int getCvId() {
        return cvId;
    }

    public void setCvId(int cvId) {
        this.cvId = cvId;
    }

    public Date getDayCreate() {
        return dayCreate;
    }

    public void setDayCreate(Date dayCreate) {
        this.dayCreate = dayCreate;
    }

    public int getStatusId() {
        return statusId;
    }

    public void setStatusId(int statusId) {
        this.statusId = statusId;
    }

    public float getRate() {
        return rate;
    }

    public void setRate(float rate) {
        this.rate = rate;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    @Override
    public String toString() {
        return "Apply{" + "applyId=" + applyId + ", jobPostId=" + jobPostId + ", candidateId=" + candidateId + ", cvId=" + cvId + ", dayCreate=" + dayCreate + ", statusId=" + statusId + ", rate=" + rate + ", note=" + note + '}';
    }
}
