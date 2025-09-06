package com.brs.gridge.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;

import lombok.RequiredArgsConstructor;
import com.brs.gridge.controller.dto.CreateCommentRequest;
import com.brs.gridge.controller.dto.CreateCommentResponse;
import com.brs.gridge.controller.dto.UpdateCommentRequest;
import com.brs.gridge.controller.dto.UpdateCommentResponse;
import com.brs.gridge.controller.dto.DeleteCommentResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import com.brs.gridge.service.CommentService;
import com.brs.gridge.controller.dto.PagedResponse;
import com.brs.gridge.controller.dto.CommentListResponse;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Min;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CommentController {
    
    private final CommentService commentService;

    @PostMapping("/post/{postId}/comment")
    public ResponseEntity<CreateCommentResponse> createComment(
            @AuthenticationPrincipal UserDetails userDetails, 
            @PathVariable Long postId,
            @RequestBody CreateCommentRequest request) {
        CreateCommentResponse response = commentService.createComment(userDetails.getUsername(), postId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);    
    }

    @GetMapping("/post/{postId}/comments")
    public ResponseEntity<PagedResponse<CommentListResponse>> getComments(
            @PathVariable Long postId,
            @RequestParam @NotNull(message = "page는 필수입니다") @Min(value = 1, message = "page는 1 이상이어야 합니다") Integer page,
            @RequestParam @NotNull(message = "size는 필수입니다") @Min(value = 1, message = "size는 1 이상이어야 합니다") Integer size) {
        PagedResponse<CommentListResponse> comments = commentService.getComments(postId, page - 1, size);
        return ResponseEntity.ok(comments);
    }

    @PatchMapping("/post/{postId}/comment/{commentId}")
    public ResponseEntity<UpdateCommentResponse> updateComment(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable Long postId,
            @PathVariable Long commentId,
            @RequestBody UpdateCommentRequest request) {
        UpdateCommentResponse response = commentService.updateComment(userDetails.getUsername(), postId, commentId, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/post/{postId}/comment/{commentId}")
    public ResponseEntity<DeleteCommentResponse> deleteComment(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable Long postId,
            @PathVariable Long commentId) {
        DeleteCommentResponse response = commentService.deleteComment(userDetails.getUsername(), postId, commentId);
        return ResponseEntity.ok(response);
    }
}
