package com.brs.gridge.controller.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CancelSubscriptionRequest {
    
    @NotNull(message = "취소 사유는 필수입니다")
    private String reason;
    
    private String refundReason; // 환불 사유
}
