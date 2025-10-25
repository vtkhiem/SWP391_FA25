/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author ADMIN
 */
public class StaffView {
    private int adminId;
    private String username;
    private String roles;
     private String password;
    public int getAdminId() { 
        return adminId; 
    }
    public void setAdminId(int adminId) { 
        this.adminId = adminId; 
    }

    public String getUsername() { 
        return username; 
    }
    public void setUsername(String username) { 
        this.username = username; }

    public String getRoles() { 
        return roles; 
    }
    public void setRoles(String roles) { 
        this.roles = roles; 
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
}

