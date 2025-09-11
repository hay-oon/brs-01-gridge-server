package com.brs.gridge.repository;

import com.brs.gridge.domain.entity.User;
import com.brs.gridge.domain.vo.UserStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    boolean existsByUsername(String username);
    
    @Query("SELECT u FROM User u WHERE " +
           "(:username IS NULL OR u.username LIKE %:username%) AND " + // LIKE : 해당 문자열이 포함된 모든 결과를 반환
           "(:status IS NULL OR u.status = :status) AND " +
           "(:startDate IS NULL OR DATE(u.createdAt) >= :startDate) AND " + // DATE() : LocalDateTime -> LocalDate (예: 2025-09-09 14:23:00 → 2025-09-09)
           "(:endDate IS NULL OR DATE(u.createdAt) <= :endDate) " +
           "ORDER BY u.createdAt DESC")
    Page<User> findUsersWithFilters(@Param("username") String username,
                                   @Param("status") UserStatus status,
                                   @Param("startDate") LocalDate startDate,
                                   @Param("endDate") LocalDate endDate,
                                   Pageable pageable);
}
