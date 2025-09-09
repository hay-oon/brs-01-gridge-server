package com.brs.gridge.controller.dto;

import com.brs.gridge.domain.entity.User;
import com.brs.gridge.domain.vo.UserStatus;
import com.brs.gridge.domain.vo.UserRole;
import com.brs.gridge.domain.vo.LoginType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserListResponse {
    private Long userId;
    private String username;
    private LocalDate birthday;
    private String phone;
    private String email;
    private String profileImageUrl;
    private UserStatus status;
    private UserRole role;
    private LoginType loginType;
    private LocalDateTime createdAt;
    private LocalDateTime lastLoginAt;
    
    public static UserListResponse from(User user) {
        return UserListResponse.builder()
                .userId(user.getUserId())
                .username(user.getUsername())
                .birthday(user.getBirthday())
                .phone(user.getPhone())
                .email(user.getEmail())
                .profileImageUrl(user.getProfileImageUrl())
                .status(user.getStatus())
                .role(user.getRole())
                .loginType(user.getLoginType())
                .createdAt(user.getCreatedAt())
                .lastLoginAt(user.getLastLoginAt())
                .build();
    }
}
