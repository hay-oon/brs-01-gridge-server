package com.brs.gridge.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
import com.brs.gridge.controller.dto.UpdateCommentRequest;
import com.brs.gridge.controller.dto.ApiResponse;
import com.brs.gridge.controller.dto.PagedResponse;
import com.brs.gridge.controller.dto.CommentListResponse;
import com.brs.gridge.domain.vo.CommentStatus;
import com.brs.gridge.domain.vo.PostStatus;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.access.AccessDeniedException;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final LogService logService;

    @Transactional
    public CreateCommentResponse createComment(String username, Long postId, CreateCommentRequest request) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다: " + username));
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 없습니다: " + postId));

        if (post.getStatus() == PostStatus.DELETED) {
            throw new IllegalArgumentException("삭제된 게시글입니다");
        }

        Comment comment = Comment.createComment(user, post, request.getContent());
        Comment savedComment = commentRepository.save(comment);
        
        logService.logAction(username, "COMMENT", savedComment.getCommentId(), "CREATE", 
            "새로운 댓글을 작성했습니다. 게시글 ID: " + postId);

        return CreateCommentResponse.of(true, "댓글이 성공적으로 생성되었습니다");
    }

    @Transactional(readOnly = true)
    public PagedResponse<CommentListResponse> getComments(Long postId, int page, int size) {
        if (!postRepository.existsById(postId)) {
            throw new IllegalArgumentException("게시글을 찾을 수 없습니다: " + postId);
        }

        Pageable pageable = PageRequest.of(page, size);
        Page<Comment> comments = commentRepository.findByPostPostIdAndStatus(postId, CommentStatus.ACTIVE, pageable);

        return PagedResponse.from(comments, CommentListResponse::from);
    }

    @Transactional
    public ApiResponse updateComment(String username, Long postId, Long commentId, UpdateCommentRequest request) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다: " + username));
        
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("댓글을 찾을 수 없습니다: " + commentId));

        if (!comment.getPost().getPostId().equals(postId)) {
            throw new IllegalArgumentException("해당 게시글의 댓글이 아닙니다");
        }

        if (comment.getStatus() == CommentStatus.DELETED) {
            throw new IllegalArgumentException("삭제된 댓글은 수정할 수 없습니다");
        }

        if (!comment.getUser().getUserId().equals(user.getUserId())) {
            throw new AccessDeniedException("댓글을 수정할 권한이 없습니다");
        }

        comment.updateContent(request.getContent());
        commentRepository.save(comment);
        
        logService.logAction(username, "COMMENT", commentId, "UPDATE", 
            "댓글을 수정했습니다. 게시글 ID: " + postId);

        return ApiResponse.of(true, "댓글이 성공적으로 수정되었습니다");
    }

    @Transactional
    public ApiResponse deleteComment(String username, Long postId, Long commentId) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다: " + username));
        
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("댓글을 찾을 수 없습니다: " + commentId));

        if (!comment.getPost().getPostId().equals(postId)) {
            throw new IllegalArgumentException("해당 게시글의 댓글이 아닙니다");
        }

        if (comment.getStatus() == CommentStatus.DELETED) {
            throw new IllegalArgumentException("이미 삭제된 댓글입니다");
        }

        if (!comment.getUser().getUserId().equals(user.getUserId())) {
            throw new AccessDeniedException("댓글을 삭제할 권한이 없습니다");
        }

        comment.softDelete();
        commentRepository.save(comment);
        
        logService.logAction(username, "COMMENT", commentId, "DELETE", 
            "댓글을 삭제했습니다. 게시글 ID: " + postId);

        return ApiResponse.of(true, "댓글이 성공적으로 삭제되었습니다");
    }
}
