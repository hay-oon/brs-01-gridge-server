package com.brs.gridge.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.brs.gridge.domain.entity.User;
import com.brs.gridge.domain.entity.Post;
import com.brs.gridge.domain.entity.Comment;
import com.brs.gridge.repository.UserRepository;
import com.brs.gridge.repository.PostRepository;
import com.brs.gridge.repository.CommentRepository;
import com.brs.gridge.controller.dto.CreateCommentRequest;
import com.brs.gridge.controller.dto.CreateCommentResponse;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    @Transactional
    public CreateCommentResponse createComment(String username, CreateCommentRequest request) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다: " + username));
        Post post = postRepository.findById(request.getPostId())
                .orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 없습니다: " + request.getPostId()));

        Comment comment = Comment.createComment(user, post, request.getContent());
        commentRepository.save(comment);

        return CreateCommentResponse.of(true, "댓글이 성공적으로 생성되었습니다");
    }
}
