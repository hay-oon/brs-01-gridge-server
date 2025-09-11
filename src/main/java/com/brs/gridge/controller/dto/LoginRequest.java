package com.brs.gridge.controller.dto;

import com.brs.gridge.common.Constants;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LoginRequest {
    @NotBlank(message = "사용자 이름은 필수입니다")
    @Pattern(regexp = Constants.Regex.USERNAME_PATTERN, message = "사용자명은 영문, 숫자, 언더스코어만 사용 가능하며 3-20자여야 합니다")
    @Size(max = Constants.DatabaseLimits.USERNAME_MAX_LENGTH, message = "사용자명은 " + Constants.DatabaseLimits.USERNAME_MAX_LENGTH + "자를 초과할 수 없습니다")
    private String username;
    
    @NotBlank(message = "비밀번호는 필수입니다")
    @Size(min = 8, message = "비밀번호는 8자 이상이어야 합니다")
    private String password;
}