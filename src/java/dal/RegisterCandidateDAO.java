package dal;

import java.sql.*;
import model.Candidate;
import tool.EncodePassword;

public class RegisterCandidateDAO extends DBContext {

    // Kiểm tra email tồn tại
    public boolean isEmailCandidateExist(String mail) {
        String query = "SELECT 1 FROM [dbo].[Candidate] WHERE Email = ?";
        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, mail);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            System.out.println("SQL error (isEmailCandidateExist): " + e.getMessage());
        }
        return false;
    }

    // Kiểm tra phone tồn tại
    public boolean isPhoneCandidateExist(String phone) {
        String query = "SELECT 1 FROM [dbo].[Candidate] WHERE PhoneNumber = ?";
        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, phone);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            System.out.println("SQL error (isPhoneCandidateExist): " + e.getMessage());
        }
        return false;
    }

    // Đăng ký candidate
    public boolean registerCandidate(String name, String mail, String phone, String password) {
        String query = "INSERT INTO [dbo].[Candidate] "
                + "([CandidateName], [Email], [PhoneNumber], [PasswordHash]) "
                + "VALUES (?, ?, ?, ?)";

        String passwordHash = EncodePassword.encodePasswordbyHash(password);

        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, name);
            ps.setString(2, mail);
            ps.setString(3, phone);
            ps.setString(4, passwordHash);

            int row = ps.executeUpdate();
            return row > 0;

        } catch (SQLException e) {
            System.out.println("SQL error (registerCandidate): " + e.getMessage());
        }
        return false;
    }

    // Login candidate
    public boolean loginCandidate(String email, String password) {
        String query = "SELECT PasswordHash FROM [dbo].[Candidate] WHERE Email = ?";

        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    String storedHash = rs.getString("PasswordHash");
                    String inputHash = EncodePassword.encodePasswordbyHash(password);
                    return inputHash.equals(storedHash);
                }
            }
        } catch (SQLException e) {
            System.out.println("SQL error (loginCandidate): " + e.getMessage());
        }
        return false;
    }

    // Lấy CandidateID theo email
    public String getIDByEmail(String email) {
        String query = "SELECT CandidateID FROM [dbo].[Candidate] WHERE Email = ?";
        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("CandidateID");
                }
            }
        } catch (SQLException e) {
            System.out.println("SQL error (getIDByEmail): " + e.getMessage());
        }
        return null;
    }

    // Lấy Candidate theo email
    public Candidate getCandidateByEmail(String email) {
        String query = "SELECT CandidateID, CandidateName, Address, Email, PhoneNumber, "
                + "Nationality, PasswordHash, Avatar "
                + "FROM [dbo].[Candidate] WHERE Email = ?";
        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Candidate(
                            rs.getInt("CandidateID"),
                            rs.getString("CandidateName"),
                            rs.getString("Address"),
                            rs.getString("Email"),
                            rs.getString("PhoneNumber"),
                            rs.getString("Nationality"),
                            rs.getString("PasswordHash"),
                            rs.getString("Avatar")
                    );
                }
            }
        } catch (SQLException e) {
            System.out.println("SQL error (getCandidateByEmail): " + e.getMessage());
        }
        return null;
    }
}