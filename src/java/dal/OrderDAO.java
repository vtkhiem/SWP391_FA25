/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;
import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import model.Order;
import model.OrderView;

/**
 *
 * @author Admin
 */
public class OrderDAO extends DBContext{
      public boolean addOrder(Order order) throws SQLException {
        String sql = "INSERT INTO Orders (EmployerID, ServiceID, Amount, PayMethod, Status, Date, Duration) VALUES (?, ?, ?, ?, ?, GETDATE(), ?)";
        try (PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, order.getEmployerID());
            ps.setInt(2, order.getServiceID());
            ps.setBigDecimal(3, order.getAmount());
            ps.setString(4, order.getPayMethod());
            ps.setString(5, order.getStatus());
            ps.setInt(6, order.getDuration());
            return ps.executeUpdate() > 0;
        }
    }public boolean updateOrderStatus(int orderID, String newStatus) throws SQLException {
        String sql = "UPDATE Orders SET Status = ? WHERE OrderID = ?";
        try (PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, newStatus);
            ps.setInt(2, orderID);
            return ps.executeUpdate() > 0;
        }
    }

    // 🔵 Lấy danh sách tất cả đơn hàng
    public List<Order> getAllOrders() throws SQLException {
        List<Order> list = new ArrayList<>();
        String sql = "SELECT * FROM Orders ORDER BY OrderID DESC";
        try (PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Order order = new Order(
                        rs.getInt("OrderID"),
                        rs.getInt("EmployerID"),
                        rs.getInt("ServiceID"),
                        rs.getBigDecimal("Amount"),
                        rs.getString("PayMethod"),
                        rs.getString("Status"),
                        rs.getTimestamp("Date").toLocalDateTime(),
                        rs.getInt("Duration")
                );
                list.add(order);
            }
        }
        return list;
    }

    // 🟣 Lấy đơn hàng theo ID
    public Order getOrderById(int orderID) throws SQLException {
        String sql = "SELECT * FROM Orders WHERE OrderID = ?";
        try (PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, orderID);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Order(
                            rs.getInt("OrderID"),
                            rs.getInt("EmployerID"),
                            rs.getInt("ServiceID"),
                            rs.getBigDecimal("Amount"),
                            rs.getString("PayMethod"),
                            rs.getString("Status"),
                            rs.getTimestamp("Date").toLocalDateTime(),
                            rs.getInt("Duration")
                    );
                }
            }
        }
        return null;
    }

    // 🟤 Lấy danh sách đơn hàng theo EmployerID
    public List<Order> getOrdersByEmployer(int employerID) throws SQLException {
        List<Order> list = new ArrayList<>();
        String sql = "SELECT * FROM Orders WHERE EmployerID = ? ORDER BY Date DESC";
        try (PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, employerID);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Order order = new Order(
                            rs.getInt("OrderID"),
                            rs.getInt("EmployerID"),
                            rs.getInt("ServiceID"),
                            rs.getBigDecimal("Amount"),
                            rs.getString("PayMethod"),
                            rs.getString("Status"),
                            rs.getTimestamp("Date").toLocalDateTime(),
                            rs.getInt("Duration")
                    );
                    list.add(order);
                }
            }
        }
        return list;
    }

    // 🔴 Xóa đơn hàng
    public boolean deleteOrder(int orderID) throws SQLException {
        String sql = "DELETE FROM Orders WHERE OrderID = ?";
        try (PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, orderID);
            return ps.executeUpdate() > 0;
        }
    }

private String baseSelect() {
    return """
        SELECT o.OrderID, o.EmployerID, e.EmployerName, e.Email AS EmployerEmail,
               o.ServiceID, s.ServiceName,
               o.Amount, o.PayMethod, o.Status, o.[Date], o.Duration,
               ap.Code       AS PromotionCode,
               ap.Discount   AS DiscountPercent,
               CAST(o.Amount * (1 - ISNULL(ap.Discount,0)/100.0) AS DECIMAL(18,2)) AS FinalAmount
        FROM Orders o
        JOIN Employer e ON e.EmployerID = o.EmployerID
        JOIN Service  s ON s.ServiceID  = o.ServiceID
        OUTER APPLY (
            SELECT TOP 1 p.Code, p.Discount
            FROM ServicePromotion sp
            JOIN Promotion p ON p.PromotionID = sp.PromotionID
            WHERE sp.ServiceID = o.ServiceID
              AND p.DateSt <= o.[Date] AND o.[Date] <= p.DateEn
            ORDER BY p.Discount DESC, p.PromotionID ASC
        ) ap
    """;
}

    public int countAll() {
        String sql = "SELECT COUNT(*) FROM Orders";
        try (PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            return rs.next() ? rs.getInt(1) : 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return 0;
        }
    }
    public List<OrderView> findPage(int page, int pageSize) {
        if (page < 1) page = 1;
        if (pageSize < 1) pageSize = 10;
        int offset = (page - 1) * pageSize;

        String sql = baseSelect() +
                     " ORDER BY o.[OrderID] ASC " +
                     " OFFSET ? ROWS FETCH NEXT ? ROWS ONLY;";

        List<OrderView> list = new ArrayList<>();
        try (PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, offset);
            ps.setInt(2, pageSize);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) list.add(mapOrderView(rs));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return list;
    }
    public BigDecimal totalRevenue() {
        String sql = """
            SELECT SUM(CAST(o.Amount * (1 - ISNULL(ap.Discount,0)/100.0) AS DECIMAL(18,2)))
            FROM Orders o
            OUTER APPLY (
                SELECT TOP 1 p.Discount
                FROM ServicePromotion sp
                JOIN Promotion p ON p.PromotionID = sp.PromotionID
                WHERE sp.ServiceID = o.ServiceID
                  AND p.DateSt <= o.[Date] AND o.[Date] <= p.DateEn
                ORDER BY p.Discount DESC, p.PromotionID ASC
            ) ap
            WHERE o.Status IN ('paid','completed','success')
        """;
        try (PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            BigDecimal sum = rs.next() ? rs.getBigDecimal(1) : null;
            return sum != null ? sum : BigDecimal.ZERO;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return BigDecimal.ZERO;
        }
    }
    private OrderView mapOrderView(ResultSet rs) throws SQLException {
    OrderView ov = new OrderView();
    ov.setOrderId(rs.getInt("OrderID"));
    ov.setEmployerId(rs.getInt("EmployerID"));
    ov.setEmployerName(rs.getString("EmployerName"));
    ov.setEmployerEmail(rs.getString("EmployerEmail"));
    ov.setServiceId(rs.getInt("ServiceID"));
    ov.setServiceName(rs.getString("ServiceName"));
    ov.setAmount(rs.getBigDecimal("Amount"));
    ov.setPayMethod(rs.getString("PayMethod"));
    ov.setStatus(rs.getString("Status"));

    Timestamp ts = rs.getTimestamp("Date");
    ov.setDate(ts != null ? ts.toLocalDateTime() : null);

    int dur = rs.getInt("Duration");
    ov.setDuration(rs.wasNull() ? null : dur);

    ov.setPromotionCode(rs.getString("PromotionCode"));
    int disc = rs.getInt("DiscountPercent");
    ov.setDiscountPercent(rs.wasNull() ? null : disc);

    ov.setFinalAmount(rs.getBigDecimal("FinalAmount"));
    return ov;
}
}
