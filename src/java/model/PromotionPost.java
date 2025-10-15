/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.time.LocalDateTime;

/**
 *
 * @author vuthienkhiem
 */
public class PromotionPost {
     private int promotionPostID;
    private int serviceID;
    private String title;
    private String content;
    private String bannerImage;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private boolean isActive;
    private String displayPosition;
    private int priorityLevel;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private int createdBy; 

    public PromotionPost() {
    }

    public PromotionPost(int promotionPostID,  int serviceID, String title, String content, String bannerImage, LocalDateTime startDate, LocalDateTime endDate, boolean isActive, String displayPosition, int priorityLevel, LocalDateTime createdAt, LocalDateTime updatedAt, int createdBy) {
        this.promotionPostID = promotionPostID;
        this.serviceID = serviceID;
        this.title = title;
        this.content = content;
        this.bannerImage = bannerImage;
        this.startDate = startDate;
        this.endDate = endDate;
        this.isActive = isActive;
        this.displayPosition = displayPosition;
        this.priorityLevel = priorityLevel;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.createdBy = createdBy;
    }

    public int getPromotionPostID() {
        return promotionPostID;
    }

    public void setPromotionPostID(int promotionPostID) {
        this.promotionPostID = promotionPostID;
    }

    public int getServiceID() {
        return serviceID;
    }

    public void setServiceID(int serviceID) {
        this.serviceID = serviceID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getBannerImage() {
        return bannerImage;
    }

    public void setBannerImage(String bannerImage) {
        this.bannerImage = bannerImage;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public boolean isIsActive() {
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }

    public String getDisplayPosition() {
        return displayPosition;
    }

    public void setDisplayPosition(String displayPosition) {
        this.displayPosition = displayPosition;
    }

    public int getPriorityLevel() {
        return priorityLevel;
    }

    public void setPriorityLevel(int priorityLevel) {
        this.priorityLevel = priorityLevel;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public int getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy( int createdBy) {
        this.createdBy = createdBy;
    }

}
