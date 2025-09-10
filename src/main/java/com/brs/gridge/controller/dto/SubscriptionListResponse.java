package com.brs.gridge.controller.dto;

import com.brs.gridge.domain.entity.PaymentHistory;
import com.brs.gridge.domain.entity.SubscriptionHistory;
import com.brs.gridge.domain.entity.User;
import com.brs.gridge.domain.vo.PaymentMethod;
import com.brs.gridge.domain.vo.SubscriptionStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SubscriptionListResponse {
    
    private Long subscriptionHistoryId;
    private Long userId;
    private String username;
    private String email;
    private String phone;
    private LocalDate startDate;
    private LocalDate endDate;
    private SubscriptionStatus status;
    private String reason;
    private LocalDateTime createdAt;
    private Long paymentHistoryId;
    private Long amount;
    private PaymentMethod paymentMethod;
    
    public static SubscriptionListResponse from(SubscriptionHistory subscription) {
        
        User user = subscription.getUser();
        PaymentHistory paymentHistory = subscription.getPaymentHistory();
        
        return SubscriptionListResponse.builder()
                .subscriptionHistoryId(subscription.getSubscriptionHistoryId())
                .userId(user.getUserId())
                .username(user.getUsername())
                .email(user.getEmail())
                .phone(user.getPhone())
                .startDate(subscription.getStartDate())
                .endDate(subscription.getEndDate())
                .status(subscription.getStatus())
                .reason(subscription.getReason())
                .createdAt(subscription.getCreatedAt())
                .paymentHistoryId(paymentHistory != null ? paymentHistory.getPaymentHistoryId() : null)
                .amount(paymentHistory != null ? paymentHistory.getAmount() : null)
                .paymentMethod(paymentHistory != null ? paymentHistory.getMethod() : null)
                .build();
    }
}
