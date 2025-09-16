package dal;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBContext {
    private static final String URL = "jdbc:sqlserver://localhost:1433;"
            + "databaseName=SWP391;"
            + "encrypt=true;"
            + "trustServerCertificate=true";

    private static final String USER = "sa";
    private static final String PASSWORD = "123";

    static {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("SQLServer JDBC Driver not found!", e);
        }
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public void closeConnection(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
                // System.out.println("Connection closed successfully.");
            } catch (SQLException e) {
                System.out.println("Error closing connection: " + e.getMessage());
            }
        }
    }
    
    public static void main(String[] args) {
        DBContext db = new DBContext();
        try (Connection conn = db.getConnection()) {
            if (conn != null && !conn.isClosed()) {
                System.out.println("Successfully connected to SQL Server");
            } else {
                System.out.println("Failed to connect to SQL Server");
            }
        } catch (Exception e) {
            System.out.println("Error while connecting: " + e.getMessage());
            e.printStackTrace();
        }
    }
}