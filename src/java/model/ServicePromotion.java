/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package model;

import java.util.Date;

public class ServicePromotion {
 
    private int serviceID;
    private int promotionID;
  

    public ServicePromotion() {
    }

    public ServicePromotion(int serviceID, int promotionID) {
        this.serviceID = serviceID;
        this.promotionID = promotionID;
    }

    public int getServiceID() {
        return serviceID;
    }

    public void setServiceID(int serviceID) {
        this.serviceID = serviceID;
    }

    public int getPromotionID() {
        return promotionID;
    }

    public void setPromotionID(int promotionID) {
        this.promotionID = promotionID;
    }

    
}