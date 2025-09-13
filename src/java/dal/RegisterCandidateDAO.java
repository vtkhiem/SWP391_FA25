/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.sql.*;
import model.Candidate;
import tool.EncodePassword;

/**
 *
 * @author Admin
 */
public class RegisterCandidateDAO extends DBContext {

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

     public boolean isPhoneCandidateExist(String phone) {
    try {
        String query = "SELECT 1 FROM [dbo].[Candidate] WHERE PhoneNumber = ?";
        PreparedStatement push = c.prepareStatement(query);
        push.setString(1, phone);
        ResultSet rs = push.executeQuery();

        return rs.next(); // nếu có dòng nào => số điện thoại đã tồn tại
    } catch (SQLException s) {
        System.out.println("Bug SQL: " + s.getMessage());
    }
    return false;
}


    public boolean registerCandidate(String name, String mail, String phone, String password) {
        try {
            String query = "INSERT INTO [dbo].[Candidate]\n"
                    + "           ([CandidateName]          \n"
                    + "           ,[Email]                   \n"
                    + "           ,[PhoneNumber]                   \n"
                    + "           ,[PasswordHash])\n"
                    + "     VALUES (?,?,?,?)";

            // Mã hóa password trước khi lưu vào database 
            String passwordHash = EncodePassword.encodePasswordbyHash(password);

            PreparedStatement push = c.prepareStatement(query);
            push.setString(1, name);
            push.setString(2, mail);
            push.setString(3, phone);
            push.setString(4, passwordHash);

            int row = push.executeUpdate();

           
            if (row > 0) {
                System.out.println("Đăng ký ứng viên thành công!");
            } else {
                System.out.println("Đăng ký thất bại!");
            }

            return row != 0;
        } catch (SQLException s) {
            System.out.println("Lỗi SQL: " + s.getMessage());
        }
        return false;
    }
      public boolean loginCandidate(String email, String password) {
    String query = "SELECT PasswordHash FROM [dbo].[Candidate] WHERE Email = ?";

    try (PreparedStatement ps = c.prepareStatement(query)) {
        ps.setString(1, email);
        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                String storedHash = rs.getString("PasswordHash");
                String inputHash = EncodePassword.encodePasswordbyHash(password);
                return inputHash.equals(storedHash);
            } else {
                // Không tìm thấy email
                return false;
            }
        }
    } catch (SQLException e) {
        System.out.println("Lỗi SQL: " + e.getMessage());
        return false;
    }
}
    public String getIDByEmail(String email) {
        try {

            String query = "SELECT [CandidateID]\n"
                    + "  FROM [dbo].[Candidate]\n"
                    + "  Where Email = ?";

            PreparedStatement push = c.prepareStatement(query);

            push.setString(1, email);

            ResultSet rs = push.executeQuery();
            while (rs.next()) {
                return rs.getString("CandidateID");
            }
        } catch (Exception s) {
            System.out.println("Bug  SQL:" + s.getMessage());

        }
        return null;
    }
    public Candidate getCandidateByEmail(String email) {
        try {
              String query = "SELECT [CandidateID], [CandidateName], [Address], [Email], [PhoneNumber], "
                     + "[Nationality], [PasswordHash], [Avatar] "
                     + "FROM [dbo].[Candidate] "
                     + "WHERE Email = ?";
      
             PreparedStatement ps = c.prepareStatement(query);
            
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            
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
        } catch (Exception e) {
        }
      
      
        return null;  
    }
     
     
     
    public static void main(String[] args) {
        RegisterCandidateDAO dao = new RegisterCandidateDAO();

        String name = "Nguyen Van C";
        String email = "nguyenvanc@example.com";
        String phone = "0912345678";
        String password = "123456";

        // Kiểm tra email và phone trước khi đăng ký
        if (dao.isEmailCandidateExist(email)) {
            System.out.println("Email đã tồn tại!");
        } else if (dao.isPhoneCandidateExist(phone)) {
            System.out.println("Số điện thoại đã tồn tại!");
        } else {
            boolean success = dao.registerCandidate(name, email, phone, password);
            System.out.println("Kết quả đăng ký: " + (success ? "Thành công" : "Thất bại"));
        }

        // Đóng connection
        dao.closeConnection();
    }
  


}
