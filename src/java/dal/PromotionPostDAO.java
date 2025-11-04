package dal;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.PromotionPost;

public class PromotionPostDAO extends DBContext {

    public boolean insertPromotionPost(PromotionPost proPost) {
        String sql = "INSERT INTO PromotionPost (ServiceID, Title, [Content], BannerImage, StartDate, EndDate, IsActive, PriorityLevel, CreatedAt, UpdatedAt, CreatedBy)"
                + "VALUE (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, proPost.getServiceID());
            ps.setString(2, proPost.getTitle());
            ps.setString(3, proPost.getContent());
            ps.setString(4, proPost.getBannerImage());
            if (proPost.getStartDate() != null) {
                ps.setTimestamp(5, Timestamp.valueOf(proPost.getStartDate()));
            } else {
                ps.setTimestamp(5, null);
            }
            if (proPost.getEndDate() != null) {
                ps.setTimestamp(6, Timestamp.valueOf(proPost.getEndDate()));
            } else {
                ps.setTimestamp(6, null);
            }
            ps.setBoolean(7, proPost.isIsActive());
            ps.setInt(8, proPost.getPriorityLevel());
            if (proPost.getStartDate() != null) {
                ps.setTimestamp(9, Timestamp.valueOf(proPost.getCreatedAt()));
            } else {
                ps.setTimestamp(9, null);
            }
            if (proPost.getEndDate() != null) {
                ps.setTimestamp(10, Timestamp.valueOf(proPost.getUpdatedAt()));
            } else {
                ps.setTimestamp(10, null);
            }
            ps.setInt(11, proPost.getCreatedBy());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
