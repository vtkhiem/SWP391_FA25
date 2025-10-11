/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
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
}