/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.List;
import model.Apply;
import model.CV;
import model.Candidate;
import model.JobPost;
import model.Status;
import model.StatusLog;

/**
 *
 * @author shiro
 */
public class ApplyDAO {

    Connection con = new DBContext().c;
    List<Apply> applies = new ArrayList<>();


    // Delete Apply
    public void deleteApply(int applyId) {
        String sql = "DELETE FROM Apply WHERE ApplyID=?";
        try (PreparedStatement st = con.prepareStatement(sql)) {
            st.setInt(1, applyId);
            st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Get Apply by ID
    public Apply getApplyById(int applyId) {
        String sql = "SELECT * FROM Apply WHERE ApplyID=?";
        try (PreparedStatement st = con.prepareStatement(sql)) {
            st.setInt(1, applyId);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                return mapResultSetToApply(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Get all Applies
    public List<Apply> getAllApplies() {
        List<Apply> applies = new ArrayList<>();
        String sql = "SELECT * FROM Apply";
        try (PreparedStatement st = con.prepareStatement(sql)) {
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                applies.add(mapResultSetToApply(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return applies;
    }

    // Get Applies by Status
    public List<Apply> getAppliesByStatus(int statusId) {
        List<Apply> applies = new ArrayList<>();
        String sql = "SELECT * FROM Apply WHERE StatusID=?";
        try (PreparedStatement st = con.prepareStatement(sql)) {
            st.setInt(1, statusId);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                applies.add(mapResultSetToApply(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return applies;
    }

    // Helper method: map ResultSet to Apply object
    private Apply mapResultSetToApply(ResultSet rs) {
        try {
            int applyId = rs.getInt("ApplyID");
            int jobPostId = rs.getInt("JobPostID");
            int candidateId = rs.getInt("CandidateID");
            int cvId = rs.getInt("CVID");
            Timestamp dayCreate = rs.getTimestamp("DayCreate");
            int statusId = rs.getInt("StatusID");
            return new Apply(applyId, jobPostId, candidateId, cvId, dayCreate, statusId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Lấy job theo ID
    public JobPost getJobPostById(int id) {
        String sql = "SELECT * FROM JobPost WHERE JobPostID = ?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
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
    // Helper methods

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
            job.setDayCreate(ts.toLocalDateTime());
        }
        return job;
    }

    public CV getCVById(int cvId) {
        String sql = "SELECT * FROM CV WHERE CVID = ?";
        try (PreparedStatement st = con.prepareStatement(sql)) {
            st.setInt(1, cvId);
            ResultSet rs = st.executeQuery();
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
                cv.setCurrentSalary(rs.getBigDecimal("CurrentSalary")); // MONEY → BigDecimal
                cv.setBirthday(rs.getDate("Birthday")); // DATE → java.sql.Date
                cv.setNationality(rs.getString("Nationality"));
                cv.setGender(rs.getString("Gender"));
                cv.setFileData(rs.getString("FileData"));
                cv.setMimeType(rs.getString("MimeType"));
                cv.setDayCreate(rs.getTimestamp("DayCreate")); // DATETIME → Timestamp
                return cv;
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
            ResultSet rs = st.executeQuery();
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
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Status getStatusById(int statusId) {
        String sql = "SELECT * FROM ApplyStatus WHERE StatusID = ?";
        try (PreparedStatement st = con.prepareStatement(sql)) {
            st.setInt(1, statusId);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                Status status = new Status();
                status.setStatusId(rs.getInt("StatusID"));
                status.setStatusName(rs.getString("StatusName"));
                return status;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

// Lấy danh sách transition log theo ApplyID
    public List<StatusLog> getTransitionsByApplyId(int applyId) {
        List<StatusLog> transitions = new ArrayList<>();
        String sql = "SELECT TransitionID, ApplyID, EmployerID, FromStatusID, ToStatusID, Note, TransitionDate "
                + "FROM StatusTransition WHERE ApplyID = ? ORDER BY TransitionDate ASC";

        try (PreparedStatement st = con.prepareStatement(sql)) {
            st.setInt(1, applyId);
            ResultSet rs = st.executeQuery();

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
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return transitions;
    }

}
