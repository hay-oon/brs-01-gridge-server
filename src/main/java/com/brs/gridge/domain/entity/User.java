package com.brs.gridge.domain.entity;

import com.brs.gridge.domain.vo.UserStatus;
import com.brs.gridge.domain.vo.UserRole;
import com.brs.gridge.domain.vo.LoginType;
import com.brs.gridge.domain.validation.ValidUserLogin;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AccessLevel;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
@Getter 
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ValidUserLogin
public class User {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "email")
    private String email;

    @Column(name = "phone")
    private String phone;

    @Column(name = "profile_image_url")
    private String profileImageUrl;
    
    @Column(name = "status")
    private UserStatus status = UserStatus.ACTIVE;

    @Column(name = "role")
    private UserRole role = UserRole.USER;

    @Column(name = "login_type", nullable = false)
    private LoginType loginType;


    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<SocialAccount> socialAccounts = new ArrayList<>();

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
