/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author vuthienkhiem
 */
public class EmailBanned {
    private int emailBannedId;
    private String email;
    private String role;
    private String reason;

    public EmailBanned() {
    }

    public EmailBanned(int emailBanned, String email, String role, String reason) {
        this.emailBannedId = emailBanned;
        this.email = email;
        this.role = role;
        this.reason = reason;
    }

    public int getEmailBannedId() {
        return emailBannedId;
    }

    public void setEmailBannedId(int emailBanned) {
        this.emailBannedId = emailBanned;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
    
    
}
