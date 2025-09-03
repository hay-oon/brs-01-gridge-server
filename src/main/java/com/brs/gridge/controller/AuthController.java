package com.brs.gridge.controller;

import com.brs.gridge.controller.dto.SignupRequest;
import com.brs.gridge.controller.dto.LoginRequest;
import com.brs.gridge.controller.dto.AuthResponse;
import com.brs.gridge.domain.entity.User;
import com.brs.gridge.security.JwtTokenProvider;
import com.brs.gridge.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> signup(@Valid @RequestBody SignupRequest request) {
        User user = authService.signup(request);
            
        String accessToken = jwtTokenProvider.createAccessToken(user.getUsername(), user.getRole());
        String refreshToken = jwtTokenProvider.createRefreshToken(user.getUsername());
        
        AuthResponse authResponse = new AuthResponse(accessToken, refreshToken);

        return ResponseEntity.ok(authResponse);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request) {
        User user = authService.login(request);
        
        String accessToken = jwtTokenProvider.createAccessToken(user.getUsername(), user.getRole());
        String refreshToken = jwtTokenProvider.createRefreshToken(user.getUsername());
        
        AuthResponse authResponse = new AuthResponse(accessToken, refreshToken);

        return ResponseEntity.ok(authResponse);
    }
}