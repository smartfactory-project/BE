package com.example.demo.service;

import com.example.demo.dto.BookmarkStatusResponse;
import com.example.demo.mapper.PostBookmarkMapper;
import com.example.demo.mapper.UserMapper;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PostBookmarkService {

    private final PostBookmarkMapper bookmarkMapper;
    private final UserMapper userMapper;

    public PostBookmarkService(PostBookmarkMapper bookmarkMapper, UserMapper userMapper) {
        this.bookmarkMapper = bookmarkMapper;
        this.userMapper = userMapper;
    }

    public BookmarkStatusResponse getStatus(Integer postId) {
        int count = bookmarkMapper.countByPostId(postId);
        Integer userId = getCurrentUserIdOrNull();
        boolean bookmarked = (userId != null) && bookmarkMapper.existsByPostIdAndUserId(postId, userId);
        return new BookmarkStatusResponse(bookmarked, count);
    }

    @Transactional
    public BookmarkStatusResponse add(Integer postId) {
        Integer userId = getCurrentUserIdOrThrow();
        try {
            bookmarkMapper.insertBookmark(postId, userId);
        } catch (DuplicateKeyException ignore) {
            // 이미 북마크한 경우 → 멱등
        }
        int count = bookmarkMapper.countByPostId(postId);
        return new BookmarkStatusResponse(true, count);
    }

    @Transactional
    public BookmarkStatusResponse remove(Integer postId) {
        Integer userId = getCurrentUserIdOrThrow();
        bookmarkMapper.deleteBookmark(postId, userId); // 없어도 OK(멱등)
        int count = bookmarkMapper.countByPostId(postId);
        return new BookmarkStatusResponse(false, count);
    }

    private Integer getCurrentUserIdOrNull() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated()) return null;
        if ("anonymousUser".equals(String.valueOf(auth.getPrincipal()))) return null;
        String username = auth.getName();
        return userMapper.findIdByUsername(username);
    }

    private Integer getCurrentUserIdOrThrow() {
        Integer id = getCurrentUserIdOrNull();
        if (id == null) throw new IllegalStateException("인증된 사용자만 가능합니다.");
        return id;
    }
}
