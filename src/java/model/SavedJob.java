package model;

import java.time.LocalDateTime;

public class SavedJob {
    private int savedJobID;
    private int candidateID;
    private int jobPostID;
    private LocalDateTime savedDate;

    public SavedJob() {
    }

    public SavedJob(int savedJobID, int candidateID, int jobPostID, LocalDateTime savedDate) {
        this.savedJobID = savedJobID;
        this.candidateID = candidateID;
        this.jobPostID = jobPostID;
        this.savedDate = savedDate;
    }

    public int getSavedJobID() {
        return savedJobID;
    }

    public void setSavedJobID(int savedID) {
        this.savedJobID = savedID;
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
}