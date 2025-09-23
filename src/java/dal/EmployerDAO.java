package dal;

import model.Employer;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmployerDAO extends DBContext {

    public int countAll(String keyword) {
        String base = "SELECT COUNT(*) FROM Employer";
        String where = "";
        if (keyword != null && !keyword.trim().isEmpty()) {
            where = " WHERE EmployerName LIKE ? OR Email LIKE ? OR PhoneNumber LIKE ? OR CompanyName LIKE ?";
        }
        String sql = base + where;

        try (PreparedStatement ps = c.prepareStatement(sql)) {
            if (!where.isEmpty()) {
                String like = "%" + keyword.trim() + "%";
                ps.setString(1, like);
                ps.setString(2, like);
                ps.setString(3, like);
                ps.setString(4, like);
            }
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public List<Employer> findPage(int page, int pageSize, String keyword) {
        StringBuilder sb = new StringBuilder();
        sb.append("""
            SELECT EmployerID, EmployerName, Email, PhoneNumber, PasswordHash,
                   CompanyName, Description, Location, URLWebsite, TaxCode, ImgLogo
            FROM Employer
        """);

        List<Object> params = new ArrayList<>();
        if (keyword != null && !keyword.trim().isEmpty()) {
            sb.append(" WHERE EmployerName LIKE ? OR Email LIKE ? OR PhoneNumber LIKE ? OR CompanyName LIKE ? ");
            String like = "%" + keyword.trim() + "%";
            params.add(like); params.add(like); params.add(like); params.add(like);
        }
        sb.append(" ORDER BY EmployerID DESC OFFSET ? ROWS FETCH NEXT ? ROWS ONLY");

        int offset = (Math.max(page, 1) - 1) * pageSize;
        params.add(offset);
        params.add(pageSize);

        List<Employer> list = new ArrayList<>();
        try (PreparedStatement ps = c.prepareStatement(sb.toString())) {
            for (int i = 0; i < params.size(); i++) {
                Object v = params.get(i);
                if (v instanceof Integer) ps.setInt(i + 1, (Integer) v);
                else ps.setString(i + 1, v.toString());
            }
            try (ResultSet rs = ps.executeQuery()) {
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
                    e.setTaxCode(rs.getString("TaxCode"));
                    e.setImgLogo(rs.getString("ImgLogo"));
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
               CompanyName, Description, Location, URLWebsite, TaxCode, ImgLogo
        FROM Employer WHERE EmployerID = ?
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
                e.setTaxCode(rs.getString("TaxCode"));
                e.setImgLogo(rs.getString("ImgLogo"));
                return e;
            }
        }
    } catch (SQLException ex) { ex.printStackTrace(); }
    return null;
}

public int insert(Employer e) {
    String sql = """
        INSERT INTO Employer (EmployerName, Email, PhoneNumber, PasswordHash,
                              CompanyName, Description, Location, URLWebsite, TaxCode, ImgLogo)
        OUTPUT INSERTED.EmployerID
        VALUES (?,?,?,?,?,?,?,?,?,?)
    """;
    try (PreparedStatement ps = c.prepareStatement(sql)) {
        ps.setString(1, e.getEmployerName());
        ps.setString(2, e.getEmail());
        ps.setString(3, e.getPhoneNumber());
        ps.setString(4, e.getPasswordHash()); // HASH đã tạo từ servlet
        ps.setString(5, e.getCompanyName());
        ps.setString(6, e.getDescription());
        ps.setString(7, e.getLocation());
        ps.setString(8, e.getUrlWebsite());
        ps.setString(9, e.getTaxCode());
        ps.setString(10, e.getImgLogo()); // null
        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) return rs.getInt(1);
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
    } catch (SQLException ex) { ex.printStackTrace(); return false; }
}

    
}
