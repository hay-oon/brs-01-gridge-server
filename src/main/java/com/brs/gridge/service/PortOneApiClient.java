package com.brs.gridge.service;

import com.brs.gridge.config.PortOneProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class PortOneApiClient {
    
    private final PortOneProperties portOneProperties;
    private final RestTemplate restTemplate;
    
    public PortOneApiClient(PortOneProperties portOneProperties) {
        this.portOneProperties = portOneProperties;
        this.restTemplate = new RestTemplate();
    }

    // 포트원 액세스 토큰 발급
    public String getAccessToken() {
        log.info("포트원 액세스 토큰 발급 요청");
        
        String apiKey = portOneProperties.getApiKey();
        String secretKey = portOneProperties.getSecretKey();
        
        log.info("API Key: {}", apiKey);
        log.info("Secret Key: {}", secretKey);
        
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("imp_key", apiKey);
        requestBody.put("imp_secret", secretKey);
        
        try {            
            @SuppressWarnings("unchecked") // Map.class 사용 시 타입 경고 방지
                Map<String, Object> response = (Map<String, Object>) restTemplate.postForObject(
                "https://api.iamport.kr/users/getToken",
                requestBody,
                Map.class
            );
            
            if (response != null && response.containsKey("response")) {
                @SuppressWarnings("unchecked")
                Map<String, Object> responseData = (Map<String, Object>) response.get("response");
                if (responseData != null && responseData.containsKey("access_token")) {
                    log.info("포트원 액세스 토큰 발급 성공");
                    return (String) responseData.get("access_token");
                }
            }
            throw new RuntimeException("액세스 토큰을 받을 수 없습니다");
        } catch (Exception e) {
            log.error("포트원 액세스 토큰 발급 실패: {}", e.getMessage());
            throw new RuntimeException("액세스 토큰 발급 실패: " + e.getMessage());
        }
    }
    
    // 결제 취소 - WIP
    // public Map<String, Object> cancelPayment(String paymentId, Map<String, Object> cancelRequest) {
    //     log.info("포트원 결제 취소 요청: " + paymentId);
        
    //     try {
    //         String token = getAccessToken();
            
    //         HttpHeaders headers = new HttpHeaders();
    //         headers.setContentType(MediaType.APPLICATION_JSON);
    //         headers.setBearerAuth(token);
            
    //         @SuppressWarnings("unchecked")
    //         Map<String, Object> response = (Map<String, Object>) restTemplate.postForObject(
    //             "https://api.iamport.kr/payments/" + paymentId + "/cancel",
    //             cancelRequest,
    //             Map.class
    //         );
            
    //         log.info("포트원 결제 취소 성공: " + response);
    //         return response;
            
    //     } catch (Exception e) {
    //         log.error("포트원 결제 취소 실패: " + e.getMessage());
    //         throw new RuntimeException("결제 취소 실패: " + e.getMessage());
    //     }
    // }
}
