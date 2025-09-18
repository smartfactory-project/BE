package com.example.demo.post.dto;

public class BookmarkStatusResponse {
    private boolean bookmarked; // 현재 사용자가 북마크했는지
    private int count;          // (선택) 총 북마크 수

    public BookmarkStatusResponse() {}
    public BookmarkStatusResponse(boolean bookmarked, int count) {
        this.bookmarked = bookmarked;
        this.count = count;
    }
    public boolean isBookmarked() { return bookmarked; }
    public int getCount() { return count; }
    public void setBookmarked(boolean bookmarked) { this.bookmarked = bookmarked; }
    public void setCount(int count) { this.count = count; }
}
