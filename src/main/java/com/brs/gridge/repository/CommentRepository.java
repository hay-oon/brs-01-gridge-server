package com.brs.gridge.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.brs.gridge.domain.entity.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    Page<Comment> findByPostPostId(Long postId, Pageable pageable); // Post 엔티티 기본키 필드명이 post_id이기 때문에 이렇게 작성
}