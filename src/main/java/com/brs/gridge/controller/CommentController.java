package com.brs.gridge.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import com.brs.gridge.controller.dto.CreateCommentRequest;
import com.brs.gridge.controller.dto.CreateCommentResponse;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import com.brs.gridge.service.CommentService;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CommentController {
    
    private final CommentService commentService;

    @PostMapping("/comment")
    public ResponseEntity<CreateCommentResponse> createComment(@AuthenticationPrincipal UserDetails userDetails, @RequestBody CreateCommentRequest request) {
        CreateCommentResponse response = commentService.createComment(userDetails.getUsername(), request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);    
    }
}
