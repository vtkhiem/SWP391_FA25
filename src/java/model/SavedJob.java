package model;

import java.time.LocalDateTime;

public class SavedJob {
    private int savedID;
    private int candidateID;
    private int jobPostID;
    private LocalDateTime savedDate;
    private JobPost job;

    public SavedJob() {
    }

    public SavedJob(int candidateID, int jobPostID, LocalDateTime savedDate) {
        this.candidateID = candidateID;
        this.jobPostID = jobPostID;
        this.savedDate = savedDate;
    }

    // Getters & setters
    public int getSavedID() {
        return savedID;
    }

    public void setSavedID(int savedID) {
        this.savedID = savedID;
    }

    public int getCandidateID() {
        return candidateID;
    }

    public void setCandidateID(int candidateID) {
        this.candidateID = candidateID;
    }

    public int getJobPostID() {
        return jobPostID;
    }

    public void setJobPostID(int jobPostID) {
        this.jobPostID = jobPostID;
    }

    public LocalDateTime getSavedDate() {
        return savedDate;
    }

    public void setSavedDate(LocalDateTime savedDate) {
        this.savedDate = savedDate;
    }

    public JobPost getJob() {
        return job;
    }

    public void setJob(JobPost job) {
        this.job = job;
    }
}