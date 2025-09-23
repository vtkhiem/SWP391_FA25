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
import model.Status;
import model.StatusLog;

public class ApplyDAO {

    Connection con = new DBContext().c;

    // CREATE
    public void insertApply(Apply apply) {
        String sql = "INSERT INTO Apply (jobPostId, candidateId, cvId, dayCreate, statusId, rate, note) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement st = con.prepareStatement(sql)) {
            st.setInt(1, apply.getJobPostId());
            st.setInt(2, apply.getCandidateId());
            st.setInt(3, apply.getCvId());
            st.setDate(4, new java.sql.Date(apply.getDayCreate().getTime()));
            st.setInt(5, apply.getStatusId());
            st.setFloat(6, apply.getRate());
            st.setString(7, apply.getNote());
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
                + "statusId=?, rate=?, note=? WHERE applyId=?";
        try (PreparedStatement st = con.prepareStatement(sql)) {
            st.setInt(1, apply.getJobPostId());
            st.setInt(2, apply.getCandidateId());
            st.setInt(3, apply.getCvId());
            st.setDate(4, new java.sql.Date(apply.getDayCreate().getTime()));
            st.setInt(5, apply.getStatusId());
            st.setFloat(6, apply.getRate());
            st.setString(7, apply.getNote());
            st.setInt(8, apply.getApplyId());
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
        apply.setDayCreate(rs.getDate("dayCreate"));
        apply.setStatusId(rs.getInt("statusId"));
        apply.setRate(rs.getFloat("rate"));
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
                    cv.setCvId(rs.getInt("CVID"));
                    cv.setCandidateId(rs.getInt("CandidateID"));
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

    public Status getStatusById(int statusId) {
        String sql = "SELECT * FROM ApplyStatus WHERE StatusID = ?";
        try (PreparedStatement st = con.prepareStatement(sql)) {
            st.setInt(1, statusId);
            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    Status status = new Status();
                    status.setStatusId(rs.getInt("StatusID"));
                    status.setStatusName(rs.getString("StatusName"));
                    return status;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<StatusLog> getTransitionsByApplyId(int applyId) {
        List<StatusLog> transitions = new ArrayList<>();
        String sql = "SELECT TransitionID, ApplyID, EmployerID, FromStatusID, ToStatusID, Note, TransitionDate "
                + "FROM StatusTransition WHERE ApplyID = ? ORDER BY TransitionDate ASC";
        try (PreparedStatement st = con.prepareStatement(sql)) {
            st.setInt(1, applyId);
            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    StatusLog log = new StatusLog();
                    log.setTransitionId(rs.getInt("TransitionID"));
                    log.setApplyId(rs.getInt("ApplyID"));
                    log.setEmployerId(rs.getInt("EmployerID"));
                    log.setFromStatusId(rs.getInt("FromStatusID"));
                    log.setToStatusId(rs.getInt("ToStatusID"));
                    log.setNote(rs.getString("Note"));
                    Timestamp ts = rs.getTimestamp("TransitionDate");
                    if (ts != null) {
                        log.setTransitionDate(ts.toLocalDateTime());
                    }
                    transitions.add(log);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return transitions;
    }
}
