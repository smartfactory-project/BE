package com.example.demo.post.mapper;

import com.example.demo.post.model.Comment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.demo.post.dto.CommentResponse;

import java.util.List;

@Mapper
public interface CommentMapper {
    int insertComment(Comment comment); // useGeneratedKeys로 commentId 채움
    List<Comment> selectByPostId(@Param("postId") Integer postId);
    Comment selectOne(@Param("commentId") Integer commentId);

    // 부모 댓글이 같은 게시글에 속하는지 검증용
    Integer selectPostIdByCommentId(@Param("commentId") Integer commentId);


    List<CommentResponse> selectByPostIdWithUser(@Param("postId") Integer postId);
    CommentResponse selectOneWithUser(@Param("commentId") Integer commentId);

}
