/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.sql.Timestamp; 
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
    private Timestamp dayCreate;
    private int statusId;

    public Apply() {
    }

    public Apply(int applyId, int jobPostId, int candidateId, int cvId, Timestamp dayCreate, int StatusId) {
        this.applyId = applyId;
        this.jobPostId = jobPostId;
        this.candidateId = candidateId;
        this.cvId = cvId;
        this.dayCreate = dayCreate;
        this.statusId = StatusId;
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

    public Timestamp getDayCreate() {
        return dayCreate;
    }

    public void setDayCreate(Timestamp dayCreate) {
        this.dayCreate = dayCreate;
    }

    public int getStatusId() {
        return statusId;
    }

    public void setStatusId(int StatusId) {
        this.statusId = StatusId;
    }

    @Override
    public String toString() {
        return "Apply{" + "applyId=" + applyId + ", jobPostId=" + jobPostId + ", candidateId=" + candidateId + ", cvId=" + cvId + ", dayCreate=" + dayCreate + ", StatusId=" + statusId + '}';
    }
}
