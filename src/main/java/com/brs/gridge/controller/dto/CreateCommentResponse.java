package com.brs.gridge.controller.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateCommentResponse {
    private boolean success;
    private String message;

    public static CreateCommentResponse of(boolean success, String message) {
        return CreateCommentResponse.builder()
                .success(success)
                .message(message)
                .build();
    }
}
