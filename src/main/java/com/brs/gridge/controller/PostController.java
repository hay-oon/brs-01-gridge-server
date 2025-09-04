package com.brs.gridge.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import jakarta.validation.Valid;

import com.brs.gridge.controller.dto.CreatePostRequest;
import com.brs.gridge.controller.dto.CreatePostResponse;
import com.brs.gridge.service.PostService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/post")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping("")
    public ResponseEntity<CreatePostResponse> createPost(@AuthenticationPrincipal UserDetails userDetails, @Valid @RequestBody CreatePostRequest request) {
        CreatePostResponse Response = postService.createPost(userDetails.getUsername(), request);
        return ResponseEntity.ok(Response);
    }
}
