package com.brs.gridge.repository;

import com.brs.gridge.domain.entity.Report;
import com.brs.gridge.domain.entity.Post;
import com.brs.gridge.domain.entity.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ReportRepository extends JpaRepository<Report, Long> {
    boolean existsByPostAndUser(Post post, User user);
    Optional<Report> findByPostAndUser(Post post, User user);
    
    @Query("SELECT COUNT(r) FROM Report r WHERE r.post.postId = :postId")
    long countByPostId(@Param("postId") Long postId);
}
