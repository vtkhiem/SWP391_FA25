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
        b.setFeatured(rs.getBoolean("IsFeatured"));
        int fo = rs.getInt("FeaturedOrder");
        b.setFeaturedOrder(rs.wasNull() ? null : fo);
        b.setStatus(rs.getBoolean("Status"));
        b.setViewCount(rs.getInt("ViewCount"));
        return b;
    }

    public List<BlogPost> getFeaturedTop4() throws Exception {
        String sql = """
    SELECT TOP (4)
           PostID, Title, Url, CategoryName, AuthorName, PublishedDate,
           CoverImageUrl, Excerpt, IsFeatured, FeaturedOrder, Status, ViewCount
    FROM dbo.BlogPost
    WHERE Status = 1 AND IsFeatured = 1
    ORDER BY ISNULL(CONVERT(INT, FeaturedOrder), 2147483647),
             PublishedDate DESC, PostID DESC
""";

        try (PreparedStatement ps = c.prepareStatement(sql);
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
            SELECT p.PostID, p.Title, p.Url, p.CategoryName, p.AuthorName, p.PublishedDate,
                   p.CoverImageUrl, p.Excerpt, p.IsFeatured, p.FeaturedOrder,
                   p.Status, p.ViewCount,
                   d.DetailID, d.ContentHtml, d.Status AS DetailStatus
            FROM dbo.BlogPost p
            LEFT JOIN dbo.BlogPostDetail d ON d.PostID = p.PostID AND d.Status = 1
            WHERE p.Url = ? AND p.Status = 1
        """;
        try (PreparedStatement ps = c.prepareStatement(sql)) {
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
                    det.setContentHtml(rs.getNString("ContentHtml"));
                    det.setStatus(rs.getBoolean("DetailStatus"));
                }
                if (detailHolder != null && detailHolder.length > 0) detailHolder[0] = det;
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

    public List<BlogPost> findAll(int offset, int limit) throws Exception {
        String sql = """
            SELECT PostID, Title, Url, CategoryName, AuthorName, PublishedDate, Status
            FROM dbo.BlogPost
            ORDER BY PostID ASC
            OFFSET ? ROWS FETCH NEXT ? ROWS ONLY
        """;
        List<BlogPost> list = new ArrayList<>();
        try (
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, offset);
            ps.setInt(2, limit);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    BlogPost p = new BlogPost();
                    p.setPostID(rs.getInt("PostID"));
                    p.setTitle(rs.getNString("Title"));
                    p.setUrl(rs.getNString("Url"));
                    p.setCategoryName(rs.getNString("CategoryName"));
                    java.sql.Date d = rs.getDate("PublishedDate");
                    p.setPublishedDate(new java.util.Date(d.getTime()));
                    p.setStatus(rs.getBoolean("Status"));
                    list.add(p);
                }
            }
        }
        return list;
    }

    public int countAll() throws Exception {
        String sql = "SELECT COUNT(*) FROM dbo.BlogPost";
        try (
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            rs.next();
            return rs.getInt(1);
        }
    }

    public BlogPost getById(int id) throws Exception {
        String sql = """
            SELECT PostID, Title, Url, CategoryName, AuthorName, PublishedDate,
                   CoverImageUrl, Excerpt, IsFeatured, FeaturedOrder,
                   Status, ViewCount
            FROM dbo.BlogPost
            WHERE PostID = ?
        """;
        try (PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) return null;
                return map(rs);
            }
        }
    }

    public void updateAdmin(int id, String title, String categoryName, boolean status) throws Exception {
        String sql = """
            UPDATE dbo.BlogPost
               SET Title = ?, CategoryName = ?, Status = ?,
                   UpdatedAt = SYSUTCDATETIME()
             WHERE PostID = ?
        """;
        try (PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setNString(1, title);
            ps.setNString(2, categoryName);
            ps.setBoolean(3, status);
            ps.setInt(4, id);
            ps.executeUpdate();
        }
    }

    public BlogPostDetail getDetailByPostId(int postId) throws Exception {
        String sql = """
            SELECT DetailID, PostID, ContentHtml, Status, CreatedAt, UpdatedAt
            FROM dbo.BlogPostDetail WHERE PostID = ?
        """;
        try (PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, postId);
            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) return null;
                BlogPostDetail d = new BlogPostDetail();
                d.setDetailID(rs.getInt("DetailID"));
                d.setPostID(rs.getInt("PostID"));
                d.setContentHtml(rs.getNString("ContentHtml"));
                d.setStatus(rs.getBoolean("Status"));
                return d;
            }
        }
    }

    public void upsertDetail(int postId, String contentHtml, boolean status) throws Exception {
        String sql = """
        IF EXISTS (SELECT 1 FROM dbo.BlogPostDetail WHERE PostID = ?)
            UPDATE dbo.BlogPostDetail
               SET ContentHtml = ?, Status = ?, UpdatedAt = SYSUTCDATETIME()
             WHERE PostID = ?
        ELSE
            INSERT INTO dbo.BlogPostDetail (PostID, ContentHtml, Status)
            VALUES (?, ?, ?)
        """;
        try (PreparedStatement ps = c.prepareStatement(sql)) {
            int i = 1;
            ps.setInt(i++, postId);                
            ps.setNString(i++, contentHtml);       
            ps.setBoolean(i++, status);
            ps.setInt(i++, postId);              
            ps.setInt(i++, postId);               
            ps.setNString(i++, contentHtml);
            ps.setBoolean(i++, status);
            ps.executeUpdate();
        }
    }

    public void updateExcerpt(int postId, String excerpt) throws Exception {
        if (excerpt != null && excerpt.length() > 300) {
            excerpt = excerpt.substring(0, 300);
        }
        String sql = "UPDATE dbo.BlogPost SET Excerpt = ?, UpdatedAt = SYSUTCDATETIME() WHERE PostID = ?";
        try (PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setNString(1, excerpt);
            ps.setInt(2, postId);
            ps.executeUpdate();
        }
    }
    public void updateCoverImage(int postId, String coverUrl) throws Exception {
    String sql = "UPDATE dbo.BlogPost SET CoverImageUrl = ?, UpdatedAt = SYSUTCDATETIME() WHERE PostID = ?";
    try (PreparedStatement ps = c.prepareStatement(sql)) {
        ps.setNString(1, coverUrl);
        ps.setInt(2, postId);
        ps.executeUpdate();
    }
    }
     public int insertPostWithDetail(BlogPost post, String contentHtml) throws Exception {
        // Dùng OUTPUT INSERTED.PostID để lấy id mới
        String insertPostSql =
            "INSERT INTO dbo.BlogPost " +
            "(Title, Url, CategoryName, AuthorName, PublishedDate, CoverImageUrl, Excerpt, IsFeatured, FeaturedOrder, Status, ViewCount) " +
            "OUTPUT INSERTED.PostID " +
            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        String insertDetailSql =
            "INSERT INTO dbo.BlogPostDetail (PostID, ContentHtml, Status) VALUES (?, ?, 1)";

        try (
             PreparedStatement psPost = c.prepareStatement(insertPostSql)) {

            c.setAutoCommit(false);
             psPost.setString(1, post.getTitle());
            psPost.setString(2, post.getUrl());
            psPost.setString(3, post.getCategoryName());
            psPost.setString(4, post.getAuthorName());

            if (post.getPublishedDate() == null) {
                throw new SQLException("PublishedDate không được null.");
            }
            psPost.setDate(5, new java.sql.Date(post.getPublishedDate().getTime()));

            if (post.getCoverImageUrl() == null || post.getCoverImageUrl().isBlank()) {
                psPost.setNull(6, Types.NVARCHAR);
            } else {
                psPost.setString(6, post.getCoverImageUrl());
            }

            if (post.getExcerpt() == null || post.getExcerpt().isBlank()) {
                psPost.setNull(7, Types.NVARCHAR);
            } else {
                psPost.setString(7, post.getExcerpt());
            }

            psPost.setBoolean(8, post.IsFeatured()); 
            if (post.getFeaturedOrder() == null) {
                psPost.setNull(9, Types.TINYINT);
            } else {
                psPost.setInt(9, post.getFeaturedOrder());
            }
            psPost.setBoolean(10, post.isStatus());
            psPost.setInt(11, post.getViewCount());

            int newPostId = -1;
            try (ResultSet rs = psPost.executeQuery()) {
                if (rs.next()) {
                    newPostId = rs.getInt(1);
                }
            }
            if (newPostId <= 0) {
                c.rollback();
                throw new SQLException("Không lấy được PostID sau khi insert BlogPost.");
            }

            try (PreparedStatement psDetail = c.prepareStatement(insertDetailSql)) {
                psDetail.setInt(1, newPostId);
                psDetail.setString(2, contentHtml);
                psDetail.executeUpdate();
            }

            c.commit();
            return newPostId;

        } catch (SQLException ex) {
            throw ex;
        }
    }
     public int deleteById(int postId) throws Exception {
    String sql = "DELETE FROM dbo.BlogPost WHERE PostID = ?";
    try (
         PreparedStatement ps = c.prepareStatement(sql)) {
        ps.setInt(1, postId);
        return ps.executeUpdate();
    }
}
}
