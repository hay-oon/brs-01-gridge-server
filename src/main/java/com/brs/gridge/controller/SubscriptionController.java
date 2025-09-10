package com.brs.gridge.controller;

import com.brs.gridge.controller.dto.*;
import com.brs.gridge.service.SubscriptionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;



@Slf4j
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class SubscriptionController {
    
    private final SubscriptionService subscriptionService;

    // 구독 결제 생성 API
    @PostMapping("/subscription/create")
    public ResponseEntity<CreateSubscriptionResponse> createSubscription(
            @AuthenticationPrincipal UserDetails userDetails,
            @Valid @RequestBody CreateSubscriptionRequest request
            ) {
        
        String username = userDetails.getUsername();
        log.info("구독 결제 생성 요청 - 사용자명: " + username);
        
        CreateSubscriptionResponse response = subscriptionService.createSubscription(username, request);
        return ResponseEntity.ok(response);
    }
    
    // 구독 취소 API - WIP
    // @PostMapping("/cancel")
    // public ResponseEntity<String> cancelSubscription(
    //         @AuthenticationPrincipal UserDetails userDetails,
    //         @Valid @RequestBody CancelSubscriptionRequest request
    //         ) {
        
    //     String username = userDetails.getUsername();
    //     log.info("구독 취소 요청 - 사용자명: " + username);
        
    //     subscriptionService.cancelSubscription(username, request);
    //     return ResponseEntity.ok("구독이 취소되었습니다");
    // }

    // 전체 구독 내역 조회 API (페이지네이션)
    @GetMapping("/admin/subscriptions")
    public ResponseEntity<PagedResponse<SubscriptionListResponse>> getSubscriptionList(
            @ModelAttribute SubscriptionSearchRequest request,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "9") int size
            ) {
        
        PagedResponse<SubscriptionListResponse> response = subscriptionService.getSubscriptionList(request, page - 1, size);
        return ResponseEntity.ok(response);
    }

    // 구독 상세 조회 API
    @GetMapping("/admin/subscription/{subscriptionHistoryId}")
    public ResponseEntity<SubscriptionListResponse> getSubscriptionDetail(
            @PathVariable Long subscriptionHistoryId
            ) {
        
        SubscriptionListResponse response = subscriptionService.getSubscriptionDetail(subscriptionHistoryId);
        return ResponseEntity.ok(response);
    }
}
