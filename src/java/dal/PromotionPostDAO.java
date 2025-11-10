package dal;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.PromotionPost;
import model.Service;
import java.time.LocalDateTime;

public class PromotionPostDAO extends DBContext {

    // Thêm mới
    public boolean insertPromotionPost(PromotionPost proPost) {
        String sql = "INSERT INTO PromotionPost (ServiceID, Title, [Content], BannerImage, StartDate, EndDate, IsActive, PriorityLevel, CreatedAt, UpdatedAt, CreatedBy)"
                + " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, proPost.getServiceID());
            ps.setString(2, proPost.getTitle());
            ps.setString(3, proPost.getContent());
            ps.setString(4, proPost.getBannerImage());
            ps.setTimestamp(5, proPost.getStartDate() != null ? Timestamp.valueOf(proPost.getStartDate()) : null);
            ps.setTimestamp(6, proPost.getEndDate() != null ? Timestamp.valueOf(proPost.getEndDate()) : null);
            ps.setBoolean(7, proPost.isIsActive());
            ps.setInt(8, proPost.getPriorityLevel());
            ps.setTimestamp(9, proPost.getCreatedAt() != null ? Timestamp.valueOf(proPost.getCreatedAt()) : null);
            ps.setTimestamp(10, proPost.getUpdatedAt() != null ? Timestamp.valueOf(proPost.getUpdatedAt()) : null);
            ps.setInt(11, proPost.getCreatedBy());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<PromotionPost> getByPage(int page, int pageSize) {
        List<PromotionPost> list = new ArrayList<>();
        // Tính số dòng bỏ qua
        int offset = (page - 1) * pageSize;
        String sql = "SELECT * FROM PromotionPost ORDER BY CreatedAt DESC "
                + "OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";
        try (PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, offset);
            ps.setInt(2, pageSize);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(mapResultSetToPromotion(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    
    public List<PromotionPost> filterByPage(int isActive, int page, int pageSize) {
        List<PromotionPost> list = new ArrayList<>();
        // Tính số dòng bỏ qua
        int offset = (page - 1) * pageSize;
        String sql = "SELECT * FROM PromotionPost WHERE isActive = ? ORDER BY CreatedAt DESC "
                + "OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";
        try (PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, isActive);
            ps.setInt(2, offset);
            ps.setInt(3, pageSize);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(mapResultSetToPromotion(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    
    // Lấy tổng số promotionPost
    public int countPromotionPosts() {
        String sql = "SELECT COUNT(*) AS total FROM PromotionPost";
        try (PreparedStatement ps = c.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return rs.getInt("total");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
    
        // Lấy tổng số promotionPost
    public int countFilteredPosts(int isActive) {
        String sql = "SELECT COUNT(*) WHERE isActive = ? AS total FROM PromotionPost";
        try (PreparedStatement ps = c.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            ps.setInt(1,isActive);
            if (rs.next()) {
                return rs.getInt("total");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    // Lấy theo ID
    public PromotionPost getById(int id) {
        String sql = "SELECT * FROM PromotionPost WHERE PromotionPostID = ?";
        try (PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToPromotion(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Cập nhật
    public boolean updatePromotionPost(PromotionPost proPost) {
        String sql = "UPDATE PromotionPost SET ServiceID=?, Title=?, [Content]=?, BannerImage=?, StartDate=?, EndDate=?, IsActive=?, PriorityLevel=?, UpdatedAt=? WHERE PromotionPostID=?";
        try (PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, proPost.getServiceID());
            ps.setString(2, proPost.getTitle());
            ps.setString(3, proPost.getContent());
            ps.setString(4, proPost.getBannerImage());
            ps.setTimestamp(5, proPost.getStartDate() != null ? Timestamp.valueOf(proPost.getStartDate()) : null);
            ps.setTimestamp(6, proPost.getEndDate() != null ? Timestamp.valueOf(proPost.getEndDate()) : null);
            ps.setBoolean(7, proPost.isIsActive());
            ps.setInt(8, proPost.getPriorityLevel());
            ps.setTimestamp(9, proPost.getUpdatedAt() != null ? Timestamp.valueOf(proPost.getUpdatedAt()) : null);
            ps.setInt(10, proPost.getPromotionPostID());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Xóa
    public boolean deletePromotionPost(int id) {
        String sql = "DELETE FROM PromotionPost WHERE PromotionPostID=?";
        try (PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Map ResultSet sang PromotionPost
    private PromotionPost mapResultSetToPromotion(ResultSet rs) throws SQLException {
        PromotionPost p = new PromotionPost();
        p.setPromotionPostID(rs.getInt("PromotionPostID"));
        p.setServiceID(rs.getInt("ServiceID"));
        p.setTitle(rs.getString("Title"));
        p.setContent(rs.getString("Content"));
        p.setBannerImage(rs.getString("BannerImage"));
        Timestamp start = rs.getTimestamp("StartDate");
        if (start != null) {
            p.setStartDate(start.toLocalDateTime());
        }
        Timestamp end = rs.getTimestamp("EndDate");
        if (end != null) {
            p.setEndDate(end.toLocalDateTime());
        }
        p.setIsActive(rs.getBoolean("IsActive"));
        p.setPriorityLevel(rs.getInt("PriorityLevel"));
        Timestamp created = rs.getTimestamp("CreatedAt");
        if (created != null) {
            p.setCreatedAt(created.toLocalDateTime());
        }
        Timestamp updated = rs.getTimestamp("UpdatedAt");
        if (updated != null) {
            p.setUpdatedAt(updated.toLocalDateTime());
        }
        p.setCreatedBy(rs.getInt("CreatedBy"));
        return p;
    }

    // Lấy Service theo PromotionPostID
    public Service getServiceByPostID(int promotionPostID) {
        Service service = null;
        String sql = "SELECT s.* "
                + "FROM Service s "
                + "JOIN PromotionPost pp ON s.ServiceID = pp.ServiceID "
                + "WHERE pp.PromotionPostID = ?";
        try (PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, promotionPostID);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    service = new Service();
                    service.setServiceID(rs.getInt("ServiceID"));
                    service.setServiceName(rs.getString("ServiceName"));
                    service.setPrice(rs.getBigDecimal("Price"));
                    service.setDescription(rs.getString("Description"));
                    service.setDuration(rs.getInt("Duration"));
                    service.setIsVisible(rs.getBoolean("IsVisible"));
                    service.setJobPostAmount(rs.getInt("JobPostAmount"));
                    service.setIsUnlimited(rs.getBoolean("IsUnlimited"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return service;
    }

    public static void main(String[] args) {
    }
}
