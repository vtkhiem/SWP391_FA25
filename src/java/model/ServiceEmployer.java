package model;

import java.sql.Timestamp;

public class ServiceEmployer {

    private int employerID;
    private int serviceID;
    private Timestamp registerDate;
    private Timestamp expirationDate;
    private String paymentStatus;

    public ServiceEmployer() {
    }

    public ServiceEmployer(int employerID, int serviceID, Timestamp registerDate,
            Timestamp expirationDate, String paymentStatus) {
        this.employerID = employerID;
        this.serviceID = serviceID;
        this.registerDate = registerDate;
        this.expirationDate = expirationDate;
        this.paymentStatus = paymentStatus;

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

}
