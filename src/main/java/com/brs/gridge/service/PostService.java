package com.brs.gridge.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.brs.gridge.domain.entity.Post;
import com.brs.gridge.domain.entity.User;
import com.brs.gridge.domain.entity.Attachment;
import com.brs.gridge.controller.dto.CreatePostRequest;
import com.brs.gridge.controller.dto.CreatePostResponse;
import com.brs.gridge.controller.dto.PagedResponse;
import com.brs.gridge.controller.dto.PostListResponse;
import com.brs.gridge.repository.PostRepository;
import com.brs.gridge.repository.UserRepository;
import com.brs.gridge.domain.vo.PostStatus;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

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
}
    