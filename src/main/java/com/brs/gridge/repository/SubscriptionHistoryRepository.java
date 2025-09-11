package com.brs.gridge.repository;

import com.brs.gridge.domain.entity.SubscriptionHistory;
import com.brs.gridge.domain.entity.User;
import com.brs.gridge.domain.vo.SubscriptionStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface SubscriptionHistoryRepository extends JpaRepository<SubscriptionHistory, Long> {
    
    // 사용자의 현재 활성 구독 조회
    @Query("SELECT sh FROM SubscriptionHistory sh WHERE sh.user = :user AND sh.status = 'ACTIVE' AND sh.endDate >= :currentDate ORDER BY sh.createdAt DESC")
    Optional<SubscriptionHistory> findActiveSubscriptionByUser(@Param("user") User user, @Param("currentDate") LocalDate currentDate);
    
    // 결제 히스토리 ID로 구독 히스토리 조회
    @Query("SELECT sh FROM SubscriptionHistory sh WHERE sh.paymentHistory.paymentHistoryId = :paymentHistoryId")
    Optional<SubscriptionHistory> findByPaymentHistoryId(@Param("paymentHistoryId") Long paymentHistoryId);
    
    // 구독 내역 조회 (다중 조건 검색) - 페이지네이션 지원
    @Query("SELECT sh FROM SubscriptionHistory sh " +
           "JOIN FETCH sh.user u " +
           "JOIN FETCH sh.paymentHistory ph " +
           "WHERE (:username IS NULL OR u.username LIKE %:username%) " +
           "AND (:startDate IS NULL OR sh.startDate >= :startDate) " +
           "AND (:endDate IS NULL OR sh.endDate <= :endDate) " +
           "AND (:status IS NULL OR sh.status = :status) " +
           "ORDER BY sh.createdAt DESC")
    Page<SubscriptionHistory> findSubscriptionsWithConditions(
            @Param("username") String username,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate,
            @Param("status") SubscriptionStatus status,
            Pageable pageable
    );
    
    // 구독 상세 조회
    @Query("SELECT sh FROM SubscriptionHistory sh " +
           "JOIN FETCH sh.user u " +
           "JOIN FETCH sh.paymentHistory ph " +
           "WHERE sh.subscriptionHistoryId = :subscriptionHistoryId")
    Optional<SubscriptionHistory> findByIdWithDetails(@Param("subscriptionHistoryId") Long subscriptionHistoryId);
}
