package dal;

import model.Candidate;
import java.sql.*;
import java.util.*;

public class CandidateDAO extends DBContext {

    private Connection conn() {
        try {
            try {
                var f = DBContext.class.getDeclaredField("c");
                f.setAccessible(true);
                Object v = f.get(this);
                if (v instanceof Connection) return (Connection) v;
            } catch (NoSuchFieldException ignore) { }
            try {
                var f = DBContext.class.getDeclaredField("connection");
                f.setAccessible(true);
                Object v = f.get(this);
                if (v instanceof Connection) return (Connection) v;
            } catch (NoSuchFieldException ignore) { }
        } catch (IllegalAccessException ignore) { }
        return null;
    }

    private Connection requireConn() throws SQLException {
        Connection cx = conn();
        if (cx == null) throw new SQLException("DB connection is null");
        return cx;
    }

    public int countAll(String keyword) {
        String base = "SELECT COUNT(*) FROM Candidate";
        boolean hasKw = keyword != null && !keyword.trim().isEmpty();
        String where = hasKw ? " WHERE CandidateName LIKE ? OR Email LIKE ? OR PhoneNumber LIKE ?" : "";
        String sql = base + where;

        try (PreparedStatement ps = requireConn().prepareStatement(sql)) {
            if (hasKw) {
                String like = "%" + keyword.trim() + "%";
                ps.setString(1, like);
                ps.setString(2, like);
                ps.setString(3, like);
            }
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public List<Candidate> findPage(int page, int pageSize, String keyword) {
        StringBuilder sb = new StringBuilder();
        sb.append("""
            SELECT CandidateID, CandidateName, Address, Email, PhoneNumber, Nationality, PasswordHash, Avatar
            FROM Candidate
        """);

        List<Object> params = new ArrayList<>();
        if (keyword != null && !keyword.trim().isEmpty()) {
            sb.append(" WHERE CandidateName LIKE ? OR Email LIKE ? OR PhoneNumber LIKE ? ");
            String like = "%" + keyword.trim() + "%";
            params.add(like);
            params.add(like);
            params.add(like);
        }
        sb.append(" ORDER BY CandidateID DESC OFFSET ? ROWS FETCH NEXT ? ROWS ONLY");

        int offset = (Math.max(page, 1) - 1) * pageSize;
        params.add(offset);
        params.add(pageSize);

        List<Candidate> list = new ArrayList<>();
        try (PreparedStatement ps = requireConn().prepareStatement(sb.toString())) {
            for (int i = 0; i < params.size(); i++) {
                Object v = params.get(i);
                if (v instanceof Integer) ps.setInt(i + 1, (Integer) v);
                else ps.setString(i + 1, v.toString());
            }
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) list.add(mapRow(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public Candidate findById(int id) {
        String sql = """
            SELECT CandidateID, CandidateName, Address, Email, PhoneNumber, Nationality, PasswordHash, Avatar
            FROM Candidate
            WHERE CandidateID = ?
        """;
        try (PreparedStatement ps = requireConn().prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapRow(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
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
            return true; // phòng thủ: coi như đã tồn tại khi lỗi
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

    /** BẢN HỢP NHẤT từ nhánh main (đã bỏ conflict markers) */
    public int insert(Candidate cd) {
        String sql = """
            INSERT INTO Candidate (CandidateName, Address, Email, PhoneNumber, Nationality, PasswordHash, Avatar)
            OUTPUT INSERTED.CandidateID
            VALUES (?, ?, ?, ?, ?, ?, ?)
        """;
        try (PreparedStatement ps = requireConn().prepareStatement(sql)) {
            ps.setString(1, cd.getCandidateName());
            ps.setString(2, cd.getAddress());
            ps.setString(3, cd.getEmail());
            ps.setString(4, cd.getPhoneNumber());
            ps.setString(5, cd.getNationality());
            ps.setString(6, cd.getPasswordHash());
            if (cd.getAvatar() == null) ps.setNull(7, Types.NVARCHAR);
            else ps.setString(7, cd.getAvatar());

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public Candidate checkLogin(String email, String passwordHash) {
        String sql = """
            SELECT CandidateID, CandidateName, Address, Email, PhoneNumber, Nationality, PasswordHash, Avatar
            FROM Candidate
            WHERE Email = ? AND PasswordHash = ?
        """;
        try (PreparedStatement ps = requireConn().prepareStatement(sql)) {
            ps.setString(1, email);
            ps.setString(2, passwordHash);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapRow(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean deleteCascade(int candidateId) {
        String delApply = "DELETE FROM Apply WHERE CandidateID = ?";
        String delCand  = "DELETE FROM Candidate WHERE CandidateID = ?";
        try {
            Connection cx = requireConn();
            boolean oldAuto = cx.getAutoCommit();
            cx.setAutoCommit(false);
            try (PreparedStatement ps1 = cx.prepareStatement(delApply);
                 PreparedStatement ps2 = cx.prepareStatement(delCand)) {
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
        return cd;
    }
}
