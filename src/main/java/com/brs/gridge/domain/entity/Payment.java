package com.brs.gridge.domain.entity;

import com.brs.gridge.domain.vo.PaymentStatus;
import com.brs.gridge.domain.vo.PaymentMethod;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AccessLevel;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "payments")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_id")
    private Long paymentId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "amount", nullable = false)
    private Long amount;

    @Column(name = "status")
    private PaymentStatus status = PaymentStatus.PENDING;

    @Column(name = "method", nullable = false)
    private PaymentMethod method;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

}
