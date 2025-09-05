package com.brs.gridge.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.brs.gridge.controller.dto.CreatePostRequest;
import com.brs.gridge.controller.dto.CreatePostResponse;
import com.brs.gridge.controller.dto.UpdatePostRequest;
import com.brs.gridge.controller.dto.UpdatePostResponse;
import com.brs.gridge.controller.dto.DeletePostResponse;
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

    // 게시글 작성 API
    @PostMapping("/post")
    public ResponseEntity<CreatePostResponse> createPost(@AuthenticationPrincipal UserDetails userDetails, @RequestBody CreatePostRequest request) {
        CreatePostResponse Response = postService.createPost(userDetails.getUsername(), request);
        return ResponseEntity.status(HttpStatus.CREATED).body(Response);
    }

    // 팔로우 게시물 조회 API
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

    // 게시글 수정 API
    @PutMapping("/post/{postId}")
    public ResponseEntity<UpdatePostResponse> updatePost(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable Long postId,
            @RequestBody UpdatePostRequest request) {
        UpdatePostResponse response = postService.updatePost(userDetails.getUsername(), postId, request);
        return ResponseEntity.ok(response);
    }

    // 게시글 삭제 API
    @DeleteMapping("/post/{postId}")
    public ResponseEntity<DeletePostResponse> deletePost(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable Long postId) {
        DeletePostResponse response = postService.deletePost(userDetails.getUsername(), postId);
        return ResponseEntity.ok(response);
    }
}
