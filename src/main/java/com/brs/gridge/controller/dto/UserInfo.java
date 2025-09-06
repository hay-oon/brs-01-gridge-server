package com.brs.gridge.controller.dto;

import com.brs.gridge.domain.entity.User;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserInfo {
    
    private Long userId;
    private String username;
    private String profileImageUrl;

    public static UserInfo from(User user) {
        return UserInfo.builder()
                .userId(user.getUserId())
                .username(user.getUsername())
                .profileImageUrl(user.getProfileImageUrl())
                .build();
    }
}
