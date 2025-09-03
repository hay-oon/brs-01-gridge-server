package com.brs.gridge.domain.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AccessLevel;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "privacy_agreements")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PrivacyAgreement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "agreement_id")
    private Long agreementId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "version", nullable = false)
    private String version;

    @Column(name = "agreed_at", nullable = false, updatable = false)
    private LocalDateTime agreedAt;

    @Column(name = "expired_at")
    private LocalDateTime expiredAt;

    @PrePersist
    protected void onCreate() {
        agreedAt = LocalDateTime.now();
        expiredAt = LocalDateTime.now().plusYears(1);
    }
}
