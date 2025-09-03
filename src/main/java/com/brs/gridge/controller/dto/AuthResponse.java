package com.brs.gridge.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AuthResponse {
    private final String accessToken;
    private final String refreshToken;
}
