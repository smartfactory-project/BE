package com.example.demo.post.service;

import com.example.demo.post.dto.CommentCreateRequest;
import com.example.demo.post.dto.CommentResponse;
import com.example.demo.post.mapper.CommentMapper;
import com.example.demo.auth.mapper.UserMapper;
import com.example.demo.post.model.Comment;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CommentService {

    private final CommentMapper commentMapper;
    private final UserMapper userMapper;

    public CommentService(CommentMapper commentMapper, UserMapper userMapper) {
        this.commentMapper = commentMapper;
        this.userMapper = userMapper;
    }
    public List<CommentResponse> listByPost(Integer postId) {
        // ✅ username 포함된 DTO로 바로 반환
        return commentMapper.selectByPostIdWithUser(postId);
    }
//    public List<CommentResponse> listByPost(Integer postId) {
//        List<Comment> rows = commentMapper.selectByPostId(postId);
//        List<CommentResponse> result = new ArrayList<>(rows.size());
//        for (Comment c : rows) {
//            result.add(toDto(c));
//        }
//        return result;
//    }
    @Transactional
    public CommentResponse create(Integer postId, CommentCreateRequest req) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Integer userId = userMapper.findIdByUsername(username);
        if (userId == null) throw new IllegalStateException("사용자를 찾을 수 없습니다.");

        if (req.getParentCommentId() != null) {
            Integer parentPostId = commentMapper.selectPostIdByCommentId(req.getParentCommentId());
            if (parentPostId == null || !parentPostId.equals(postId)) {
                throw new IllegalArgumentException("부모 댓글이 해당 게시글에 속하지 않습니다.");
            }
        }

        Comment c = new Comment();
        c.setPostId(postId);
        c.setUserId(userId);
        c.setParentCommentId(req.getParentCommentId());
        c.setContent(req.getContent());

        int rows = commentMapper.insertComment(c);
        if (rows != 1 || c.getCommentId() == null) {
            throw new IllegalStateException("댓글 저장 실패");
        }

        // ✅ 저장 직후 username 포함 단건 조회
        return commentMapper.selectOneWithUser(c.getCommentId());
    }

//    @Transactional
//    public CommentResponse create(Integer postId, CommentCreateRequest req) {
//        // 로그인 사용자 → user_id 조회
//        String username = SecurityContextHolder.getContext().getAuthentication().getName();
//        Integer userId = userMapper.findIdByUsername(username);
//        if (userId == null) {
//            throw new IllegalStateException("사용자를 찾을 수 없습니다.");
//        }
//
//        // 대댓글이면 부모가 같은 게시글에 속하는지 체크(데이터 무결성)
//        if (req.getParentCommentId() != null) {
//            Integer parentPostId = commentMapper.selectPostIdByCommentId(req.getParentCommentId());
//            if (parentPostId == null || !parentPostId.equals(postId)) {
//                throw new IllegalArgumentException("부모 댓글이 해당 게시글에 속하지 않습니다.");
//            }
//        }
//
//        Comment c = new Comment();
//        c.setPostId(postId);
//        c.setUserId(userId);
//        c.setParentCommentId(req.getParentCommentId());
//        c.setContent(req.getContent());
//
//        int rows = commentMapper.insertComment(c);
//        if (rows != 1 || c.getCommentId() == null) {
//            throw new IllegalStateException("댓글 저장 실패");
//        }
//
//        Comment saved = commentMapper.selectOne(c.getCommentId());
//        return toDto(saved);
//    }

//    private CommentResponse toDto(Comment c) {
//        return new CommentResponse(
//                c.getCommentId(),
//                c.getPostId(),
//                c.getUserId(),
//                c.getParentCommentId(),
//                c.getContent(),
//                c.getCreatedAt(),
//                c.getUpdatedAt(),
//                c.getIsActive()
//        );
//    }
}
