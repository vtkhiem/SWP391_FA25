package dal;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.Function;
import model.Service;

public class ServiceDAO extends DBContext {

    // ✅ Thêm service mới
    public boolean addService(Service service) throws SQLException {
        String sql = "INSERT INTO Service (ServiceName, Price, Description, Duration, IsVisible, JobPostAmount, IsUnlimited) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, service.getServiceName());
            ps.setBigDecimal(2, service.getPrice());
            ps.setString(3, service.getDescription());
            ps.setInt(4, service.getDuration());
            ps.setBoolean(5, service.getIsVisible());
            ps.setInt(6, service.getJobPostAmount());
            ps.setBoolean(7, service.isIsUnlimited());
            return ps.executeUpdate() > 0;
        }
    }

    // ✅ Thêm service mới và trả về ID
    public int addServiceAndReturnId(Service service) throws SQLException {
        String sql = "INSERT INTO Service (ServiceName, Price, Description, Duration, IsVisible, JobPostAmount, IsUnlimited) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, service.getServiceName());
            ps.setBigDecimal(2, service.getPrice());
            ps.setString(3, service.getDescription());
            ps.setInt(4, service.getDuration());
            ps.setBoolean(5, service.getIsVisible());
            ps.setInt(6, service.getJobPostAmount());
            ps.setBoolean(7, service.isIsUnlimited());

            int affectedRows = ps.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) return rs.getInt(1);
                }
            }
        }
        return -1;
    }

    // ✅ Cập nhật service
    public boolean updateService(Service service) throws SQLException {
        String sql = "UPDATE Service SET ServiceName=?, Price=?, Description=?, Duration=?, IsVisible=?, JobPostAmount=?, IsUnlimited=? WHERE ServiceID=?";
        try (PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, service.getServiceName());
            ps.setBigDecimal(2, service.getPrice());
            ps.setString(3, service.getDescription());
            ps.setInt(4, service.getDuration());
            ps.setBoolean(5, service.getIsVisible());
            ps.setInt(6, service.getJobPostAmount());
            ps.setBoolean(7, service.isIsUnlimited());
            ps.setInt(8, service.getServiceID());
            return ps.executeUpdate() > 0;
        }
    }

    // ✅ Xóa service
    public boolean deleteService(int serviceID) throws SQLException {
        String sql = "DELETE FROM Service WHERE ServiceID = ?";
        try (PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, serviceID);
            return ps.executeUpdate() > 0;
        }
    }

    // ✅ Lấy tất cả service
    public List<Service> getAllVisibleServices() throws SQLException {
        List<Service> list = new ArrayList<>();
        String sql = "SELECT * FROM Service Where IsVisible = 1 ORDER BY ServiceID DESC";
        try (PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Service s = new Service(
                        rs.getInt("ServiceID"),
                        rs.getString("ServiceName"),
                        rs.getBigDecimal("Price"),
                        rs.getString("Description"),
                        rs.getInt("Duration"),
                        rs.getBoolean("IsVisible"),
                        rs.getInt("JobPostAmount")
                );
              
                s.setIsUnlimited(rs.getBoolean("IsUnlimited"));
                list.add(s);
            }
        }
        return list;
    }
        public List<Service> getAllServices() throws SQLException {
        List<Service> list = new ArrayList<>();
        String sql = "SELECT * FROM Service ORDER BY ServiceID DESC";
        try (PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Service s = new Service(
                        rs.getInt("ServiceID"),
                        rs.getString("ServiceName"),
                        rs.getBigDecimal("Price"),
                        rs.getString("Description"),
                        rs.getInt("Duration"),
                        rs.getBoolean("IsVisible"),
                        rs.getInt("JobPostAmount")
                );
              
                s.setIsUnlimited(rs.getBoolean("IsUnlimited"));
                list.add(s);
            }
        }
        return list;
    }

    // ✅ Lấy service theo ID
    public Service getServiceById(int id) throws SQLException {
        String sql = "SELECT * FROM Service WHERE ServiceID = ?";
        try (PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Service s = new Service(
                            rs.getInt("ServiceID"),
                            rs.getString("ServiceName"),
                            rs.getBigDecimal("Price"),
                            rs.getString("Description"),
                            rs.getInt("Duration"),
                            rs.getBoolean("IsVisible"),
                              rs.getInt("JobPostAmount")
                    );
                   
                    s.setIsUnlimited(rs.getBoolean("IsUnlimited"));
                    return s;
                }
            }
        }
        return null;
    }

    // ✅ Lấy ID theo tên service
    public int getIdByServiceName(String name) throws SQLException {
        String sql = "SELECT ServiceID FROM Service WHERE ServiceName = ?";
        try (PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, name);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return rs.getInt("ServiceID");
            }
        }
        return -1;
    }

    // ✅ Kiểm tra tên service có tồn tại không
    public boolean isServiceNameExists(String name) throws SQLException {
        String sql = "SELECT COUNT(*) FROM Service WHERE ServiceName = ?";
        try (PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, name.trim());
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return rs.getInt(1) > 0;
            }
        }
        return false;
    }

    // ✅ Lấy danh sách function theo service
    public List<Function> getFunctionsByServiceId(int serviceId) throws SQLException {
        List<Function> functions = new ArrayList<>();
        String query = """
            SELECT f.FunctionID, f.FunctionName
            FROM ServiceFunction sf
            JOIN Functions f ON sf.FunctionID = f.FunctionID
            WHERE sf.ServiceID = ?
        """;
        try (PreparedStatement ps = c.prepareStatement(query)) {
            ps.setInt(1, serviceId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    functions.add(new Function(
                            rs.getInt("FunctionID"),
                            rs.getString("FunctionName")
                    ));
                }
            }
        }
        return functions;
    }
}
