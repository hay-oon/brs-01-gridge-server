package com.brs.gridge.repository;

import java.time.LocalDate;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.brs.gridge.domain.entity.Post;
import com.brs.gridge.domain.vo.PostStatus;


@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    
    // 피드 조회 (팔로우 게시물 조회) - 페이지네이션
    @Query("SELECT p FROM Post p " +
           "JOIN Follow f ON p.user.userId = f.followee.userId " + // followee(팔로우받는 사용자)의 게시물을 조회
           "WHERE f.follower.userId = :followerId " + // follower(현재 로그인 사용자)가 팔로우한 사용자들의 게시물만 조회
           "AND p.status = :status " +
           "ORDER BY p.createdAt DESC")
    Page<Post> findPostsByFollowerAndStatus(@Param("followerId") Long followerId, 
                                            @Param("status") PostStatus status, 
                                            Pageable pageable);
    
    // 내 피드 조회 (내가 작성한 게시물 조회) - 페이지네이션
    @Query("SELECT p FROM Post p " +
           "WHERE p.user.userId = :userId " +
           "AND p.status = :status " +
           "ORDER BY p.createdAt DESC")
    Page<Post> findMyPosts(@Param("userId") Long userId, 
                     @Param("status") PostStatus status, Pageable pageable);
    
    // 관리자 게시글 목록 조회 - 검색 조건에 따른 동적 쿼리
    @Query("SELECT p FROM Post p " +
           "JOIN p.user u " +
           "WHERE (:username IS NULL OR u.username LIKE %:username%) " +
           "AND (:status IS NULL OR p.status = :status) " +
           "AND (:createdDate IS NULL OR DATE(p.createdAt) = :createdDate) " +
           "ORDER BY p.createdAt DESC")
    Page<Post> findPostsByAdmin(@Param("username") String username, 
                                @Param("status") PostStatus status, 
                                @Param("createdDate") LocalDate createdDate, 
                                Pageable pageable);
}
