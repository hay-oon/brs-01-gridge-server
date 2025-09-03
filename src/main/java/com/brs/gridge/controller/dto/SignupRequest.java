package com.brs.gridge.controller.dto;

import java.time.LocalDate;

import com.brs.gridge.domain.vo.LoginType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SignupRequest {
    @NotBlank(message = "사용자 이름은 필수입니다")
    private String username;
    
    @NotBlank(message = "비밀번호는 필수입니다")
    private String password;
    
    @Email(message = "올바른 이메일 형식이어야 합니다")
    private String email;
    
    @NotNull(message = "생년월일은 필수입니다")
    private LocalDate birthday;
    
    @NotBlank(message = "전화번호는 필수입니다")
    private String phone;

    private String profileImageUrl;
    
    @NotNull(message = "로그인 타입은 필수입니다")
    private LoginType loginType;

    @NotNull(message = "약관 동의는 필수입니다")
    private Boolean privacyAgreement;

    // 소셜 로그인 구현시 주석 해제
    /*
    private String provider;
    private String providerUserId;
    */
}