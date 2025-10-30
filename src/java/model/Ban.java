/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.time.Instant;

/**
 *
 * @author ADMIN
 */
public class Ban {
     private int banId;
    private Integer employerId;   
    private Integer candidateId;  
    private Instant bannedAt;    
    private Instant bannedUntil;

    public Ban(int banId, Integer employerId, Integer candidateId, Instant bannedAt, Instant bannedUntil) {
        this.banId = banId;
        this.employerId = employerId;
        this.candidateId = candidateId;
        this.bannedAt = bannedAt;
        this.bannedUntil = bannedUntil;
    }

    public Ban() {
    }

    public int getBanId() {
        return banId;
    }

    public void setBanId(int banId) {
        this.banId = banId;
    }

    public Integer getEmployerId() {
        return employerId;
    }

    public void setEmployerId(Integer employerId) {
        this.employerId = employerId;
    }

    public Integer getCandidateId() {
        return candidateId;
    }

    public void setCandidateId(Integer candidateId) {
        this.candidateId = candidateId;
    }

    public Instant getBannedAt() {
        return bannedAt;
    }

    public void setBannedAt(Instant bannedAt) {
        this.bannedAt = bannedAt;
    }

    public Instant getBannedUntil() {
        return bannedUntil;
    }

    public void setBannedUntil(Instant bannedUntil) {
        this.bannedUntil = bannedUntil;
    }
    
}
