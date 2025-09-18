package com.example.demo.post.controller;

import com.example.demo.post.dto.LikeStatusResponse;
import com.example.demo.post.service.PostLikeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/posts/{postId}/like")
@CrossOrigin(origins = "*")
public class PostLikeController {

    private final PostLikeService postLikeService;

    public PostLikeController(PostLikeService postLikeService) {
        this.postLikeService = postLikeService;
    }

    // 총 개수 + (로그인 시) 내가 눌렀는지 여부
    @GetMapping
    public ResponseEntity<LikeStatusResponse> getStatus(@PathVariable Integer postId) {
        return ResponseEntity.ok(postLikeService.getStatus(postId));
    }

    // 좋아요 (멱등)
    @PutMapping
    public ResponseEntity<LikeStatusResponse> like(@PathVariable Integer postId) {
        return ResponseEntity.ok(postLikeService.like(postId));
    }

    // 좋아요 취소 (멱등)
    @DeleteMapping
    public ResponseEntity<LikeStatusResponse> unlike(@PathVariable Integer postId) {
        return ResponseEntity.ok(postLikeService.unlike(postId));
    }
}
