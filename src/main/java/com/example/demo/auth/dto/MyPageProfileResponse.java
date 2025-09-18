package com.example.demo.auth.dto;

import java.time.LocalDateTime;

public class MyPageProfileResponse {
    private Integer userId;
    private String username;
    private String email;
    private LocalDateTime joinDate; // users.created_at
    private Integer postCount;
    private Integer likeCount;   // 내가 누른 좋아요 수
    private Integer scrapCount;  // 내가 스크랩한(북마크) 수

    // getters/setters
    public Integer getUserId() { return userId; }
    public void setUserId(Integer userId) { this.userId = userId; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public LocalDateTime getJoinDate() { return joinDate; }
    public void setJoinDate(LocalDateTime joinDate) { this.joinDate = joinDate; }
    public Integer getPostCount() { return postCount; }
    public void setPostCount(Integer postCount) { this.postCount = postCount; }
    public Integer getLikeCount() { return likeCount; }
    public void setLikeCount(Integer likeCount) { this.likeCount = likeCount; }
    public Integer getScrapCount() { return scrapCount; }
    public void setScrapCount(Integer scrapCount) { this.scrapCount = scrapCount; }
}
