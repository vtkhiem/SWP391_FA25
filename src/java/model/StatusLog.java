/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author shiro
 */
import java.time.LocalDateTime;

public class StatusLog {
    private int transitionId;
    private int applyId;
    private int employerId;
    private int fromStatusId;
    private int toStatusId;
    private String note;
    private LocalDateTime transitionDate;

    public StatusLog() {
    }

    public StatusLog(int transitionId, int applyId, int employerId, int fromStatusId, int toStatusId, String note, LocalDateTime transitionDate) {
        this.transitionId = transitionId;
        this.applyId = applyId;
        this.employerId = employerId;
        this.fromStatusId = fromStatusId;
        this.toStatusId = toStatusId;
        this.note = note;
        this.transitionDate = transitionDate;
    }

    public int getTransitionId() {
        return transitionId;
    }

    public void setTransitionId(int transitionId) {
        this.transitionId = transitionId;
    }

    public int getApplyId() {
        return applyId;
    }

    public void setApplyId(int applyId) {
        this.applyId = applyId;
    }

    public int getEmployerId() {
        return employerId;
    }

    public void setEmployerId(int employerId) {
        this.employerId = employerId;
    }

    public int getFromStatusId() {
        return fromStatusId;
    }

    public void setFromStatusId(int fromStatusId) {
        this.fromStatusId = fromStatusId;
    }

    public int getToStatusId() {
        return toStatusId;
    }

    public void setToStatusId(int toStatusId) {
        this.toStatusId = toStatusId;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public LocalDateTime getTransitionDate() {
        return transitionDate;
    }

    public void setTransitionDate(LocalDateTime transitionDate) {
        this.transitionDate = transitionDate;
    }

    @Override
    public String toString() {
        return "StatusLog{" + "transitionId=" + transitionId + ", applyId=" + applyId + ", employerId=" + employerId + ", fromStatusId=" + fromStatusId + ", toStatusId=" + toStatusId + ", note=" + note + ", transitionDate=" + transitionDate + '}';
    }

}

