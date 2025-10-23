/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.util.Date;

/**
 *
 * @author ADMIN
 */
public class BlogPost {
    private int postID;
    private String title;
    private String url;            
    private String categoryName;
    private String authorName;
    private Date publishedDate;
    private String coverImageUrl;
    private String excerpt;
    private boolean isFeatured;
    private Integer featuredOrder;
    private boolean status;        
    private int viewCount;

    public BlogPost() {
    }

    public BlogPost(int postID, String title, String url, String categoryName, String authorName, Date publishedDate, String coverImageUrl, String excerpt, boolean isFeatured, Integer featuredOrder,boolean status, int viewCount) {
        this.postID = postID;
        this.title = title;
        this.url = url;
        this.categoryName = categoryName;
        this.authorName = authorName;
        this.publishedDate = publishedDate;
        this.coverImageUrl = coverImageUrl;
        this.excerpt = excerpt;
        this.isFeatured = isFeatured;
        this.featuredOrder = featuredOrder;
        this.status = status;
        this.viewCount = viewCount;
    }

    public int getPostID() {
        return postID;
    }

    public void setPostID(int postID) {
        this.postID = postID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public Date getPublishedDate() {
        return publishedDate;
    }

    public void setPublishedDate(Date publishedDate) {
        this.publishedDate = publishedDate;
    }

    public String getCoverImageUrl() {
        return coverImageUrl;
    }

    public void setCoverImageUrl(String coverImageUrl) {
        this.coverImageUrl = coverImageUrl;
    }

    public String getExcerpt() {
        return excerpt;
    }

    public void setExcerpt(String excerpt) {
        this.excerpt = excerpt;
    }


    public boolean IsFeatured() {
        return isFeatured;
    }

    public void setFeatured(boolean isFeatured) {
        this.isFeatured = isFeatured;
    }

    public Integer getFeaturedOrder() {
        return featuredOrder;
    }

    public void setFeaturedOrder(Integer featuredOrder) {
        this.featuredOrder = featuredOrder;
    }


    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public int getViewCount() {
        return viewCount;
    }

    public void setViewCount(int viewCount) {
        this.viewCount = viewCount;
    }
    
}
