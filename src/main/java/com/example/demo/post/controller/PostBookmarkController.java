package com.example.demo.post.controller;

import com.example.demo.post.dto.BookmarkStatusResponse;
import com.example.demo.post.service.PostBookmarkService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/posts/{postId}/bookmark")
@CrossOrigin(origins = "*")
public class PostBookmarkController {

    private final PostBookmarkService bookmarkService;

    public PostBookmarkController(PostBookmarkService bookmarkService) {
        this.bookmarkService = bookmarkService;
    }

    // 상태 조회: 북마크 여부 + 총 개수(선택)
    @GetMapping
    public ResponseEntity<BookmarkStatusResponse> status(@PathVariable Integer postId) {
        return ResponseEntity.ok(bookmarkService.getStatus(postId));
    }

    // 북마크 추가(멱등)
    @PutMapping
    public ResponseEntity<BookmarkStatusResponse> add(@PathVariable Integer postId) {
        return ResponseEntity.ok(bookmarkService.add(postId));
    }

    // 북마크 해제(멱등)
    @DeleteMapping
    public ResponseEntity<BookmarkStatusResponse> remove(@PathVariable Integer postId) {
        return ResponseEntity.ok(bookmarkService.remove(postId));
    }
}
