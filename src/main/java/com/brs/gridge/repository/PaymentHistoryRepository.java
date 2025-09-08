package com.brs.gridge.repository;

import com.brs.gridge.domain.entity.PaymentHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PaymentHistoryRepository extends JpaRepository<PaymentHistory, Long> {
    
    // 포트원 결제 ID로 조회
    Optional<PaymentHistory> findByPortonePaymentId(String portonePaymentId);
    
    // 포트원 주문 ID로 조회
    Optional<PaymentHistory> findByPortoneOrderId(String portoneOrderId);
}
