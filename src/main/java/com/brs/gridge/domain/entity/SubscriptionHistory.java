package com.brs.gridge.domain.entity;

import com.brs.gridge.domain.vo.SubscriptionStatus;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "subscription_histories")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class SubscriptionHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "subscription_history_id")
    private Long subscriptionHistoryId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private SubscriptionStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "payment_history_id")
    private PaymentHistory paymentHistory; // 결제 히스토리와의 연관관계

    @Column(name = "reason")
    private String reason; // 상태 변경 사유 (예: "정기결제", "취소", "만료")

    @Column(name = "created_at", nullable = false, updatable = false, columnDefinition = "TIMESTAMP")
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

    public static SubscriptionHistory createSubscriptionHistory(User user, LocalDate startDate, LocalDate endDate, SubscriptionStatus status, PaymentHistory paymentHistory, String reason) {
        return SubscriptionHistory.builder()
                .user(user)
                .startDate(startDate)
                .endDate(endDate)
                .status(status)
                .paymentHistory(paymentHistory)
                .reason(reason)
                .build();
    }

    // 구독 상태 업데이트
    public void updateStatus(SubscriptionStatus status, String reason) {
        this.status = status;
        this.reason = reason;
    }
}
