package com.example.demo.post.controller;

import com.example.demo.post.dto.CommentCreateRequest;
import com.example.demo.post.dto.CommentResponse;
import com.example.demo.post.service.CommentService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/posts/{postId}/comments")
@CrossOrigin(origins = "*")
public class CommentController {

    private final CommentService commentService;
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    // GET /api/posts/{postId}/comments
    @GetMapping
    public ResponseEntity<List<CommentResponse>> list(@PathVariable Integer postId) {
        return ResponseEntity.ok(commentService.listByPost(postId));
    }

    // POST /api/posts/{postId}/comments
    @PostMapping
    public ResponseEntity<CommentResponse> create(@PathVariable Integer postId,
                                                  @Valid @RequestBody CommentCreateRequest req) {
        CommentResponse created = commentService.create(postId, req);
        return ResponseEntity
                .created(URI.create("/api/posts/" + postId + "/comments/" + created.getId()))
                .body(created);
    }
}
