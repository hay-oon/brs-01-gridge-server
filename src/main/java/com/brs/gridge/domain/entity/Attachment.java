package com.brs.gridge.domain.entity;

import com.brs.gridge.domain.vo.FileType;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.AccessLevel;
import lombok.Builder;

import jakarta.persistence.*;

@Entity
@Table(name = "attachments")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Attachment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "attachment_id")
    private Long attachmentId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    @Enumerated(EnumType.STRING)
    @Column(name = "file_type", nullable = false)
    private FileType fileType;

    @Column(name = "file_url", nullable = false, length = 500)
    private String fileUrl;

    public static FileType determineFileType(String fileUrl) {
        if (fileUrl == null || fileUrl.isEmpty()) {
            return FileType.ETC;
        }
        
        String lowerUrl = fileUrl.toLowerCase();
        
        // 이미지 확장자 확인
        if (lowerUrl.matches(".*\\.(jpg|jpeg|png|gif|bmp|webp|svg)$")) {
            return FileType.IMAGE;
        }
        
        // 동영상 확장자 확인
        if (lowerUrl.matches(".*\\.(mp4|avi|mov|wmv|flv|webm|mkv|m4v)$")) {
            return FileType.VIDEO;
        }
        
        return FileType.ETC;
    }

    public static Attachment createAttachment(Post post, String fileUrl) {
        return Attachment.builder()
                .post(post)
                .fileType(determineFileType(fileUrl))
                .fileUrl(fileUrl)
                .build();
    }
}
