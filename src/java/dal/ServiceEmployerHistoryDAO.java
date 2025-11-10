package dal;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.ServiceEmployerHistory;

public class ServiceEmployerHistoryDAO extends DBContext {
    public List<ServiceEmployerHistory> getServiceEmployerHistoryByEmployer(int employerID, int offset, int limit) {
        List<ServiceEmployerHistory> list = new ArrayList();
        String sql = """
            SELECT *
            FROM ServiceEmployerHistory seh
            JOIN Service s ON seh.ServiceID = s.ServiceID
            WHERE EmployerID = ?
            ORDER BY CreatedAt DESC
            OFFSET ? ROWS FETCH NEXT ? ROWS ONLY
        """;
        
        try (PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, employerID);
            ps.setInt(2, offset);
            ps.setInt(3, limit);
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                ServiceEmployerHistory seh = new ServiceEmployerHistory();
                seh.setHistoryID(rs.getInt("HistoryID"));
                seh.setEmployerID(rs.getInt("EmployerID"));
                seh.setServiceID(rs.getInt("ServiceID"));
                seh.setServiceName(rs.getString("ServiceName"));
                seh.setRegisterDate(rs.getTimestamp("RegisterDate"));
                seh.setExpirationDate(rs.getTimestamp("ExpirationDate"));
                seh.setPaymentStatus(rs.getString("PaymentStatus"));
                seh.setActionType(rs.getString("ActionType"));
                seh.setCreatedAt(rs.getTimestamp("CreatedAt"));
                
                list.add(seh);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }
    
    public int countServiceEmployerHistoryByEmployer(int employerID) {
        String sql = "SELECT COUNT(*) FROM ServiceEmployerHistory WHERE EmployerID = ?";

        try (PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, employerID);
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
}