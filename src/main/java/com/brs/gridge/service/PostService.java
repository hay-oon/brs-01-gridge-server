package com.brs.gridge.service;

import com.brs.gridge.common.Constants;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.brs.gridge.domain.entity.Post;
import com.brs.gridge.domain.entity.User;
import com.brs.gridge.domain.entity.Attachment;
import com.brs.gridge.domain.entity.Report;
import com.brs.gridge.domain.entity.PostLike;
import com.brs.gridge.domain.entity.PostBookmark;
import com.brs.gridge.controller.dto.CreatePostRequest;
import com.brs.gridge.controller.dto.ApiResponse;
import com.brs.gridge.controller.dto.UpdatePostRequest;
import com.brs.gridge.controller.dto.PagedResponse;
import com.brs.gridge.controller.dto.PostResponse;
import com.brs.gridge.controller.dto.ReportPostRequest;
import com.brs.gridge.controller.dto.ReportPostResponse;
import com.brs.gridge.controller.dto.LikePostResponse;
import com.brs.gridge.controller.dto.BookmarkPostResponse;
import com.brs.gridge.controller.dto.PostSearchRequest;
import com.brs.gridge.repository.PostRepository;
import com.brs.gridge.repository.UserRepository;
import com.brs.gridge.repository.ReportRepository;
import com.brs.gridge.repository.PostLikeRepository;
import com.brs.gridge.repository.PostBookmarkRepository;
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
    private final PostLikeRepository postLikeRepository;
    private final PostBookmarkRepository postBookmarkRepository;
    private final LogService logService;

    @Transactional
    public ApiResponse createPost(String username, CreatePostRequest request) {
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
        
        Post savedPost = postRepository.save(post);
        
        logService.logAction(username, "POST", savedPost.getPostId(), "CREATE", 
            "새로운 게시글을 작성했습니다. 작성 내용: " + request.getContent());

        return ApiResponse.of(true, "게시글이 성공적으로 생성되었습니다");
    }

    @Transactional
    public PagedResponse<PostResponse> getPosts(String username, int page, int size) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다: " + username));
            
        Pageable pageable = PageRequest.of(page, size);
        Page<Post> posts = postRepository.findPostsByFollowerAndStatus(user.getUserId(), PostStatus.VISIBLE, pageable);
        
        return PagedResponse.from(posts, PostResponse::from);
    }

    @Transactional
    public PagedResponse<PostResponse> getMyPosts(String username, int page, int size) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다: " + username));
            
        Pageable pageable = PageRequest.of(page, size);
        Page<Post> posts = postRepository.findMyPosts(user.getUserId(), PostStatus.VISIBLE, pageable);
        
        return PagedResponse.from(posts, PostResponse::from);
    }

    @Transactional
    public ApiResponse updatePost(String username, Long postId, UpdatePostRequest request) {
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
        
        logService.logAction(username, "POST", postId, "UPDATE", 
            "게시글을 수정했습니다. 게시글 ID: " + postId);
        
        return ApiResponse.of(true, "게시글이 성공적으로 수정되었습니다");
    }

    @Transactional
    public ApiResponse deletePost(String username, Long postId) {
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
        
        logService.logAction(username, "POST", postId, "DELETE", 
            "게시글을 삭제했습니다. 게시글 ID: " + postId);
        
        return ApiResponse.of(true, "게시글이 성공적으로 삭제되었습니다");
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
        Report savedReport = reportRepository.save(report);
        
        logService.logAction(username, "REPORT", savedReport.getReportId(), "CREATE", 
            "게시글을 신고했습니다. 게시글 ID: " + postId + ", 사유: " + request.getReason());
        
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

    // 게시글 좋아요 토글 API
    @Transactional
    public LikePostResponse toggleLike(String username, Long postId) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다: " + username));
        
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 없습니다: " + postId));
        
        // 삭제된 게시글은 좋아요 불가
        if (post.getStatus() == PostStatus.DELETED) {
            throw new IllegalArgumentException("삭제된 게시글에는 좋아요를 누를 수 없습니다");
        }
        
        // 자신의 게시글은 좋아요 불가
        if (post.getUser().getUserId().equals(user.getUserId())) {
            throw new IllegalArgumentException("자신의 게시글에는 좋아요를 누를 수 없습니다");
        }
        
        boolean isLiked = postLikeRepository.existsByUserAndPost(user, post);
        
        if (isLiked) {
            // 이미 좋아요를 누른 경우 - 좋아요 취소
            postLikeRepository.deleteByUserAndPost(user, post);
            long likeCount = postLikeRepository.countByPostId(postId);
            return LikePostResponse.of(true, "좋아요가 취소되었습니다", false, likeCount);
        } else {
            // 좋아요를 누르지 않은 경우 - 좋아요 추가
            PostLike postLike = PostLike.builder()
                    .user(user)
                    .post(post)
                    .build();
            postLikeRepository.save(postLike);
            long likeCount = postLikeRepository.countByPostId(postId);
            return LikePostResponse.of(true, "좋아요가 추가되었습니다", true, likeCount);
        }
    }

    // 게시글 북마크 토글 API
    @Transactional
    public BookmarkPostResponse toggleBookmark(String username, Long postId) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다: " + username));
        
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 없습니다: " + postId));
        
        // 삭제된 게시글은 북마크 불가
        if (post.getStatus() == PostStatus.DELETED) {
            throw new IllegalArgumentException("삭제된 게시글은 북마크할 수 없습니다");
        }
        
        boolean isBookmarked = postBookmarkRepository.existsByUserAndPost(user, post);
        
        if (isBookmarked) {
            // 이미 북마크한 경우 - 북마크 취소
            postBookmarkRepository.deleteByUserAndPost(user, post);
            long bookmarkCount = postBookmarkRepository.countByPostId(postId);
            return BookmarkPostResponse.of(true, "북마크가 취소되었습니다", false, bookmarkCount);
        } else {
            // 북마크하지 않은 경우 - 북마크 추가
            PostBookmark postBookmark = PostBookmark.builder()
                    .user(user)
                    .post(post)
                    .build();
            postBookmarkRepository.save(postBookmark);
            long bookmarkCount = postBookmarkRepository.countByPostId(postId);
            return BookmarkPostResponse.of(true, "북마크가 추가되었습니다", true, bookmarkCount);
        }
    }

    // 게시글 좋아요 상태 조회 API
    @Transactional(readOnly = true)
    public LikePostResponse getLikeStatus(String username, Long postId) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다: " + username));
        
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 없습니다: " + postId));
        
        boolean isLiked = postLikeRepository.existsByUserAndPost(user, post);
        long likeCount = postLikeRepository.countByPostId(postId);
        
        return LikePostResponse.of(true, "좋아요 상태를 조회했습니다", isLiked, likeCount);
    }

    // 게시글 북마크 상태 조회 API
    @Transactional(readOnly = true)
    public BookmarkPostResponse getBookmarkStatus(String username, Long postId) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다: " + username));
        
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 없습니다: " + postId));
        
        boolean isBookmarked = postBookmarkRepository.existsByUserAndPost(user, post);
        long bookmarkCount = postBookmarkRepository.countByPostId(postId);
        
        return BookmarkPostResponse.of(true, "북마크 상태를 조회했습니다", isBookmarked, bookmarkCount);
    }

    // 관리자 게시글 목록 조회 API
    @Transactional(readOnly = true)
    public PagedResponse<PostResponse> getPostsByAdmin(PostSearchRequest request, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Post> posts = postRepository.findPostsByAdmin(
            request.getUsername(), 
            request.getStatus(), 
            request.getCreatedDate(), 
            pageable
        );
        
        return PagedResponse.from(posts, PostResponse::from);
    }

    // 관리자 게시글 상세 조회 API
    @Transactional(readOnly = true)
    public PostResponse getPostByAdmin(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 없습니다: " + postId));
        
        return PostResponse.from(post);
    }

    // 관리자 게시글 삭제 API
    @Transactional
    public ApiResponse deletePostByAdmin(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 없습니다: " + postId));
        
        // 이미 삭제된 게시글인지 확인
        if (post.getStatus() == PostStatus.DELETED) {
            throw new IllegalArgumentException("이미 삭제된 게시글입니다");
        }
        
        post.delete(); // PostStatus.DELETED
        postRepository.save(post);
        
        return ApiResponse.of(true, "게시글이 성공적으로 삭제되었습니다");
    }
}