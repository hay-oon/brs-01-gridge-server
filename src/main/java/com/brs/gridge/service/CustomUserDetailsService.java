// 현재 UserDetailsService 사용하지 않음 -> JWT 토큰 사용

// package com.brs.gridge.service;

// import com.brs.gridge.domain.entity.User;
// import com.brs.gridge.repository.UserRepository;
// import lombok.RequiredArgsConstructor;
// import org.springframework.security.core.userdetails.UserDetails;
// import org.springframework.security.core.userdetails.UserDetailsService;
// import org.springframework.security.core.userdetails.UsernameNotFoundException;
// import org.springframework.stereotype.Service;
// import org.springframework.transaction.annotation.Transactional;

// @Service
// @RequiredArgsConstructor
// public class CustomUserDetailsService implements UserDetailsService {

//     private final UserRepository userRepository;

//     @Override
//     @Transactional(readOnly = true)
//     public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//         User user = userRepository.findByUsername(username)
//                 .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다: " + username));

//         return user;
//     }
// }
