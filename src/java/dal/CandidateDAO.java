/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import model.Candidate;

/**
 *
 * @author Admin
 */
public class CandidateDAO extends DBContext{
    
    // ✅ 1. Check login
   public Candidate checkLogin(String email, String passwordHash) {
        Candidate candidate = null;
        String sql = "SELECT CandidateID, CandidateName, Address, Email, PhoneNumber, Nationality, PasswordHash, Avatar "
                   + "FROM Candidate WHERE Email = ? AND PasswordHash = ?";
        try (
             PreparedStatement ps = c.prepareStatement(sql)) {
            
            ps.setString(1, email);
            ps.setString(2, passwordHash);
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    candidate = new Candidate();
                    candidate.setCandidateId(rs.getInt("CandidateID"));
                    candidate.setCandidateName(rs.getString("CandidateName"));
                    candidate.setAddress(rs.getString("Address"));
                    candidate.setEmail(rs.getString("Email"));
                    candidate.setPhoneNumber(rs.getString("PhoneNumber"));
                    candidate.setNationality(rs.getString("Nationality"));
                    candidate.setPasswordHash(rs.getString("PasswordHash"));
                    candidate.setAvatar(rs.getString("Avatar"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return candidate;
    }

    // ✅ 2. Get candidate by ID
    public Candidate getCandidateById(int id) {
        Candidate candidate = null;
        String sql = "SELECT CandidateID, CandidateName, Address, Email, PhoneNumber, Nationality, PasswordHash, Avatar "
                   + "FROM Candidate WHERE CandidateID = ?";
        try (
             PreparedStatement ps = c.prepareStatement(sql)) {
            
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    candidate = new Candidate();
                    candidate.setCandidateId(rs.getInt("CandidateID"));
                    candidate.setCandidateName(rs.getString("CandidateName"));
                    candidate.setAddress(rs.getString("Address"));
                    candidate.setEmail(rs.getString("Email"));
                    candidate.setPhoneNumber(rs.getString("PhoneNumber"));
                    candidate.setNationality(rs.getString("Nationality"));
                    candidate.setPasswordHash(rs.getString("PasswordHash"));
                    candidate.setAvatar(rs.getString("Avatar"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return candidate;
    }

    // ✅ 3. Insert candidate (khi user đăng ký)
    public boolean insertCandidate(Candidate candidate) {
        String sql = "INSERT INTO Candidate (CandidateName, Address, Email, PhoneNumber, Nationality, PasswordHash, Avatar) "
                   + "VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (
             PreparedStatement ps = c.prepareStatement(sql)) {
            
            ps.setString(1, candidate.getCandidateName());
            ps.setString(2, candidate.getAddress());
            ps.setString(3, candidate.getEmail());
            ps.setString(4, candidate.getPhoneNumber());
            ps.setString(5, candidate.getNationality());
            ps.setString(6, candidate.getPasswordHash());
            ps.setString(7, candidate.getAvatar());
            
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    
    
    public static void main(String[] args) {
        CandidateDAO dao = new CandidateDAO();

        // 1️⃣ Test insert candidate
        Candidate newC = new Candidate();
        newC.setCandidateName("Nguyen Van A");
        newC.setAddress("Ha Noi");
        newC.setEmail("test@example.com");
        newC.setPhoneNumber("0123456789");
        newC.setNationality("Vietnam");
        newC.setPasswordHash("123456");  // ⚠️ Demo thôi, thực tế nên hash password
        newC.setAvatar("avatar.png");

        boolean inserted = dao.insertCandidate(newC);
        System.out.println("Insert candidate result: " + inserted);

        // 2️⃣ Test login
        Candidate loginC = dao.checkLogin("test@example.com", "123456");
        if (loginC != null) {
            System.out.println("Login success! CandidateID = " + loginC.getCandidateId());
            System.out.println("CandidateName = " + loginC.getCandidateName());
        } else {
            System.out.println("Login failed!");
        }

        // 3️⃣ Test get by ID
        if (loginC != null) {
            Candidate c2 = dao.getCandidateById(loginC.getCandidateId());
            System.out.println("Get Candidate By ID: " + c2.getCandidateName() + " - " + c2.getEmail());
        }
    }
}
