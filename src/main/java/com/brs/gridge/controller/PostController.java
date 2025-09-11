package com.brs.gridge.controller;

import com.brs.gridge.common.Constants;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.brs.gridge.controller.dto.CreatePostRequest;
import com.brs.gridge.controller.dto.ApiResponse;
import com.brs.gridge.controller.dto.UpdatePostRequest;
import com.brs.gridge.controller.dto.PagedResponse;
import com.brs.gridge.controller.dto.PostResponse;
import com.brs.gridge.controller.dto.ReportPostRequest;
import com.brs.gridge.controller.dto.ReportPostResponse;
import com.brs.gridge.controller.dto.LikePostResponse;
import com.brs.gridge.controller.dto.BookmarkPostResponse;
import com.brs.gridge.controller.dto.PostSearchRequest;
import com.brs.gridge.service.PostService;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.RequiredArgsConstructor;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Validated
public class PostController {

    private final PostService postService;

    // 게시글 작성 API
    @PostMapping("/post")
    public ResponseEntity<ApiResponse> createPost(@AuthenticationPrincipal UserDetails userDetails, @Valid @RequestBody CreatePostRequest request) {
        ApiResponse Response = postService.createPost(userDetails.getUsername(), request);
        return ResponseEntity.status(HttpStatus.CREATED).body(Response);
    }

    // 팔로우 게시물 조회 API
    @GetMapping("/posts")
    public ResponseEntity<PagedResponse<PostResponse>> getPosts(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestParam @NotNull(message = "page는 필수입니다") @Min(value = 1, message = "page는 1 이상이어야 합니다") Integer page,
            @RequestParam @NotNull(message = "size는 필수입니다") @Min(value = 1, message = "size는 1 이상이어야 합니다") Integer size) {
        PagedResponse<PostResponse> posts = postService.getPosts(userDetails.getUsername(), page - 1, size);
        return ResponseEntity.ok(posts);
    }

    // 내가 작성한 게시물 조회 API
    @GetMapping("/my-posts")
    public ResponseEntity<PagedResponse<PostResponse>> getMyPosts(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestParam @NotNull(message = "page는 필수입니다") @Min(value = 1, message = "page는 1 이상이어야 합니다") Integer page,
            @RequestParam @NotNull(message = "size는 필수입니다") @Min(value = 1, message = "size는 1 이상이어야 합니다") Integer size) {
        PagedResponse<PostResponse> myPosts = postService.getMyPosts(userDetails.getUsername(), page - 1, size);
        return ResponseEntity.ok(myPosts);
    }

    // 게시글 수정 API
    @PatchMapping("/post/{postId}")
    public ResponseEntity<ApiResponse> updatePost(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable Long postId,
            @Valid @RequestBody UpdatePostRequest request) {
        ApiResponse response = postService.updatePost(userDetails.getUsername(), postId, request);
        return ResponseEntity.ok(response);
    }

    // 게시글 삭제 API
    @DeleteMapping("/post/{postId}")
    public ResponseEntity<ApiResponse> deletePost(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable Long postId) {
        ApiResponse response = postService.deletePost(userDetails.getUsername(), postId);
        return ResponseEntity.ok(response);
    }

    // 게시글 신고 API
    @PostMapping("/post/{postId}/report")
    public ResponseEntity<ReportPostResponse> reportPost(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable Long postId,
            @Valid @RequestBody ReportPostRequest request) {
        ReportPostResponse response = postService.reportPost(userDetails.getUsername(), postId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // 게시글 좋아요 토글 API
    @PostMapping("/post/{postId}/like")
    public ResponseEntity<LikePostResponse> toggleLike(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable Long postId) {
        LikePostResponse response = postService.toggleLike(userDetails.getUsername(), postId);
        return ResponseEntity.ok(response);
    }

    // 게시글 북마크 토글 API
    @PostMapping("/post/{postId}/bookmark")
    public ResponseEntity<BookmarkPostResponse> toggleBookmark(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable Long postId) {
        BookmarkPostResponse response = postService.toggleBookmark(userDetails.getUsername(), postId);
        return ResponseEntity.ok(response);
    }

    // 게시글 좋아요 상태 조회 API
    @GetMapping("/post/{postId}/like")
    public ResponseEntity<LikePostResponse> getLikeStatus(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable Long postId) {
        LikePostResponse response = postService.getLikeStatus(userDetails.getUsername(), postId);
        return ResponseEntity.ok(response);
    }

    // 게시글 북마크 상태 조회 API
    @GetMapping("/post/{postId}/bookmark")
    public ResponseEntity<BookmarkPostResponse> getBookmarkStatus(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable Long postId) {
        BookmarkPostResponse response = postService.getBookmarkStatus(userDetails.getUsername(), postId);
        return ResponseEntity.ok(response);
    }

    // 관리자 게시글 목록 조회 API
    @GetMapping("/admin/posts")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<PagedResponse<PostResponse>> getPostsByAdmin(
            @ModelAttribute PostSearchRequest request, 
            @RequestParam(defaultValue = Constants.Pagination.DEFAULT_PAGE) int page, 
            @RequestParam(defaultValue = Constants.Pagination.DEFAULT_SIZE) int size) {
        
        PagedResponse<PostResponse> posts = postService.getPostsByAdmin(request, page - 1, size);
        return ResponseEntity.ok(posts);
    }

    // 관리자 게시글 상세 조회 API
    @GetMapping("/admin/post/{postId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<PostResponse> getPostByAdmin(@PathVariable Long postId) {
        PostResponse post = postService.getPostByAdmin(postId);
        return ResponseEntity.ok(post);
    }

    // 관리자 게시글 삭제 API
    @DeleteMapping("/admin/post/{postId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse> deletePostByAdmin(@PathVariable Long postId) {
        ApiResponse response = postService.deletePostByAdmin(postId);
        return ResponseEntity.ok(response);
    }
}
