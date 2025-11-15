/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.time.LocalDateTime;
import java.util.List;

/**
 *
 * @author Admin
 */
public class Feedback {
     private int feedbackID;
    private Integer employerID;      
    private Integer candidateID;     
    private String subject;
    private String content;
    private LocalDateTime createdAt;
    private String status;            
    private String adminResponse;
    private LocalDateTime respondedAt; 
    private Integer serviceID;
private Integer promotionID;
private String email;
private List<TypeFeedback> types;

    public Feedback() {
    }

    public Feedback( Integer employerID, Integer candidateID, String subject, String content,  Integer serviceID, Integer promotionID, String email) {
     
        this.employerID = employerID;
        this.candidateID = candidateID;
        this.subject = subject;
        this.content = content;
     
      
        this.serviceID = serviceID;
        this.promotionID = promotionID;
        this.email = email;
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

    public Integer getServiceID() {
        return serviceID;
    }

    public void setServiceID(Integer serviceID) {
        this.serviceID = serviceID;
    }

    public Integer getPromotionID() {
        return promotionID;
    }

    public void setPromotionID(Integer promotionID) {
        this.promotionID = promotionID;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<TypeFeedback> getTypes() {
        return types;
    }

    public void setTypes(List<TypeFeedback> types) {
        this.types = types;
    }
    
    
}
