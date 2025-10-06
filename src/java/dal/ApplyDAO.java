package dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Apply;
import model.CV;
import model.Candidate;
import model.JobPost;

public class ApplyDAO {

    Connection con = new DBContext().c;

    // CREATE
    public void insertApply(Apply apply) {
        String sql = "INSERT INTO Apply (jobPostId, candidateID, CVID, dayCreate, status, note) "
                + "VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement st = con.prepareStatement(sql)) {
            st.setInt(1, apply.getJobPostId());
            st.setInt(2, apply.getCandidateId());
            st.setInt(3, apply.getCvId());
            st.setTimestamp(4, Timestamp.valueOf(apply.getDayCreate())); // LocalDateTime
            st.setString(5, apply.getStatus());
            st.setString(6, apply.getNote());
            st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // READ - get by id
    public Apply getApplyById(int applyId) {
        String sql = "SELECT * FROM Apply WHERE applyId = ?";
        try (PreparedStatement st = con.prepareStatement(sql)) {
            st.setInt(1, applyId);
            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    return mapResultSet(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // READ - get all
    public List<Apply> getAllApplies() {
        List<Apply> list = new ArrayList<>();
        String sql = "SELECT * FROM Apply";
        try (PreparedStatement st = con.prepareStatement(sql); ResultSet rs = st.executeQuery()) {
            while (rs.next()) {
                list.add(mapResultSet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // UPDATE
    public void updateApply(Apply apply) {
        String sql = "UPDATE Apply SET jobPostId=?, candidateId=?, cvId=?, dayCreate=?, "
                + "status=?, note=? WHERE applyId=?";
        try (PreparedStatement st = con.prepareStatement(sql)) {
            st.setInt(1, apply.getJobPostId());
            st.setInt(2, apply.getCandidateId());
            st.setInt(3, apply.getCvId());
            st.setTimestamp(4, Timestamp.valueOf(apply.getDayCreate())); // LocalDateTime
            st.setString(5, apply.getStatus());
            st.setString(6, apply.getNote());
            st.setInt(7, apply.getApplyId());
            st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // DELETE
    public void deleteApply(int applyId) {
        String sql = "DELETE FROM Apply WHERE applyId = ?";
        try (PreparedStatement st = con.prepareStatement(sql)) {
            st.setInt(1, applyId);
            st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Helper: mapping ResultSet -> Apply object
    private Apply mapResultSet(ResultSet rs) throws SQLException {
        Apply apply = new Apply();
        apply.setApplyId(rs.getInt("applyId"));
        apply.setJobPostId(rs.getInt("jobPostId"));
        apply.setCandidateId(rs.getInt("candidateId"));
        apply.setCvId(rs.getInt("cvId"));
        Timestamp ts = rs.getTimestamp("dayCreate");
        if (ts != null) {
            apply.setDayCreate(ts.toLocalDateTime());
        }
        apply.setStatus(rs.getString("status"));   // String
        apply.setNote(rs.getString("note"));
        return apply;
    }

    // Lấy job theo ID
    public JobPost getJobPostById(int id) {
        String sql = "SELECT * FROM JobPost WHERE JobPostID = ?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
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
                        job.setDayCreate(ts.toLocalDateTime());
                    }
                    return job;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public CV getCVById(int cvId) {
        String sql = "SELECT * FROM CV WHERE CVID = ?";
        try (PreparedStatement st = con.prepareStatement(sql)) {
            st.setInt(1, cvId);
            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    CV cv = new CV();
                    cv.setCVID(rs.getInt("CVID"));
                    cv.setCandidateID(rs.getInt("CandidateID"));
                    cv.setFullName(rs.getString("FullName"));
                    cv.setAddress(rs.getString("Address"));
                    cv.setEmail(rs.getString("Email"));
                    cv.setPosition(rs.getString("Position"));
                    cv.setNumberExp(rs.getInt("NumberExp"));
                    cv.setEducation(rs.getString("Education"));
                    cv.setField(rs.getString("Field"));
                    cv.setCurrentSalary(rs.getBigDecimal("CurrentSalary"));
                    cv.setBirthday(rs.getDate("Birthday"));
                    cv.setNationality(rs.getString("Nationality"));
                    cv.setGender(rs.getString("Gender"));
                    cv.setFileData(rs.getString("FileData"));
                    cv.setDayCreate(rs.getDate("DayCreate"));
                    return cv;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Candidate getCandidateById(int candidateId) {
        String sql = "SELECT * FROM Candidate WHERE CandidateID = ?";
        try (PreparedStatement st = con.prepareStatement(sql)) {
            st.setInt(1, candidateId);
            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    Candidate c = new Candidate();
                    c.setCandidateId(rs.getInt("CandidateID"));
                    c.setCandidateName(rs.getString("CandidateName"));
                    c.setAddress(rs.getString("Address"));
                    c.setEmail(rs.getString("Email"));
                    c.setPhoneNumber(rs.getString("PhoneNumber"));
                    c.setNationality(rs.getString("Nationality"));
                    c.setPasswordHash(rs.getString("PasswordHash"));
                    c.setAvatar(rs.getString("Avatar"));
                    return c;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Apply> searchApplyByCandidateName(String keyword) {
        List<Apply> list = new ArrayList<>();
        String sql = "SELECT a.* "
                + "FROM Apply a "
                + "JOIN Candidate c ON a.CandidateID = c.CandidateID "
                + "WHERE c.CandidateName LIKE ?";
        try (PreparedStatement st = con.prepareStatement(sql)) {
            st.setString(1, "%" + keyword + "%");
            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    list.add(mapResultSet(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    // Lấy tất cả apply theo jobPostId

    public List<Apply> getApplyByJobPost(int jobPostId) {
        List<Apply> list = new ArrayList<>();
        String sql = "SELECT * FROM Apply WHERE jobPostId = ?";
        try (PreparedStatement st = con.prepareStatement(sql)) {
            st.setInt(1, jobPostId);
            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    list.add(mapResultSet(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public static void main(String[] args) {
        System.out.println(new ApplyDAO().getAllApplies());
    }
}
