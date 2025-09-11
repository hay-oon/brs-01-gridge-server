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
public class PostResponse {
    
    private Long postId;
    private String content;
    private PostStatus status;
    private String placeName;
    private Integer likeCount;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private UserInfo user;
    private List<AttachmentInfo> attachments;
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class AttachmentInfo {
        private Long attachmentId;
        private String fileUrl;
        private FileType fileType;
    }
    
    public static PostResponse from(Post post) {
        return PostResponse.builder()
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
