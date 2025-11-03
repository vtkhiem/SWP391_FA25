/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author vuthienkhiem
 */
public class TypeFeedback {
    private int typeFeedbackID;
    private String typeFeedbackName;
    private String roleScope;

    public TypeFeedback() {
    }

    public TypeFeedback(int typeFeedbackID, String typeFeedbackName, String roleScope) {
        this.typeFeedbackID = typeFeedbackID;
        this.typeFeedbackName = typeFeedbackName;
        this.roleScope = roleScope;
    }

    public int getTypeFeedbackID() {
        return typeFeedbackID;
    }

    public void setTypeFeedbackID(int typeFeedbackID) {
        this.typeFeedbackID = typeFeedbackID;
    }

    public String getTypeFeedbackName() {
        return typeFeedbackName;
    }

    public void setTypeFeedbackName(String typeFeedbackName) {
        this.typeFeedbackName = typeFeedbackName;
    }

    public String getRoleScope() {
        return roleScope;
    }

    public void setRoleScope(String roleScope) {
        this.roleScope = roleScope;
    }
    
    
   
    
}
