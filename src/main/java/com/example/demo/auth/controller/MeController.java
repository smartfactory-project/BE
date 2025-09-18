package com.example.demo.auth.controller;

import com.example.demo.auth.dto.MyPageProfileResponse;
import com.example.demo.post.dto.PostWithStatsResponse;
import com.example.demo.auth.service.MeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/me")
@CrossOrigin(origins = "*")
public class MeController {

    private final MeService meService;

    public MeController(MeService meService) {
        this.meService = meService;
    }

    @GetMapping("/profile")
    public ResponseEntity<MyPageProfileResponse> profile() {
        return ResponseEntity.ok(meService.profile());
    }

    @GetMapping("/posts")
    public ResponseEntity<List<PostWithStatsResponse>> myPosts(
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size
    ) {
        return ResponseEntity.ok(meService.written(page, size));
    }

    @GetMapping("/likes")
    public ResponseEntity<List<PostWithStatsResponse>> myLikes(
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size
    ) {
        return ResponseEntity.ok(meService.liked(page, size));
    }

    @GetMapping("/bookmarks")
    public ResponseEntity<List<PostWithStatsResponse>> myBookmarks(
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size
    ) {
        return ResponseEntity.ok(meService.bookmarked(page, size));
    }
}
