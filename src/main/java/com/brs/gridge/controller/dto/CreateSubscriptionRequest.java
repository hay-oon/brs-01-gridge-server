package com.brs.gridge.controller.dto;

import com.brs.gridge.domain.vo.PaymentMethod;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateSubscriptionRequest {
    
    @NotNull(message = "결제 수단은 필수입니다")
    private PaymentMethod paymentMethod;
    
    @NotNull(message = "결제 금액은 필수입니다")
    private Long amount;
    
    // 클라이언트에서 전송된 금액 (서버 검증용)
    @NotNull(message = "클라이언트 금액은 필수입니다")
    private Long clientAmount;
    
    private String customerName;
    private String customerEmail;
    private String customerPhone;
}
