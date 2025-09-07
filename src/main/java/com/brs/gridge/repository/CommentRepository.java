package com.brs.gridge.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.brs.gridge.domain.entity.Comment;
import com.brs.gridge.domain.vo.CommentStatus;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    Page<Comment> findByPostPostIdAndStatus(Long postId, CommentStatus status, Pageable pageable);
}