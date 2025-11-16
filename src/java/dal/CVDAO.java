package dal;

import java.math.BigDecimal;
import model.CV;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.Candidate;

public class CVDAO extends DBContext {

    public boolean createCV(CV cv) {

        if (c == null) {
            System.out.println("ERROR: Database connection is null");
            return false;
        }
        System.out.println("=== CVDAO createCV DEBUG ===");
        System.out.println("CandidateID: " + cv.getCandidateID());

        if (!isCandidateExists(cv.getCandidateID())) {
            System.out.println("ERROR: CandidateID " + cv.getCandidateID() + " không tồn tại trong bảng Candidate");
            return false;
        }
        System.out.println("SUCCESS: CandidateID exists, proceeding with INSERT");

        PreparedStatement ps = null;
        try {
            String sql = "INSERT INTO CV (CandidateId, FullName, Address, Email, Position, NumberExp, Education, "
                    + "Field, CurrentSalary, Birthday, Nationality, Gender, FileData, DayCreate) "
                    + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, GETDATE())";

            System.out.println("Preparing SQL statement...");
            ps = c.prepareStatement(sql);

            System.out.println("Setting parameters...");
            ps.setInt(1, cv.getCandidateID());
            ps.setString(2, cv.getFullName());
            ps.setString(3, cv.getAddress());
            ps.setString(4, cv.getEmail());
            ps.setString(5, cv.getPosition());
            ps.setInt(6, cv.getNumberExp());
            ps.setString(7, cv.getEducation());
            ps.setString(8, cv.getField());
            ps.setBigDecimal(9, cv.getCurrentSalary());
            ps.setDate(10, cv.getBirthday());
            ps.setString(11, cv.getNationality());
            ps.setString(12, cv.getGender());
            ps.setString(13, cv.getFileData()); // Lưu đường dẫn file

            System.out.println("Executing INSERT statement...");
            int result = ps.executeUpdate();
            System.out.println("INSERT result: " + result + " rows affected");

            return result > 0;

        } catch (SQLException e) {
            System.out.println("SQL ERROR in createCV:");
            System.out.println("Error Code: " + e.getErrorCode());
            System.out.println("SQL State: " + e.getSQLState());
            System.out.println("Message: " + e.getMessage());
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean isCandidateExists(int candidateID) {
        System.out.println("Checking if CandidateID " + candidateID + " exists...");
        String sql = "SELECT CandidateID, CandidateName FROM Candidate WHERE CandidateID = ?";
        try (PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, candidateID);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    System.out.println("Found candidate: ID=" + rs.getInt("CandidateID")
                            + ", Name=" + rs.getString("CandidateName"));
                    return true;
                } else {
                    System.out.println("No candidate found with ID: " + candidateID);
                    return false;
                }
            }
        } catch (SQLException e) {
            System.out.println("Error checking candidate existence:");
            e.printStackTrace();
            return false;
        }
    }

    // Method để debug - liệt kê tất cả candidates
    public void debugListAllCandidates() {
        System.out.println("=== ALL CANDIDATES IN DATABASE ===");
        String sql = "SELECT CandidateID, CandidateName, Email FROM Candidate";
        try (PreparedStatement ps = c.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            boolean hasData = false;
            while (rs.next()) {
                hasData = true;
                System.out.println("ID: " + rs.getInt("CandidateID")
                        + ", Name: " + rs.getString("CandidateName")
                        + ", Email: " + rs.getString("Email"));
            }

            if (!hasData) {
                System.out.println("No candidates found in database!");
            }

        } catch (SQLException e) {
            System.out.println("Error listing candidates:");
            e.printStackTrace();
        }
        System.out.println("================================");
    }

    public String getCVFilePath(int cvId) {
        String sql = "SELECT FileData FROM CV WHERE CVID = ?";
        try (PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, cvId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("FileData");
                }
            }
        } catch (SQLException e) {
            System.out.println("Error getting CV file path:");
            e.printStackTrace();
        }
        return null;
    }

    public List<CV> getCVsByCandidate(int candidateId) {
        List<CV> cvList = new ArrayList<>();
        // Thêm điều kiện WHERE để chỉ lấy CV của candidate cụ thể
        String sql = "SELECT * FROM CV WHERE CandidateId = ? ORDER BY DayCreate DESC";

        try {
            if (c == null) {
                System.out.println("Database connection is null!");
                return cvList;
            }

            PreparedStatement ps = c.prepareStatement(sql);
            ps.setInt(1, candidateId);

            System.out.println("Executing query for CandidateId: " + candidateId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                // Chỉ thêm CV nếu candidateId khớp
                if (rs.getInt("CandidateId") == candidateId) {
                    CV cv = new CV();
                    cv.setCVID(rs.getInt("CVID"));
                    cv.setCandidateID(rs.getInt("CandidateId"));
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
                    cvList.add(cv);
                }
            }
            System.out.println("Found " + cvList.size() + " CVs for candidate " + candidateId);

        } catch (SQLException e) {
            System.out.println("Error getting CVs by candidate:");
            e.printStackTrace();
        }
        return cvList;
    }

    public CV getCVById(int cvId) {
        String sql = "SELECT * FROM CV WHERE CVID = ?";
        try {
            PreparedStatement ps = c.prepareStatement(sql);
            ps.setInt(1, cvId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                CV cv = new CV();
                cv.setCVID(rs.getInt("CVID"));
                cv.setCandidateID(rs.getInt("CandidateId"));
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
        } catch (SQLException e) {
            System.out.println("Error getting CV by ID:");
            e.printStackTrace();
        }
        return null;
    }

    public boolean updateCV(CV cv) {
        String sql = "UPDATE CV SET FullName=?, Address=?, Email=?, Position=?, "
                + "NumberExp=?, Education=?, Field=?, CurrentSalary=?, Birthday=?, "
                + "Nationality=?, Gender=?, FileData=? WHERE CVID=?";
        try {
            PreparedStatement ps = c.prepareStatement(sql);
            ps.setString(1, cv.getFullName());
            ps.setString(2, cv.getAddress());
            ps.setString(3, cv.getEmail());
            ps.setString(4, cv.getPosition());
            ps.setInt(5, cv.getNumberExp());
            ps.setString(6, cv.getEducation());
            ps.setString(7, cv.getField());
            ps.setBigDecimal(8, cv.getCurrentSalary());
            ps.setDate(9, cv.getBirthday());
            ps.setString(10, cv.getNationality());
            ps.setString(11, cv.getGender());
            ps.setString(12, cv.getFileData());
            ps.setInt(13, cv.getCVID());

            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.out.println("Error updating CV:");
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteCV(int cvId) {
        String sql = "DELETE FROM CV WHERE CVID=?";
        try {
            PreparedStatement ps = c.prepareStatement(sql);
            ps.setInt(1, cvId);
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.out.println("Error deleting CV:");
            e.printStackTrace();
            return false;
        }
    }

    private void closeResources(ResultSet rs, PreparedStatement ps) {
        try {
            if (rs != null) {
                rs.close();
            }
            if (ps != null) {
                ps.close();
            }
        } catch (SQLException e) {
            System.out.println("Error closing resources:");
            e.printStackTrace();
        }

    }

    public boolean updateFullName(CV cv) {
        String sql = "UPDATE CV SET FullName = ? WHERE CVID = ?";
        try (PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, cv.getFullName());
            ps.setInt(2, cv.getCVID());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean updateBasicInfo(CV cv) {
        String sql = "UPDATE CV SET FullName = ?, Email = ?, Address = ?, Position = ?, Education = ?, Field = ?, Nationality = ?, Gender = ?, NumberExp = ? WHERE CVID = ?";
        try (PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, cv.getFullName());
            ps.setString(2, cv.getEmail());
            ps.setString(3, cv.getAddress());
            ps.setString(4, cv.getPosition());
            ps.setString(5, cv.getEducation());
            ps.setString(6, cv.getField());
            ps.setString(7, cv.getNationality());
            ps.setString(8, cv.getGender());
            ps.setInt(9, cv.getNumberExp());
            ps.setInt(10, cv.getCVID());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean updateFullCV(CV cv) {
        String sql = "UPDATE CV SET FullName = ?, Email = ?, Address = ?, Position = ?, NumberExp = ?, Education = ?, Field = ?, CurrentSalary = ?, Birthday = ?, Nationality = ?, Gender = ?, FileData = ? WHERE CVID = ?";
        try (PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, cv.getFullName());
            ps.setString(2, cv.getEmail());
            ps.setString(3, cv.getAddress());
            ps.setString(4, cv.getPosition());
            ps.setInt(5, cv.getNumberExp());
            ps.setString(6, cv.getEducation());
            ps.setString(7, cv.getField());
            if (cv.getCurrentSalary() != null) {
                ps.setBigDecimal(8, cv.getCurrentSalary());
            } else {
                ps.setNull(8, java.sql.Types.DECIMAL);
            }
            if (cv.getBirthday() != null) {
                ps.setDate(9, cv.getBirthday());
            } else {
                ps.setNull(9, java.sql.Types.DATE);
            }
            ps.setString(10, cv.getNationality());
            ps.setString(11, cv.getGender());
            ps.setString(12, cv.getFileData());
            ps.setInt(13, cv.getCVID());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<CV> getPublicCVs(int offset, int recordsPerPage) {
        List<CV> list = new ArrayList<>();
        String sql = """
            SELECT 
                cv.CVID,
                cv.CandidateID,
                cv.FullName,
                cv.Address,
                cv.Email,
                cv.Position,
                cv.NumberExp,
                cv.Education,
                cv.Field,
                cv.CurrentSalary,
                cv.Birthday,
                cv.Nationality,
                cv.Gender,
                cv.FileData,
                cv.DayCreate,
                c.CandidateName,
                c.PhoneNumber,
                c.Avatar
            FROM CV cv
            JOIN Candidate c ON cv.CandidateID = c.CandidateID
            WHERE c.isPublic = 1 
            ORDER BY cv.DayCreate DESC OFFSET ? ROWS FETCH NEXT ? ROWS ONLY
        """;

        try (
                PreparedStatement ps = c.prepareStatement(sql);) {
            ps.setInt(1, offset);
            ps.setInt(2, recordsPerPage);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
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

                    list.add(cv);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public int countPublicCVs() {
        String sql = """
        SELECT COUNT(*) AS total
        FROM CV cv
        JOIN Candidate c ON cv.CandidateID = c.CandidateID
        WHERE c.isPublic = 1
    """;

        try (PreparedStatement ps = c.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                return rs.getInt("total");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public List<CV> filterPublicCVs(String text, Double offerMin, int offset, int recordsPerPage) {
        List<CV> list = new ArrayList<>();

        StringBuilder sql = new StringBuilder("""
            SELECT 
                cv.CVID,
                cv.CandidateID,
                cv.FullName,
                cv.Address,
                cv.Email,
                cv.Position,
                cv.NumberExp,
                cv.Education,
                cv.Field,
                cv.CurrentSalary,
                cv.Birthday,
                cv.Nationality,
                cv.Gender,
                cv.FileData,
                cv.DayCreate,
                c.CandidateName,
                c.PhoneNumber,
                c.Avatar
            FROM CV cv
            JOIN Candidate c ON cv.CandidateID = c.CandidateID
            WHERE c.isPublic = 1
        """);

        List<Object> params = new ArrayList<>();

        // Filter theo text (field hoặc position)
        if (text != null && !text.isEmpty()) {
            sql.append(" AND (cv.Field LIKE ? OR cv.Position LIKE ?)");
            params.add("%" + text + "%");
            params.add("%" + text + "%");
        }

        // Filter theo lương tối thiểu
        if (offerMin != null && offerMin >= 0) {
            sql.append(" AND cv.CurrentSalary >= ?");
            params.add(offerMin);
        }

        // Phân trang
        sql.append(" ORDER BY cv.DayCreate DESC OFFSET ? ROWS FETCH NEXT ? ROWS ONLY");
        params.add(offset);
        params.add(recordsPerPage);

        try (PreparedStatement ps = c.prepareStatement(sql.toString())) {
            for (int i = 0; i < params.size(); i++) {
                ps.setObject(i + 1, params.get(i));
            }

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
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
                    list.add(cv);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    // Đếm tổng số CV public với filter
    public int countFilteredPublicCV(String text, Double offerMin) {
        int count = 0;

        StringBuilder sql = new StringBuilder("""
            SELECT COUNT(*) AS total
            FROM CV cv
            JOIN Candidate c ON cv.CandidateID = c.CandidateID
            WHERE c.isPublic = 1
        """);

        List<Object> params = new ArrayList<>();

        if (text != null && !text.isEmpty()) {
            sql.append(" AND (cv.Field LIKE ? OR cv.Position LIKE ?)");
            params.add("%" + text + "%");
            params.add("%" + text + "%");
        }

        if (offerMin != null && offerMin >= 0) {
            sql.append(" AND cv.CurrentSalary >= ?");
            params.add(offerMin);
        }

        try (PreparedStatement ps = c.prepareStatement(sql.toString())) {
            for (int i = 0; i < params.size(); i++) {
                ps.setObject(i + 1, params.get(i));
            }

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    count = rs.getInt("total");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return count;
    }

    public boolean hasApplied(int CVID) {
        String sql = "SELECT 1 FROM Apply WHERE CVID = ?";
        try (PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, CVID);

            try (ResultSet rs = ps.executeQuery()) {
                return rs.next(); 
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return false; 
        }
    }

    public static void main(String[] args) {
        CVDAO dao = new CVDAO();
        /*
        CV cv = new CV();
        cv.setCVID(2); // ID CV đã tồn tại trong DB để test
        cv.setFullName("Nguyen Van Test");
        cv.setEmail("test@example.com");
        cv.setAddress("Hanoi, Vietnam");
        cv.setPosition("Java Developer");
        cv.setNumberExp(3);
        cv.setEducation("Bachelor of IT");
        cv.setField("Software Engineering");
        cv.setCurrentSalary(new java.math.BigDecimal("1500.50"));
        cv.setBirthday(java.sql.Date.valueOf("1995-08-15"));
        cv.setNationality("Vietnamese");
        cv.setGender("Male");
        cv.setFileData("uploads/cv_files/test_cv.pdf");

        boolean result = dao.updateFullCV(cv);
        if (result) {
            System.out.println("✅ Update thành công!");
        } else {
            System.out.println("❌ Update thất bại!");
         */

        List<CV> cvs = dao.filterPublicCVs("Design", -1.0, 1, 10); // page = 1
        int total = dao.countFilteredPublicCV("design", -1.0);
        System.out.println(cvs);
        System.out.println(total);

    }
}