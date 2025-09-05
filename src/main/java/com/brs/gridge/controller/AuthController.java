package com.brs.gridge.controller;

import com.brs.gridge.controller.dto.SignupRequest;
import com.brs.gridge.controller.dto.LoginRequest;
import com.brs.gridge.controller.dto.AuthResponse;
import com.brs.gridge.controller.dto.ResetPasswordRequest;
import com.brs.gridge.controller.dto.ResetPasswordResponse;
import com.brs.gridge.domain.entity.User;
import com.brs.gridge.security.JwtTokenProvider;
import com.brs.gridge.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PatchMapping;
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

        return ResponseEntity.status(201).body(authResponse);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request) {
        User user = authService.login(request);
        
        String accessToken = jwtTokenProvider.createAccessToken(user.getUsername(), user.getRole());
        String refreshToken = jwtTokenProvider.createRefreshToken(user.getUsername());
        
        AuthResponse authResponse = new AuthResponse(accessToken, refreshToken);

        return ResponseEntity.status(201).body(authResponse);
    }

    @PostMapping("/logout") 
    public ResponseEntity<Void> logout() { 
        return ResponseEntity.ok().build(); // 로그아웃은 spring security (SecurityConfig.java)에서 처리
    }

    @PatchMapping("/password")
    public ResponseEntity<ResetPasswordResponse> resetPassword(
        @AuthenticationPrincipal UserDetails userDetails, // @AuthenticationPrincipal : 현재 인증된 사용자의 정보를 추출
        @Valid @RequestBody ResetPasswordRequest request) {
        ResetPasswordResponse response = authService.resetPassword(userDetails.getUsername(), request);
        return ResponseEntity.ok(response);
    }
}