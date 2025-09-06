package com.brs.gridge.controller.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DeletePostResponse {
    private boolean success;
    private String message;

    public static DeletePostResponse of(boolean success, String message) {
        return DeletePostResponse.builder()
                .success(success)
                .message(message)
                .build();
    }
}
