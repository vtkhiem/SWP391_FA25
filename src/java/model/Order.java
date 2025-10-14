/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 *
 * @author Admin
 */
public class Order {
    private int orderID;
    private int employerID;
    private int serviceID;
    private BigDecimal amount;         // 💡 DECIMAL(18,2) → BigDecimal
    private String payMethod;
    private String status;
    private LocalDateTime date;        // DATETIME → LocalDateTime
    private Integer duration;       

    public Order() {
    }

    public Order(int orderID, int employerID, int serviceID, BigDecimal amount, String payMethod, String status, LocalDateTime date, Integer duration) {
        this.orderID = orderID;
        this.employerID = employerID;
        this.serviceID = serviceID;
        this.amount = amount;
        this.payMethod = payMethod;
        this.status = status;
        this.date = date;
        this.duration = duration;
    }

    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
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

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getPayMethod() {
        return payMethod;
    }

    public void setPayMethod(String payMethod) {
        this.payMethod = payMethod;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }
    
    
}
