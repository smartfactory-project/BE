package com.example.demo.controller;

import com.example.demo.dto.PostCreateRequest;
import com.example.demo.dto.PostWithStatsResponse;
import com.example.demo.dto.PostResponse;
import com.example.demo.service.PostService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/posts")
@CrossOrigin(origins = "*")
public class PostController {
    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping
    public ResponseEntity<List<PostWithStatsResponse>> list(
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size) {
        return ResponseEntity.ok(postService.listPosts(page, size));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostWithStatsResponse> detail(@PathVariable Integer id) {
        Optional<PostWithStatsResponse> post = postService.getPost(id);
        return post.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<PostWithStatsResponse> create(@Valid @RequestBody PostCreateRequest req) {
        PostWithStatsResponse created = postService.createPost(req);
        return ResponseEntity
                .created(URI.create("/api/posts/" + created.getId()))
                .body(created);
    }

    @PostMapping("/{id}/view")
    public ResponseEntity<Void> increaseView(@PathVariable Integer id) {
        postService.increaseView(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        postService.deletePost(id);
        return ResponseEntity.noContent().build();
    }
}