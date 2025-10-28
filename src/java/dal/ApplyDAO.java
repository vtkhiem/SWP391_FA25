package dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import model.Apply;
import model.CV;
import model.Candidate;
import model.JobPost;

public class ApplyDAO {

    Connection con = new DBContext().c;

    // CREATE
    public void insertApply(int jobId, int candidateId, int CVID, LocalDateTime dayCreate, String status, String note) {
        String sql = "INSERT INTO Apply (jobPostId, candidateID, CVID, dayCreate, status, note) "
                + "VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement st = con.prepareStatement(sql)) {
            st.setInt(1, jobId);
            st.setInt(2, candidateId);
            st.setInt(3, CVID);
            st.setTimestamp(4, Timestamp.valueOf(dayCreate)); // LocalDateTime
            st.setString(5, status);
            st.setString(6, note);
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

    public void updateApplyStatus(int applyId, String newStatus) {
        String sql = "UPDATE Apply SET Status = ? WHERE ApplyID = ?";
        try (PreparedStatement st = con.prepareStatement(sql)) {
            st.setString(1, newStatus);
            st.setInt(2, applyId);
            st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateApplyNote(int applyId, String newNote) {
        String sql = "UPDATE Apply SET Note = ? WHERE ApplyID = ?";
        try (PreparedStatement st = con.prepareStatement(sql)) {
            st.setString(1, newNote);
            st.setInt(2, applyId);
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

    public CV getCVByApplyId(int applyId) {
        String sql = "SELECT c.* "
                + "FROM CV c "
                + "INNER JOIN Apply a ON a.CVID = c.CVID "
                + "WHERE a.ApplyID = ?";

        try (PreparedStatement st = con.prepareStatement(sql)) {
            st.setInt(1, applyId);
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

    public List<Apply> getApplyByJobPost(int jobPostId, int employerId, int offSet, int recordsPerPage) {
        List<Apply> list = new ArrayList<>();
        String sql = "SELECT \n"
                + "    a.ApplyID, \n"
                + "    a.JobPostID, \n"
                + "    a.CandidateID, \n"
                + "    a.CVID, \n"
                + "    a.DayCreate, \n"
                + "    a.Status, \n"
                + "    a.Note\n"
                + "FROM Apply AS a\n"
                + "JOIN JobPost AS j \n"
                + "    ON a.JobPostID = j.JobPostID\n"
                + "WHERE j.EmployerID = ? \n"
                + "  AND j.JobPostID = ? "
                + "  ORDER BY a.DayCreate OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";
        try (PreparedStatement st = con.prepareStatement(sql)) {
            st.setInt(1, employerId);
            st.setInt(2, jobPostId);
            st.setInt(3, offSet);
            st.setInt(4, recordsPerPage);
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

    public List<Apply> getApplyByCandidate(int candidateId, int offSet, int recordsPerPage) {
        List<Apply> list = new ArrayList<>();
        String sql = "SELECT *\n"
                + "FROM Apply \n"
                + "WHERE CandidateID = ? "
                + "ORDER BY DayCreate OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";
        try (PreparedStatement st = con.prepareStatement(sql)) {
            st.setInt(1, candidateId);
            st.setInt(2, offSet);
            st.setInt(3, recordsPerPage);
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


    public boolean checkHasApply(int jobId) {
        String sql = "SELECT COUNT(*) FROM Apply WHERE JobPostID =?";
        try (PreparedStatement st = con.prepareStatement(sql)) {
            st.setInt(1, jobId);
            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    int count = rs.getInt(1);
                    return count > 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public int countApply(int jobId) {
        String sql = "SELECT COUNT (*) FROM Apply WHERE JobPostID = ?";
        try (PreparedStatement st = con.prepareStatement(sql)) {
            st.setInt(1, jobId);
            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int countCandidateApply(int candidateId) {
        String sql = "SELECT COUNT (*) FROM Apply WHERE CandidateID = ?";
        try (PreparedStatement st = con.prepareStatement(sql)) {
            st.setInt(1, candidateId);
            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public List<Apply> filterApply(int jobPostId, int employerId, String txt, String status, String exp, int offSet, int recordsPerPage) {
        List<Apply> list = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT "
                + "a.ApplyID, a.JobPostID, a.CandidateID, a.CVID, a.DayCreate, a.Status, a.Note "
                + "FROM Apply AS a "
                + "JOIN JobPost AS j ON a.JobPostID = j.JobPostID "
                + "JOIN Candidate AS can ON a.CandidateID = can.CandidateID "
                + "JOIN CV AS cv ON a.CVID = cv.CVID "
                + "WHERE j.EmployerID = ? AND j.JobPostID = ? ");

        if (txt != null && !txt.isEmpty()) {
            sql.append(" AND (cv.Email LIKE ? OR cv.FullName LIKE ?)");
        }
        if (status != null && !status.isEmpty()) {
            sql.append(" AND a.Status = ?");
        }
        if (exp != null && !exp.isEmpty()) {
            switch (exp) {
                case "0-1" ->
                    sql.append(" AND cv.NumberExp BETWEEN 0 AND 1");
                case "2-3" ->
                    sql.append(" AND cv.NumberExp BETWEEN 2 AND 3");
                case "4-5" ->
                    sql.append(" AND cv.NumberExp BETWEEN 4 AND 5");
                case "5+" ->
                    sql.append(" AND cv.NumberExp > 5");
            }
        }

        sql.append(" ORDER BY DayCreate DESC OFFSET ? ROWS FETCH NEXT ? ROWS ONLY;");
        try (PreparedStatement st = con.prepareStatement(sql.toString())) {
            int i = 1;
            st.setInt(i++, employerId);
            st.setInt(i++, jobPostId);

            if (txt != null && !txt.isEmpty()) {
                st.setString(i++, "%" + txt + "%");
                st.setString(i++, "%" + txt + "%");
            }
            if (status != null && !status.isEmpty()) {
                st.setString(i++, status);
            }

            st.setInt(i++, offSet);
            st.setInt(i++, recordsPerPage);

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

    public int countFilteredApply(int jobPostId, int employerId, String txt, String status, String exp) {
        StringBuilder sql = new StringBuilder("SELECT COUNT(*) AS Total "
                + "FROM Apply AS a "
                + "JOIN JobPost AS j ON a.JobPostID = j.JobPostID "
                + "JOIN Candidate AS can ON a.CandidateID = can.CandidateID "
                + "JOIN CV AS cv ON a.CVID = cv.CVID "
                + "WHERE j.EmployerID = ? AND j.JobPostID = ? ");

        // Filter theo text
        if (txt != null && !txt.isEmpty()) {
            sql.append(" AND (cv.Email LIKE ? OR cv.FullName LIKE ?)");
        }
        // Filter theo status
        if (status != null && !status.isEmpty()) {
            sql.append(" AND a.Status = ?");
        }
        // Filter theo kinh nghiệm
        if (exp != null && !exp.isEmpty()) {
            switch (exp) {
                case "0-1" ->
                    sql.append(" AND cv.NumberExp BETWEEN 0 AND 1");
                case "2-3" ->
                    sql.append(" AND cv.NumberExp BETWEEN 2 AND 3");
                case "4-5" ->
                    sql.append(" AND cv.NumberExp BETWEEN 4 AND 5");
                case "5+" ->
                    sql.append(" AND cv.NumberExp > 5");
            }
        }

        try (PreparedStatement st = con.prepareStatement(sql.toString())) {
            int i = 1;
            st.setInt(i++, employerId);
            st.setInt(i++, jobPostId);

            if (txt != null && !txt.isEmpty()) {
                st.setString(i++, "%" + txt + "%");
                st.setString(i++, "%" + txt + "%");
            }
            if (status != null && !status.isEmpty()) {
                st.setString(i++, status);
            }

            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("Total");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;
    }
    
    public int countFilteredApply(int candidateId, String txt, String status, String exp) {
        StringBuilder sql = new StringBuilder("SELECT COUNT(*) AS Total "
                + "FROM Apply AS a "
                + "JOIN JobPost AS j ON a.JobPostID = j.JobPostID "
                + "JOIN Candidate AS can ON a.CandidateID = can.CandidateID "
                + "JOIN CV AS cv ON a.CVID = cv.CVID "
                + "WHERE j.EmployerID = ? AND j.JobPostID = ? ");

        // Filter theo text
        if (txt != null && !txt.isEmpty()) {
            sql.append(" AND (cv.Email LIKE ? OR cv.FullName LIKE ?)");
        }
        // Filter theo status
        if (status != null && !status.isEmpty()) {
            sql.append(" AND a.Status = ?");
        }
        // Filter theo kinh nghiệm
        if (exp != null && !exp.isEmpty()) {
            switch (exp) {
                case "0-1" ->
                    sql.append(" AND cv.NumberExp BETWEEN 0 AND 1");
                case "2-3" ->
                    sql.append(" AND cv.NumberExp BETWEEN 2 AND 3");
                case "4-5" ->
                    sql.append(" AND cv.NumberExp BETWEEN 4 AND 5");
                case "5+" ->
                    sql.append(" AND cv.NumberExp > 5");
            }
        }

        try (PreparedStatement st = con.prepareStatement(sql.toString())) {
            int i = 1;


            if (txt != null && !txt.isEmpty()) {
                st.setString(i++, "%" + txt + "%");
                st.setString(i++, "%" + txt + "%");
            }
            if (status != null && !status.isEmpty()) {
                st.setString(i++, status);
            }

            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("Total");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;
    }

    public boolean isApplicable(int jobId, int candidateId) {
        String sql = "SELECT 1 FROM Apply WHERE JobPostID = ? AND CandidateID = ?";
        try (PreparedStatement st = con.prepareStatement(sql)) {
            st.setInt(1, jobId);
            st.setInt(2, candidateId);
            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    return false;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    public static void main(String[] args) {
        System.out.println(new ApplyDAO().getApplyByCandidate(28,0,10));
    }
}
