/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package dal;

import java.math.BigDecimal;
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
     public List<Promotion> getAllActivePromotions() throws SQLException {
        List<Promotion> list = new ArrayList<>();
        String sql = "SELECT * FROM Promotion where Status = 1 ORDER BY PromotionID DESC";
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
    public List<Promotion> getAllActiveAndDatePromotions() throws SQLException {
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
 public List<Promotion> getAllActiveAndDatePromotionsForAService(int serviceId) throws SQLException {
    List<Promotion> list = new ArrayList<>();
    String sql = """
        SELECT p.*
        FROM Promotion p
        INNER JOIN ServicePromotion sp ON p.PromotionID = sp.PromotionID
        WHERE sp.ServiceID = ?
          AND p.Status = 1
          AND GETDATE() BETWEEN p.DateSt AND p.DateEn
        ORDER BY p.DateSt DESC
    """;

    try (PreparedStatement ps = c.prepareStatement(sql)) {
        ps.setInt(1, serviceId);
        try (ResultSet rs = ps.executeQuery()) {
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
     public Promotion getPromotionByCode(String code) throws SQLException {
        String sql = "SELECT * FROM Promotion WHERE Code = ?";
        try (PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, code);
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
     public Promotion getBestPromotionFromList(List<Promotion> promotions) {
    if (promotions == null || promotions.isEmpty()) {
        return null;
    }

    Promotion best = promotions.get(0);
    for (Promotion p : promotions) {
        // So sánh discount (giả sử discount là BigDecimal)
        if (p.getDiscount().compareTo(best.getDiscount()) > 0) {
            best = p;
        }
    }

    return best;
}


 public static void main(String[] args) {
        PromotionDAO dao = new PromotionDAO();
        try {
            System.out.println("---- TEST PromotionDAO ----");

            // 🟢 Tạo promotion mới
            Promotion p = new Promotion();
            p.setCode("TEST02");
            p.setDiscount(new BigDecimal("10"));
            p.setDateSt(Timestamp.valueOf("2025-10-01 00:00:00"));
            p.setDateEn(Timestamp.valueOf("2025-12-31 23:59:59"));
            p.setDescription("Test giảm giá 10%");
            p.setStatus(true);

            boolean added = dao.addPromotion(p);
            System.out.println("✅ Thêm promotion: " + added);

            // 🔍 Lấy theo code để kiểm tra discount
            Promotion promo = dao.getPromotionByCode("TEST02");
            if (promo != null) {
                System.out.println("🎁 Mã: " + promo.getCode());
                System.out.println("💸 Discount (BigDecimal): " + promo.getDiscount());

                // 🧮 Tính thử giá sau khuyến mãi
                BigDecimal price = new BigDecimal("100"); // 1 triệu
                BigDecimal discountValue = price.multiply(promo.getDiscount());
                BigDecimal finalPrice = price.subtract(discountValue);
                System.out.println("💰 Giá gốc: " + price);
                System.out.println("➡️ Sau giảm: " + finalPrice);
            } else {
                System.out.println("⚠️ Không tìm thấy promotion!");
            }

            // 📋 Lấy danh sách
            List<Promotion> list = dao.getAllPromotions();
            System.out.println("\nDanh sách Promotion:");
            for (Promotion pr : list) {
                System.out.println(pr.getPromotionID() + " | " + pr.getCode() + " | " + pr.getDiscount());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}