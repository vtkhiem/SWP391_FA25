/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.JobPost;
/**
 *
 * @author vuthienkhiem
 */
public class WallDAO extends DBContext {
    public boolean addJobToWall(int employerId, int jobPostId) {
        String sql = """
            MERGE EmployerWall AS target
            USING (SELECT ? AS EmployerID, ? AS JobPostID) AS src
            ON target.EmployerID = src.EmployerID AND target.JobPostID = src.JobPostID
            WHEN MATCHED THEN
                UPDATE SET IsActive = 1, CreatedAt = GETDATE()
            WHEN NOT MATCHED THEN
                INSERT (EmployerID, JobPostID, CreatedAt, IsPinned, IsActive)
                VALUES (src.EmployerID, src.JobPostID, GETDATE(), 0, 1);
        """;

        try (PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, employerId);
            ps.setInt(2, jobPostId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Bật / tắt trạng thái IsActive của job trên wall.
     * @param employerId ID nhà tuyển dụng
     * @param jobPostId ID bài đăng
     * @param isActive true = bật, false = tắt
     */
    public boolean toggleActiveStatus(int employerId, int jobPostId, boolean isActive) {
        String sql = "UPDATE EmployerWall SET IsActive = ? WHERE EmployerID = ? AND JobPostID = ?";

        try (PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setBoolean(1, isActive);
            ps.setInt(2, employerId);
            ps.setInt(3, jobPostId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    
}
  public boolean pinJob(int employerId, int jobPostId) {
        // Kiểm tra xem job có active trên wall không
        String checkSql = """
            SELECT COUNT(*) 
            FROM EmployerWall 
            WHERE EmployerID = ? AND JobPostID = ? AND IsActive = 1
        """;

        String updateSql = """
            UPDATE EmployerWall
            SET IsPinned = 1, PinnedAt = GETDATE()
            WHERE EmployerID = ? AND JobPostID = ? AND IsActive = 1
        """;

        try (
            PreparedStatement check = c.prepareStatement(checkSql);
            PreparedStatement update = c.prepareStatement(updateSql)
        ) {
            check.setInt(1, employerId);
            check.setInt(2, jobPostId);
            ResultSet rs = check.executeQuery();

            if (rs.next() && rs.getInt(1) > 0) {
                // Nếu job đang active thì cho phép pin
                update.setInt(1, employerId);
                update.setInt(2, jobPostId);
                return update.executeUpdate() > 0;
            } else {
                System.out.println("❌ JobPost " + jobPostId + " không active, không thể pin.");
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

 public boolean unpinJob(int employerId, int jobPostId) {
        String sql = """
            UPDATE EmployerWall
            SET IsPinned = 0, PinnedAt = NULL
            WHERE EmployerID = ? AND JobPostID = ?
        """;
        try (PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, employerId);
            ps.setInt(2, jobPostId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
      public List<JobPost> getPinnedJobs(int employerId) {
        List<JobPost> list = new ArrayList<>();
        String sql = """
            SELECT jp.*
            FROM JobPost jp
            JOIN EmployerWall ew ON jp.JobPostID = ew.JobPostID
            WHERE ew.EmployerID = ? AND ew.IsPinned = 1 AND ew.IsActive = 1
            ORDER BY ew.PinnedAt DESC
        """;

        try (PreparedStatement st = c.prepareStatement(sql)) {
            st.setInt(1, employerId);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                JobPost j = new JobPost();
                j.setJobPostID(rs.getInt("JobPostID"));
                j.setTitle(rs.getString("Title"));
                j.setLocation(rs.getString("Location"));
                j.setPosition(rs.getString("Position"));
                j.setOfferMin(rs.getBigDecimal("OfferMin"));
                j.setOfferMax(rs.getBigDecimal("OfferMax"));
                if (rs.getTimestamp("DayCreate") != null)
                    j.setDayCreate(rs.getTimestamp("DayCreate").toLocalDateTime());
                if (rs.getTimestamp("DueDate") != null)
                    j.setDueDate(rs.getTimestamp("DueDate").toLocalDateTime());
                j.setTypeJob(rs.getString("TypeJob"));
                list.add(j);
            }
            System.out.println("✅ Found " + list.size() + " pinned jobs for employer " + employerId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

     public static void main(String[] args) {
        WallDAO dao = new WallDAO();

        int employerId = 1;  // đổi thành EmployerID có thật trong DB của bạn
        int jobPostId = 2;   // đổi thành JobPostID có thật trong DB của bạn

        // Test thêm job vào wall
        boolean added = dao.addJobToWall(employerId, jobPostId);
        System.out.println("Add job to wall result: " + added);

    
    }
    public List<JobPost> getJobsOnWallByEmployer(int employerId) {
    List<JobPost> list = new ArrayList<>();
    String sql = """
    SELECT jp.*, ew.IsActive, ew.IsPinned, ew.CreatedAt, ew.PinnedAt
    FROM JobPost jp
    JOIN EmployerWall ew ON jp.JobPostID = ew.JobPostID
    WHERE ew.EmployerID = ?
                  AND jp.Visible = 1    
    ORDER BY ew.IsPinned DESC, ew.PinnedAt DESC, ew.CreatedAt DESC
""";


    try (PreparedStatement st = c.prepareStatement(sql)) {
        st.setInt(1, employerId);
        ResultSet rs = st.executeQuery();
        while (rs.next()) {
            JobPost j = new JobPost();
            j.setJobPostID(rs.getInt("JobPostID"));
            j.setTitle(rs.getString("Title"));
            j.setLocation(rs.getString("Location"));
            j.setPosition(rs.getString("Position"));
            j.setOfferMin(rs.getBigDecimal("OfferMin"));  // ✅ Sử dụng BigDecimal
            j.setOfferMax(rs.getBigDecimal("OfferMax"));  // ✅ Sử dụng BigDecimal
            if (rs.getTimestamp("DayCreate") != null) {
                j.setDayCreate(rs.getTimestamp("DayCreate").toLocalDateTime());
            }
            if (rs.getTimestamp("DueDate") != null) {
                j.setDueDate(rs.getTimestamp("DueDate").toLocalDateTime());
            }
            j.setTypeJob(rs.getString("TypeJob"));
            j.setActiveOnWall(rs.getBoolean("IsActive"));
j.setPinned(rs.getBoolean("IsPinned"));
            list.add(j);
        }
        
    } catch (Exception e) {
        e.printStackTrace();
    }
    return list;
}
    public boolean isJobOnWall(int employerId, int jobpostId) {
    String sql = "SELECT COUNT(*) FROM EmployerWall WHERE EmployerID = ? AND JobPostID = ?";
    
    try (
         PreparedStatement ps = c.prepareStatement(sql)) {

        ps.setInt(1, employerId);
        ps.setInt(2, jobpostId);

        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                int count = rs.getInt(1);
           
                return count > 0; 
            }
        }
    } catch (Exception e) {
        e.printStackTrace();
        // Xử lý lỗi nếu cần, có thể trả về false hoặc ném-lỗi
    }
    // Mặc định trả về false nếu có lỗi hoặc không tìm thấy
    return false; 
}

}