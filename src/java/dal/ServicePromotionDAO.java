/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.Promotion;
import model.ServicePromotion;

public class ServicePromotionDAO extends DBContext {

    // ✅ Thêm quan hệ giữa Service và Promotion
    public boolean addServicePromotion(ServicePromotion sp) throws SQLException {
        String sql = "INSERT INTO ServicePromotion (ServiceID, PromotionID) VALUES (?, ?)";
        try (PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, sp.getServiceID());
            ps.setInt(2, sp.getPromotionID());
            return ps.executeUpdate() > 0;
        }
    }

    // ✅ Xóa quan hệ giữa Service và Promotion
    public boolean deleteServicePromotion(int serviceID, int promotionID) throws SQLException {
        String sql = "DELETE FROM ServicePromotion WHERE ServiceID = ? AND PromotionID = ?";
        try (PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, serviceID);
            ps.setInt(2, promotionID);
            return ps.executeUpdate() > 0;
        }
    }

    // ✅ Xóa toàn bộ khuyến mãi của 1 Service
    public boolean deletePromotionsByService(int serviceID) throws SQLException {
        String sql = "DELETE FROM ServicePromotion WHERE ServiceID = ?";
        try (PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, serviceID);
            return ps.executeUpdate() > 0;
        }
    }

    // ✅ Lấy danh sách PromotionID theo ServiceID
    public List<Integer> getPromotionsByService(int serviceID) throws SQLException {
        List<Integer> list = new ArrayList<>();
        String sql = "SELECT PromotionID FROM ServicePromotion WHERE ServiceID = ?";
        try (PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, serviceID);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(rs.getInt("PromotionID"));
                }
            }
        }
        return list;
    }

    // ✅ Lấy danh sách ServiceID theo PromotionID
    public List<Integer> getServicesByPromotion(int promotionID) throws SQLException {
        List<Integer> list = new ArrayList<>();
        String sql = "SELECT ServiceID FROM ServicePromotion WHERE PromotionID = ?";
        try (PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, promotionID);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(rs.getInt("ServiceID"));
                }
            }
        }
        return list;
    }

    // ✅ Kiểm tra xem Service có khuyến mãi cụ thể chưa
    public boolean exists(int serviceID, int promotionID) throws SQLException {
        String sql = "SELECT 1 FROM ServicePromotion WHERE ServiceID = ? AND PromotionID = ?";
        try (PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, serviceID);
            ps.setInt(2, promotionID);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next(); // nếu có record → true
            }
        }
    }
     // Lấy danh sách Promotion của 1 Service
 public List<Promotion> getPromotionsByServiceId(int serviceId) throws SQLException {
    List<Promotion> promotions = new ArrayList<>();
    String sql = """
        SELECT p.PromotionID, p.Code, p.Discount, p.Description, p.DateSt, p.DateEn, p.DateCr, p.Status
        FROM ServicePromotion sp
        JOIN Promotion p ON sp.PromotionID = p.PromotionID
        WHERE sp.ServiceID = ?
    """;

    try (PreparedStatement ps = c.prepareStatement(sql)) {
        ps.setInt(1, serviceId);
        try (ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Promotion promotion = new Promotion();
                promotion.setPromotionID(rs.getInt("PromotionID"));
                promotion.setCode(rs.getString("Code"));
                promotion.setDiscount(rs.getBigDecimal("Discount"));
                promotion.setDescription(rs.getString("Description"));
                promotion.setDateSt(rs.getTimestamp("DateSt"));
                promotion.setDateEn(rs.getTimestamp("DateEn"));
                promotion.setDateCr(rs.getTimestamp("DateCr"));
                promotion.setStatus(rs.getBoolean("Status"));
                if(rs.getBoolean("Status")){
                       promotions.add(promotion);
                }

              
            }
        }
    }
    return promotions;
}
  public List<Promotion> getPromotionsDateFineByServiceId(int serviceId) throws SQLException {
    List<Promotion> promotions = new ArrayList<>();
    String sql = """
        SELECT p.PromotionID, p.Code, p.Discount, p.Description, p.DateSt, p.DateEn, p.DateCr, p.Status
        FROM ServicePromotion sp
        JOIN Promotion p ON sp.PromotionID = p.PromotionID
        WHERE sp.ServiceID = ?
                  AND GETDATE() BETWEEN p.DateSt AND p.DateEn
    """;

    try (PreparedStatement ps = c.prepareStatement(sql)) {
        ps.setInt(1, serviceId);
        try (ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Promotion promotion = new Promotion();
                promotion.setPromotionID(rs.getInt("PromotionID"));
                promotion.setCode(rs.getString("Code"));
                promotion.setDiscount(rs.getBigDecimal("Discount"));
                promotion.setDescription(rs.getString("Description"));
                promotion.setDateSt(rs.getTimestamp("DateSt"));
                promotion.setDateEn(rs.getTimestamp("DateEn"));
                promotion.setDateCr(rs.getTimestamp("DateCr"));
                promotion.setStatus(rs.getBoolean("Status"));
                if(rs.getBoolean("Status")){
                       promotions.add(promotion);
                }

              
            }
        }
    }
    return promotions;
}
 public Promotion getBestPromotionForService(int serviceId) throws SQLException {
    String sql = """
        SELECT TOP 1 p.*
        FROM Promotion p
        INNER JOIN ServicePromotion sp ON p.PromotionID = sp.PromotionID
        WHERE sp.ServiceID = ?
          AND p.Status = 1
          AND GETDATE() BETWEEN p.DateSt AND p.DateEn
        ORDER BY p.Discount DESC, p.DateSt DESC
    """;

    try (PreparedStatement ps = c.prepareStatement(sql)) {
        ps.setInt(1, serviceId);
        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return new Promotion(
                        rs.getInt("PromotionID"),
                        rs.getString("Code"),
                        rs.getBigDecimal("Discount"),
                        rs.getTimestamp("DateSt"),
                        rs.getTimestamp("DateEn"),
                        rs.getTimestamp("DateCr"),
                        rs.getString("Description"),
                        rs.getBoolean("Status")
                );
            }
        }
    }
    return null; // không có khuyến mãi phù hợp
}
 
}
