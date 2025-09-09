package com.brs.gridge.controller.dto;

import com.brs.gridge.domain.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserInfoDetailResponse {
    private UserListResponse userInfo;
    private LocalDateTime updatedAt;
    private List<PrivacyAgreementResponse> privacyAgreements;
    
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class PrivacyAgreementResponse {
        private Long agreementId;
        private String version;
        private LocalDateTime agreedAt;
    }
    
    public static UserInfoDetailResponse from(User user) {
        List<PrivacyAgreementResponse> privacyAgreements = user.getPrivacyAgreements().stream()
                .map(agreement -> PrivacyAgreementResponse.builder()
                        .agreementId(agreement.getAgreementId())
                        .version(agreement.getVersion())
                        .agreedAt(agreement.getAgreedAt())
                        .build())
                .toList();
        
        return UserInfoDetailResponse.builder()
                .userInfo(UserListResponse.from(user))
                .updatedAt(user.getUpdatedAt())
                .privacyAgreements(privacyAgreements)
                .build();
    }
}
