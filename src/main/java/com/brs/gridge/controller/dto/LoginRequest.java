package com.brs.gridge.controller.dto;

import com.brs.gridge.domain.vo.LoginType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LoginRequest {
    @NotBlank(message = "사용자 이름은 필수입니다")
    private String username;
    
    @NotBlank(message = "비밀번호는 필수입니다")
    private String password;
    
    @NotNull(message = "로그인 타입은 필수입니다")
    private LoginType loginType;
}