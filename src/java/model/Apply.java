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
    private String status;
    private String note;

    public Apply() {
    }

    public Apply(int applyId, int jobPostId, int candidateId, int cvId, Date dayCreate, String status, String note) {
        this.applyId = applyId;
        this.jobPostId = jobPostId;
        this.candidateId = candidateId;
        this.cvId = cvId;
        this.dayCreate = dayCreate;
        this.status = status;
        this.note = note;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    @Override
    public String toString() {
        return "Apply{" + "applyId=" + applyId + ", jobPostId=" + jobPostId + ", candidateId=" + candidateId + ", cvId=" + cvId + ", dayCreate=" + dayCreate + ", status=" + status + ", note=" + note + '}';
    }
    
    
}
