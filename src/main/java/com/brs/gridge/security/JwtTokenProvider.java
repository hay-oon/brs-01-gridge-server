package com.brs.gridge.security;

import com.brs.gridge.config.JwtProperties;
import com.brs.gridge.domain.vo.UserRole;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

    private final JwtProperties jwtProperties;
    private final UserDetailsService userDetailsService;

    private Key getSigningKey() {
        byte[] keyBytes = jwtProperties.getSecret().getBytes();
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String createAccessToken(String username, UserRole role) {
        // claims : payload에 저장될 정보 추가 
        Claims claims = Jwts.claims().setSubject(username); // "sub" : "username"
        claims.put("roles", role); // "roles" : "role"

        Date now = new Date();
        Date validity = new Date(now.getTime() + jwtProperties.getAccessTokenValidityInMinutes() * 60 * 1000); // 1시간

        return Jwts.builder()
                .setClaims(claims) // "sub", "roles"
                .setIssuedAt(now) // "iat"
                .setExpiration(validity) // "exp"
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public String createRefreshToken(String username) {
        Date now = new Date();
        Date validity = new Date(now.getTime() + jwtProperties.getRefreshTokenValidityInDays() * 24 * 60 * 60 * 1000); // 7일

        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    // 토큰에서 사용자 정보 추출 (인증 객체 생성)
    public Authentication getAuthentication(String token) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(getUsername(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    public String getUsername(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    // 토큰 유효성 검사
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(getSigningKey()).build().parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }
}