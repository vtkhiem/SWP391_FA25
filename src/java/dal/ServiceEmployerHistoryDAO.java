package dal;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.ServiceEmployerHistory;

public class ServiceEmployerHistoryDAO extends DBContext {
    public List<ServiceEmployerHistory> getServiceEmployerHistoryByEmployer(int employerID, Timestamp fromDate, Timestamp toDate, int offset, int limit) {
        List<ServiceEmployerHistory> list = new ArrayList();
        StringBuilder sql = new StringBuilder("""
            SELECT seh.*, s.ServiceName
            FROM ServiceEmployerHistory seh
            JOIN Service s ON seh.ServiceID = s.ServiceID
            WHERE seh.EmployerID = ?
        """);
            
        if (fromDate != null) {
            sql.append(" AND seh.CreatedAt >= ? ");
        }
        
        if (toDate != null) {
            sql.append(" AND seh.CreatedAt <= ? ");
        }
        
        sql.append("""
            ORDER BY seh.CreatedAt DESC
            OFFSET ? ROWS FETCH NEXT ? ROWS ONLY
        """);
        
        
        try (PreparedStatement ps = c.prepareStatement(sql.toString())) {
            int i = 1;
            ps.setInt(i++, employerID);
            if (fromDate != null) {
                ps.setTimestamp(i++, fromDate);
            }
            if (toDate != null) {
                ps.setTimestamp(i++, toDate);
            }
            ps.setInt(i++, offset);
            ps.setInt(i++, limit);
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
    
    public int countServiceEmployerHistoryByEmployer(int employerID, Timestamp fromDate, Timestamp toDate) {
        StringBuilder sql = new StringBuilder("""
            SELECT COUNT(*)
            FROM ServiceEmployerHistory
            WHERE EmployerID = ?
        """);
        
        if (fromDate != null) {
            sql.append(" AND CreatedAt >= ? ");
        }
        
        if (toDate != null) {
            sql.append(" AND CreatedAt <= ? ");
        }

        try (PreparedStatement ps = c.prepareStatement(sql.toString())) {
            int i = 1;
            ps.setInt(i++, employerID);
            if (fromDate != null) {
                ps.setTimestamp(i++, fromDate);
            }
            if (toDate != null) {
                ps.setTimestamp(i++, toDate);
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
}