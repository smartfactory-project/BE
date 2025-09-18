package com.example.demo.post.dto;

public class LikeStatusResponse {
    private int likes;      // 총 좋아요 수
    private boolean liked;  // 현재 사용자(로그인)가 좋아요 눌렀는지

    public LikeStatusResponse() {}

    public LikeStatusResponse(int likes, boolean liked) {
        this.likes = likes;
        this.liked = liked;
    }

    public int getLikes() { return likes; }
    public boolean isLiked() { return liked; }

    public void setLikes(int likes) { this.likes = likes; }
    public void setLiked(boolean liked) { this.liked = liked; }
}
