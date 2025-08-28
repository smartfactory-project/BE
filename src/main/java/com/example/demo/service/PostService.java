package com.example.demo.service;

import com.example.demo.dto.PostCreateRequest;

import com.example.demo.exception.ForbiddenException;
import com.example.demo.exception.NotFoundException;
import com.example.demo.dto.PostWithStatsResponse;
import org.springframework.security.core.Authentication;
import com.example.demo.mapper.PostMapper;
import com.example.demo.mapper.UserMapper;
import com.example.demo.model.Post;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.*;

@Service
public class PostService {

    private final PostMapper postMapper;
    private final UserMapper userMapper;

    public PostService(PostMapper postMapper, UserMapper userMapper) {
        this.postMapper = postMapper;
        this.userMapper = userMapper;
    }
    private Integer currentUserIdOrNull() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated()) return null;
        if ("anonymousUser".equals(String.valueOf(auth.getPrincipal()))) return null;
        String username = auth.getName();
        return userMapper.findIdByUsername(username);
    }

    private boolean hasRole(String role) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null) return false;
        return auth.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_" + role));
    }

    public List<PostWithStatsResponse> listPosts(Integer page, Integer size) {
        int pageSize = (size == null || size <= 0) ? 20 : size;
        int pageNo   = (page == null || page < 0) ? 0 : page;
        int offset   = pageNo * pageSize;

        Integer viewerId = currentUserIdOrNull();
        List<PostWithStatsResponse> rows = postMapper.selectPostsWithStats(pageSize, offset, viewerId);

        // 태그 N+1 (원하면 최적화 가능)
        for (PostWithStatsResponse r : rows) {
            r.setTags(postMapper.selectTagsByPostId(r.getId()));
        }
        return rows;
    }

    @Transactional
    public PostWithStatsResponse createPost(PostCreateRequest req) {
        // 1) 로그인 사용자 id 안전하게 획득
        Integer userId = currentUserIdOrNull();
        if (userId == null) {
            throw new IllegalStateException("로그인이 필요합니다.");
        }

        // 2) 저장
        Post post = new Post();
        post.setUserId(userId);
        post.setTitle(req.getTitle());
        post.setContent(req.getContent());
        post.setCategory(req.getCategory());

        int rows = postMapper.insertPost(post);
        if (rows != 1 || post.getPostId() == null) {
            throw new IllegalStateException("게시글 저장 실패");
        }

        // 3) 태그 저장(선택)
        List<String> tags = req.getTags() == null ? Collections.emptyList() : req.getTags();
        if (!tags.isEmpty()) {
            postMapper.insertPostTagBatch(post.getPostId(), tags);
        }

        Integer postId = post.getPostId();
        Integer viewerId = userId; // 또는 currentUserIdOrNull()
        PostWithStatsResponse saved = postMapper.selectPostByIdWithStats(postId, viewerId);
        saved.setTags(postMapper.selectTagsByPostId(postId));
        return saved;
    }
    public Optional<PostWithStatsResponse> getPost(Integer id) {
        Integer viewerId = currentUserIdOrNull();
        PostWithStatsResponse row = postMapper.selectPostByIdWithStats(id, viewerId);
        if (row == null) return Optional.empty();
        row.setTags(postMapper.selectTagsByPostId(row.getId()));
        return Optional.of(row);
    }
    public void increaseView(Integer postId) {
        postMapper.increaseViewCount(postId);
    }


    @Transactional
    public void deletePost(Integer postId) {
        // 1) 존재여부 및 글 소유자 확인
        Integer ownerId = postMapper.findAuthorIdByPostId(postId);
        if (ownerId == null) {
            throw new NotFoundException("게시글을 찾을 수 없습니다.");
        }

        Integer me = currentUserIdOrNull();
        if (me == null) {
            throw new ForbiddenException("로그인이 필요합니다.");
        }

        boolean isAdminOrManager = hasRole("ADMIN") || hasRole("MANAGER");
        if (!me.equals(ownerId) && !isAdminOrManager) {
            throw new ForbiddenException("본인 글만 삭제할 수 있습니다.");
        }

        // 2) 실제 삭제
        // FK가 ON DELETE CASCADE로 설정되어 있다면 아래 한 줄이면 충분합니다.
        int affected = postMapper.deletePost(postId);
        if (affected != 1) {
            throw new NotFoundException("이미 삭제되었거나 삭제할 수 없습니다.");
        }

        // (선택) 만약 CASCADE가 없다면 아래처럼 수동 삭제를 수행하세요.
        // postMapper.deleteCommentsByPostId(postId);
        // postMapper.deleteLikesByPostId(postId);
        // postMapper.deleteBookmarksByPostId(postId);
        // postMapper.deleteTagsByPostId(postId);
        // postMapper.deletePostOnly(postId);
    }
}
