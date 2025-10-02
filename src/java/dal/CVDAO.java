package dal;

import java.math.BigDecimal;
import model.CV;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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

public static void main(String[] args) {
    CVDAO dao = new CVDAO();

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
    }
}
}


    /*
    public static void main(String[] args) {
        try {
            // Tạo đối tượng CV test
            CV testCV = new CV();
            testCV.setCandidateID(1); // Đảm bảo ID này tồn tại trong bảng Candidate
            testCV.setFullName("Test User");
            testCV.setEmail("test@example.com");
            testCV.setAddress("Test Address");
            testCV.setPosition("Software Developer");
            testCV.setNumberExp(3);
            testCV.setEducation("Bachelor Degree");
            testCV.setField("Information Technology");
            testCV.setCurrentSalary(new BigDecimal("50000.00"));
            testCV.setBirthday(Date.valueOf("1990-01-01"));
            testCV.setNationality("Vietnamese");
            testCV.setGender("male");
            testCV.setFileData("uploads/cv_files/test_cv.pdf");

            // Gọi DAO
            CVDAO cvDao = new CVDAO();

            // Thử thêm CV vào database
            boolean success = cvDao.createCV(testCV);

            if (success) {
                System.out.println("CV created successfully!");
                // Kiểm tra CV vừa tạo
                System.out.println("Checking CVs for candidate 1:");
                cvDao.getCVsByCandidate(2).forEach(cv -> {
                    System.out.println("CV ID: " + cv.getCVID());
                    System.out.println("Full Name: " + cv.getFullName());
                    System.out.println("Position: " + cv.getPosition());
                    System.out.println("Email: " + cv.getEmail());
                    System.out.println("--------------------");
                });
            } else {
                System.out.println("Failed to create CV!");
            }

        } catch (Exception e) {
            System.out.println("Error creating CV:");
            e.printStackTrace();
        }
    }*/
    

   