package com.brs.gridge.service;

import com.brs.gridge.controller.dto.SignupRequest;
import com.brs.gridge.controller.dto.LoginRequest;
import com.brs.gridge.domain.entity.User;
import com.brs.gridge.domain.vo.LoginType;
import com.brs.gridge.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public User signup(SignupRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new IllegalArgumentException("이미 존재하는 사용자 이름입니다");
        }

        if (!Boolean.TRUE.equals(request.getPrivacyAgreement())) {
            throw new IllegalArgumentException("개인정보 처리방침 동의는 필수입니다");
        }

        String encodedPassword = passwordEncoder.encode(request.getPassword());

        User user = User.createLocalUser(
            request.getUsername(),
            encodedPassword,
            request.getBirthday(),
            request.getEmail(),
            request.getPhone(),
            request.getProfileImageUrl()
        );

        return userRepository.save(user);
    }

    @Transactional
    public User login(LoginRequest request) {
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new EntityNotFoundException("사용자를 찾을 수 없습니다"));

        validateLocalLogin(user, request.getPassword());

        return user;
    }

    private void validateLocalLogin(User user, String rawPassword) {
        if (!LoginType.LOCAL.equals(user.getLoginType())) {
            throw new BadCredentialsException("잘못된 로그인 타입입니다");
        }
        
        if (!passwordEncoder.matches(rawPassword, user.getPassword())) {
            throw new BadCredentialsException("잘못된 비밀번호입니다");
        }

        switch (user.getStatus()) {
            case INACTIVE:
                throw new BadCredentialsException("비활성화된 계정입니다. 이메일 인증을 완료해주세요.");
            case BLOCKED:
                throw new BadCredentialsException("차단된 계정입니다. 관리자에게 문의해주세요.");
            case DELETED:
                throw new BadCredentialsException("삭제된 계정입니다.");
            case ACTIVE:
                break;
            default:
                throw new BadCredentialsException("알 수 없는 계정 상태입니다.");
        }
    }
}