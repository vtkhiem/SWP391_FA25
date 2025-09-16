/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

/**
 *
 * @author ADMIN
 */

import jakarta.servlet.ServletContext;
import model.Candidate;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
public class CandidateDAO extends DBContext {

  
      public int countAll(String keyword) {
        String base = "SELECT COUNT(*) FROM Candidate";
        String where = "";
        if (keyword != null && !keyword.trim().isEmpty()) {
            where = " WHERE CandidateName LIKE ? OR Email LIKE ? OR PhoneNumber LIKE ?";
        }
        String sql = base + where;

        try (
             PreparedStatement ps = c.prepareStatement(sql)) {
            if (!where.isEmpty()) {
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
            params.add(like); params.add(like); params.add(like);
        }
        sb.append(" ORDER BY CandidateID DESC OFFSET ? ROWS FETCH NEXT ? ROWS ONLY");

        int offset = (Math.max(page,1) - 1) * pageSize;
        params.add(offset);
        params.add(pageSize);

        List<Candidate> list = new ArrayList<>();
        try (
             PreparedStatement ps = c.prepareStatement(sb.toString())) {
            for (int i = 0; i < params.size(); i++) {
                Object v = params.get(i);
                if (v instanceof Integer) ps.setInt(i + 1, (Integer) v);
                else ps.setString(i + 1, v.toString());
            }
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Candidate c = new Candidate();
                    c.setCandidateId(rs.getInt("CandidateID"));
                    c.setCandidateName(rs.getString("CandidateName"));
                    c.setAddress(rs.getString("Address"));
                    c.setEmail(rs.getString("Email"));
                    c.setPhoneNumber(rs.getString("PhoneNumber"));
                    c.setNationality(rs.getString("Nationality"));
                    c.setPasswordHash(rs.getString("PasswordHash"));
                    c.setAvatar(rs.getString("Avatar"));
                    list.add(c);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    public Candidate findById(int id) {
    String sql = """
        SELECT CandidateID, CandidateName, Address, Email, PhoneNumber,
               Nationality, PasswordHash, Avatar
        FROM Candidate
        WHERE CandidateID = ?
    """;
    try (PreparedStatement ps = c.prepareStatement(sql)) {
        ps.setInt(1, id);
        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
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
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return null;
}
// Thêm mới 1 ứng viên, trả về ID vừa tạo (0 nếu lỗi)
public int insert(model.Candidate cd) {
    String sql = """
        INSERT INTO Candidate (CandidateName, Address, Email, PhoneNumber, Nationality, PasswordHash, Avatar)
        VALUES (?, ?, ?, ?, ?, ?, ?)
    """;
    try (PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
        ps.setString(1, cd.getCandidateName());
        ps.setString(2, cd.getAddress());
        ps.setString(3, cd.getEmail());
        ps.setString(4, cd.getPhoneNumber());
        ps.setString(5, cd.getNationality());
        // PasswordHash NOT NULL -> gán mặc định (bạn có thể thay bằng hash thực tế)
        ps.setString(6, (cd.getPasswordHash() == null || cd.getPasswordHash().isEmpty()) ? "hashed_default" : cd.getPasswordHash());
        ps.setString(7, cd.getAvatar());
        int affected = ps.executeUpdate();
        if (affected > 0) {
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) return rs.getInt(1);
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return 0;
}

// Xóa ứng viên theo ID (xóa Apply trước để tránh FK), trả về true nếu thành công
public boolean deleteCascade(int candidateId) {
    String delApply = "DELETE FROM Apply WHERE CandidateID = ?";
    String delCand  = "DELETE FROM Candidate WHERE CandidateID = ?";
    try {
        boolean oldAuto = c.getAutoCommit();
        c.setAutoCommit(false);
        try (PreparedStatement ps1 = c.prepareStatement(delApply);
             PreparedStatement ps2 = c.prepareStatement(delCand)) {
            ps1.setInt(1, candidateId);
            ps1.executeUpdate();
            ps2.setInt(1, candidateId);
            int rows = ps2.executeUpdate();
            c.commit();
            c.setAutoCommit(oldAuto);
            return rows > 0;
        } catch (SQLException ex) {
            c.rollback();
            c.setAutoCommit(true);
            throw ex;
        }
    } catch (SQLException e) {
        e.printStackTrace();
        return false;
    }
}
}

