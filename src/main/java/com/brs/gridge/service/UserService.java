package com.brs.gridge.service;

import com.brs.gridge.controller.dto.ApiResponse;
import com.brs.gridge.controller.dto.PagedResponse;
import com.brs.gridge.controller.dto.UserInfoDetailResponse;
import com.brs.gridge.controller.dto.UserListResponse;
import com.brs.gridge.controller.dto.UserSearchRequest;
import com.brs.gridge.controller.dto.UserStatusUpdateRequest;
import com.brs.gridge.domain.vo.UserStatus;
import com.brs.gridge.domain.entity.User;
import com.brs.gridge.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {
    
    private final UserRepository userRepository;
    
    public PagedResponse<UserListResponse> getUsers(UserSearchRequest request, int page, int size) {
        
        Pageable pageable = PageRequest.of(page, size);
        Page<User> users = userRepository.findUsersWithFilters(
            request.getUsername(),
            request.getStatus(),
            request.getStartDate(),
            request.getEndDate(),
            pageable
        );
        
        return PagedResponse.from(users, UserListResponse::from);
    }
    
    public UserInfoDetailResponse getUserDetail(Long userId) {
        
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다. userId: " + userId));
        
        return UserInfoDetailResponse.from(user);
    }

    @Transactional
    public ApiResponse updateUserStatus(Long userId, UserStatusUpdateRequest request) {
        
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다. userId: " + userId));
        
        UserStatus previousStatus = user.getStatus();
        UserStatus newStatus = request.getStatus();

        if (previousStatus == newStatus) {
            throw new IllegalArgumentException("회원(" + user.getUsername() + ")의 상태가 이미 " + newStatus.name() + "입니다.");
        }

        user.changeStatus(newStatus);

        userRepository.save(user);
        
        return ApiResponse.of(true, "회원(" + user.getUsername() + ")의 상태가 " + previousStatus.name() + "에서 " + newStatus.name() + "로 변경되었습니다.");
    }
}
