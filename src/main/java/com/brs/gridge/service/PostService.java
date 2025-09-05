package com.brs.gridge.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.brs.gridge.domain.entity.Post;
import com.brs.gridge.domain.entity.User;
import com.brs.gridge.controller.dto.CreatePostRequest;
import com.brs.gridge.controller.dto.CreatePostResponse;
import com.brs.gridge.controller.dto.PostListResponse;
import com.brs.gridge.repository.PostRepository;
import com.brs.gridge.repository.UserRepository;
import com.brs.gridge.domain.vo.PostStatus;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;

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
        
        postRepository.save(post);

        return CreatePostResponse.of(true, "게시글이 성공적으로 생성되었습니다");
    }

    @Transactional
    public List<PostListResponse> getPosts(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다: " + username));
            
        List<Post> posts = postRepository.findPostsByFollowerAndStatus(user.getUserId(), PostStatus.VISIBLE);
        
        return posts.stream()
                .map(PostListResponse::from)
                .toList();
    }
}
    