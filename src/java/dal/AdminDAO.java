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

        if (admin != null) {
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

}
