package com.brs.gridge.domain.entity;

import com.brs.gridge.domain.vo.PaymentStatus;
import com.brs.gridge.domain.vo.PaymentMethod;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "payment_histories")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class PaymentHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_history_id")
    private Long paymentHistoryId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "amount", nullable = false)
    private Long amount;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private PaymentStatus status;

    @Enumerated(EnumType.STRING)
    @Column(name = "method", nullable = false)
    private PaymentMethod method;

    @Column(name = "portone_payment_id")
    private String portonePaymentId; // 포트원에서 발급하는 결제 ID

    @Column(name = "portone_order_id")
    private String portoneOrderId; // 포트원에서 발급하는 주문 ID

    @Column(name = "error_code")
    private String errorCode; // 포트원 오류 코드

    @Column(name = "error_message")
    private String errorMessage; // 포트원 오류 메시지

    @Column(name = "created_at", nullable = false, updatable = false, columnDefinition = "TIMESTAMP")
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

    public static PaymentHistory createPaymentHistory(User user, Long amount, PaymentMethod method, String portonePaymentId, String portoneOrderId) {
        return PaymentHistory.builder()
                .user(user)
                .amount(amount)
                .status(PaymentStatus.PENDING)
                .method(method)
                .portonePaymentId(portonePaymentId)
                .portoneOrderId(portoneOrderId)
                .build();
    }

    // 결제 상태 업데이트
    public void updateStatus(PaymentStatus status) {
        this.status = status;
    }

    // 오류 정보 업데이트
    public void updateError(String errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }
}
