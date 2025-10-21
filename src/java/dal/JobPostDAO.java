package dal;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.JobPost;

public class JobPostDAO extends DBContext {
    public List<JobPost> getAllJobPosts() {
        List<JobPost> list = new ArrayList<>();
        String sql = "SELECT * FROM JobPost WHERE Visible = 1 ORDER BY DayCreate DESC";

        try (PreparedStatement ps = c.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(extractJobPost(rs));
                System.out.println("Loaded job: " + rs.getString("Title"));
            }
        } catch (SQLException e) {
            System.out.println("SQL error (getAllJobPosts): " + e.getMessage());
        }

        System.out.println("Total jobs: " + list.size());
        return list;
    }

    public List<JobPost> getJobPosts(int offset, int noOfRecords) {
        List<JobPost> list = new ArrayList<>();
        String sql = "SELECT * FROM JobPost WHERE Visible = 1 ORDER BY DayCreate DESC OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";

        try (PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, offset);
            ps.setInt(2, noOfRecords);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(extractJobPost(rs));
                    System.out.println("Loaded job: " + rs.getString("Title"));
                }
            }
        } catch (SQLException e) {
            System.out.println("SQL error (getJobPosts): " + e.getMessage());
        }

        System.out.println("Total jobs: " + list.size());
        return list;
    }
    
    public int countJobs() {
        String sql = "SELECT COUNT(*) FROM JobPost WHERE Visible = 1";

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

    public JobPost getJobPostById(int id) {
        String sql = "SELECT * FROM JobPost WHERE JobPostID = ?";
        try (PreparedStatement ps = c.prepareStatement(sql)) {
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

    public List<JobPost> searchJobs(String category, String location,
            Double minSalary, Double maxSalary, String keyword, int numberExp, String jobType,
            int offset, int noOfRecords) {
        List<JobPost> list = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT * FROM JobPost WHERE Visible = 1");

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
                    list.add(extractJobPost(rs));
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

    public int countJobsSearched(String category, String location,
            Double minSalary, Double maxSalary, String keyword, int numberExp, String jobType) {
        StringBuilder sql = new StringBuilder("SELECT COUNT(*) FROM JobPost WHERE Visible = 1");

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

    public boolean insertJobPost(JobPost job) {
        String sql = "INSERT INTO JobPost (EmployerID, Title, Description, Category, Position, Location, "
                + "OfferMin, OfferMax, NumberExp, Visible, TypeJob, DayCreate, DueDate) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement ps = c.prepareStatement(sql)) {
            setJobPostParams(ps, job);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<JobPost> getJobsByEmployer(int employerId, int offset, int noOfRecords) {
        List<JobPost> list = new ArrayList<>();
        String sql = "SELECT * FROM JobPost WHERE EmployerID = ? ORDER BY DayCreate DESC OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";

        try (PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, employerId);
            ps.setInt(2, offset);
            ps.setInt(3, noOfRecords);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(extractJobPost(rs));
                    System.out.println("Loaded job: " + rs.getString("Title"));
                }
            }
        } catch (SQLException e) {
            System.out.println("SQL error (getJobPosts): " + e.getMessage());
        }
        
        System.out.println("Total jobs: " + list.size());
        return list;
    }
    
    public int countJobsByEmployer(int employerId) {
        String sql = "SELECT COUNT(*) FROM JobPost WHERE EmployerID = ?";

        try (PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, employerId);
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
    
    public int countJobsByEmployerAndDayCreate(int employerId) {
        String sql = "SELECT COUNT(*) FROM JobPost jp JOIN ServiceEmployer se ON jp.EmployerID = se.EmployerID WHERE jp.EmployerID = ? AND jp.DayCreate BETWEEN se.RegisterDate AND se.ExpirationDate";

        try (PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, employerId);
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
    
    public List<JobPost> searchJobsByEmployer(int employerId, String category, String location,
            Double minSalary, Double maxSalary, String keyword, int numberExp, String jobType,
            int offset, int noOfRecords) {
        List<JobPost> list = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT * FROM JobPost WHERE EmployerID = ?");

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
            ps.setInt(idx++, employerId);
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
                    list.add(extractJobPost(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    
    public int countSearchedJobsByEmployer(int employerId, String category, String location,
            Double minSalary, Double maxSalary, String keyword, int numberExp, String jobType) {
        StringBuilder sql = new StringBuilder("SELECT COUNT(*) FROM JobPost WHERE EmployerID = ?");

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
            ps.setInt(idx++, employerId);
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

    public boolean updateJobPost(JobPost job) {
        String sql = "UPDATE JobPost SET EmployerID=?, Title=?, Description=?, Category=?, Position=?, "
                + "Location=?, OfferMin=?, OfferMax=?, NumberExp=?, Visible=?, TypeJob=?, DayCreate=?, DueDate=? "
                + "WHERE JobPostID=?";

        try (PreparedStatement ps = c.prepareStatement(sql)) {
            setJobPostParams(ps, job);
            ps.setInt(14, job.getJobPostID());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean hideJobPost(int id) {
        String sql = "UPDATE JobPost SET Visible = 0 WHERE JobPostID = ?";
        try (PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public boolean visibleJobPost(int id) {
        String sql = "UPDATE JobPost SET Visible = 1 WHERE JobPostID = ?";
        try (PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
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
            ps.setTimestamp(12, Timestamp.valueOf(job.getDayCreate()));
        } else {
            ps.setTimestamp(12, null);
        }
        if (job.getDueDate() != null) {
            ps.setTimestamp(13, Timestamp.valueOf(job.getDueDate()));
        } else {
            ps.setTimestamp(13, null);
        }
    }
}