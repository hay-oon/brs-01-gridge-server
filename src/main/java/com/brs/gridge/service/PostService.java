package com.brs.gridge.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.brs.gridge.domain.entity.Post;
import com.brs.gridge.domain.entity.User;
import com.brs.gridge.domain.entity.Attachment;
import com.brs.gridge.domain.entity.Report;
import com.brs.gridge.controller.dto.CreatePostRequest;
import com.brs.gridge.controller.dto.CreatePostResponse;
import com.brs.gridge.controller.dto.UpdatePostRequest;
import com.brs.gridge.controller.dto.UpdatePostResponse;
import com.brs.gridge.controller.dto.DeletePostResponse;
import com.brs.gridge.controller.dto.PagedResponse;
import com.brs.gridge.controller.dto.PostListResponse;
import com.brs.gridge.controller.dto.ReportPostRequest;
import com.brs.gridge.controller.dto.ReportPostResponse;
import com.brs.gridge.repository.PostRepository;
import com.brs.gridge.repository.UserRepository;
import com.brs.gridge.repository.ReportRepository;
import com.brs.gridge.domain.vo.PostStatus;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.access.AccessDeniedException;

import java.util.List;
import java.util.ArrayList;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final ReportRepository reportRepository;

    @Transactional
    public CreatePostResponse createPost(String username, CreatePostRequest request) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다: " + username));
        Post post = Post.createPost(user, request);
        
        // 첨부파일 처리
        if (request.getAttachmentUrls() != null && !request.getAttachmentUrls().isEmpty()) {
            for (String fileUrl : request.getAttachmentUrls()) {
                Attachment attachment = Attachment.createAttachment(post, fileUrl);
                post.getAttachments().add(attachment);
            }
        }
        
        postRepository.save(post);

        return CreatePostResponse.of(true, "게시글이 성공적으로 생성되었습니다");
    }

    @Transactional
    public PagedResponse<PostListResponse> getPosts(String username, int page, int size) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다: " + username));
            
        Pageable pageable = PageRequest.of(page, size);
        Page<Post> posts = postRepository.findPostsByFollowerAndStatus(user.getUserId(), PostStatus.VISIBLE, pageable);
        
        return PagedResponse.from(posts, PostListResponse::from);
    }

    @Transactional
    public PagedResponse<PostListResponse> getMyPosts(String username, int page, int size) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다: " + username));
            
        Pageable pageable = PageRequest.of(page, size);
        Page<Post> posts = postRepository.findMyPosts(user.getUserId(), PostStatus.VISIBLE, pageable);
        
        return PagedResponse.from(posts, PostListResponse::from);
    }

    @Transactional
    public UpdatePostResponse updatePost(String username, Long postId, UpdatePostRequest request) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다: " + username));
        
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 없습니다: " + postId));
        
        // 권한 검증: 작성자만 수정 가능
        if (!post.getUser().getUserId().equals(user.getUserId())) {
            throw new AccessDeniedException("게시글을 수정할 권한이 없습니다");
        }
        
        // 삭제된 게시글은 수정 불가
        if (post.getStatus() == PostStatus.DELETED) {
            throw new IllegalArgumentException("삭제된 게시글은 수정할 수 없습니다");
        }
        
        // Content 수정
        post.updateContent(request.getContent());
        
        // PlaceName 수정 (optional)
        if (request.getPlaceName() != null) {
            post.updatePlaceName(request.getPlaceName());
        }
        
        // Attachments 수정 (optional)
        if (request.getAttachmentUrls() != null) {
            List<Attachment> newAttachments = new ArrayList<>();
            for (String fileUrl : request.getAttachmentUrls()) {
                Attachment attachment = Attachment.createAttachment(post, fileUrl);
                newAttachments.add(attachment);
            }
            post.updateAttachments(newAttachments);
        }
        
        postRepository.save(post);
        
        return UpdatePostResponse.of(true, "게시글이 성공적으로 수정되었습니다");
    }

    @Transactional
    public DeletePostResponse deletePost(String username, Long postId) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다: " + username));
        
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 없습니다: " + postId));
        
        // 권한 검증: 작성자만 삭제 가능
        if (!post.getUser().getUserId().equals(user.getUserId())) {
            throw new AccessDeniedException("게시글을 삭제할 권한이 없습니다");
        }
        
        // 이미 삭제된 게시글인지 확인
        if (post.getStatus() == PostStatus.DELETED) {
            throw new IllegalArgumentException("이미 삭제된 게시글입니다");
        }
        
        // 게시글 삭제 (soft delete)
        post.delete();
        postRepository.save(post);
        
        return DeletePostResponse.of(true, "게시글이 성공적으로 삭제되었습니다");
    }

    // 게시글 신고 API
    @Transactional
    public ReportPostResponse reportPost(String username, Long postId, ReportPostRequest request) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다: " + username));
        
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 없습니다: " + postId));
        
        // 삭제된 게시글은 신고 불가
        if (post.getStatus() == PostStatus.DELETED) {
            throw new IllegalArgumentException("삭제된 게시글은 신고할 수 없습니다");
        }
        
        // 자신의 게시글은 신고 불가
        if (post.getUser().getUserId().equals(user.getUserId())) {
            throw new IllegalArgumentException("자신의 게시글은 신고할 수 없습니다");
        }
        
        // 이미 신고한 게시글인지 확인
        if (reportRepository.existsByPostAndUser(post, user)) {
            throw new IllegalArgumentException("이미 신고한 게시글입니다");
        }
        
        // 신고 생성 및 저장
        Report report = Report.createReport(post, user, request.getReason());
        reportRepository.save(report);
        
        // 신고 횟수에 따른 처리 로직
        long reportCount = reportRepository.countByPostId(post.getPostId());
        
        // 신고가 3회 이상 누적되면 자동으로 숨김 처리
        if (reportCount >= 3) {
            post.updateStatus(PostStatus.INVISIBLE);
            postRepository.save(post);
            return ReportPostResponse.of(true, "신고가 접수되었습니다. 해당 게시글은 신고 누적으로 인해 숨김 처리되었습니다.");
        }
        
        return ReportPostResponse.of(true, "신고가 성공적으로 접수되었습니다. 검토 후 조치하겠습니다.");
    }
}