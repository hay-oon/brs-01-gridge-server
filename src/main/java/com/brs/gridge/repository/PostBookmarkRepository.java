package com.brs.gridge.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.brs.gridge.domain.entity.PostBookmark;
import com.brs.gridge.domain.entity.Post;
import com.brs.gridge.domain.entity.User;

@Repository
public interface PostBookmarkRepository extends JpaRepository<PostBookmark, Long> {
    
    // 특정 사용자가 특정 게시글을 북마크했는지 확인
    boolean existsByUserAndPost(User user, Post post);
    
    // 특정 사용자가 특정 게시글을 북마크했는지 확인 (ID로)
    @Query("SELECT COUNT(pb) > 0 FROM PostBookmark pb WHERE pb.user.userId = :userId AND pb.post.postId = :postId")
    boolean existsByUserIdAndPostId(@Param("userId") Long userId, @Param("postId") Long postId);
    
    // 특정 게시글의 북마크 수 조회
    @Query("SELECT COUNT(pb) FROM PostBookmark pb WHERE pb.post.postId = :postId")
    long countByPostId(@Param("postId") Long postId);
    
    // 특정 사용자의 북마크 삭제
    void deleteByUserAndPost(User user, Post post);
}
