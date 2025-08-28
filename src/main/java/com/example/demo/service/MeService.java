package com.example.demo.service;

import com.example.demo.dto.MyPageProfileResponse;
import com.example.demo.dto.PostWithStatsResponse;
import com.example.demo.mapper.PostMapper;
import com.example.demo.mapper.UserMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MeService {
    private final PostMapper postMapper;
    private final UserMapper userMapper;

    public MeService(PostMapper postMapper, UserMapper userMapper) {
        this.postMapper = postMapper;
        this.userMapper = userMapper;
    }

    private Integer currentUserId() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated()) return null;
        if ("anonymousUser".equals(String.valueOf(auth.getPrincipal()))) return null;
        String username = auth.getName();
        return userMapper.findIdByUsername(username);
    }

    public MyPageProfileResponse profile() {
        Integer uid = currentUserId();
        if (uid == null) throw new IllegalStateException("로그인이 필요합니다.");
        return postMapper.selectMyProfile(uid);
    }

    public List<PostWithStatsResponse> written(Integer page, Integer size) {
        Integer uid = currentUserId();
        if (uid == null) throw new IllegalStateException("로그인이 필요합니다.");
        int pageSize = (size == null || size <= 0) ? 20 : size;
        int pageNo   = (page == null || page < 0) ? 0 : page;
        int offset   = pageNo * pageSize;
        return postMapper.selectPostsByAuthorWithStats(uid, pageSize, offset, uid);
    }

    public List<PostWithStatsResponse> liked(Integer page, Integer size) {
        Integer uid = currentUserId();
        if (uid == null) throw new IllegalStateException("로그인이 필요합니다.");
        int pageSize = (size == null || size <= 0) ? 20 : size;
        int pageNo   = (page == null || page < 0) ? 0 : page;
        int offset   = pageNo * pageSize;
        return postMapper.selectPostsLikedByUserWithStats(uid, pageSize, offset, uid);
    }

    public List<PostWithStatsResponse> bookmarked(Integer page, Integer size) {
        Integer uid = currentUserId();
        if (uid == null) throw new IllegalStateException("로그인이 필요합니다.");
        int pageSize = (size == null || size <= 0) ? 20 : size;
        int pageNo   = (page == null || page < 0) ? 0 : page;
        int offset   = pageNo * pageSize;
        return postMapper.selectPostsBookmarkedByUserWithStats(uid, pageSize, offset, uid);
    }
}
