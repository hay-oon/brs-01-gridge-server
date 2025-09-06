package com.brs.gridge.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponse {
    
    private String message;
    private String error;

    public static ErrorResponse of(String message, String error) {
        return new ErrorResponse(message, error);
    }
}
