/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;
 import java.sql.*;
import tool.EncodePassword;
/**
 *
 * @author Admin
 */
public class AdminDAO extends DBContext{
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

    // đăng nhập  Admin  (đẫ test)
    public boolean loginAccountAdmin(String username, String password) {
        try {
            String query = "SELECT [AdminID]\n"
                    + "      ,[Username]\n"
                    + "      ,[PasswordHash]\n"
                    + "  FROM [dbo].[Admin]\n"
                    + "  Where Username like ?";

           String passwordHash = EncodePassword.encodePasswordbyHash(password); 

            PreparedStatement push = c.prepareStatement(query);
            push.setString(1, username);

            ResultSet rs = push.executeQuery();

            while (rs.next()) {
                String getpasswordEncodeInBase = rs.getString("PasswordHash");
                if (passwordHash.equals(getpasswordEncodeInBase)) { 
                    return true;
                }
            }

        } catch (SQLException s) {
            System.out.println("Lỗi SQL: " + s.getMessage());
        }
        return false;
    }

 
}
