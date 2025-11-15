package dal;

import java.sql.*;
import java.util.*;
import java.time.LocalDateTime;
import model.SavedJob;
import model.JobPost;

public class SavedJobDAO extends DBContext {
    public boolean saveJob(int candidateID, int jobPostID) {
        String checkSql = "SELECT 1 FROM SavedJob WHERE CandidateID = ? AND JobPostID = ?";
        String insertSql = "INSERT INTO SavedJob (CandidateID, JobPostID, DateCreate) VALUES (?, ?, ?)";
        try (PreparedStatement check = c.prepareStatement(checkSql)) {
            check.setInt(1, candidateID);
            check.setInt(2, jobPostID);
            try (ResultSet rs = check.executeQuery()) {
                if (rs.next()) {
                    return false;
                }
            }
            try (PreparedStatement ps = c.prepareStatement(insertSql)) {
                ps.setInt(1, candidateID);
                ps.setInt(2, jobPostID);
                ps.setTimestamp(3, Timestamp.valueOf(LocalDateTime.now()));
                return ps.executeUpdate() > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
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
            e.printStackTrace();
        }
        return false;
    }

    public List<JobPost> getSavedJobsByCandidate(int candidateID, int offset, int noOfRecords) {
        List<JobPost> list = new ArrayList<>();
        String sql = """
            SELECT
                jp.*,
                e.ImgLogo
            FROM SavedJob s
            JOIN JobPost jp ON s.JobPostID = jp.JobPostID
            JOIN Employer e ON jp.EmployerID = e.EmployerID
            WHERE s.CandidateID = ? AND jp.Visible = 1
            ORDER BY s.DateCreate DESC OFFSET ? ROWS FETCH NEXT ? ROWS ONLY
        """;
        try (PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, candidateID);
            ps.setInt(2, offset);
            ps.setInt(3, noOfRecords);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
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
                    job.setImageUrl(rs.getString("ImgLogo"));
                    Timestamp dc = rs.getTimestamp("DayCreate");
                    if (dc != null) {
                        job.setDayCreate(dc.toLocalDateTime());
                    }
                    Timestamp dd = rs.getTimestamp("DueDate");
                    if (dd != null) {
                        job.setDueDate(dd.toLocalDateTime());
                    }
                    list.add(job);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public int countSavedJobsByCandidate(int candidateID) {
        String sql = """
            SELECT COUNT(*) AS SavedCount FROM SavedJob s
            JOIN JobPost jp ON s.JobPostID = jp.JobPostID
            WHERE s.CandidateID = ? AND jp.Visible = 1
        """;

        try (PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, candidateID);
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

    public SavedJob getSavedJobByID(int candidateID, int jobId) {
        String sql = "SELECT * FROM SavedJob WHERE CandidateID = ? AND JobPostID = ?";
        try (PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, candidateID);
            ps.setInt(2, jobId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    SavedJob savedJob = new SavedJob();
                    savedJob.setCandidateID(rs.getInt("CandidateID"));
                    savedJob.setJobPostID(rs.getInt("JobPostID"));
                    savedJob.setSavedJobID(rs.getInt("SavedJobID"));
                    Timestamp sd = rs.getTimestamp("DateCreate");
                    if (sd != null) {
                        savedJob.setSavedDate(sd.toLocalDateTime());
                    }
                    return savedJob;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<JobPost> searchSavedJobs(int candidateId, String category, String location, Double minSalary, Double maxSalary, String keyword, int numberExp, String jobType, int offset, int noOfRecords) {
        List<JobPost> list = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT jp.*, e.ImgLogo FROM SavedJob s JOIN JobPost jp ON s.JobPostID = jp.JobPostID JOIN Employer e ON jp.EmployerID = e.EmployerID WHERE s.CandidateID = ? AND jp.Visible = 1");

        if (category != null && !category.isEmpty()) {
            sql.append(" AND Category LIKE ?");
        }
        if (location != null && !location.isEmpty()) {
            sql.append(" AND Location LIKE ?");
        }
        if (minSalary != null && minSalary >= 0) {
            sql.append(" AND OfferMin >= ?");
        }
        if (maxSalary != null && maxSalary >= 0) {
            sql.append(" AND OfferMax <= ?");
        }
        if (keyword != null && !keyword.isEmpty()) {
            // Sử dụng COLLATE chính xác và đặt điều kiện cho Title/Description
            sql.append(" AND (Title COLLATE SQL_Latin1_General_Cp1253_CI_AI LIKE ? OR Description COLLATE SQL_Latin1_General_Cp1253_CI_AI LIKE ?)");
        }
        if (numberExp >= 0) {
            sql.append(" AND NumberExp = ?");
        }
        if (jobType != null && !jobType.isEmpty()) {
            sql.append(" AND TypeJob = ?");
        }

        sql.append(" ORDER BY DayCreate DESC OFFSET ? ROWS FETCH NEXT ? ROWS ONLY");

        try (PreparedStatement ps = c.prepareStatement(sql.toString())) {
            int idx = 1;
            ps.setInt(idx++, candidateId);
            if (category != null && !category.isEmpty()) {
                ps.setString(idx++, "%" + category + "%");
            }
            if (location != null && !location.isEmpty()) {
                ps.setString(idx++, "%" + location + "%");
            }
            if (minSalary != null && minSalary >= 0) {
                ps.setDouble(idx++, minSalary);
            }
            if (maxSalary != null && maxSalary >= 0) {
                ps.setDouble(idx++, maxSalary);
            }
            if (keyword != null && !keyword.isEmpty()) {
                String normalized = normalizeKeyword(keyword);
                ps.setString(idx++, "%" + normalized + "%");
                ps.setString(idx++, "%" + normalized + "%");
            }
            if (numberExp >= 0) {
                ps.setInt(idx++, numberExp);
            }
            if (jobType != null && !jobType.isEmpty()) {
                ps.setString(idx++, jobType);
            }
            ps.setInt(idx++, offset);
            ps.setInt(idx++, noOfRecords);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
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
                    job.setImageUrl(rs.getString("ImgLogo"));
                    Timestamp dc = rs.getTimestamp("DayCreate");
                    if (dc != null) {
                        job.setDayCreate(dc.toLocalDateTime());
                    }
                    Timestamp dd = rs.getTimestamp("DueDate");
                    if (dd != null) {
                        job.setDueDate(dd.toLocalDateTime());
                    }
                    list.add(job);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    private String normalizeKeyword(String keyword) {
        if (keyword == null) {
            return "";
        }
        keyword = keyword.trim();
        keyword = java.text.Normalizer.normalize(keyword, java.text.Normalizer.Form.NFD);
        keyword = keyword.replaceAll("\\p{InCombiningDiacriticalMarks}+", ""); // xóa dấu
        keyword = keyword.replaceAll("[^\\p{L}\\p{N}\\s]", ""); // xóa ký tự đặc biệt
        keyword = keyword.replaceAll("\\s+", " ");
        return keyword.toLowerCase();
    }

    public int countSearchedSavedJobs(int candidateId, String category, String location, Double minSalary, Double maxSalary, String keyword, int numberExp, String jobType) {
        StringBuilder sql = new StringBuilder("SELECT COUNT(*) AS SavedCount FROM SavedJob s JOIN JobPost jp ON s.JobPostID = jp.JobPostID WHERE s.CandidateID = ? AND jp.Visible = 1");

        if (category != null && !category.isEmpty()) {
            sql.append(" AND Category LIKE ?");
        }
        if (location != null && !location.isEmpty()) {
            sql.append(" AND Location LIKE ?");
        }
        if (minSalary != null && minSalary >= 0) {
            sql.append(" AND OfferMin >= ?");
        }
        if (maxSalary != null && maxSalary >= 0) {
            sql.append(" AND OfferMax <= ?");
        }
        if (keyword != null && !keyword.isEmpty()) {
            sql.append(" AND (Title COLLATE SQL_Latin1_General_Cp1253_CI_AI LIKE ? OR Description COLLATE SQL_Latin1_General_Cp1253_CI_AI LIKE ?)");
        }
        if (numberExp >= 0) {
            sql.append(" AND NumberExp = ?");
        }
        if (jobType != null && !jobType.isEmpty()) {
            sql.append(" AND TypeJob = ?");
        }

        try (PreparedStatement ps = c.prepareStatement(sql.toString())) {
            int idx = 1;
            ps.setInt(idx++, candidateId);
            if (category != null && !category.isEmpty()) {
                ps.setString(idx++, "%" + category + "%");
            }
            if (location != null && !location.isEmpty()) {
                ps.setString(idx++, "%" + location + "%");
            }
            if (minSalary != null && minSalary >= 0) {
                ps.setDouble(idx++, minSalary);
            }
            if (maxSalary != null && maxSalary >= 0) {
                ps.setDouble(idx++, maxSalary);
            }
            if (keyword != null && !keyword.isEmpty()) {
                String normalized = normalizeKeyword(keyword);
                ps.setString(idx++, "%" + normalized + "%");
                ps.setString(idx++, "%" + normalized + "%");
            }
            if (numberExp >= 0) {
                ps.setInt(idx++, numberExp);
            }
            if (jobType != null && !jobType.isEmpty()) {
                ps.setString(idx++, jobType);
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
}