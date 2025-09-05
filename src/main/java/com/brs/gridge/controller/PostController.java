package com.brs.gridge.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.brs.gridge.controller.dto.CreatePostRequest;
import com.brs.gridge.controller.dto.CreatePostResponse;
import com.brs.gridge.controller.dto.PagedResponse;
import com.brs.gridge.controller.dto.PostListResponse;
import com.brs.gridge.service.PostService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.RequiredArgsConstructor;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping("/post")
    public ResponseEntity<CreatePostResponse> createPost(@AuthenticationPrincipal UserDetails userDetails, @RequestBody CreatePostRequest request) {
        CreatePostResponse Response = postService.createPost(userDetails.getUsername(), request);
        return ResponseEntity.status(HttpStatus.CREATED).body(Response);
    }

    @GetMapping("/posts")
    public ResponseEntity<PagedResponse<PostListResponse>> getPosts(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestParam @NotNull(message = "page는 필수입니다") @Min(value = 1, message = "page는 1 이상이어야 합니다") Integer page,
            @RequestParam @NotNull(message = "size는 필수입니다") @Min(value = 1, message = "size는 1 이상이어야 합니다") Integer size) {
        PagedResponse<PostListResponse> posts = postService.getPosts(userDetails.getUsername(), page - 1, size);
        return ResponseEntity.ok(posts);
    }

    // 내가 작성한 게시물 조회 API
    @GetMapping("/my-posts")
    public ResponseEntity<PagedResponse<PostListResponse>> getMyPosts(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestParam @NotNull(message = "page는 필수입니다") @Min(value = 1, message = "page는 1 이상이어야 합니다") Integer page,
            @RequestParam @NotNull(message = "size는 필수입니다") @Min(value = 1, message = "size는 1 이상이어야 합니다") Integer size) {
        PagedResponse<PostListResponse> myPosts = postService.getMyPosts(userDetails.getUsername(), page - 1, size);
        return ResponseEntity.ok(myPosts);
    }
}
