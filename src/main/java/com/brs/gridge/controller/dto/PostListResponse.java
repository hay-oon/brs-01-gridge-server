package com.brs.gridge.controller.dto;

import com.brs.gridge.domain.entity.Post;
import com.brs.gridge.domain.vo.PostStatus;
import com.brs.gridge.domain.vo.FileType;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostListResponse {
    
    private Long postId;
    private String content;
    private PostStatus status;
    private String placeName;
    private Integer likeCount;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private UserInfo user;
    private List<AttachmentInfo> attachments;
    
    // TODO: 추후 상황에 따라 분리할 것   
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class AttachmentInfo {
        private Long attachmentId;
        private String fileUrl;
        private FileType fileType;
    }
    
    public static PostListResponse from(Post post) {
        return PostListResponse.builder()
                .postId(post.getPostId())
                .content(post.getContent())
                .status(post.getStatus())
                .placeName(post.getPlaceName())
                .likeCount(post.getLikeCount())
                .createdAt(post.getCreatedAt())
                .updatedAt(post.getUpdatedAt())
                .user(UserInfo.from(post.getUser()))
                .attachments(post.getAttachments().stream()
                        .map(attachment -> AttachmentInfo.builder()
                                .attachmentId(attachment.getAttachmentId())
                                .fileUrl(attachment.getFileUrl())
                                .fileType(attachment.getFileType())
                                .build())
                        .toList())
                .build();
    }
}
