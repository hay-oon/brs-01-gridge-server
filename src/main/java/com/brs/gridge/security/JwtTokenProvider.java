package com.brs.gridge.security;

import com.brs.gridge.config.JwtProperties;
import com.brs.gridge.domain.vo.UserRole;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.Collections;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

    private final JwtProperties jwtProperties;

    private Key getSigningKey() {
        byte[] keyBytes = jwtProperties.getSecret().getBytes();
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String createAccessToken(String username, UserRole role) {
        // claims : payload에 저장될 정보 추가 
        Claims claims = Jwts.claims().setSubject(username); // "sub" : "username"
        claims.put("role", role.name()); // "role" : "role"

        Date now = new Date();
        Date validity = new Date(now.getTime() + jwtProperties.getAccessTokenValidityInMinutes() * 60 * 1000); // 1시간

        return Jwts.builder()
                .setClaims(claims) // "sub", "role"
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

    // 주석처리한 이유 : DB 조회 없이 JWT 정보만으로 인증 객체 생성
    // public Authentication getAuthentication(String token) {
    //     String username = getUsername(token);
        
    //     // 데이터베이스에서 실제 User 엔티티 조회
    //     User user = userRepository.findByUsername(username)
    //             .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다: " + username));
        
    //     // User 엔티티가 UserDetails를 구현하므로 직접 사용
    //     return new UsernamePasswordAuthenticationToken(
    //         user, 
    //         "", 
    //         user.getAuthorities()
    //     );
    // }

    // 인증 객체 생성
    public Authentication getAuthentication(String token) {
        Claims claims = parseClaims(token);
        String username = extractUsername(claims);
        UserRole role = extractRole(claims);
        UserDetails userDetails = createUserDetails(username, role);
        
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

      // JWT 토큰에서 클레임 추출
      private Claims parseClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    // JWT 클레임에서 Username 추출
    private String extractUsername(Claims claims) {
        String username = claims.getSubject();
        if (username == null) {
            throw new JwtException("Missing subject claim");
        }
        return username;
    }

    // JWT 클레임에서 Role 추출
    private UserRole extractRole(Claims claims) {
        String roleString = claims.get("role", String.class);
        if (roleString == null) {
            throw new JwtException("Missing role claim");
        }
        
        try {
            return UserRole.valueOf(roleString);
        } catch (IllegalArgumentException ex) {
            throw new JwtException("Invalid role claim: " + roleString);
        }
    }

    // UserDetails 객체 생성
    private UserDetails createUserDetails(String username, UserRole role) {
        return new org.springframework.security.core.userdetails.User(
            username,
            "",
            Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + role.name()))
        );
    }

    // 토큰 유효성 검사
    public boolean validateToken(String token) {
        try {
            parseClaims(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }
}