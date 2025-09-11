package com.brs.gridge.domain.entity;

import com.brs.gridge.common.Constants;
import com.brs.gridge.controller.dto.CreatePostRequest;
import com.brs.gridge.domain.vo.PostStatus;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.AccessLevel;
import lombok.Builder;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "posts")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long postId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "content", nullable = false, length = Constants.DatabaseLimits.CONTENT_MAX_LENGTH)
    private String content;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    @Builder.Default
    private PostStatus status = PostStatus.VISIBLE;

    @Column(name = "place_name", length = Constants.DatabaseLimits.PLACE_NAME_MAX_LENGTH)
    private String placeName;

    @Column(name = "like_count", nullable = false)
    @Builder.Default
    private Integer likeCount = 0;

    @Column(name = "created_at", nullable = false, updatable = false, columnDefinition = "TIMESTAMP")
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false, columnDefinition = "TIMESTAMP")
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    @Builder.Default
    private List<Attachment> attachments = new ArrayList<>();

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    @Builder.Default
    private List<Comment> comments = new ArrayList<>();

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    public static Post createPost(User user, CreatePostRequest request) {
        if (user == null) {
            throw new IllegalArgumentException("게시글 작성자는 필수입니다");
        }
        return Post.builder()
                .user(user)
                .content(request.getContent())
                .placeName(request.getPlaceName())
                .build();
    }
    
    public void updateContent(String content) {
        this.content = content;
    }

    public void updatePlaceName(String placeName) {
        this.placeName = placeName;
    }

    public void updateAttachments(List<Attachment> newAttachments) {
        this.attachments.clear();
        this.attachments.addAll(newAttachments);
    }

    public void delete() {
        this.status = PostStatus.DELETED;
    }

    public void updateStatus(PostStatus status) {
        this.status = status;
    }
}
