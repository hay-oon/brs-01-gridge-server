package com.brs.gridge.controller.dto;

import com.brs.gridge.domain.vo.UserStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserStatusUpdateRequest {
    private UserStatus status;
}
