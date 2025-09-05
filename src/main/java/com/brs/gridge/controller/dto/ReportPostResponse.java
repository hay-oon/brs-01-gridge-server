package com.brs.gridge.controller.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReportPostResponse {

    private boolean success;
    private String message;

    public static ReportPostResponse of(boolean success, String message) {
        return ReportPostResponse.builder()
                .success(success)
                .message(message)
                .build();
    }
}
