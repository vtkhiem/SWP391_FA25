/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import model.BlogPost;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;  
import model.BlogPostDetail;
/**
 *
 * @author ADMIN
 */
public class BlogPostDAO extends DBContext{
    
    private BlogPost map(ResultSet rs) throws SQLException {
        BlogPost b = new BlogPost();
        b.setPostID(rs.getInt("PostID"));
        b.setTitle(rs.getNString("Title"));
        b.setUrl(rs.getNString("Url"));
        b.setCategoryName(rs.getNString("CategoryName"));
        b.setAuthorName(rs.getNString("AuthorName"));
        Date d = rs.getDate("PublishedDate");   
        b.setPublishedDate(d != null ? new Date(d.getTime()) : null);
        b.setCoverImageUrl(rs.getNString("CoverImageUrl"));
        b.setExcerpt(rs.getNString("Excerpt"));
        b.setContent(rs.getNString("Content"));
        b.setFeatured(rs.getBoolean("IsFeatured"));
        int fo = rs.getInt("FeaturedOrder");
        b.setFeaturedOrder(rs.wasNull() ? null : fo);
        int rm = rs.getInt("ReadMinutes");
        b.setReadMinutes(rs.wasNull() ? null : rm);
        b.setStatus(rs.getBoolean("Status"));
        b.setViewCount(rs.getInt("ViewCount"));
        return b;
    }

    public List<BlogPost> getFeaturedTop4() throws Exception {
        String sql = """
            SELECT TOP (4) * FROM dbo.BlogPost
            WHERE Status = 1 AND IsFeatured = 1
            ORDER BY FeaturedOrder ASC, PostID DESC
        """;
        try (
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            List<BlogPost> list = new ArrayList<>();
            while (rs.next()) list.add(map(rs));
            return list;
        }
    }

    public int countPublished() throws Exception {
        String sql = "SELECT COUNT(*) FROM dbo.BlogPost WHERE Status = 1";
        try (
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            rs.next();
            return rs.getInt(1);
        }
    }

    public List<BlogPost> getPage(int pageIndex, int pageSize) throws Exception {
        if (pageIndex < 1) pageIndex = 1;
        int offset = (pageIndex - 1) * pageSize;
        String sql = """
            SELECT * FROM dbo.BlogPost
            WHERE Status = 1
            ORDER BY PublishedDate DESC, PostID DESC
            OFFSET ? ROWS FETCH NEXT ? ROWS ONLY
        """;
        try (
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, offset);
            ps.setInt(2, pageSize);
            try (ResultSet rs = ps.executeQuery()) {
                List<BlogPost> list = new ArrayList<>();
                while (rs.next()) list.add(map(rs));
                return list;
            }
        }
    }
    
    public BlogPost getByUrlWithDetail(String url, BlogPostDetail[] detailHolder) throws Exception {
        String sql = """
            SELECT p.*, d.DetailID, d.SeoTitle, d.SeoDescription, d.OGImageUrl, d.ContentHtml, d.Status AS DetailStatus
            FROM dbo.BlogPost p
            LEFT JOIN dbo.BlogPostDetail d ON d.PostID = p.PostID AND d.Status = 1
            WHERE p.Url = ? AND p.Status = 1
        """;
        try (
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setNString(1, url);
            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) return null;

                BlogPost b = map(rs); 
                BlogPostDetail det = null;
                int did = rs.getInt("DetailID");
                if (!rs.wasNull()) {
                    det = new BlogPostDetail();
                    det.setDetailID(did);
                    det.setPostID(b.getPostID());
                    det.setSeoTitle(rs.getNString("SeoTitle"));
                    det.setSeoDescription(rs.getNString("SeoDescription"));
                    det.setOgImageUrl(rs.getNString("OGImageUrl"));
                    det.setContentHtml(rs.getNString("ContentHtml"));
                    det.setStatus(true);
                }
                if (detailHolder != null && detailHolder.length > 0) {
                    detailHolder[0] = det;
                }
                return b;
            }
        }
    }

    public void increaseView(String url) throws Exception {
        String sql = "UPDATE dbo.BlogPost SET ViewCount = ViewCount + 1 WHERE Url = ?";
        try (
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setNString(1, url);
            ps.executeUpdate();
        }
    }
    public boolean existsByUrl(String url) throws Exception {
    String sql = "SELECT 1 FROM dbo.BlogPost WHERE Url = ? AND Status = 1";
    try (
         PreparedStatement ps = c.prepareStatement(sql)) {
        ps.setNString(1, url);
        try (ResultSet rs = ps.executeQuery()) {
            return rs.next();
        }
    }
    }
}
