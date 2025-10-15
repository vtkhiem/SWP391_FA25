/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package dal;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.Promotion;

public class PromotionDAO extends DBContext {

    // 🟢 Thêm mới một promotion
    public boolean addPromotion(Promotion p) throws SQLException {
        String sql = """
            INSERT INTO Promotion (Code, Discount, DateSt, DateEn, DateCr, Description, Status)
            VALUES (?, ?, ?, ?, GETDATE(), ?, ?)
        """;
        try (PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, p.getCode());
            ps.setBigDecimal(2, p.getDiscount());
            ps.setTimestamp(3, new Timestamp(p.getDateSt().getTime()));
            ps.setTimestamp(4, new Timestamp(p.getDateEn().getTime()));
            ps.setString(5, p.getDescription());
            ps.setBoolean(6, p.isStatus());
            return ps.executeUpdate() > 0;
        }
    }

    // 🟣 Cập nhật promotion
    public boolean updatePromotion(Promotion p) throws SQLException {
        String sql = """
            UPDATE Promotion
            SET Code=?, Discount=?, DateSt=?, DateEn=?, Description=?, Status=?
            WHERE PromotionID=?
        """;
        try (PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, p.getCode());
            ps.setBigDecimal(2, p.getDiscount());
            ps.setTimestamp(3, new Timestamp(p.getDateSt().getTime()));
            ps.setTimestamp(4, new Timestamp(p.getDateEn().getTime()));
            ps.setString(5, p.getDescription());
            ps.setBoolean(6, p.isStatus());
            ps.setInt(7, p.getPromotionID());
            return ps.executeUpdate() > 0;
        }
    }

    // 🔴 Xóa promotion
    public boolean deletePromotion(int promotionId) throws SQLException {
        String sql = "DELETE FROM Promotion WHERE PromotionID = ?";
        try (PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, promotionId);
            return ps.executeUpdate() > 0;
        }
    }

    // 🔵 Lấy toàn bộ danh sách khuyến mãi
    public List<Promotion> getAllPromotions() throws SQLException {
        List<Promotion> list = new ArrayList<>();
        String sql = "SELECT * FROM Promotion ORDER BY PromotionID DESC";
        try (PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Promotion p = new Promotion(
                        rs.getInt("PromotionID"),
                        rs.getString("Code"),
                        rs.getBigDecimal("Discount"),
                        rs.getTimestamp("DateSt"),
                        rs.getTimestamp("DateEn"),
                        rs.getTimestamp("DateCr"),
                        rs.getString("Description"),
                        rs.getBoolean("Status")
                );
                list.add(p);
            }
        }
        return list;
    }

    // 🟢 Lấy promotion theo ID
    public Promotion getPromotionById(int id) throws SQLException {
        String sql = "SELECT * FROM Promotion WHERE PromotionID = ?";
        try (PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, id);
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
        return null;
    }

    // 🟣 Lấy promotion đang hoạt động (đang trong khoảng thời gian hiệu lực)
    public List<Promotion> getAllActivePromotions() throws SQLException {
        List<Promotion> list = new ArrayList<>();
        String sql = """
            SELECT * FROM Promotion
            WHERE Status = 1
              AND GETDATE() BETWEEN DateSt AND DateEn
            ORDER BY DateSt DESC
        """;
        try (PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Promotion p = new Promotion(
                        rs.getInt("PromotionID"),
                        rs.getString("Code"),
                        rs.getBigDecimal("Discount"),
                        rs.getTimestamp("DateSt"),
                        rs.getTimestamp("DateEn"),
                        rs.getTimestamp("DateCr"),
                        rs.getString("Description"),
                        rs.getBoolean("Status")
                );
                list.add(p);
            }
        }
        return list;
    }

    // 🔍 Kiểm tra mã khuyến mãi đã tồn tại chưa
    public boolean isCodeExists(String code) throws SQLException {
        String sql = "SELECT COUNT(*) FROM Promotion WHERE Code = ?";
        try (PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, code);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        }
        return false;
    }
    public boolean updatePromotionStatus(int promotionId, boolean status) throws SQLException {
    String sql = "UPDATE Promotion SET Status = ? WHERE PromotionID = ?";
    try (PreparedStatement ps = c.prepareStatement(sql)) {
        ps.setBoolean(1, status);
        ps.setInt(2, promotionId);
        return ps.executeUpdate() > 0;
    }
}
}