package com.brs.gridge.domain.entity;

import com.brs.gridge.domain.vo.UserStatus;
import com.brs.gridge.domain.vo.UserRole;
import com.brs.gridge.domain.vo.LoginType;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AccessLevel;
import lombok.Builder;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
@Getter 
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "birthday", nullable = false)
    private LocalDate birthday;

    @Column(name = "phone", nullable = false)
    private String phone;

    @Column(name = "email")
    private String email;

    @Column(name = "profile_image_url")
    private String profileImageUrl;
    
    @Column(name = "status", nullable = false)
    private UserStatus status = UserStatus.ACTIVE;

    @Column(name = "role", nullable = false)
    private UserRole role = UserRole.USER;

    @Column(name = "login_type", nullable = false)
    private LoginType loginType;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<SocialAccount> socialAccounts = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<PrivacyAgreement> privacyAgreements = new ArrayList<>();

    @Builder(access = AccessLevel.PRIVATE)
    private User(String username, String password, LocalDate birthday, String email, String phone, 
                String profileImageUrl, LoginType loginType) {
        this.username = username;
        this.password = password;
        this.birthday = birthday;
        this.email = email;
        this.phone = phone;
        this.profileImageUrl = profileImageUrl;
        this.loginType = loginType;
    }

    // 로컬 로그인 유저 생성
    public static User createLocalUser(String username, String encodedPassword, LocalDate birthday, String email, String phone, String profileImageUrl) {
        if (encodedPassword == null || encodedPassword.trim().isEmpty()) {
            throw new IllegalArgumentException("비밀번호는 필수입니다");
        }

        User user = User.builder()
                .username(username)
                .password(encodedPassword)
                .birthday(birthday)
                .email(email)
                .phone(phone)
                .profileImageUrl(profileImageUrl)
                .loginType(LoginType.LOCAL)
                .build();

        // 약관 동의 생성 및 연결
        user.addPrivacyAgreement();

        return user;
    }

    // 약관 동의 추가 메서드
    public void addPrivacyAgreement() {
        PrivacyAgreement agreement = PrivacyAgreement.createAgreement(this, "1.0");
        this.privacyAgreements.add(agreement);
    }

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}