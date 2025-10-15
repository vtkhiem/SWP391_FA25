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
        public int countOrders() {
        String sql = "SELECT COUNT(*) FROM Orders";
        try (PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) return rs.getInt(1);
        } catch (SQLException e) { e.printStackTrace(); }
        return 0;
    }

    public List<Order> findOrdersPage(int page, int pageSize) {
        List<Order> list = new ArrayList<>();
        int offset = Math.max(0, (page - 1) * pageSize);

        String sql =
            "SELECT o.OrderID, o.EmployerID, o.ServiceID, o.Amount, o.PayMethod, " +
            "       o.Status, o.[Date], o.Duration " +
            "FROM Orders o " +
            "ORDER BY o.[Date] DESC, o.OrderID DESC " +
            "OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";

        try (PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, offset);
            ps.setInt(2, pageSize);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Order o = new Order();
                    o.setOrderID(rs.getInt("OrderID"));
                    o.setEmployerID(rs.getInt("EmployerID"));
                    o.setServiceID(rs.getInt("ServiceID"));
                    o.setAmount(rs.getBigDecimal("Amount"));
                    o.setPayMethod(rs.getString("PayMethod"));
                    o.setStatus(rs.getString("Status"));
                    Timestamp ts = rs.getTimestamp("Date");
                    o.setDate(ts == null ? null : ts.toLocalDateTime());
                    int dur = rs.getInt("Duration");
                    o.setDuration(rs.wasNull() ? null : dur);
                    list.add(o);
                }
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }

    public BigDecimal sumRevenue() {
        String sql = "SELECT SUM(Amount) FROM Orders";
        try (PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                BigDecimal total = rs.getBigDecimal(1);
                return total != null ? total : BigDecimal.ZERO;
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return BigDecimal.ZERO;
    }
    public Map<Integer, String> employerNameMapByIds(Collection<Integer> ids) {
        Map<Integer, String> map = new HashMap<>();
        if (ids == null || ids.isEmpty()) return map;
        String placeholders = String.join(",", Collections.nCopies(ids.size(), "?"));
        String sql = "SELECT EmployerID, EmployerName FROM Employer WHERE EmployerID IN (" + placeholders + ")";
        try (PreparedStatement ps = c.prepareStatement(sql)) {
            int i = 1; for (Integer id : ids) ps.setInt(i++, id);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) map.put(rs.getInt(1), rs.getString(2));
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return map;
    }

    public Map<Integer, String> serviceNameMapByIds(Collection<Integer> ids) {
        Map<Integer, String> map = new HashMap<>();
        if (ids == null || ids.isEmpty()) return map;
        String placeholders = String.join(",", Collections.nCopies(ids.size(), "?"));
        String sql = "SELECT ServiceID, ServiceName FROM Service WHERE ServiceID IN (" + placeholders + ")";
        try (PreparedStatement ps = c.prepareStatement(sql)) {
            int i = 1; for (Integer id : ids) ps.setInt(i++, id);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) map.put(rs.getInt(1), rs.getString(2));
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return map;
    }
}
