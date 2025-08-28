package com.example.demo.dto;

import jakarta.validation.constraints.NotBlank;

public class CommentCreateRequest {
    @NotBlank
    private String content;
    private Integer parentCommentId; // 대댓글이면 전달, 아니면 null

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public Integer getParentCommentId() { return parentCommentId; }
    public void setParentCommentId(Integer parentCommentId) { this.parentCommentId = parentCommentId; }
}
