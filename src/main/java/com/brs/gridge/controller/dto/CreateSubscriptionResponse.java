package com.brs.gridge.controller.dto;

import com.brs.gridge.domain.vo.PaymentStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateSubscriptionResponse {
    
    private Long paymentHistoryId;
    private String portonePaymentId;
    private String portoneOrderId;
    private PaymentStatus status;
    private String message;
    private String redirectUrl; // 결제 페이지 URL
}
