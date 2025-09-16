package dal;

import java.sql.*;
import model.Employer;
import tool.EncodePassword;

public class RegisterEmployerDAO extends DBContext {

    // Kiểm tra email đã tồn tại chưa
    public boolean isEmailEmployerExist(String mail) {
        String query = "SELECT 1 FROM [dbo].[Employer] WHERE Email = ?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, mail);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            System.out.println("SQL error (isEmailEmployerExist): " + e.getMessage());
        }
        return false;
    }

    // Kiểm tra phone đã tồn tại chưa
    public boolean isPhoneEmployerExist(String phone) {
        String query = "SELECT 1 FROM [dbo].[Employer] WHERE PhoneNumber = ?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, phone);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            System.out.println("SQL error (isPhoneEmployerExist): " + e.getMessage());
        }
        return false;
    }

    // Đăng ký employer mới
    public boolean registerEmployer(String name, String mail, String phone, String password) {
        String query = "INSERT INTO [dbo].[Employer] "
                     + "([EmployerName], [Email], [PhoneNumber], [PasswordHash]) "
                     + "VALUES (?, ?, ?, ?)";

        String passwordHash = EncodePassword.encodePasswordbyHash(password);

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, name);
            ps.setString(2, mail);
            ps.setString(3, phone);
            ps.setString(4, passwordHash);

            int row = ps.executeUpdate();
            return row > 0;
        } catch (SQLException e) {
            System.out.println("SQL error (registerEmployer): " + e.getMessage());
        }
        return false;
    }

    // Đăng nhập employer
    public boolean loginEmployer(String email, String password) {
        String query = "SELECT PasswordHash FROM [dbo].[Employer] WHERE Email = ?";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    String storedHash = rs.getString("PasswordHash");
                    String inputHash = EncodePassword.encodePasswordbyHash(password);
                    return inputHash.equals(storedHash);
                }
            }
        } catch (SQLException e) {
            System.out.println("SQL error (loginEmployer): " + e.getMessage());
        }
        return false;
    }

    // Lấy EmployerID theo email
    public String getIDByEmail(String email) {
        String query = "SELECT EmployerID FROM [dbo].[Employer] WHERE Email = ?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("EmployerID");
                }
            }
        } catch (SQLException e) {
            System.out.println("SQL error (getIDByEmail): " + e.getMessage());
        }
        return null;
    }

    // Lấy EmployerName theo email
    public String getNameByEmail(String email) {
        String query = "SELECT EmployerName FROM [dbo].[Employer] WHERE Email = ?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("EmployerName");
                }
            }
        } catch (SQLException e) {
            System.out.println("SQL error (getNameByEmail): " + e.getMessage());
        }
        return null;
    }

    // Lấy đầy đủ thông tin Employer theo email
    public Employer getEmployerByEmail(String email) {
        String query = "SELECT EmployerID, EmployerName, Email, PhoneNumber, "
                     + "CompanyName, PasswordHash, Description, Location, "
                     + "URLWebsite, TaxCode, ImgLogo "
                     + "FROM [dbo].[Employer] WHERE Email = ?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Employer(
                        rs.getInt("EmployerID"),
                        rs.getString("EmployerName"),
                        rs.getString("Email"),
                        rs.getString("PhoneNumber"),
                        rs.getString("CompanyName"),
                        rs.getString("PasswordHash"),
                        rs.getString("Description"),
                        rs.getString("Location"),
                        rs.getString("URLWebsite"),
                        rs.getString("TaxCode"),
                        rs.getString("ImgLogo")
                    );
                }
            }
        } catch (SQLException e) {
            System.out.println("SQL error (getEmployerByEmail): " + e.getMessage());
        }
        return null;
    }
}