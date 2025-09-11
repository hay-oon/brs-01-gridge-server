package com.brs.gridge.controller.dto;

import java.time.LocalDate;

import com.brs.gridge.common.Constants;
import com.brs.gridge.domain.vo.LoginType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SignupRequest {
    @NotBlank(message = "사용자 이름은 필수입니다")
    @Pattern(regexp = Constants.Regex.USERNAME_PATTERN, message = "사용자명은 영문, 숫자, 언더스코어만 사용 가능하며 3-20자여야 합니다")
    @Size(max = Constants.DatabaseLimits.USERNAME_MAX_LENGTH, message = "사용자명은 " + Constants.DatabaseLimits.USERNAME_MAX_LENGTH + "자를 초과할 수 없습니다")
    private String username;
    
    @NotBlank(message = "비밀번호는 필수입니다")
    @Size(min = 8, message = "비밀번호는 8자 이상이어야 합니다")
    private String password;
    
    @Email(message = "올바른 이메일 형식이어야 합니다")
    @Pattern(regexp = Constants.Regex.EMAIL_PATTERN, message = "올바른 이메일 형식이어야 합니다")
    @Size(max = Constants.DatabaseLimits.EMAIL_MAX_LENGTH, message = "이메일은 " + Constants.DatabaseLimits.EMAIL_MAX_LENGTH + "자를 초과할 수 없습니다")
    private String email;
    
    @NotNull(message = "생년월일은 필수입니다")
    private LocalDate birthday;
    
    @NotBlank(message = "전화번호는 필수입니다")
    @Pattern(regexp = Constants.Regex.PHONE_PATTERN, message = "올바른 전화번호 형식이어야 합니다")
    @Size(max = Constants.DatabaseLimits.PHONE_MAX_LENGTH, message = "전화번호는 " + Constants.DatabaseLimits.PHONE_MAX_LENGTH + "자를 초과할 수 없습니다")
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