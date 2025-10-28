/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author vuthienkhiem
 */
public class FeedbackType {
    private int feedbackID ;
    private int typeFeedbackID;

    public FeedbackType() {
    }

    public FeedbackType(int feedbackID, int typeFeedbackID) {
        this.feedbackID = feedbackID;
        this.typeFeedbackID = typeFeedbackID;
    }

    public int getFeedbackID() {
        return feedbackID;
    }

    public void setFeedbackID(int feedbackID) {
        this.feedbackID = feedbackID;
    }

    public int getTypeFeedbackID() {
        return typeFeedbackID;
    }

    public void setTypeFeedbackID(int typeFeedbackID) {
        this.typeFeedbackID = typeFeedbackID;
    }
    
}
