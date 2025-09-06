package com.brs.gridge.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.brs.gridge.domain.entity.PostLike;
import com.brs.gridge.domain.entity.Post;
import com.brs.gridge.domain.entity.User;

@Repository
public interface PostLikeRepository extends JpaRepository<PostLike, Long> {
    
    // 특정 사용자가 특정 게시글에 좋아요를 눌렀는지 확인
    boolean existsByUserAndPost(User user, Post post);
    
    // 특정 사용자가 특정 게시글에 좋아요를 눌렀는지 확인 (ID로)
    @Query("SELECT COUNT(pl) > 0 FROM PostLike pl WHERE pl.user.userId = :userId AND pl.post.postId = :postId")
    boolean existsByUserIdAndPostId(@Param("userId") Long userId, @Param("postId") Long postId);
    
    // 특정 게시글의 좋아요 수 조회
    @Query("SELECT COUNT(pl) FROM PostLike pl WHERE pl.post.postId = :postId")
    long countByPostId(@Param("postId") Long postId);
    
    // 특정 사용자의 좋아요 삭제
    void deleteByUserAndPost(User user, Post post);
}
