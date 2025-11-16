package model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public class OrderView implements Serializable {

    private int orderId;
    private int employerId;
    private String employerName;
    private String employerEmail;
    private int serviceId;  
    private String serviceName;
    private BigDecimal amount;
    private String payMethod;
    private String status;
    private LocalDateTime date;
    private Integer duration;
    private String promotionCode;
    private BigDecimal discountPercent;
    private BigDecimal finalAmount;

    public OrderView() {
    }

    public OrderView(int orderId, int employerId, String employerName, int serviceId, String serviceName, BigDecimal amount, String payMethod, String status, LocalDateTime date, Integer duration, String promotionCode, BigDecimal discountPercent, BigDecimal finalAmount) {
        this.orderId = orderId;
        this.employerId = employerId;
        this.employerName = employerName;
        this.serviceId = serviceId;
        this.serviceName = serviceName;
        this.amount = amount;
        this.payMethod = payMethod;
        this.status = status;
        this.date = date;
        this.duration = duration;
        this.promotionCode = promotionCode;
        this.discountPercent = discountPercent;
        this.finalAmount = finalAmount;
    }

    public String getEmployerEmail() {
        return employerEmail;
    }

    public void setEmployerEmail(String employerEmail) {
        this.employerEmail = employerEmail;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getEmployerId() {
        return employerId;
    }

    public void setEmployerId(int employerId) {
        this.employerId = employerId;
    }

    public String getEmployerName() {
        return employerName;
    }

    public void setEmployerName(String employerName) {
        this.employerName = employerName;
    }

    public int getServiceId() {
        return serviceId;
    }

    public void setServiceId(int serviceId) {
        this.serviceId = serviceId;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
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

    public Date getDate() {
    return Date.from(date.atZone(ZoneId.systemDefault()).toInstant());
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

    public String getPromotionCode() {
        return promotionCode;
    }

    public void setPromotionCode(String promotionCode) {
        this.promotionCode = promotionCode;
    }

    public BigDecimal getDiscountPercent() {
        return discountPercent;
    }

    public void setDiscountPercent(BigDecimal discountPercent) {
        this.discountPercent = discountPercent;
    }

    public BigDecimal getFinalAmount() {
        return finalAmount;
    }

    public void setFinalAmount(BigDecimal finalAmount) {
        this.finalAmount = finalAmount;
    }

public Date getEndDate() {
    if (duration == null || date == null) return null;
    LocalDateTime end = date.plusDays(duration);
    return Date.from(end.atZone(ZoneId.systemDefault()).toInstant());
}


    @Override
    public String toString() {
        return "OrderView{"
                + "orderId=" + orderId
                + ", employerId=" + employerId
                + ", employerName='" + employerName + '\''
                + ", serviceId=" + serviceId
                + ", serviceName='" + serviceName + '\''
                + ", amount=" + amount
                + ", payMethod='" + payMethod + '\''
                + ", status='" + status + '\''
                + ", date=" + date
                + ", duration=" + duration
                + ", promotionCode='" + promotionCode + '\''
                + ", discountPercent=" + discountPercent
                + ", finalAmount=" + finalAmount
                + '}';
    }
}
