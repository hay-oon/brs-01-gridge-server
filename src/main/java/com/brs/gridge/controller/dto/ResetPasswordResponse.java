package com.brs.gridge.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public class ResetPasswordResponse {
    private String message;
    private boolean success;

    public static ResetPasswordResponse of(boolean success, String message) {
        return new ResetPasswordResponse(message, success);
    }
}
