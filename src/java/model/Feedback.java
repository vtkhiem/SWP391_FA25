/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.time.LocalDateTime;

/**
 *
 * @author Admin
 */
public class Feedback {
     private int feedbackID;
    private Integer employerID;       // có thể null
    private Integer candidateID;      // có thể null
    private String subject;
    private String content;
    private String type;              // loại feedback: complaint, suggestion, etc.
    private LocalDateTime createdAt;
    private String status;            // Pending / Resolved / In Progress
    private String adminResponse;
    private LocalDateTime respondedAt; 

    public Feedback() {
    }

    public Feedback(int feedbackID, Integer employerID, Integer candidateID, String subject, String content, String type, LocalDateTime createdAt, String status, String adminResponse, LocalDateTime respondedAt) {
        this.feedbackID = feedbackID;
        this.employerID = employerID;
        this.candidateID = candidateID;
        this.subject = subject;
        this.content = content;
        this.type = type;
        this.createdAt = createdAt;
        this.status = status;
        this.adminResponse = adminResponse;
        this.respondedAt = respondedAt;
    }

    public int getFeedbackID() {
        return feedbackID;
    }

    public void setFeedbackID(int feedbackID) {
        this.feedbackID = feedbackID;
    }

    public Integer getEmployerID() {
        return employerID;
    }

    public void setEmployerID(Integer employerID) {
        this.employerID = employerID;
    }

    public Integer getCandidateID() {
        return candidateID;
    }

    public void setCandidateID(Integer candidateID) {
        this.candidateID = candidateID;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAdminResponse() {
        return adminResponse;
    }

    public void setAdminResponse(String adminResponse) {
        this.adminResponse = adminResponse;
    }

    public LocalDateTime getRespondedAt() {
        return respondedAt;
    }

    public void setRespondedAt(LocalDateTime respondedAt) {
        this.respondedAt = respondedAt;
    }
    
}
