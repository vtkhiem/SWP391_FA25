/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.sql.*;
import model.Employer;
import tool.EncodePassword;

/**
 *
 * @author Admin
 */
public class RegisterEmployerDAO extends DBContext {

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

    public boolean isPhoneEmployerExist(String phone) {
        try {
            String query = "SELECT 1 FROM [dbo].[Employer] WHERE PhoneNumber = ?";
            PreparedStatement push = c.prepareStatement(query);
            push.setString(1, phone);
            ResultSet rs = push.executeQuery();

            return rs.next(); // nếu có dòng nào => số điện thoại đã tồn tại
        } catch (SQLException s) {
            System.out.println("Bug SQL: " + s.getMessage());
        }
        return false;
    }

    public boolean registerEmployer(String name, String mail, String phone, String password) {
        try {
            String query = "INSERT INTO [dbo].[Employer]\n"
                    + "           ([EmployerName]          \n"
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
                System.out.println("Đăng ký nhà tuyển dụng thành công!");
            } else {
                System.out.println("Đăng ký thất bại!");
            }

            return row != 0;
        } catch (SQLException s) {
            System.out.println("Lỗi SQL: " + s.getMessage());
        }
        return false;
    }

    public boolean loginEmployer(String email, String password) {
        String query = "SELECT PasswordHash FROM [dbo].[Employer] WHERE Email = ?";

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

            String query = "SELECT [EmployerID]\n"
                    + "  FROM [dbo].[Employer]\n"
                    + "  Where Email = ?";

            PreparedStatement push = c.prepareStatement(query);

            push.setString(1, email);

            ResultSet rs = push.executeQuery();
            while (rs.next()) {
                return rs.getString("EmployerID");
            }
        } catch (Exception s) {
            System.out.println("Bug  SQL:" + s.getMessage());

        }
        return null;
    }

    public String getNameByEmail(String email) {
        try {

            String query = "SELECT [EmployerName]\n"
                    + "  FROM [dbo].[Employer]\n"
                    + "  Where Email = ?";

            PreparedStatement push = c.prepareStatement(query);

            push.setString(1, email);

            ResultSet rs = push.executeQuery();
            while (rs.next()) {
                return rs.getString("EmployerName");
            }
        } catch (Exception s) {
            System.out.println("Bug  SQL:" + s.getMessage());

        }
        return null;
    }

    public Employer getEmployerByEmail(String email) {
        try {

            String query = "SELECT [EmployerID], [EmployerName], [Email], [PhoneNumber], [PasswordHash], "
                    + "[CompanyName], [Description], [Location], [URLWebsite], [ImgLogo] "
                    + "FROM [dbo].[Employer] "

                    + "WHERE Email = ?";

            PreparedStatement ps = c.prepareStatement(query);

            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new Employer(
                        rs.getInt("EmployerID"),
                        rs.getString("EmployerName"),
                        rs.getString("Email"),
                        rs.getString("PhoneNumber"),
                        rs.getString("PasswordHash"),
                        rs.getString("CompanyName"),
                        rs.getString("Description"),
                        rs.getString("Location"),
                        rs.getString("URLWebsite"),
     
                        rs.getString("ImgLogo"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public boolean verifyPassword(String email, String password) {
        String query = "SELECT [PasswordHash] FROM [dbo].[Employer] WHERE Email = ?";
        try {
            PreparedStatement ps = c.prepareStatement(query);
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                String storedHash = rs.getString("PasswordHash");
                String inputHash = EncodePassword.encodePasswordbyHash(password);
                return inputHash.equals(storedHash);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean changePassword(String email, String newHashedPassword) {
        String query = "UPDATE [dbo].[Employer] SET [PasswordHash] = ? WHERE Email = ?";
        try {
            PreparedStatement ps = c.prepareStatement(query);
            ps.setString(1, newHashedPassword);
            ps.setString(2, email);
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void main(String[] args) {
        RegisterEmployerDAO dao = new RegisterEmployerDAO();

        String name = "Nguyen Van Ca";
        String email = "nguyenvanca@example.com";
        String phone = "0912345674";
        String password = "1234561";
        Employer emp = dao.getEmployerByEmail(email);
if(emp!= null){
    System.out.println(emp.getEmployerName());
}
        // Kiểm tra email và phone trước khi đăng ký
    

        // Đóng connection
        dao.closeConnection();
    }
}
