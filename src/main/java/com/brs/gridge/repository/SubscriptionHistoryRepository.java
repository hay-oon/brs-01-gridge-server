package com.brs.gridge.repository;

import com.brs.gridge.domain.entity.SubscriptionHistory;
import com.brs.gridge.domain.entity.User;
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
}
