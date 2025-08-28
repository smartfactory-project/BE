package com.example.demo.dto;

import java.time.LocalDateTime;
import java.util.List;

public class PostWithStatsResponse {
    private Integer id;
    private Integer userId;
    private String title;
    private String content;
    private String category;
    private Integer viewCount;
    private Boolean isActive;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private List<String> tags; // 서비스에서 채움(기존 로직 재사용)
    private String author;
    // ✅ 추가 집계/상태
    private Integer likes;       // 총 좋아요 수
    private Integer comments;    // 총 댓글 수
    private Boolean liked;       // 현재 사용자가 좋아요 눌렀는지
    private Boolean bookmarked;  // 현재 사용자가 북마크했는지

    // getters/setters (생략 가능)
    // ... IDE로 자동 생성 추천
    // 아래는 필수 몇 개만 예시:
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public Integer getUserId() { return userId; }
    public void setUserId(Integer userId) { this.userId = userId; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
    public Integer getViewCount() { return viewCount; }
    public void setViewCount(Integer viewCount) { this.viewCount = viewCount; }
    public Boolean getIsActive() { return isActive; }
    public void setIsActive(Boolean isActive) { this.isActive = isActive; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
    public List<String> getTags() { return tags; }
    public void setTags(List<String> tags) { this.tags = tags; }
    public Integer getLikes() { return likes; }
    public void setLikes(Integer likes) { this.likes = likes; }
    public Integer getComments() { return comments; }
    public void setComments(Integer comments) { this.comments = comments; }
    public Boolean getLiked() { return liked; }
    public void setLiked(Boolean liked) { this.liked = liked; }
    public Boolean getBookmarked() { return bookmarked; }
    public void setBookmarked(Boolean bookmarked) { this.bookmarked = bookmarked; }
    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }
}
