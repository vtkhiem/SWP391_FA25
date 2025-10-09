/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.Feedback;
/**
 *
 * @author Admin
 */
public class FeedbackDAO extends DBContext{
    public boolean addFeedback(Feedback feedback) throws SQLException {
        String sql = "INSERT INTO Feedback (EmployerID, CandidateID, Subject, Content, Type, Status, CreatedAt) VALUES (?, ?, ?, ?, ?, 'Pending', GETDATE())";
        try (PreparedStatement ps = c.prepareStatement(sql)) {
            if (feedback.getEmployerID() != null) {
                ps.setInt(1, feedback.getEmployerID());
            } else {
                ps.setNull(1, Types.INTEGER);
            }

            if (feedback.getCandidateID() != null) {
                ps.setInt(2, feedback.getCandidateID());
            } else {
                ps.setNull(2, Types.INTEGER);
            }

            ps.setString(3, feedback.getSubject());
            ps.setString(4, feedback.getContent());
            ps.setString(5, feedback.getType());

            return ps.executeUpdate() > 0;
        }
    }

    // 🟠 Admin phản hồi feedback
    public boolean respondToFeedback(int feedbackID, String adminResponse, String newStatus) throws SQLException {
        String sql = "UPDATE Feedback SET AdminResponse = ?, Status = ?, RespondedAt = GETDATE() WHERE FeedbackID = ?";
        try (PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, adminResponse);
            ps.setString(2, newStatus);
            ps.setInt(3, feedbackID);
            return ps.executeUpdate() > 0;
        }
    }

    // 🔵 Lấy tất cả feedback
    public List<Feedback> getAllFeedback() throws SQLException {
        List<Feedback> list = new ArrayList<>();
        String sql = "SELECT * FROM Feedback ORDER BY CreatedAt DESC";
        try (PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(mapResultSetToFeedback(rs));
            }
        }
        return list;
    }

    // 🟣 Lấy feedback theo ID
    public Feedback getFeedbackById(int feedbackID) throws SQLException {
        String sql = "SELECT * FROM Feedback WHERE FeedbackID = ?";
        try (PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, feedbackID);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToFeedback(rs);
                }
            }
        }
        return null;
    }

    // 🟤 Lấy feedback theo EmployerID
    public List<Feedback> getFeedbackByEmployer(int employerID) throws SQLException {
        List<Feedback> list = new ArrayList<>();
        String sql = "SELECT * FROM Feedback WHERE EmployerID = ? ORDER BY CreatedAt DESC";
        try (PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, employerID);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(mapResultSetToFeedback(rs));
                }
            }
        }
        return list;
    }

    // 🟠 Lấy feedback theo CandidateID
    public List<Feedback> getFeedbackByCandidate(int candidateID) throws SQLException {
        List<Feedback> list = new ArrayList<>();
        String sql = "SELECT * FROM Feedback WHERE CandidateID = ? ORDER BY CreatedAt DESC";
        try (PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, candidateID);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(mapResultSetToFeedback(rs));
                }
            }
        }
        return list;
    }

    // 🔴 Xóa feedback
    public boolean deleteFeedback(int feedbackID) throws SQLException {
        String sql = "DELETE FROM Feedback WHERE FeedbackID = ?";
        try (PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, feedbackID);
            return ps.executeUpdate() > 0;
        }
    }

    // 🧩 Hàm map ResultSet → Feedback object
    private Feedback mapResultSetToFeedback(ResultSet rs) throws SQLException {
        return new Feedback(
                rs.getInt("FeedbackID"),
                rs.getObject("EmployerID") != null ? rs.getInt("EmployerID") : null,
                rs.getObject("CandidateID") != null ? rs.getInt("CandidateID") : null,
                rs.getString("Subject"),
                rs.getString("Content"),
                rs.getString("Type"),
                rs.getTimestamp("CreatedAt") != null ? rs.getTimestamp("CreatedAt").toLocalDateTime() : null,
                rs.getString("Status"),
                rs.getString("AdminResponse"),
                rs.getTimestamp("RespondedAt") != null ? rs.getTimestamp("RespondedAt").toLocalDateTime() : null
        );
    }

}
