package dal;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
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
            ps.setInt(6, se.getJobPostCount());
            return ps.executeUpdate() > 0;
        }
    }

    // ✅ 2. Lấy tất cả dịch vụ mà Employer đã đăng ký
    public List<ServiceEmployer> getServicesByEmployer(int employerID) throws SQLException {
        List<ServiceEmployer> list = new ArrayList<>();
        String sql = "SELECT * FROM ServiceEmployer WHERE EmployerID = ?";
        try (PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, employerID);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(mapResultSetToServiceEmployer(rs));
                }
            }
        }
        return list;
    }

    // ✅ 3. Lấy chi tiết 1 đăng ký
    public ServiceEmployer getByEmployerAndService(int employerID, int serviceID) throws SQLException {
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

    // ✅ 6. Cập nhật số lượng bài đăng
    public boolean updateJobPostCount(int employerID, int serviceID, int newCount) throws SQLException {
        String sql = "UPDATE ServiceEmployer SET JobPostCount = ? WHERE EmployerID = ? AND ServiceID = ?";
        try (PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, newCount);
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
                rs.getString("PaymentStatus"),
                rs.getInt("JobPostCount")
        );
    }
    public boolean registerService(int employerId, int serviceId, Timestamp registerDate, Timestamp expirationDate,
                                   String paymentStatus, int jobPostCount) throws SQLException {
        String sql = """
            INSERT INTO ServiceEmployer (EmployerID, ServiceID, RegisterDate, ExpirationDate, PaymentStatus, JobPostCount)
            VALUES (?, ?, ?, ?, ?, ?)
        """;

        try (PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, employerId);
            ps.setInt(2, serviceId);
            ps.setTimestamp(3, registerDate);
            ps.setTimestamp(4, expirationDate);
            ps.setString(5, paymentStatus);
            ps.setInt(6, jobPostCount);
            return ps.executeUpdate() > 0;
        }
    }
}
