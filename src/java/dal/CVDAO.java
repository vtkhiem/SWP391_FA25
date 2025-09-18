package dal;

import model.CV;
import java.sql.*;

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
                    + "Field, CurrentSalary, Birthday, Nationality, Gender, FileData, MimeType, DayCreate) "
                    + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, GETDATE())";

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
            ps.setDouble(9, cv.getCurrentSalary());
            ps.setDate(10, cv.getBirthday());
            ps.setString(11, cv.getNationality());
            ps.setString(12, cv.getGender());
            ps.setBytes(13, cv.getFileData());
            ps.setString(14, cv.getMimeType());

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

    public static void main(String[] args) {
        try {
            CVDAO cvDao = new CVDAO();

            // Debug: List all candidates first
            cvDao.debugListAllCandidates();

            // Create test CV object
            CV testCV = new CV();
            testCV.setCandidateID(2); // Thay đổi ID này dựa trên kết quả debug trên
            testCV.setFullName("Test Name");
            testCV.setAddress("Test Address");
            testCV.setEmail("test@email.com");
            testCV.setPosition("Developer");
            testCV.setNumberExp(2);
            testCV.setEducation("Bachelor");
            testCV.setField("IT");
            testCV.setCurrentSalary(50000.0);
            testCV.setBirthday(new Date(System.currentTimeMillis()));
            testCV.setNationality("Vietnamese");
            testCV.setGender("Male");
            testCV.setFileData(new byte[0]);
            testCV.setMimeType("application/pdf");

            // Test creating CV
            boolean result = cvDao.createCV(testCV);
            System.out.println("CV creation " + (result ? "successful" : "failed"));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
