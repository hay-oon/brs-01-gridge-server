package com.brs.gridge.controller.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdatePostResponse {
    private boolean success;
    private String message;

    public static UpdatePostResponse of(boolean success, String message) {
        return UpdatePostResponse.builder()
                .success(success)
                .message(message)
                .build();
    }
}
