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
public class EmployerWall {
        private int wallID;
    private int employerID;
    private int jobPostID;
    private LocalDateTime createdAt;
    private boolean isPinned;
    private LocalDateTime pinnedAt;
    private boolean isActive;
    private int displayOrder;

    public EmployerWall() {
    }

    public EmployerWall(int wallID, int employerID, int jobPostID, LocalDateTime createdAt, boolean isPinned, LocalDateTime pinnedAt, boolean isActive, int displayOrder) {
        this.wallID = wallID;
        this.employerID = employerID;
        this.jobPostID = jobPostID;
        this.createdAt = createdAt;
        this.isPinned = isPinned;
        this.pinnedAt = pinnedAt;
        this.isActive = isActive;
        this.displayOrder = displayOrder;
    }

    

    public int getWallID() {
        return wallID;
    }

    public void setWallID(int wallID) {
        this.wallID = wallID;
    }

    public int getEmployerID() {
        return employerID;
    }

    public void setEmployerID(int employerID) {
        this.employerID = employerID;
    }

    public int getJobPostID() {
        return jobPostID;
    }

    public void setJobPostID(int jobPostID) {
        this.jobPostID = jobPostID;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public boolean isIsPinned() {
        return isPinned;
    }

    public void setIsPinned(boolean isPinned) {
        this.isPinned = isPinned;
    }

    public LocalDateTime getPinnedAt() {
        return pinnedAt;
    }

    public void setPinnedAt(LocalDateTime pinnedAt) {
        this.pinnedAt = pinnedAt;
    }

    public boolean isIsActive() {
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }

    public int getDisplayOrder() {
        return displayOrder;
    }

    public void setDisplayOrder(int displayOrder) {
        this.displayOrder = displayOrder;
    }

    
}
