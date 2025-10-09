/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * @author Admin
 */
public class Promotion {
     private int promotionID;
    private String code;
    private BigDecimal discount;
    private Date dateSt;
    private Date dateEn;
    private Date dateCr;
    private String description;
     private boolean status;

    public Promotion() {
    }

    public Promotion(int promotionID, String code, BigDecimal discount, Date dateSt, Date dateEn, Date dateCr, String description, boolean status) {
        this.promotionID = promotionID;
        this.code = code;
        this.discount = discount;
        this.dateSt = dateSt;
        this.dateEn = dateEn;
        this.dateCr = dateCr;
        this.description = description;
        this.status = status;
    }

   

    public int getPromotionID() {
        return promotionID;
    }

    public void setPromotionID(int promotionID) {
        this.promotionID = promotionID;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public BigDecimal getDiscount() {
        return discount;
    }

    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }

    public Date getDateSt() {
        return dateSt;
    }

    public void setDateSt(Date dateSt) {
        this.dateSt = dateSt;
    }

    public Date getDateEn() {
        return dateEn;
    }

    public void setDateEn(Date dateEn) {
        this.dateEn = dateEn;
    }

    public Date getDateCr() {
        return dateCr;
    }

    public void setDateCr(Date dateCr) {
        this.dateCr = dateCr;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
    
     

}
