/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import model.EmailService;
import tool.EncodePassword;

/**
 *
 * @author Admin
 */
public class PasswordDAO extends DBContext {

    public boolean isEmailCandidateExist(String mail) {
        try {
            String query = "SELECT 1 FROM [dbo].[Candidate] WHERE Email = ?";
            PreparedStatement push = c.prepareStatement(query);
            push.setString(1, mail);
            ResultSet rs = push.executeQuery();

            return rs.next(); // có dòng nào => email đã tồn tại
        } catch (SQLException s) {
            System.out.println("Bug SQL: " + s.getMessage());
        }
        return false;
    }

    public boolean isEmailEmployerExist(String mail) {
        try {
            String query = "SELECT 1 FROM [dbo].[Employer] WHERE Email = ?";
            PreparedStatement push = c.prepareStatement(query);
            push.setString(1, mail);
            ResultSet rs = push.executeQuery();

            return rs.next(); // có dòng nào => email đã tồn tại
        } catch (SQLException s) {
            System.out.println("Bug SQL: " + s.getMessage());
        }
        return false;
    }

    public void sendResetLink(String email, String role) {
        try {
            String token = java.util.UUID.randomUUID().toString();
            java.sql.Timestamp expiry = new java.sql.Timestamp(System.currentTimeMillis() + 15 * 60 * 1000); // 15 phút

            // Lưu token vào DB
            PasswordDAO passwordDAO = new PasswordDAO();
            passwordDAO.savePasswordResetToken(email, role, token, expiry);

            // Tạo link reset
            String resetLink = "http://localhost:8080/YourApp/resetPassword?token=" + token + "&role=" + role;

            // Gửi mail (bạn cần cấu hình JavaMail)
            String subject = "Đặt lại mật khẩu";
            String content = "Nhấn vào link sau để đặt lại mật khẩu (có hiệu lực 15 phút):\n" + resetLink;

            EmailService.sendEmail(email, subject, content); // tự viết class EmailUtility
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void savePasswordResetToken(String email, String role, String token, Timestamp expiry) {
      String query = "INSERT INTO PasswordResetToken (Email, TokenHash, ExpiresAt, Role) VALUES (?, ?, ?, ?)";
        try {
            PreparedStatement ps = c.prepareStatement(query);
            ps.setString(1, email);
        ps.setString(2, hashToken(token)); // token hashed
        ps.setTimestamp(3, expiry);
        ps.setString(4, role);
        ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private String hashToken(String token) {
        try {
            EncodePassword encode = new EncodePassword();
            return encode.encodePasswordbyHash(token);
            
        } catch (Exception e) {
        }
        return null;
        
}
   public boolean isValidToken(String token, String role) {
    String query = "SELECT 1 FROM PasswordResetToken " +
                   "WHERE TokenHash = ? AND Role = ? " +
                   "AND ExpiresAt > SYSUTCDATETIME() AND Used = 0";
    try (PreparedStatement ps = c.prepareStatement(query)) {
        ps.setString(1, hashToken(token));
        ps.setString(2, role);

        try (ResultSet rs = ps.executeQuery()) {
            return rs.next(); // true nếu có bản ghi
        }
    } catch (SQLException e) {
        e.printStackTrace();
        return false;
    }
}

    public void markTokenAsUsed(String token, String role) {
    String sql = "UPDATE PasswordResetToken SET Used = 1 WHERE TokenHash = ? AND Role = ?";
    try (PreparedStatement ps = c.prepareStatement(sql)) {
        ps.setString(1, hashToken(token));
        ps.setString(2, role);
        ps.executeUpdate();
    } catch (SQLException e) {
        e.printStackTrace();
    }
}



}
