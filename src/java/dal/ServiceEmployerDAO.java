package dal;

import java.sql.*;

import model.ServiceEmployer;

public class ServiceEmployerDAO extends DBContext {
    // ✅ 1. Thêm đăng ký dịch vụ mới
    public boolean addServiceForEmployer(ServiceEmployer se) throws SQLException {
        String sql = """
            INSERT INTO ServiceEmployer 
            (EmployerID, ServiceID, RegisterDate, ExpirationDate, PaymentStatus, JobPostCount)
            VALUES (?, ?, ?, ?, ?, ?)
        """;
        try (PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, se.getEmployerID());
            ps.setInt(2, se.getServiceID());
            ps.setTimestamp(3, se.getRegisterDate());
            ps.setTimestamp(4, se.getExpirationDate());
            ps.setString(5, se.getPaymentStatus());

            return ps.executeUpdate() > 0;
        }
    }

    // ✅ 2. Lấy tất cả dịch vụ mà Employer đã đăng ký
    public int getServiceIdByEmployerId(int employerID) throws SQLException {
        String sql = "SELECT ServiceID FROM ServiceEmployerHistory WHERE EmployerID = ?";
        try (PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, employerID);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("ServiceID");
                } else {
                    return -1;
                }
            }
        }

    }
    // ✅ 2.5. Lấy dịch vụ hiện tại mà Employer đang dùng.
    public int getCurrentServiceByEmployerId(int employerID) throws SQLException {
        String sql = "SELECT ServiceID FROM ServiceEmployer WHERE EmployerID = ?";
        try (PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, employerID);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("ServiceID");
                } else {
                    return -1;
                }
            }
        }
    }

    // ✅ 3. Lấy chi tiết 1 đăng ký
    public ServiceEmployer getByEmployerAndService(int employerID, int serviceID) throws SQLException {
        String sql = "SELECT * FROM ServiceEmployerHistory WHERE EmployerID = ? AND ServiceID = ?";
        try (PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, employerID);
            ps.setInt(2, serviceID);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToServiceEmployer(rs);
                }
            }
        }
        return null;
    }
    // ✅ 3. Lấy chi tiết 1 đăng ký
    public ServiceEmployer getCurrentServiceInfoByEmployerID(int employerID, int serviceID) throws SQLException {
        String sql = "SELECT * FROM ServiceEmployer WHERE EmployerID = ? AND ServiceID = ?";
        try (PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, employerID);
            ps.setInt(2, serviceID);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToServiceEmployer(rs);
                }
            }
        }
        return null;
    }

    // ✅ 4. Cập nhật trạng thái thanh toán
    public boolean updatePaymentStatus(int employerID, int serviceID, String newStatus) throws SQLException {
        String sql = "UPDATE ServiceEmployer SET PaymentStatus = ? WHERE EmployerID = ? AND ServiceID = ?";
        try (PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, newStatus);
            ps.setInt(2, employerID);
            ps.setInt(3, serviceID);
            return ps.executeUpdate() > 0;
        }
    }

    // ✅ 5. Gia hạn dịch vụ
    public boolean extendService(int employerID, int serviceID, Timestamp newExpirationDate) throws SQLException {
        String sql = "UPDATE ServiceEmployer SET ExpirationDate = ? WHERE EmployerID = ? AND ServiceID = ?";
        try (PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setTimestamp(1, newExpirationDate);
            ps.setInt(2, employerID);
            ps.setInt(3, serviceID);
            return ps.executeUpdate() > 0;
        }
    }

    // ✅ 7. Xóa đăng ký
    public boolean deleteServiceEmployer(int employerID, int serviceID) throws SQLException {
        String sql = "DELETE FROM ServiceEmployer WHERE EmployerID = ? AND ServiceID = ?";
        try (PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, employerID);
            ps.setInt(2, serviceID);
            return ps.executeUpdate() > 0;
        }
    }

    // ✅ 8. Kiểm tra hết hạn
    public boolean isServiceExpired(int employerID, int serviceID) throws SQLException {
        String sql = "SELECT ExpirationDate FROM ServiceEmployer WHERE EmployerID = ? AND ServiceID = ?";
        try (PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, employerID);
            ps.setInt(2, serviceID);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Timestamp exp = rs.getTimestamp("ExpirationDate");
                    return exp != null && exp.before(new Timestamp(System.currentTimeMillis()));
                }
            }
        }
        return true;
    }

    // ✅ Helper: map từ ResultSet sang Object
    private ServiceEmployer mapResultSetToServiceEmployer(ResultSet rs) throws SQLException {
        return new ServiceEmployer(
                rs.getInt("EmployerID"),
                rs.getInt("ServiceID"),
                rs.getTimestamp("RegisterDate"),
                rs.getTimestamp("ExpirationDate"),
                rs.getString("PaymentStatus")
        );
    }

    public boolean registerService(int employerId, int serviceId,
            Timestamp registerDate, Timestamp expirationDate,
            String paymentStatus,
            String actionType) throws SQLException {
        String insertHistorySQL = """
        INSERT INTO ServiceEmployerHistory 
        (EmployerID, ServiceID, RegisterDate, ExpirationDate, PaymentStatus, ActionType)
        VALUES (?, ?, ?, ?, ?, ?)
    """;

        String upsertCurrentSQL = """
        MERGE ServiceEmployer AS target
        USING (SELECT ? AS EmployerID, ? AS ServiceID, ? AS RegisterDate, ? AS ExpirationDate, ? AS PaymentStatus) AS src
        ON target.EmployerID = src.EmployerID
        WHEN MATCHED THEN
            UPDATE SET target.ServiceID = src.ServiceID, target.RegisterDate = src.RegisterDate, 
                       target.ExpirationDate = src.ExpirationDate, target.PaymentStatus = src.PaymentStatus
                  
        WHEN NOT MATCHED THEN
            INSERT (EmployerID, ServiceID, RegisterDate, ExpirationDate, PaymentStatus)
            VALUES (src.EmployerID, src.ServiceID, src.RegisterDate, src.ExpirationDate, src.PaymentStatus);
    """;

        try {
            c.setAutoCommit(false);

            try (PreparedStatement ps1 = c.prepareStatement(insertHistorySQL)) {
                ps1.setInt(1, employerId);
                ps1.setInt(2, serviceId);
                ps1.setTimestamp(3, registerDate);
                ps1.setTimestamp(4, expirationDate);
                ps1.setString(5, paymentStatus);

                ps1.setString(6, actionType);
                ps1.executeUpdate();
            }

            try (PreparedStatement ps2 = c.prepareStatement(upsertCurrentSQL)) {
                ps2.setInt(1, employerId);
                ps2.setInt(2, serviceId);
                ps2.setTimestamp(3, registerDate);
                ps2.setTimestamp(4, expirationDate);
                ps2.setString(5, paymentStatus);

                ps2.executeUpdate();
            }

            c.commit();
            return true;
        } catch (SQLException e) {
            c.rollback();
            throw e;
        } finally {
            c.setAutoCommit(true);
        }
    }
}