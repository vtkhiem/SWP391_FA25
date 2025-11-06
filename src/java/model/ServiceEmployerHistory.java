package model;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class ServiceEmployerHistory {
    private int historyID;
    private int employerID;
    private int serviceID;
    private String serviceName;
    private Timestamp registerDate;
    private Timestamp expirationDate;
    private String paymentStatus;
    private String actionType;
    private Timestamp createdAt;
    private int code;

    public ServiceEmployerHistory() {
    }

    public ServiceEmployerHistory(int historyID, int employerID, int serviceID,
            String serviceName, Timestamp registerDate, Timestamp expirationDate,
            String paymentStatus, String actionType, Timestamp createdAt, int code) {
        this.historyID = historyID;
        this.employerID = employerID;
        this.serviceID = serviceID;
        this.serviceName = serviceName;
        this.registerDate = registerDate;
        this.expirationDate = expirationDate;
        this.paymentStatus = paymentStatus;
        this.actionType = actionType;
        this.createdAt = createdAt;
        this.code = code;
    }

    public int getHistoryID() {
        return historyID;
    }

    public void setHistoryID(int historyID) {
        this.historyID = historyID;
    }

    public int getEmployerID() {
        return employerID;
    }

    public void setEmployerID(int employerID) {
        this.employerID = employerID;
    }

    public int getServiceID() {
        return serviceID;
    }

    public void setServiceID(int serviceID) {
        this.serviceID = serviceID;
    }

    public Timestamp getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(Timestamp registerDate) {
        this.registerDate = registerDate;
    }

    public Timestamp getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Timestamp expirationDate) {
        this.expirationDate = expirationDate;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public String getActionType() {
        return actionType;
    }

    public void setActionType(String actionType) {
        this.actionType = actionType;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }
    
    public int getCode() {
        return code;
    }
    
    public void setCode(int code) {
        this.code = code;
    }
}