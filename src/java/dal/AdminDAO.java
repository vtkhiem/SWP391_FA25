package dal;

import java.sql.*;
import tool.EncodePassword;

public class AdminDAO extends DBContext {
    public boolean isAdmin(String accountName) {
        String query = "SELECT [AdminID], [Username], [PasswordHash] "
                + "FROM [dbo].[Admin] "
                + "WHERE Username = ?";

        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, accountName);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next(); // nếu có user thì là admin
            }

        } catch (SQLException e) {
            System.out.println("SQL error (isAdmin): " + e.getMessage());
        }
        return false;
    }

    public boolean loginAccountAdmin(String username, String password) {
        String query = "SELECT [AdminID], [Username], [PasswordHash] "
                + "FROM [dbo].[Admin] "
                + "WHERE Username = ?";

        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, username);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    String dbPasswordHash = rs.getString("PasswordHash");
                    String inputPasswordHash = EncodePassword.encodePasswordbyHash(password);

                    return inputPasswordHash.equals(dbPasswordHash);
                }
            }

        } catch (SQLException e) {
            System.out.println("SQL error (loginAccountAdmin): " + e.getMessage());
        }
        return false;
    }
}