package com.example.demo.post.service;

import com.example.demo.post.dto.LikeStatusResponse;
import com.example.demo.post.mapper.PostLikeMapper;
import com.example.demo.auth.mapper.UserMapper;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PostLikeService {

    private final PostLikeMapper postLikeMapper;
    private final UserMapper userMapper;

    public PostLikeService(PostLikeMapper postLikeMapper, UserMapper userMapper) {
        this.postLikeMapper = postLikeMapper;
        this.userMapper = userMapper;
    }

    /**
     * 총 좋아요 수 + (로그인 시) 내가 눌렀는지 여부
     */
    public LikeStatusResponse getStatus(Integer postId) {
        int likes = postLikeMapper.countByPostId(postId);
        Integer userId = getCurrentUserIdOrNull();
        boolean liked = (userId != null) && postLikeMapper.existsByPostIdAndUserId(postId, userId);
        return new LikeStatusResponse(likes, liked);
    }

    /**
     * 좋아요 (멱등)
     */
    @Transactional
    public LikeStatusResponse like(Integer postId) {
        Integer userId = getCurrentUserIdOrThrow();
        try {
            postLikeMapper.insertLike(postId, userId);
        } catch (DuplicateKeyException ignore) {
            // 이미 누른 경우 -> 멱등
        }
        int likes = postLikeMapper.countByPostId(postId);
        return new LikeStatusResponse(likes, true);
    }

    /**
     * 좋아요 취소 (멱등)
     */
    @Transactional
    public LikeStatusResponse unlike(Integer postId) {
        Integer userId = getCurrentUserIdOrThrow();
        postLikeMapper.deleteLike(postId, userId); // 없어도 OK(멱등)
        int likes = postLikeMapper.countByPostId(postId);
        return new LikeStatusResponse(likes, false);
    }

    private Integer getCurrentUserIdOrNull() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated()) return null;
        Object principal = auth.getPrincipal();
        if (principal == null || "anonymousUser".equals(principal)) return null;
        String username = auth.getName();
        return userMapper.findIdByUsername(username);
    }

    private Integer getCurrentUserIdOrThrow() {
        Integer id = getCurrentUserIdOrNull();
        if (id == null) throw new IllegalStateException("인증된 사용자만 가능합니다.");
        return id;
    }
}
