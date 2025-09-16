/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import model.CV;
import java.sql.*;

public class CVDAO extends DBContext {

    public boolean createCV(CV cv) {
        PreparedStatement ps = null;
        try {
            String sql = "INSERT INTO CV (CandidateID, FullName, Address, Email, Position, NumberExp, Education, "
                    + "Field, CurrentSalary, Birthday, Nationality, Gender, FileData, MimeType, DayCreate) "
                    + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, GETDATE())";

            ps = c.prepareStatement(sql);
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

            int result = ps.executeUpdate();
            return result > 0;

        } catch (SQLException e) {
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

    public static void main(String[] args) {
        try {
            // Create test CV object
            CV testCV = new CV();
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
            testCV.setFileData(new byte[0]); // Empty byte array for testing
            testCV.setMimeType("application/pdf");

            // Test creating CV
            CVDAO cvDao = new CVDAO();
            boolean result = cvDao.createCV(testCV);
            System.out.println("CV creation " + (result ? "successful" : "failed"));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
