package dal;

import model.EmailBanned;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmailBannedDAO extends DBContext {
    
    // Kiểm tra email + role có bị ban không
    public boolean isEmailBanned(String email, String role) {
        String sql = "SELECT 1 FROM EmailBanned WHERE Email = ? AND Role = ?";
        
        try (PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, email);
            ps.setString(2, role);
            
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next(); 
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false; 
        }
    }
      public boolean isEmailBanned(String email) {
        String sql = "SELECT * FROM EmailBanned WHERE Email = ? ";
        
        try (PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, email);
          
            
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next(); 
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false; 
        }
    }
 
    // Lấy thông tin banned email theo email + role
    public EmailBanned getBannedEmail(String email, String role) {
        String sql = "SELECT * FROM EmailBanned WHERE Email = ? AND Role = ?";
        
        try (PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, email);
            ps.setString(2, role);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return extractEmailBanned(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; 
    }
    
    // Lấy thông tin banned email theo ID
    public EmailBanned getBannedById(int id) {
        String sql = "SELECT * FROM EmailBanned WHERE EmailBannedID = ?";
        
        try (PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return extractEmailBanned(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; 
    }
  
    // Thêm email vào danh sách ban
    public boolean addBannedEmail(String email, String role, String reason) {
        String sql = "INSERT INTO EmailBanned (Email, Role, Reason) VALUES (?, ?, ?)";
        
        try (PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, email);
            ps.setString(2, role);
            ps.setString(3, reason);
            
            return ps.executeUpdate() > 0;
            
        } catch (SQLException e) {
            if (e.getMessage().contains("UNIQUE") || e.getMessage().contains("UQ_Email_Role")) {
                System.err.println("Email + Role này đã bị cấm từ trước: " + email + " (" + role + ")");
            } else {
                e.printStackTrace();
            }
        }
        return false;
    }
   
    // Xóa banned email theo email + role
    public boolean removeBannedEmail(String email, String role) {
        String sql = "DELETE FROM EmailBanned WHERE Email = ? AND Role = ?";
        
        try (PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, email);
            ps.setString(2, role);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    // Xóa banned email theo ID (dùng cho unban)
    public boolean removeBannedById(int id) {
        String sql = "DELETE FROM EmailBanned WHERE EmailBannedID = ?";
        
        try (PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
   
    // Lấy tất cả banned emails
    public List<EmailBanned> getAllBannedEmails() {
        List<EmailBanned> list = new ArrayList<>();
        String sql = "SELECT * FROM EmailBanned ORDER BY Role, Email";
        
        try (PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                list.add(extractEmailBanned(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    
    // Lấy danh sách banned emails với search, filter và pagination
    public List<EmailBanned> getBannedList(String query, String role, int page, int pageSize) {
        List<EmailBanned> list = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT * FROM EmailBanned ");
        
        // Search theo email hoặc reason
        if (query != null && !query.trim().isEmpty()) {
            sql.append(" AND (Email LIKE ? OR Reason LIKE ?)");
        }
        
        // Filter theo role
        if (role != null && !role.trim().isEmpty()) {
            sql.append(" AND Role = ?");
        }
        
        sql.append(" ORDER BY EmailBannedID DESC");
        sql.append(" OFFSET ? ROWS FETCH NEXT ? ROWS ONLY");
        
        try (PreparedStatement ps = c.prepareStatement(sql.toString())) {
            int paramIndex = 1;
            
            if (query != null && !query.trim().isEmpty()) {
                String searchPattern = "%" + query + "%";
                ps.setString(paramIndex++, searchPattern);
                ps.setString(paramIndex++, searchPattern);
            }
            
            if (role != null && !role.trim().isEmpty()) {
                ps.setString(paramIndex++, role);
            }
            
            ps.setInt(paramIndex++, (page - 1) * pageSize);
            ps.setInt(paramIndex++, pageSize);
            
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(extractEmailBanned(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    
    // Đếm tổng số banned emails (với search và filter)
    public int getTotalBanned(String query, String role) {
        StringBuilder sql = new StringBuilder("SELECT COUNT(*) FROM EmailBanned WHERE 1=1");
        
        if (query != null && !query.trim().isEmpty()) {
            sql.append(" AND (Email LIKE ? OR Reason LIKE ?)");
        }
        
        if (role != null && !role.trim().isEmpty()) {
            sql.append(" AND Role = ?");
        }
        
        try (PreparedStatement ps = c.prepareStatement(sql.toString())) {
            int paramIndex = 1;
            
            if (query != null && !query.trim().isEmpty()) {
                String searchPattern = "%" + query + "%";
                ps.setString(paramIndex++, searchPattern);
                ps.setString(paramIndex++, searchPattern);
            }
            
            if (role != null && !role.trim().isEmpty()) {
                ps.setString(paramIndex++, role);
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
    
    // Helper method: Extract EmailBanned từ ResultSet
    private EmailBanned extractEmailBanned(ResultSet rs) throws SQLException {
        EmailBanned eb = new EmailBanned();
        eb.setEmailBannedId(rs.getInt("EmailBannedID"));
        eb.setEmail(rs.getString("Email"));
        eb.setRole(rs.getString("Role"));
        eb.setReason(rs.getString("Reason"));
        return eb;
    }
    
    // Test method
    public static void main(String[] args) {
        EmailBannedDAO dao = new EmailBannedDAO();
        
        // Test 1: Add banned email
        System.out.println("=== Test Add ===");
        boolean addSuccess = dao.addBannedEmail("test@example.com", "Candidate", "Vi phạm quy định");
        System.out.println("Add result: " + addSuccess);
        
        // Test 2: Check if banned
        System.out.println("\n=== Test Check ===");
        boolean isBanned = dao.isEmailBanned("test@example.com", "Candidate");
        System.out.println("Is banned: " + isBanned);
        
        // Test 3: Get banned list
        System.out.println("\n=== Test Get List ===");
        List<EmailBanned> list = dao.getBannedList(null, null, 1, 10);
        System.out.println("Total found: " + list.size());
        for (EmailBanned eb : list) {
            System.out.println("- " + eb.getEmail() + " (" + eb.getRole() + "): " + eb.getReason());
        }
        
        // Test 4: Get total
        System.out.println("\n=== Test Count ===");
        int total = dao.getTotalBanned(null, null);
        System.out.println("Total banned: " + total);
    }
}