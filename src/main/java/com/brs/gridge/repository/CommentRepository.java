package com.brs.gridge.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.brs.gridge.domain.entity.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    
}
