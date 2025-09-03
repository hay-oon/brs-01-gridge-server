package com.brs.gridge.domain.entity;

import com.brs.gridge.domain.vo.SubscriptionStatus;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AccessLevel;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "subscriptions")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Subscription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "subscription_id")
    private Long subscriptionId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "start_date" , nullable = false)
    private LocalDate startDate;

    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;  

    @Column(name = "status")
    private SubscriptionStatus status = SubscriptionStatus.ACTIVE;

}
