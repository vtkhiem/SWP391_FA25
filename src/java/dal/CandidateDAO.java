package dal;

import model.Candidate;
import java.sql.*;
import java.util.*;
import model.Employer;

public class CandidateDAO extends DBContext {

    private Connection conn() {
        try {
            try {
                var f = DBContext.class.getDeclaredField("c");
                f.setAccessible(true);
                Object v = f.get(this);
                if (v instanceof Connection) {
                    return (Connection) v;
                }
            } catch (NoSuchFieldException ignore) {
            }
            try {
                var f = DBContext.class.getDeclaredField("connection");
                f.setAccessible(true);
                Object v = f.get(this);
                if (v instanceof Connection) {
                    return (Connection) v;
                }
            } catch (NoSuchFieldException ignore) {
            }
        } catch (IllegalAccessException ignore) {
        }
        return null;
    }

    private Connection requireConn() throws SQLException {
        Connection cx = conn();
        if (cx == null) {
            throw new SQLException("DB connection is null");
        }
        return cx;
    }


    public int countAll(String likePattern) {
        String sql = "SELECT COUNT(*) FROM Candidate";
        boolean hasKw = likePattern != null && !likePattern.trim().isEmpty();
        if (hasKw) {
            sql += " WHERE CandidateName COLLATE Vietnamese_100_CI_AI LIKE ?";
        }

        try (PreparedStatement ps = requireConn().prepareStatement(sql)) {
            if (hasKw) {
                ps.setString(1, likePattern);
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


    public List<Candidate> getAllCandidates() {
        List<Candidate> list = new ArrayList<>();
        String sql = "SELECT * FROM Candidate";
        try (PreparedStatement st = c.prepareStatement(sql); ResultSet rs = st.executeQuery()) {
            while (rs.next()) {
                list.add(mapRow(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }


    public List<Candidate> findPage(int page, int pageSize, String likePattern) {

        List<Candidate> list = new ArrayList<>();
        if (page < 1) {
            page = 1;
        }
        int offset = (page - 1) * pageSize;

        boolean hasKw = likePattern != null && !likePattern.trim().isEmpty();

        StringBuilder sb = new StringBuilder();
        sb.append("SELECT CandidateID, CandidateName, Email, PhoneNumber, Nationality, isPublic ")
                .append("FROM Candidate ");


        if (hasKw) {
            sb.append("WHERE CandidateName COLLATE Vietnamese_100_CI_AI LIKE ? ");
        }
        sb.append("ORDER BY CandidateID ASC ")
                .append("OFFSET ? ROWS FETCH NEXT ? ROWS ONLY;");

        try (PreparedStatement ps = requireConn().prepareStatement(sb.toString())) {
            int idx = 1;
            if (hasKw) {
                ps.setString(idx++, likePattern);
            }
            ps.setInt(idx++, offset);
            ps.setInt(idx, pageSize);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Candidate cd = new Candidate();
                    cd.setCandidateId(rs.getInt("CandidateID"));
                    cd.setCandidateName(rs.getString("CandidateName"));
                    cd.setEmail(rs.getString("Email"));
                    cd.setPhoneNumber(rs.getString("PhoneNumber"));
                    cd.setNationality(rs.getString("Nationality"));
                    list.add(cd);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public Candidate findById(int id) {
        String sql = """
            SELECT CandidateID, CandidateName, Address, Email, PhoneNumber, Nationality, PasswordHash, Avatar, isPublic 
            FROM Candidate
            WHERE CandidateID = ?
        """;
        try (PreparedStatement ps = requireConn().prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapRow(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
public int getIdByEmail(String email) {
    String sql = """
                 SELECT CandidateID
                 FROM Candidate
                 WHERE Email = ?
               """;
    try (PreparedStatement ps = requireConn().prepareStatement(sql)) {
        ps.setString(1, email);
        try (ResultSet rs = ps.executeQuery()) {
      
            if (rs.next()) {
                return rs.getInt("CandidateID"); // <--- Dòng 127 của bạn
            }
            
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    

    return -1; 
}

    public List<Candidate> getPublicCandidates(int page, int pageSize) {
        List<Candidate> list = new ArrayList<>();

        if (page < 1) {
            page = 1;
        }
        int offset = (page - 1) * pageSize;

        StringBuilder sb = new StringBuilder();
        sb.append("SELECT CandidateID, CandidateName, Email, PhoneNumber, Nationality, isPublic ")
                .append("FROM Candidate "
                        + "WHERE isPublic = 1");

        sb.append("ORDER BY CandidateID ASC ")
                .append("OFFSET ? ROWS FETCH NEXT ? ROWS ONLY;");

        try (PreparedStatement ps = c.prepareStatement(sb.toString())) {
            int idx = 1;
            ps.setInt(idx++, offset);
            ps.setInt(idx, pageSize);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Candidate cd = new Candidate();
                    cd.setCandidateId(rs.getInt("CandidateID"));
                    cd.setCandidateName(rs.getString("CandidateName"));
                    cd.setEmail(rs.getString("Email"));
                    cd.setPhoneNumber(rs.getString("PhoneNumber"));
                    cd.setNationality(rs.getString("Nationality"));
                    cd.setIsPublic(rs.getBoolean("isPublic"));
                    list.add(cd);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public int countPublicCanididates() {
        String sql = "SELECT COUNT(*) FROM Candidate WHERE isPublic =1 ";

        try (PreparedStatement ps = c.prepareStatement(sql)) {

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

    public Candidate getCandidateById(int id) {
        return findById(id);
    }

    public boolean existsByEmail(String email) {
        String sql = "SELECT 1 FROM Candidate WHERE Email = ?";
        try (PreparedStatement ps = requireConn().prepareStatement(sql)) {
            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return true; 
        }
    }

    public boolean existsByPhone(String phone) {
        String sql = "SELECT 1 FROM Candidate WHERE PhoneNumber = ?";
        try (PreparedStatement ps = requireConn().prepareStatement(sql)) {
            ps.setString(1, phone);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return true;
        }
    }

    public boolean existsByName(String name) {
        String sql = "SELECT 1 FROM Candidate WHERE CandidateName = ?";
        try (PreparedStatement ps = requireConn().prepareStatement(sql)) {
            ps.setString(1, name);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return true;
        }
    }

    public Candidate checkLogin(String email, String passwordHash) {
        String sql = """
            SELECT CandidateID, CandidateName, Address, Email, PhoneNumber, Nationality, PasswordHash, Avatar, isPublic 
            FROM Candidate
            WHERE Email = ? AND PasswordHash = ?
        """;
        try (PreparedStatement ps = requireConn().prepareStatement(sql)) {
            ps.setString(1, email);
            ps.setString(2, passwordHash);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapRow(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    public boolean deleteCascade(int candidateId) {
        String delApply = "DELETE FROM Apply WHERE CandidateID = ?";
        String delCand = "DELETE FROM Candidate WHERE CandidateID = ?";
        try {
            Connection cx = requireConn();
            boolean oldAuto = cx.getAutoCommit();
            cx.setAutoCommit(false);
            try (PreparedStatement ps1 = cx.prepareStatement(delApply); PreparedStatement ps2 = cx.prepareStatement(delCand)) {
                ps1.setInt(1, candidateId);
                ps1.executeUpdate();
                ps2.setInt(1, candidateId);
                int rows = ps2.executeUpdate();
                cx.commit();
                cx.setAutoCommit(oldAuto);
                return rows > 0;
            } catch (SQLException ex) {
                cx.rollback();
                cx.setAutoCommit(true);
                throw ex;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    // Cập nhật hồ sơ ứng viên

    public boolean updateCandidateProfile(Candidate candidate) {
        String sql = """
            UPDATE Candidate
               SET CandidateName = ?,
                   Address = ?,
                   PhoneNumber = ?,
                   Nationality = ?,
                   isPublic = ?
             WHERE CandidateID = ?
            """;
        try (PreparedStatement ps = requireConn().prepareStatement(sql)) {

            ps.setString(1, candidate.getCandidateName());
            ps.setString(2, candidate.getAddress());
            ps.setString(3, candidate.getPhoneNumber());
            ps.setString(4, candidate.getNationality());
            ps.setBoolean(5, candidate.isIsPublic());
            ps.setInt(6, candidate.getCandidateId());

            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    private Candidate mapRow(ResultSet rs) throws SQLException {
        Candidate cd = new Candidate();
        cd.setCandidateId(rs.getInt("CandidateID"));
        cd.setCandidateName(rs.getString("CandidateName"));
        cd.setAddress(rs.getString("Address"));
        cd.setEmail(rs.getString("Email"));
        cd.setPhoneNumber(rs.getString("PhoneNumber"));
        cd.setNationality(rs.getString("Nationality"));
        cd.setPasswordHash(rs.getString("PasswordHash"));
        cd.setAvatar(rs.getString("Avatar"));
        cd.setIsPublic(false);
        return cd;
    }

    public boolean updateAvatar(int candidateId, String imageURL) {
        String sql = """
            UPDATE Candidate
               SET Avatar = ?
             WHERE CandidateID = ?
            """;
        try (PreparedStatement ps = requireConn().prepareStatement(sql)) {

            ps.setString(1, imageURL);
            ps.setInt(2, candidateId);

            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Candidate getCandidateByCV(int CVID) {
        String sql = """
            SELECT c.CandidateID, c.CandidateName, c.Address, c.Email, 
                   c.PhoneNumber, c.Nationality, c.PasswordHash, c.Avatar, c.isPublic
            FROM Candidate c
            JOIN CV v ON c.CandidateID = v.CandidateID
            WHERE v.CVID = ?
        """;
        try (PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, CVID);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Candidate candidate = new Candidate();
                    candidate.setCandidateId(rs.getInt("CandidateID"));
                    candidate.setCandidateName(rs.getString("CandidateName"));
                    candidate.setAddress(rs.getString("Address"));
                    candidate.setEmail(rs.getString("Email"));
                    candidate.setPhoneNumber(rs.getString("PhoneNumber"));
                    candidate.setNationality(rs.getString("Nationality"));
                    candidate.setPasswordHash(rs.getString("PasswordHash"));
                    candidate.setAvatar(rs.getString("Avatar"));
                    candidate.setIsPublic(rs.getBoolean("isPublic"));
                    return candidate;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public static void main(String[] args) {
        System.out.println(new CandidateDAO().getCandidateByCV(2));
    }
}
