package dal;

import java.sql.*;
import java.util.*;
import model.JobPost;

public class SavedJobDAO extends DBContext {
    public boolean saveJob(int candidateID, int jobPostID) {
        String sql = "INSERT INTO SavedJob (CandidateID, JobPostID, SavedDate) VALUES (?, ?, GETDATE())";
        try (PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, candidateID);
            ps.setInt(2, jobPostID);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("saveJob error: " + e.getMessage());
        }
        return false;
    }

    public boolean removeSavedJob(int candidateID, int jobPostID) {
        String sql = "DELETE FROM SavedJob WHERE CandidateID=? AND JobPostID=?";
        try (PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, candidateID);
            ps.setInt(2, jobPostID);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("removeSavedJob error: " + e.getMessage());
        }
        return false;
    }

    public List<JobPost> getSavedJobsByCandidate(int candidateID) {
        List<JobPost> list = new ArrayList<>();
        String sql = """
            SELECT jp.* FROM SavedJob s
            JOIN JobPost jp ON s.JobPostID = jp.JobPostID
            WHERE s.CandidateID = ?
            ORDER BY s.SavedDate DESC
        """;
        try (PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, candidateID);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(extractJobPost(rs));
            }
        } catch (SQLException e) {
            System.out.println("getSavedJobs error: " + e.getMessage());
        }
        return list;
    }

    public boolean isJobSaved(int candidateID, int jobPostID) {
        String sql = "SELECT 1 FROM SavedJob WHERE CandidateID=? AND JobPostID=?";
        try (PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, candidateID);
            ps.setInt(2, jobPostID);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            System.out.println("isJobSaved error: " + e.getMessage());
        }
        return false;
    }

    private JobPost extractJobPost(ResultSet rs) throws SQLException {
        JobPost job = new JobPost();
        job.setJobPostID(rs.getInt("JobPostID"));
        job.setEmployerID(rs.getInt("EmployerID"));
        job.setTitle(rs.getString("Title"));
        job.setDescription(rs.getString("Description"));
        job.setCategory(rs.getString("Category"));
        job.setPosition(rs.getString("Position"));
        job.setLocation(rs.getString("Location"));
        job.setOfferMin(rs.getBigDecimal("OfferMin"));
        job.setOfferMax(rs.getBigDecimal("OfferMax"));
        job.setNumberExp(rs.getInt("NumberExp"));
        job.setVisible(rs.getBoolean("Visible"));
        job.setTypeJob(rs.getString("TypeJob"));
        Timestamp dc = rs.getTimestamp("DayCreate");
        if (dc != null) {
            job.setDayCreate(dc.toLocalDateTime());
        }
        Timestamp dd = rs.getTimestamp("DueDate");
        if (dd != null) {
            job.setDueDate(dd.toLocalDateTime());
        }
        return job;
    }
}