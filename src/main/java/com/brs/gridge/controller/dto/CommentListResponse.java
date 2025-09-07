package com.brs.gridge.controller.dto;

import com.brs.gridge.domain.entity.Comment;

import lombok.Getter;
import java.time.LocalDateTime;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentListResponse {
    private Long commentId;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private UserInfo user;

    public static CommentListResponse from(Comment comment) {
        return CommentListResponse.builder()
                .commentId(comment.getCommentId())
                .content(comment.getContent())
                .createdAt(comment.getCreatedAt())
                .updatedAt(comment.getUpdatedAt())
                .user(UserInfo.from(comment.getUser()))
                .build();
    }
}
