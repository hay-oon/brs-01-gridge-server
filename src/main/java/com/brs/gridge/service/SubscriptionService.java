package com.brs.gridge.service;

import com.brs.gridge.controller.dto.*;
import com.brs.gridge.domain.entity.*;
import com.brs.gridge.domain.vo.PaymentStatus;
import com.brs.gridge.domain.vo.SubscriptionStatus;
import com.brs.gridge.repository.PaymentHistoryRepository;
import com.brs.gridge.repository.SubscriptionHistoryRepository;
import com.brs.gridge.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SubscriptionService {
    
    private final PortOneApiClient portOneApiClient;
    private final PaymentHistoryRepository paymentHistoryRepository;
    private final SubscriptionHistoryRepository subscriptionHistoryRepository;
    private final UserRepository userRepository;
    private final LogService logService;
    
    private static final Long MONTHLY_AMOUNT = 9900L; // 월 구독료
    
    // 결제 생성
    @Transactional
    public CreateSubscriptionResponse createSubscription(String username, CreateSubscriptionRequest request) {
        log.info("구독 결제 생성 요청 - 사용자명: " + username + ", 금액: " + request.getAmount());
        
        // 1. 사용자 조회
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다"));
        
        // 2. 금액 검증 (클라이언트 변조 방지)
        if (!request.getAmount().equals(request.getClientAmount())) {
            throw new IllegalArgumentException("클라이언트와 서버의 금액이 일치하지 않습니다");
        }
        
        if (!request.getAmount().equals(MONTHLY_AMOUNT)) {
            throw new IllegalArgumentException("금액이 올바르지 않습니다");
        }
        
        // 3. 결제 히스토리 저장 (PENDING 상태)
        PaymentHistory paymentHistory = PaymentHistory.createPaymentHistory(
            user, 
            request.getAmount(), 
            request.getPaymentMethod(), 
            UUID.randomUUID().toString(),// 임시로 paymentId 사용
            UUID.randomUUID().toString()// 임시로 orderId 사용
        );
        paymentHistoryRepository.save(paymentHistory);
        
        // 4. 구독 히스토리 저장 (PENDING 상태)
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = startDate.plusMonths(1);
        
        SubscriptionHistory subscriptionHistory = SubscriptionHistory.createSubscriptionHistory(
            user,
            startDate,
            endDate,
            SubscriptionStatus.ACTIVE,
            paymentHistory,
            "구독 결제 생성"
        );
        subscriptionHistoryRepository.save(subscriptionHistory);
        
        logService.logAction(username, "PAYMENT", paymentHistory.getPaymentHistoryId(), "CREATE", 
            "구독 결제를 생성했습니다. 금액: " + request.getAmount() + "원");
        
        return CreateSubscriptionResponse.builder()
                .paymentHistoryId(paymentHistory.getPaymentHistoryId())
                .portonePaymentId(UUID.randomUUID().toString())// 임시로 paymentId 사용
                .portoneOrderId(UUID.randomUUID().toString())// 임시로 orderId 사용
                .status(PaymentStatus.PENDING)
                .message("구독 결제 페이지로 이동하세요")
                .redirectUrl("https://test.co.kr/payment/success")
                .build();        
    }
    
    // 구독 취소 - WIP
    // @Transactional
    // public void cancelSubscription(String username, CancelSubscriptionRequest request) {
    //     log.info("구독 취소 요청 - 사용자명: " + username);
        
    //     User user = userRepository.findByUsername(username)
    //             .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다"));
        
    //     // 현재 활성 구독 조회
    //     SubscriptionHistory activeSubscription = subscriptionHistoryRepository
    //             .findActiveSubscriptionByUser(user, LocalDate.now())
    //             .orElseThrow(() -> new IllegalArgumentException("활성 구독을 찾을 수 없습니다"));
        
    //     // 구독 상태를 CANCELED로 변경
    //     activeSubscription.updateStatus(SubscriptionStatus.CANCELED, request.getReason());
    //     subscriptionHistoryRepository.save(activeSubscription);
        
    //     // 결제 취소 (포트원 API 호출)
    //     if (activeSubscription.getPaymentHistoryId() != null) {
    //         PaymentHistory paymentHistory = paymentHistoryRepository.findById(activeSubscription.getPaymentHistoryId())
    //                 .orElseThrow(() -> new IllegalArgumentException("결제 히스토리를 찾을 수 없습니다"));
            
    //         try {
    //             Map<String, Object> cancelRequest = Map.of(
    //                 "reason", request.getRefundReason() != null ? request.getRefundReason() : "구독 취소"
    //             );
                
    //             portOneApiClient.cancelPayment(paymentHistory.getPortonePaymentId(), cancelRequest);
                
    //             // 결제 상태를 CANCELED로 업데이트
    //             paymentHistory.updateStatus(PaymentStatus.CANCELED);
    //             paymentHistoryRepository.save(paymentHistory);
                
    //             log.info("구독 취소 완료 - 사용자명: " + username);
                
    //         } catch (Exception e) {
    //             log.error("구독 취소 실패: " + e.getMessage(), e);
    //             throw new RuntimeException("구독 취소 중 오류가 발생했습니다: " + e.getMessage());
    //         }
    //     }
    // }
    
    // 구독 내역 조회 (페이지네이션)
    public PagedResponse<SubscriptionListResponse> getSubscriptionList(SubscriptionSearchRequest request, int page, int size) {
        
        String username = request.getUsername();
        LocalDate startDate = request.getStartDate();
        LocalDate endDate = request.getEndDate();
        SubscriptionStatus status = request.getStatus();
        
        Pageable pageable = PageRequest.of(page, size);
        
        Page<SubscriptionHistory> subscriptionPage = subscriptionHistoryRepository.findSubscriptionsWithConditions(
                username, startDate, endDate, status, pageable
        );
        
        return PagedResponse.from(subscriptionPage, SubscriptionListResponse::from);
    }
    
    // 구독 상세 조회
    public SubscriptionListResponse getSubscriptionDetail(Long subscriptionHistoryId) {
        
        SubscriptionHistory subscription = subscriptionHistoryRepository.findByIdWithDetails(subscriptionHistoryId)
                .orElseThrow(() -> new IllegalArgumentException("구독 내역을 찾을 수 없습니다"));
        
        return SubscriptionListResponse.from(subscription);
    }
    
    
}
