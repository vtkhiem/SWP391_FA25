/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;


import java.time.LocalDate;


public class Candidate {

   private int candidateId;
    private String candidateName;
    private String address;
    private String email;
    private String nationality;
    private String passwordHash;
    private String avatar;

    public Candidate() {
    }

    public Candidate(int candidateId, String candidateName, String address, String email, String nationality, String passwordHash, String avatar) {
        this.candidateId = candidateId;
        this.candidateName = candidateName;
        this.address = address;
        this.email = email;
        this.nationality = nationality;
        this.passwordHash = passwordHash;
        this.avatar = avatar;
    }

    public int getCandidateId() {
        return candidateId;
    }

    public void setCandidateId(int candidateId) {
        this.candidateId = candidateId;
    }

    public String getCandidateName() {
        return candidateName;
    }

    public void setCandidateName(String candidateName) {
        this.candidateName = candidateName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
  
}
