/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.math.BigDecimal;
import java.util.List;

public class Service {
    private int serviceID;
    private String serviceName;
    private BigDecimal price;
    private String description;
    private int duration;
    private boolean isVisible;
     private int jobPostAmount;    
    private boolean isUnlimited; 

   private List<Promotion> promotions;
   private List<Function> functions;

   
    public Service() {
    }

    public Service(int serviceID, String serviceName, BigDecimal price, String description, int duration, boolean isVisible, int jobPostAmount) {
        this.serviceID = serviceID;
        this.serviceName = serviceName;
        this.price = price;
        this.description = description;
        this.duration = duration;
        this.isVisible = isVisible;
        this.jobPostAmount = jobPostAmount;
      
    }

  



  
    public int getServiceID() {
        return serviceID;
    }

    public void setServiceID(int serviceID) {
        this.serviceID = serviceID;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public boolean getIsVisible() {
        return isVisible;
    }

    public void setIsVisible(boolean visible) {
        isVisible = visible;
    }

    public List<Promotion> getPromotions() {
        return promotions;
    }

    public void setPromotions(List<Promotion> promotions) {
        this.promotions = promotions;
    }

    public int getJobPostAmount() {
        return jobPostAmount;
    }

    public void setJobPostAmount(int jobPostAmount) {
        this.jobPostAmount = jobPostAmount;
    }

    public boolean isIsUnlimited() {
        return isUnlimited;
    }

    public void setIsUnlimited(boolean isUnlimited) {
        this.isUnlimited = isUnlimited;
    }

    public List<Function> getFunctions() {
        return functions;
    }

    public void setFunctions(List<Function> functions) {
        this.functions = functions;
    }
    

    @Override
    public String toString() {
        return "Service{" +
                "serviceID=" + serviceID +
                ", serviceName='" + serviceName + '\'' +
                ", price=" + price +
                ", description='" + description + '\'' +
                ", duration=" + duration +
                ", isVisible=" + isVisible +
                ", promotions=" + (promotions != null ? promotions.size() : 0) +    
                '}';
    }
}
