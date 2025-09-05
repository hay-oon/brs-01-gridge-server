package com.brs.gridge.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.brs.gridge.domain.entity.Post;
import com.brs.gridge.domain.vo.PostStatus;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    
    @Query("SELECT p FROM Post p " +
           "JOIN Follow f ON p.user.userId = f.followee.userId " + // followee(팔로우받는 사용자)의 게시물을 조회
           "WHERE f.follower.userId = :followerId " + // follower(현재 로그인 사용자)가 팔로우한 사용자들의 게시물만 조회
           "ORDER BY p.createdAt DESC")
    List<Post> findPostsByFollowerAndStatus(@Param("followerId") Long followerId, @Param("status") PostStatus status);
}
