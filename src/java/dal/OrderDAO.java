package dal;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDateTime;
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
public class OrderDAO extends DBContext {
    // Keeping the original addOrder for simple creation, but also adding the insertOrder for ID retrieval

    public boolean addOrder(Order order) throws SQLException {
        // Using GETDATE() as originally intended, simpler use case
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
    }

    public int insertOrder(Order order) throws SQLException {

        String sql = "INSERT INTO Orders (employerID, serviceID, amount, payMethod, status, date, duration,code) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?,?)";

        try (PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, order.getEmployerID());
            ps.setInt(2, order.getServiceID());
            ps.setBigDecimal(3, order.getAmount());
            ps.setString(4, order.getPayMethod());
            ps.setString(5, order.getStatus());

            // Convert LocalDateTime -> Timestamp
            LocalDateTime date = order.getDate();
            if (date != null) {
                ps.setTimestamp(6, Timestamp.valueOf(date));
            } else {
                // If date is null, use current time
                ps.setTimestamp(6, new Timestamp(System.currentTimeMillis()));
            }

            if (order.getDuration() != null) {
                ps.setInt(7, order.getDuration());
            } else {
                ps.setNull(7, Types.INTEGER);
            }
            if (order.getCode() != null) {
                ps.setString(8, order.getCode());
            } else {
                ps.setNull(8, Types.NVARCHAR);
            }

            int affectedRows = ps.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        return rs.getInt(1); // trả về orderID được sinh tự động
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
        return -1; // nếu lỗi
    }

    public boolean updateOrderStatus(int orderID, String newStatus) throws SQLException {
        String sql = "UPDATE Orders SET Status = ? WHERE OrderID = ?";
        try (PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, newStatus);
            ps.setInt(2, orderID);
            return ps.executeUpdate() > 0;
        }
    }

    public List<Order> getAllOrders() throws SQLException {
        List<Order> list = new ArrayList<>();
        String sql = "SELECT * FROM Orders ORDER BY OrderID DESC";
        try (PreparedStatement ps = c.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Order order = new Order(
                        rs.getInt("OrderID"),
                        rs.getInt("EmployerID"),
                        rs.getInt("ServiceID"),
                        rs.getBigDecimal("Amount"),
                        rs.getString("PayMethod"),
                        rs.getString("Status"),
                        rs.getTimestamp("Date").toLocalDateTime(),
                        rs.getInt("Duration"),
                        rs.getString("Code")
                );
                list.add(order);
            }
        }
        return list;
    }

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
                            rs.getInt("Duration"),
                            rs.getString("Code")
                    );
                }
            }
        }
        return null;
    }

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
                            rs.getInt("Duration"),
                            rs.getString("Code")
                    );
                    list.add(order);
                }
            }
        }
        return list;
    }

    public boolean deleteOrder(int orderID) throws SQLException {
        String sql = "DELETE FROM Orders WHERE OrderID = ?";
        try (PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, orderID);
            return ps.executeUpdate() > 0;
        }
    }

    // --- New Methods from HEAD for Reporting/OrderView ---
    private String baseSelect() {
        // SQL query with JOINs and Outer Apply for promotion calculation
        return """
    SELECT o.OrderID, o.EmployerID, e.EmployerName, e.Email AS EmployerEmail,
           o.ServiceID, s.ServiceName,
           o.Amount, o.PayMethod, o.Status, o.[Date], o.Duration,
           ap.Code       AS PromotionCode,
           ap.Discount   AS DiscountPercent,
           CAST(o.Amount * (1 - ISNULL(ap.Discount,0)) AS DECIMAL(18,2)) AS FinalAmount
    FROM Orders o
    JOIN Employer e ON e.EmployerID = o.EmployerID
    JOIN Service  s ON s.ServiceID  = o.ServiceID
    OUTER APPLY (
        SELECT TOP 1 p.Code, p.Discount
        FROM Promotion p
        LEFT JOIN ServicePromotion sp ON sp.PromotionID = p.PromotionID
        WHERE (o.Code IS NOT NULL AND p.Code = o.Code)
           OR (o.Code IS NULL 
               AND sp.ServiceID = o.ServiceID
               AND p.DateSt <= o.[Date] AND o.[Date] <= p.DateEn
               AND p.Status = 1)
        ORDER BY p.Discount DESC, p.PromotionID ASC
    ) ap
    """;

    }

    public int countAll() {
        String sql = "SELECT COUNT(*) FROM Orders";
        try (PreparedStatement ps = c.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            return rs.next() ? rs.getInt(1) : 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return 0;
        }
    }

    public List<OrderView> findPage(int page, int pageSize) {
        if (page < 1) {
            page = 1;
        }
        if (pageSize < 1) {
            pageSize = 10;
        }
        int offset = (page - 1) * pageSize;

        String sql = baseSelect()
                + " ORDER BY o.[OrderID] ASC "
                + " OFFSET ? ROWS FETCH NEXT ? ROWS ONLY;";

        List<OrderView> list = new ArrayList<>();
        try (PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, offset);
            ps.setInt(2, pageSize);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(mapOrderView(rs));
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return list;
    }

    public BigDecimal totalRevenue() {
        String sql = """
    SELECT SUM(CAST(o.Amount * (1 - ISNULL(ap.Discount,0)) AS DECIMAL(18,2)))
    FROM Orders o
    OUTER APPLY (
        SELECT TOP 1 p.Discount
        FROM ServicePromotion sp
        JOIN Promotion p ON p.PromotionID = sp.PromotionID
        WHERE sp.ServiceID = o.ServiceID
          AND p.DateSt <= o.[Date] AND o.[Date] <= p.DateEn
          AND p.Status = 1
        ORDER BY p.Discount DESC, p.PromotionID ASC
    ) ap
    WHERE o.Status IN ('paid')
""";
        try (PreparedStatement ps = c.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
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
        BigDecimal disc = rs.getBigDecimal("DiscountPercent");
        ov.setDiscountPercent(disc != null ? disc.multiply(BigDecimal.valueOf(100)) : BigDecimal.ZERO);

        ov.setFinalAmount(rs.getBigDecimal("FinalAmount"));
        return ov;
    }

    public List<OrderView> filterOrders(String txt,
            Integer serviceID, String promotionCode,
            String status,
            int offSet, int recordsPerPage) {
        List<OrderView> list = new ArrayList<>();
        StringBuilder sql = new StringBuilder(baseSelect());
        sql.append(" WHERE 1=1 ");

        List<Object> params = new ArrayList<>();

        if (txt != null && !txt.trim().isEmpty()) {
            sql.append(" AND ( e.EmployerName LIKE ? OR e.Email LIKE ? )");
            params.add("%" + txt.trim() + "%");
            params.add("%" + txt.trim() + "%");
        }

        if (serviceID != null && serviceID > 0) {
            sql.append(" AND s.ServiceID = ? ");
            params.add(serviceID);
        }
        if (promotionCode != null && !promotionCode.trim().isEmpty()) {
            sql.append(" AND ap.Code = ? ");
            params.add(promotionCode.trim());
        }
        if (status != null && !status.trim().isEmpty()) {
            sql.append(" AND o.Status = ? ");
            params.add(status.trim());
        }

        // Sắp xếp và phân trang
        sql.append(" ORDER BY o.[Date] DESC, o.OrderID DESC OFFSET ? ROWS FETCH NEXT ? ROWS ONLY");
        params.add(offSet);
        params.add(recordsPerPage);

        try (PreparedStatement ps = c.prepareStatement(sql.toString())) {
            for (int i = 0; i < params.size(); i++) {
                ps.setObject(i + 1, params.get(i));
            }

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(mapOrderView(rs));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public int countFilteredOrders(String txt,
            Integer serviceID, String promotionCode,
            String status) {

        StringBuilder sql = new StringBuilder("SELECT COUNT(*) FROM Orders o ")
                .append("JOIN Employer e ON e.EmployerID = o.EmployerID ")
                .append("JOIN Service s ON s.ServiceID = o.ServiceID ")
                .append("OUTER APPLY ( ")
                .append("  SELECT TOP 1 p.Code, p.Discount ")
                .append("  FROM ServicePromotion sp ")
                .append("  JOIN Promotion p ON p.PromotionID = sp.PromotionID ")
                .append("  WHERE sp.ServiceID = o.ServiceID ")
                .append("    AND p.DateSt <= o.[Date] AND o.[Date] <= p.DateEn ")
                .append("  ORDER BY p.Discount DESC, p.PromotionID ASC ")
                .append(") ap ")
                .append("WHERE 1=1 ");

        List<Object> params = new ArrayList<>();

        if (txt != null && !txt.trim().isEmpty()) {
            sql.append(" AND (e.EmployerName LIKE ? OR e.Email LIKE ?)");
            params.add("%" + txt.trim() + "%");
            params.add("%" + txt.trim() + "%");
        }

        if (serviceID != null && serviceID > 0) {
            sql.append(" AND s.ServiceID = ?");
            params.add(serviceID);
        }

        if (promotionCode != null && !promotionCode.trim().isEmpty()) {
            sql.append(" AND ap.Code = ?");
            params.add(promotionCode.trim());
        }

        if (status != null && !status.trim().isEmpty()) {
            sql.append(" AND o.Status = ?");
            params.add(status.trim());
        }

        try (PreparedStatement ps = c.prepareStatement(sql.toString())) {
            for (int i = 0; i < params.size(); i++) {
                ps.setObject(i + 1, params.get(i));
            }
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;
    }

    public static void main(String[] args) {
        System.out.println(new OrderDAO().filterOrders("bui", -1, "", "", 0, 10));
    }

}
