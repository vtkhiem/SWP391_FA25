/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.Role;
import tool.EncodePassword;
import model.Admin;
import model.StaffView;

/**
 *
 * @author Admin
 */
public class AdminDAO extends DBContext {

    public boolean isAdmin(String accountName) {

        try {

            String query = "SELECT [AdminID]\n"
                    + "      ,[Username]\n"
                    + "      ,[PasswordHash]\n"
                    + "  FROM [dbo].[Admin]\n"
                    + "  Where Username like ?";

            PreparedStatement push = c.prepareStatement(query);
            push.setString(1, accountName);

            ResultSet rs = push.executeQuery();

            while (rs.next()) {
                return rs.getString("Username").equals(accountName);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return false;

    }

    public Admin loginAccountAdmin(String username, String password) {
        String query = "SELECT AdminID, Username, PasswordHash FROM [dbo].[Admin] WHERE Username = ?";
        try (PreparedStatement ps = c.prepareStatement(query)) {
            ps.setString(1, username);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    String passwordHash = EncodePassword.encodePasswordbyHash(password);
                    String storedHash = rs.getString("PasswordHash");
                    if (passwordHash.equals(storedHash)) {
                        Admin admin = new Admin();
                        admin.setAdminId(rs.getInt("AdminID"));
                        admin.setUsername(rs.getString("Username"));
                        return admin; // login thành công
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("Lỗi login admin: " + e.getMessage());
        }
        return null; // login fail
    }
    public Admin getAdminByUsername(String username) {
        String query = "SELECT AdminID, Username, PasswordHash FROM [dbo].[Admin] WHERE Username = ?";
        try (PreparedStatement ps = c.prepareStatement(query)) {
            ps.setString(1, username);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                
                   
                   
                        Admin admin = new Admin();
                        admin.setAdminId(rs.getInt("AdminID"));
                        admin.setUsername(rs.getString("Username"));
                        return admin; // login thành công
                    
                }
            }
        } catch (SQLException e) {
            System.out.println("Lỗi login admin: " + e.getMessage());
        }
        return null; // login fail
    }

    public List<Role> getRolesByAdminId(int adminId) throws SQLException {
        List<Role> roles = new ArrayList<>();
        String query = "SELECT r.RoleID, r.RoleName "
                + "FROM Role_Table rt "
                + "JOIN Roles r ON rt.RoleID = r.RoleID "
                + "WHERE rt.AdminID = ?";
        PreparedStatement push = c.prepareStatement(query);
        push.setInt(1, adminId);
        try (ResultSet rs = push.executeQuery()) {
            while (rs.next()) {
                roles.add(new Role(rs.getInt("RoleID"), rs.getString("RoleName")));
            }
        }

        return roles;
    }

    public Integer getAdminIdByUsername(String username) {
        String query = "SELECT AdminID FROM [dbo].[Admin] WHERE Username = ?";
        try (PreparedStatement ps = c.prepareStatement(query)) {
            ps.setString(1, username);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("AdminID");
                }
            }
        } catch (SQLException e) {
            System.out.println("Lỗi khi lấy AdminID: " + e.getMessage());
        }
        return null; // Nếu không tìm thấy
    }

    public static void main(String[] args) {
        AdminDAO dao = new AdminDAO();

        // Test case 1: Đúng username và password
        String username = "hung";   // thay bằng username trong DB
        String password = "1234";   // thay bằng password thật (sẽ được hash để so sánh)
        Admin admin = dao.loginAccountAdmin(username, password);
        Admin admin1 = dao.getAdminByUsername(username);

        if (admin1 != null) {
            System.out.println("Đăng nhập thành công!");
            System.out.println("AdminID: " + admin.getAdminId());
            System.out.println("Username: " + admin.getUsername());
        } else {
            System.out.println("Đăng nhập thất bại!");
        }

        // Test case 2: Sai password
        String wrongPassword = "sai_pass";
        Admin adminFail = dao.loginAccountAdmin(username, wrongPassword);
        if (adminFail != null) {
            System.out.println("Bug: Sai password mà vẫn login được!");
        } else {
            System.out.println("Test sai password: Passed (login thất bại).");
        }

        // Test case 3: Username không tồn tại
        String fakeUsername = "khong_ton_tai";
        Admin adminNotFound = dao.loginAccountAdmin(fakeUsername, "any");
        if (adminNotFound == null) {
            System.out.println("Test username không tồn tại: Passed.");
        } else {
            System.out.println("Bug: Username không tồn tại mà vẫn login được!");
        }
    }
public boolean usernameExists(String username) throws SQLException {
        String sql = "SELECT 1 FROM Admin WHERE Username = ?";
        try (PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, username);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        }
    }
    public boolean createAdminWithRole(String username, String plainPassword, int roleId) throws Exception {
    if (roleId != 2 && roleId != 3) return false;

    String insertAdmin = "INSERT INTO Admin (Username, PasswordHash) VALUES (?, ?)";
    String insertMap   = "INSERT INTO Role_Table (AdminID, RoleID) VALUES (?, ?)";

    boolean oldAuto = c.getAutoCommit();
    c.setAutoCommit(false);
    try (PreparedStatement psAdmin = c.prepareStatement(insertAdmin, Statement.RETURN_GENERATED_KEYS)) {
        psAdmin.setString(1, username);
        psAdmin.setString(2, plainPassword);

        int affected = psAdmin.executeUpdate();
        if (affected == 0) {
            c.rollback();
            c.setAutoCommit(oldAuto);
            return false;
        }

        int newAdminId;
        try (ResultSet keys = psAdmin.getGeneratedKeys()) {
            if (!keys.next()) {
                c.rollback();
                c.setAutoCommit(oldAuto);
                return false;
            }
            newAdminId = keys.getInt(1);
        }

        try (PreparedStatement psMap = c.prepareStatement(insertMap)) {
            psMap.setInt(1, newAdminId);
            psMap.setInt(2, roleId);
            psMap.executeUpdate();
        }

        c.commit();
        c.setAutoCommit(oldAuto);
        return true;

    } catch (Exception ex) {
        c.rollback();
        c.setAutoCommit(oldAuto);
        throw ex;
    }
}
public List<StaffView> findAllMarketingSale() throws SQLException {
    String sql =
        "SELECT a.AdminID, a.Username, a.PasswordHash AS Password, " +
        "       STRING_AGG(r.RoleName, ', ') WITHIN GROUP (ORDER BY r.RoleName) AS Roles " +
        "FROM Admin a " +
        "JOIN Role_Table rt ON rt.AdminID = a.AdminID " +
        "JOIN Roles r ON r.RoleID = rt.RoleID " +
        "WHERE rt.RoleID IN (2,3) " +
        "GROUP BY a.AdminID, a.Username, a.PasswordHash " +            
        "ORDER BY a.AdminID";

    List<StaffView> list = new ArrayList<>();
    try (PreparedStatement ps = c.prepareStatement(sql);
         ResultSet rs = ps.executeQuery()) {
        while (rs.next()) {
            StaffView sv = new StaffView();
            sv.setAdminId(rs.getInt("AdminID"));
            sv.setUsername(rs.getString("Username"));
            sv.setRoles(rs.getString("Roles"));
            sv.setPassword(rs.getString("Password"));
            list.add(sv);
        }
    }
    return list;
}

public boolean deleteStaffById(int adminId) throws Exception {
    String deleteRole = "DELETE FROM Role_Table WHERE AdminID = ? AND RoleID IN (2,3)";
    String countRole  = "SELECT COUNT(*) FROM Role_Table WHERE AdminID = ?";
    String deleteAdmin= "DELETE FROM Admin WHERE AdminID = ?";

    boolean oldAuto = c.getAutoCommit();
    c.setAutoCommit(false);
    try (PreparedStatement psDelRole = c.prepareStatement(deleteRole)) {
        psDelRole.setInt(1, adminId);
        int removed = psDelRole.executeUpdate();
        if (removed == 0) {
            c.rollback();
            c.setAutoCommit(oldAuto);
            return false;
        }
        int remain = 0;
        try (PreparedStatement psCount = c.prepareStatement(countRole)) {
            psCount.setInt(1, adminId);
            try (ResultSet rs = psCount.executeQuery()) {
                if (rs.next()) remain = rs.getInt(1);
            }
        }
        if (remain == 0) {
            try (PreparedStatement psDelAdmin = c.prepareStatement(deleteAdmin)) {
                psDelAdmin.setInt(1, adminId);
                psDelAdmin.executeUpdate();
            }
        }

        c.commit();
        c.setAutoCommit(oldAuto);
        return true;

    } catch (Exception ex) {
        c.rollback();
        c.setAutoCommit(oldAuto);
        throw ex;
    }
}

}
