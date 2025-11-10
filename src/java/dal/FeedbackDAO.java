package dal;

import java.sql.*;
import java.util.*;
import model.Feedback;

public class FeedbackDAO extends DBContext {

    // ✅ Thêm phản hồi mới
    public boolean addFeedback(Feedback feedback) throws SQLException {
        String sql = """
            INSERT INTO Feedback (EmployerID, CandidateID, Subject, Content, Status, CreatedAt)
            VALUES (?, ?, ?, ?, ?, 'Pending', GETDATE())
        """;
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

            return ps.executeUpdate() > 0;
        }
    }

    // ✅ Admin phản hồi Feedback
    public boolean respondToFeedback(int feedbackID, String adminResponse, String newStatus) throws SQLException {
        String sql = """
            UPDATE Feedback 
            SET AdminResponse = ?, Status = ?, RespondedAt = GETDATE() 
            WHERE FeedbackID = ?
        """;
        try (PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, adminResponse);
            ps.setString(2, newStatus);
            ps.setInt(3, feedbackID);
            return ps.executeUpdate() > 0;
        }
    }

    // ✅ Lấy toàn bộ Feedback
    public List<Feedback> getAllFeedback() throws SQLException {
        List<Feedback> list = new ArrayList<>();
        String sql = "SELECT * FROM Feedback ORDER BY CreatedAt DESC";
        try (PreparedStatement ps = c.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(mapResultSetToFeedback(rs));
            }
        }
        return list;
    }

    // ✅ Lấy Feedback theo ID
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

    // ✅ Lấy Feedback theo EmployerID
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

    // ✅ Lấy Feedback theo CandidateID
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

    // ✅ Xóa Feedback
    public boolean deleteFeedback(int feedbackID) throws SQLException {
        String sql = "DELETE FROM Feedback WHERE FeedbackID = ?";
        try (PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, feedbackID);
            return ps.executeUpdate() > 0;
        }
    }

    // 🔧 Chuyển ResultSet -> Feedback object
    private Feedback mapResultSetToFeedback(ResultSet rs) throws SQLException {
        Feedback f = new Feedback();
        f.setFeedbackID(rs.getInt("FeedbackID"));
        f.setEmployerID(rs.getObject("EmployerID") != null ? rs.getInt("EmployerID") : null);
        f.setCandidateID(rs.getObject("CandidateID") != null ? rs.getInt("CandidateID") : null);
        f.setSubject(rs.getString("Subject"));
        f.setContent(rs.getString("Content"));

        f.setStatus(rs.getString("Status"));
        f.setAdminResponse(rs.getString("AdminResponse"));

        Timestamp created = rs.getTimestamp("CreatedAt");
        if (created != null) {
            f.setCreatedAt(created.toLocalDateTime());
        }

        Timestamp responded = rs.getTimestamp("RespondedAt");
        if (responded != null) {
            f.setRespondedAt(responded.toLocalDateTime());
        }
        f.setServiceID(rs.getObject("ServiceID") != null ? rs.getInt("ServiceID") : null);
        f.setPromotionID(rs.getObject("PromotionID") != null ? rs.getInt("PromotionID") : null);
        return f;
    }
    // ✅ Lấy tất cả Feedback của 1 người (có thể là Employer hoặc Candidate)

    public List<Feedback> getFeedbackByUser(int userID) throws SQLException {
        List<Feedback> list = new ArrayList<>();
        String sql = """
            SELECT * 
            FROM Feedback 
            WHERE EmployerID = ? OR CandidateID = ?
            ORDER BY CreatedAt DESC
        """;
        try (PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, userID);
            ps.setInt(2, userID);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(mapResultSetToFeedback(rs));
                }
            }
        }
        return list;
    }
    // ✅ Lấy tất cả Feedback của 1 người (có thể là Employer hoặc Candidate)

    public List<Feedback> getAllFeedbackFromEmployers() throws SQLException {
        List<Feedback> list = new ArrayList<>();
        String sql = """
            SELECT * 
            FROM Feedback 
            WHERE CandidateID is null
            ORDER BY CreatedAt DESC
        """;
        try (PreparedStatement ps = c.prepareStatement(sql)) {

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(mapResultSetToFeedback(rs));
                }
            }
        }
        return list;
    }

    public List<Feedback> getAllFeedbackFromCandidates() throws SQLException {
        List<Feedback> list = new ArrayList<>();
        String sql = """
            SELECT * 
            FROM Feedback 
            WHERE EmployerID is null 
            ORDER BY CreatedAt DESC
        """;
        try (PreparedStatement ps = c.prepareStatement(sql)) {

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(mapResultSetToFeedback(rs));
                }
            }
        }
        return list;
    }
       public List<Feedback> getAllFromBoth() throws SQLException {
        List<Feedback> list = new ArrayList<>();
        String sql = """
            SELECT * 
            FROM Feedback 
            WHERE EmployerID is not null And CandidateID is not null
            ORDER BY CreatedAt DESC
        """;
        try (PreparedStatement ps = c.prepareStatement(sql)) {

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(mapResultSetToFeedback(rs));
                }
            }
        }
        return list;
    }
    // ✅ Lấy ID người gửi feedback (tức là EmployerID hoặc CandidateID)

    public Integer getSenderId(int feedbackID) throws SQLException {
        String sql = """
        SELECT 
            CASE 
                WHEN EmployerID IS NOT NULL THEN EmployerID
                ELSE CandidateID
            END AS SenderID
        FROM Feedback
        WHERE FeedbackID = ?
    """;

        try (PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, feedbackID);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getObject("SenderID") != null ? rs.getInt("SenderID") : null;
                }
            }
        }
        return null;
    }

    public void insertFeedbackTypes(int feedbackID, List<String> typeFeedbackIDs) throws SQLException {
        String sql = "INSERT INTO FeedbackTypes (FeedbackID, TypeFeedbackID) VALUES (?, ?)";
        try (
                PreparedStatement ps = c.prepareStatement(sql)) {

            for (String id : typeFeedbackIDs) {
                ps.setInt(1, feedbackID);
                ps.setInt(2, Integer.parseInt(id));
                ps.addBatch();
            }
            ps.executeBatch();
        }
    }
    public void insertFeedbackType(int feedbackID, int feedbackType) throws SQLException {
        String sql = "INSERT INTO FeedbackTypes (FeedbackID, TypeFeedbackID) VALUES (?, ?)";
        try (
                PreparedStatement ps = c.prepareStatement(sql)) {

           
                ps.setInt(1, feedbackID);
                ps.setInt(2, feedbackType);
              

              ps.executeUpdate();
        }
    }

    public int insertFeedbackAndReturnId(Feedback feedback) throws SQLException {
        String sql = "INSERT INTO Feedback (EmployerID, Subject, Content, ServiceID, PromotionID, CreatedAt) VALUES (?, ?, ?, ?, ?, GETDATE())";
        try (
                PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, feedback.getEmployerID());
            ps.setString(2, feedback.getSubject());
            ps.setString(3, feedback.getContent());

            if (feedback.getServiceID() != null) {
                ps.setInt(4, feedback.getServiceID());
            } else {
                ps.setNull(4, java.sql.Types.INTEGER);
            }

            if (feedback.getPromotionID() != null) {
                ps.setInt(5, feedback.getPromotionID());
            } else {
                ps.setNull(5, java.sql.Types.INTEGER);
            }

            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1);
            }
        }
        return -1;
    } 
    public int insertFeedbackAndReturnIdCan(Feedback feedback) throws SQLException {
        String sql = "INSERT INTO Feedback (CandidateID, Subject, Content, ServiceID, PromotionID, CreatedAt) VALUES (?, ?, ?, ?, ?, GETDATE())";
        try (
                PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, feedback.getCandidateID());
            ps.setString(2, feedback.getSubject());
            ps.setString(3, feedback.getContent());

            if (feedback.getServiceID() != null) {
                ps.setInt(4, feedback.getServiceID());
            } else {
                ps.setNull(4, java.sql.Types.INTEGER);
            }

            if (feedback.getPromotionID() != null) {
                ps.setInt(5, feedback.getPromotionID());
            } else {
                ps.setNull(5, java.sql.Types.INTEGER);
            }

            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1);
            }
        }
        return -1;
    }
      public int insertFeedbackAndReturnIdGuest(Feedback feedback) throws SQLException {
        String sql = "INSERT INTO Feedback (EmployerID,CandidateID,Subject, Content, ServiceID, PromotionID, CreatedAt) VALUES (?,?, ?, ?, ?, ?, GETDATE())";
        try (
                PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

          
              if (feedback.getEmployerID() != null) {
            ps.setInt(1, feedback.getEmployerID());
        } else {
            ps.setNull(1, Types.INTEGER);
        }
        
        // Xử lý CandidateID
        if (feedback.getCandidateID() != null) {
            ps.setInt(2, feedback.getCandidateID());
        } else {
            ps.setNull(2, Types.INTEGER);
        }
        
        ps.setString(3, feedback.getSubject());
        ps.setString(4, feedback.getContent());
        
        if (feedback.getServiceID() != null) {
            ps.setInt(5, feedback.getServiceID());
        } else {
            ps.setNull(5, Types.INTEGER);
        }
        
        if (feedback.getPromotionID() != null) {
            ps.setInt(6, feedback.getPromotionID());
        } else {
            ps.setNull(6, Types.INTEGER);
        }
        
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1);
            }
        }
        return -1;
    }
    

}
