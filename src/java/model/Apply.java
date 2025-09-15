/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.util.*;

/**
 *
 * @author shiro
 */
public class Apply {
    private int applyId;
    private int jobPostId;
    private int candidateID;
    private int cvId;
    private String status;
    private String step;
    private Date dayCreate;

    public Apply(int applyId, int jobPostId, int candidateID, int cvId, String status, String step, Date dayCreate) {
        this.applyId = applyId;
        this.jobPostId = jobPostId;
        this.candidateID = candidateID;
        this.cvId = cvId;
        this.status = status;
        this.step = step;
        this.dayCreate = dayCreate;
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

    public int getCandidateID() {
        return candidateID;
    }

    public void setCandidateID(int candidateID) {
        this.candidateID = candidateID;
    }

    public int getCvId() {
        return cvId;
    }

    public void setCvId(int cvId) {
        this.cvId = cvId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStep() {
        return step;
    }

    public void setStep(String step) {
        this.step = step;
    }

    public Date getDayCreate() {
        return dayCreate;
    }

    public void setDayCreate(Date dayCreate) {
        this.dayCreate = dayCreate;
    }

    @Override
    public String toString() {
        return "Apply{" + "applyId=" + applyId + ", jobPostId=" + jobPostId + ", candidateID=" + candidateID + ", cvId=" + cvId + ", status=" + status + ", step=" + step + ", dayCreate=" + dayCreate + '}';
    }
    
    
}
