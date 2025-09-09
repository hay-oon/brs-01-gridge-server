package com.brs.gridge.controller;

import com.brs.gridge.controller.dto.ApiResponse;
import com.brs.gridge.controller.dto.PagedResponse;
import com.brs.gridge.controller.dto.UserInfoDetailResponse;
import com.brs.gridge.controller.dto.UserListResponse;
import com.brs.gridge.controller.dto.UserSearchRequest;
import com.brs.gridge.controller.dto.UserStatusUpdateRequest;
import com.brs.gridge.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/users")
@RequiredArgsConstructor
public class UserController {
    
    private final UserService userService;
    
    // 회원 조회 API
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<PagedResponse<UserListResponse>> getUsers(
            @ModelAttribute UserSearchRequest request, // 쿼리 파라미터 : 유저 아이디, 유저 이름, 회원가입 날짜, 회원 상태
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "9") int size) {
        
        PagedResponse<UserListResponse> users = userService.getUsers(request, page - 1, size);
        
        return ResponseEntity.ok(users);
    }
    
    // 회원 상세 조회 API
    @GetMapping("/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserInfoDetailResponse> getUserDetail(@PathVariable Long userId) {
        UserInfoDetailResponse userDetailInfo = userService.getUserDetail(userId);
        
        return ResponseEntity.ok(userDetailInfo);
    }
    
    // 회원 정지 (회원 상태 변경) API
    @PatchMapping("/{userId}/status")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse> updateUserStatus(
            @PathVariable Long userId,
            @RequestBody UserStatusUpdateRequest request) {
        
        ApiResponse response = userService.updateUserStatus(userId, request);
        
        return ResponseEntity.ok(response);
    }
    
}
