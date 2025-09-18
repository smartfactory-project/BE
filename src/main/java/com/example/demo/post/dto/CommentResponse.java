package com.example.demo.post.dto;

import java.time.LocalDateTime;

public class CommentResponse {
    private Integer id;
    private Integer postId;
    private Integer userId;
    private Integer parentCommentId;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Boolean isActive;
    private String username;

    public CommentResponse(Integer id, Integer postId, Integer userId, Integer parentCommentId,
                           String content, LocalDateTime createdAt, LocalDateTime updatedAt, Boolean isActive, String username) {
        this.id = id;
        this.postId = postId;
        this.userId = userId;
        this.parentCommentId = parentCommentId;
        this.content = content;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.isActive = isActive;
        this.username = username;
    }

    public Integer getId() { return id; }
    public Integer getPostId() { return postId; }
    public Integer getUserId() { return userId; }
    public Integer getParentCommentId() { return parentCommentId; }
    public String getContent() { return content; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public Boolean getIsActive() { return isActive; }
    public String getUsername() { return username; }

    public void setId(Integer id) { this.id = id; }
    public void setPostId(Integer postId) { this.postId = postId; }
    public void setUserId(Integer userId) { this.userId = userId; }
    public void setParentCommentId(Integer parentCommentId) { this.parentCommentId = parentCommentId; }
    public void setContent(String content) { this.content = content; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
    public void setIsActive(Boolean isActive) { this.isActive = isActive; }
    public void setUsername(String username) { this.username = username; }
}
