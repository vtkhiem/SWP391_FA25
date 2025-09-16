package dal;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import model.JobPost;

public class JobPostDAO extends DBContext {
    // Xem tất cả job
    public List<JobPost> getAllJobPosts() {
        List<JobPost> list = new ArrayList<>();
        String sql = "SELECT * FROM JobPost ORDER BY DayCreate DESC";

        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(extractJobPost(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // Xem job theo ID
    public JobPost getJobPostById(int id) {
        String sql = "SELECT * FROM JobPost WHERE JobPostID = ?";
        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return extractJobPost(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Tìm job theo category, location, salary range, keywords
    public List<JobPost> searchJobs(String category, String location,
            Double minSalary, Double maxSalary, String keyword) {
        List<JobPost> list = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT * FROM JobPost WHERE Visible = 1");

        if (category != null && !category.isEmpty()) {
            sql.append(" AND Category LIKE ?");
        }
        if (location != null && !location.isEmpty()) {
            sql.append(" AND Location LIKE ?");
        }
        if (minSalary != null) {
            sql.append(" AND OfferMin >= ?");
        }
        if (maxSalary != null) {
            sql.append(" AND OfferMax <= ?");
        }
        if (keyword != null && !keyword.isEmpty()) {
            sql.append(" AND (Title LIKE ? OR Description LIKE ?)");
        }
        sql.append(" ORDER BY DayCreate DESC");

        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql.toString())) {

            int idx = 1;
            if (category != null && !category.isEmpty()) {
                ps.setString(idx++, "%" + category + "%");
            }
            if (location != null && !location.isEmpty()) {
                ps.setString(idx++, "%" + location + "%");
            }
            if (minSalary != null) {
                ps.setDouble(idx++, minSalary);
            }
            if (maxSalary != null) {
                ps.setDouble(idx++, maxSalary);
            }
            if (keyword != null && !keyword.isEmpty()) {
                ps.setString(idx++, "%" + keyword + "%");
                ps.setString(idx++, "%" + keyword + "%");
            }

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(extractJobPost(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // Thêm job mới
    public boolean insertJobPost(JobPost job) {
        String sql = "INSERT INTO JobPost (EmployerID, Title, Description, Category, Position, Location, "
                + "OfferMin, OfferMax, NumberExp, Visible, TypeJob, DayCreate) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            setJobPostParams(ps, job);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Lấy danh sách job theo employer
    public List<JobPost> getJobsByEmployer(int employerId) {
        List<JobPost> list = new ArrayList<>();
        String sql = "SELECT * FROM JobPost WHERE EmployerID = ? ORDER BY DayCreate DESC";

        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, employerId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(extractJobPost(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // Cập nhật job
    public boolean updateJobPost(JobPost job) {
        String sql = "UPDATE JobPost SET EmployerID=?, Title=?, Description=?, Category=?, Position=?, "
                + "Location=?, OfferMin=?, OfferMax=?, NumberExp=?, Visible=?, TypeJob=?, DayCreate=? "
                + "WHERE JobPostID=?";

        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            setJobPostParams(ps, job);
            ps.setInt(13, job.getJobPostID());
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Xoá job
    public boolean deleteJobPost(int id) {
        String sql = "DELETE FROM JobPost WHERE JobPostID = ?";
        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // ---------------- Helper methods ----------------
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
        Timestamp ts = rs.getTimestamp("DayCreate");
        if (ts != null) {
            job.setDayCreate(ts.toLocalDateTime()); // chuyển sang LocalDateTime
        }
        return job;
    }

    private void setJobPostParams(PreparedStatement ps, JobPost job) throws SQLException {
        ps.setInt(1, job.getEmployerID());
        ps.setString(2, job.getTitle());
        ps.setString(3, job.getDescription());
        ps.setString(4, job.getCategory());
        ps.setString(5, job.getPosition());
        ps.setString(6, job.getLocation());
        ps.setBigDecimal(7, job.getOfferMin());
        ps.setBigDecimal(8, job.getOfferMax());
        ps.setInt(9, job.getNumberExp());
        ps.setBoolean(10, job.isVisible());
        ps.setString(11, job.getTypeJob());
        if (job.getDayCreate() != null) {
            ps.setTimestamp(12, Timestamp.valueOf(job.getDayCreate())); // LocalDateTime -> Timestamp
        } else {
            ps.setTimestamp(12, null);
        }
    }
}