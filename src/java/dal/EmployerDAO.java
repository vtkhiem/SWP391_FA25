package dal;

import model.Employer;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmployerDAO extends DBContext {

    public int countAll(String keyword, Boolean statusFilter) {
        StringBuilder sql = new StringBuilder("SELECT COUNT(*) FROM Employer WHERE 1=1");
        List<Object> params = new ArrayList<>();

        if (keyword != null && !keyword.trim().isEmpty()) {
            sql.append(" AND (EmployerName LIKE ? OR Email LIKE ? OR PhoneNumber LIKE ? OR CompanyName LIKE ?)");
            String like = "%" + keyword.trim() + "%";
            params.add(like);
            params.add(like);
            params.add(like);
            params.add(like);
        }
        if (statusFilter != null) {
            sql.append(" AND [Status] = ?");
            params.add(statusFilter);
        }


        try (PreparedStatement ps = c.prepareStatement(sql.toString())) {
            for (int i = 0; i < params.size(); i++) {
                Object v = params.get(i);
                if (v instanceof String) {
                    ps.setString(i + 1, (String) v);
                } else if (v instanceof Boolean) {
                    ps.setBoolean(i + 1, (Boolean) v);
                } else {
                    ps.setObject(i + 1, v);
                }
            }
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public List<Employer> getAllEmployers() {
        List<Employer> list = new ArrayList<>();
        String sql = "SELECT * FROM Employer";
        try (PreparedStatement st = c.prepareStatement(sql); ResultSet rs = st.executeQuery()) {
            while (rs.next()) {
                Employer e = new Employer();
                e.setEmployerId(rs.getInt("EmployerID"));
                e.setEmployerName(rs.getString("EmployerName"));
                e.setEmail(rs.getString("Email"));
                e.setPhoneNumber(rs.getString("PhoneNumber"));
                e.setPasswordHash(rs.getString("PasswordHash"));
                e.setCompanyName(rs.getString("CompanyName"));
                e.setDescription(rs.getString("Description"));
                e.setLocation(rs.getString("Location"));
                e.setUrlWebsite(rs.getString("URLWebsite"));

                // Added TaxCode based on the logic from the HEAD branch for consistency
                try {
                    e.setTaxCode(rs.getString("TaxCode"));
                } catch (SQLException ignored) {

                }
                e.setImgLogo(rs.getString("ImgLogo"));
                e.setStatus(rs.getBoolean("Status"));
                list.add(e);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    // Merged findPage: Uses Boolean for statusFilter (consistent with countAll)
    public List<Employer> findPage(int page, int pageSize, String keyword, Boolean statusFilter) {
        List<Employer> list = new ArrayList<>();
        StringBuilder sql = new StringBuilder();
        // Combined SELECT columns from both versions (TaxCode from HEAD, PasswordHash from main, keeping all)
        sql.append("""
        SELECT EmployerID, EmployerName, Email, PhoneNumber, PasswordHash,
               CompanyName, Description, Location, URLWebsite, TaxCode, ImgLogo, [Status]
        FROM Employer
        WHERE 1=1
    """);

        List<Object> params = new ArrayList<>();

        if (keyword != null && !keyword.trim().isEmpty()) {
            sql.append(" AND (EmployerName LIKE ? OR Email LIKE ? OR PhoneNumber LIKE ? OR CompanyName LIKE ?)");
            String like = "%" + keyword.trim() + "%";
            params.add(like);
            params.add(like);
            params.add(like);
            params.add(like);
        }
        if (statusFilter != null) {
            sql.append(" AND [Status] = ?");
            params.add(statusFilter);
        }

        sql.append(" ORDER BY EmployerID ASC OFFSET ? ROWS FETCH NEXT ? ROWS ONLY");
        int offset = (Math.max(page, 1) - 1) * pageSize;
        params.add(offset);
        params.add(pageSize);

        try (PreparedStatement ps = c.prepareStatement(sql.toString())) {
            int idx = 1;
            for (Object v : params) {
                // Robust parameter binding handling different types (String, Boolean, Integer for offset/pagesize)
                if (v instanceof String) {
                    ps.setString(idx++, (String) v);
                } else if (v instanceof Boolean) {
                    ps.setBoolean(idx++, (Boolean) v);
                } else if (v instanceof Integer) {
                    ps.setInt(idx++, (Integer) v);
                } else {
                    ps.setObject(idx++, v);
                }
            }
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Employer e = new Employer();
                    e.setEmployerId(rs.getInt("EmployerID"));
                    e.setEmployerName(rs.getString("EmployerName"));
                    e.setEmail(rs.getString("Email"));
                    e.setPhoneNumber(rs.getString("PhoneNumber"));

                    // PasswordHash might not be needed for all uses of this method, but include it if the model supports it.
                    // It was present in the main's SELECT list. Check if column exists before retrieving.
                    try {
                        e.setPasswordHash(rs.getString("PasswordHash"));
                    } catch (SQLException ex) {
                        // Ignore if the column 'PasswordHash' is not in the result set (e.g. if the final SELECT was simplified)
                    }

                    e.setCompanyName(rs.getString("CompanyName"));
                    e.setDescription(rs.getString("Description"));
                    e.setLocation(rs.getString("Location"));
                    e.setUrlWebsite(rs.getString("URLWebsite"));

                    // TaxCode was only in HEAD's SELECT list.
                    try {
                        e.setTaxCode(rs.getString("TaxCode"));
                    } catch (SQLException ex) {
                        // Ignore if the column 'TaxCode' is not in the result set
                    }

                    e.setImgLogo(rs.getString("ImgLogo"));
                    e.setStatus(rs.getBoolean("Status"));
                    list.add(e);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public Employer findById(int id) {
        String sql = """
            SELECT EmployerID, EmployerName, Email, PhoneNumber, PasswordHash,
                           CompanyName, Description, Location, URLWebsite, TaxCode,
                           ImgLogo, Status    
            FROM Employer
            WHERE EmployerID = ?
        """;
        try (PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Employer e = new Employer();
                    e.setEmployerId(rs.getInt("EmployerID"));
                    e.setEmployerName(rs.getString("EmployerName"));
                    e.setEmail(rs.getString("Email"));
                    e.setPhoneNumber(rs.getString("PhoneNumber"));
                    e.setPasswordHash(rs.getString("PasswordHash"));
                    e.setCompanyName(rs.getString("CompanyName"));
                    e.setDescription(rs.getString("Description"));
                    e.setLocation(rs.getString("Location"));
                    e.setUrlWebsite(rs.getString("URLWebsite"));
                    try {
                        e.setTaxCode(rs.getString("TaxCode"));
                    } catch (SQLException ignored) {
                    }
                    e.setImgLogo(rs.getString("ImgLogo"));
                    e.setStatus(rs.getBoolean("Status")); // <— đọc status
                    return e;
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }


    // New method from 'main' branch

    public String getEmailByID(int id) {
        String sql = """
            SELECT  Email
            FROM Employer
            WHERE EmployerID = ?
        """;
        try (PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("Email");
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }
        public int getIdByEmail(String email) {
        String sql = """
            SELECT  EmployerID
            FROM Employer
            WHERE Email = ?
        """;
        try (PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("EmployerID");
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return -1;
    }

    public int insertWithStatus(Employer e) {
        String sql = """
        INSERT INTO Employer (
            EmployerName, Email, PhoneNumber, PasswordHash,
            CompanyName, Description, Location, URLWebsite,
            TaxCode, ImgLogo, [Status]
        )
        OUTPUT INSERTED.EmployerID
        VALUES (?,?,?,?,?,?,?,?,?,?,?)
    """;
        try (PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, e.getEmployerName());
            ps.setString(2, e.getEmail());
            ps.setString(3, e.getPhoneNumber());
            ps.setString(4, e.getPasswordHash());
            ps.setString(5, e.getCompanyName());
            ps.setString(6, e.getDescription());
            ps.setString(7, e.getLocation());
            ps.setString(8, e.getUrlWebsite());
            if (e.getTaxCode() == null || e.getTaxCode().isBlank()) {
                ps.setNull(9, Types.NVARCHAR);
            } else {
                ps.setString(9, e.getTaxCode());
            }
            if (e.getImgLogo() == null || e.getImgLogo().isBlank()) {
                ps.setNull(10, Types.NVARCHAR);
            } else {
                ps.setString(10, e.getImgLogo());
            }
            ps.setBoolean(11, e.isStatus());

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return 0;
    }

    public boolean delete(int id) {
        String sql = "DELETE FROM Employer WHERE EmployerID = ?";
        try (PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }


    public boolean updateStatus(int employerId, int status) {
        String sql = "UPDATE Employer SET [Status] = ? WHERE EmployerID = ?";
        try (PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, status);
            ps.setInt(2, employerId);
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public boolean verify(int employerId) {
        return updateStatus(employerId, 1);
    }

    public boolean updateEmployerProfile(Employer employer) {
        String sql = "UPDATE Employer\n"
                + "SET CompanyName = ?,\n"
                + "PhoneNumber = ?,\n"
                + "Location = ?,\n"
                + "Description = ?,\n"
                + "URLWebsite =?\n"
                + "WHERE EmployerID = ?";

        try (PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setString(1, employer.getCompanyName());
            ps.setString(2, employer.getPhoneNumber());
            ps.setString(3, employer.getLocation());
            ps.setString(4, employer.getDescription());
            ps.setString(5, employer.getUrlWebsite());
            ps.setInt(6, employer.getEmployerId());

            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateLogo(int employerId, String logoURL) {
        String sql = """
            UPDATE Employer
               SET ImgLogo = ?
             WHERE EmployerID = ?
            """;
        try (PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setString(1, logoURL);
            ps.setInt(2, employerId);

            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
        
        public String getCompanyNameByEmployerID(int employerId){
     String sql = """
            SELECT  CompanyName
            FROM Employer
            WHERE EmployerID = ?
        """;
        try (PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, employerId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("CompanyName");
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
        }

    public static void main(String[] args) {
        System.out.println(new EmployerDAO().findById(5));
    }
}
