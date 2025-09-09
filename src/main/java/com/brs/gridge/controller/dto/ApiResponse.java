package com.brs.gridge.controller.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApiResponse {
    private boolean success;
    private String message;

    public static ApiResponse of(boolean success, String message) {
        return ApiResponse.builder()
                .success(success)
                .message(message)
                .build();
    }
}
