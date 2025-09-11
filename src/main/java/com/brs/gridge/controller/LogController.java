package com.brs.gridge.controller;

import com.brs.gridge.common.Constants;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.brs.gridge.controller.dto.LogListResponse;
import com.brs.gridge.controller.dto.PagedResponse;
import com.brs.gridge.service.LogService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class LogController {
    
    private final LogService logService;
    
    // 전체 로그 조회 API
    @GetMapping("/logs")
    public ResponseEntity<PagedResponse<LogListResponse>> getAllLogs(
            @RequestParam(defaultValue = Constants.Pagination.DEFAULT_PAGE) int page,
            @RequestParam(defaultValue = Constants.Pagination.DEFAULT_SIZE) int size) {
        PagedResponse<LogListResponse> logs = logService.getAllLogs(page - 1, size);
        return ResponseEntity.ok(logs);
    }
    
    
    // 회원 CRUD 히스토리 조회 API
    @GetMapping("/logs/users")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<PagedResponse<LogListResponse>> getUserLogs(
            @RequestParam(defaultValue = Constants.Pagination.DEFAULT_PAGE) int page,
            @RequestParam(defaultValue = Constants.Pagination.DEFAULT_SIZE) int size) {
        PagedResponse<LogListResponse> logs = logService.getLogsByEntityType("USER", page - 1, size);
        return ResponseEntity.ok(logs);
    }
    
    // 게시글 CRUD 히스토리 조회 API
    @GetMapping("/logs/posts")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<PagedResponse<LogListResponse>> getPostLogs(
            @RequestParam(defaultValue = Constants.Pagination.DEFAULT_PAGE) int page,
            @RequestParam(defaultValue = Constants.Pagination.DEFAULT_SIZE) int size) {
        PagedResponse<LogListResponse> logs = logService.getLogsByEntityType("POST", page - 1, size);
        return ResponseEntity.ok(logs);
    }
    
    // 신고 CRUD 히스토리 조회 API
    @GetMapping("/logs/reports")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<PagedResponse<LogListResponse>> getReportLogs(
            @RequestParam(defaultValue = Constants.Pagination.DEFAULT_PAGE) int page,
            @RequestParam(defaultValue = Constants.Pagination.DEFAULT_SIZE) int size) {
        PagedResponse<LogListResponse> logs = logService.getLogsByEntityType("REPORT", page - 1, size);
        return ResponseEntity.ok(logs);
    }
    
    // 댓글 CRUD 히스토리 조회 API
    @GetMapping("/logs/comments")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<PagedResponse<LogListResponse>> getCommentLogs(
            @RequestParam(defaultValue = Constants.Pagination.DEFAULT_PAGE) int page,
            @RequestParam(defaultValue = Constants.Pagination.DEFAULT_SIZE) int size) {
        PagedResponse<LogListResponse> logs = logService.getLogsByEntityType("COMMENT", page - 1, size);
        return ResponseEntity.ok(logs);
    }
    
    // 결제 CRUD 히스토리 조회 API
    @GetMapping("/logs/payments")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<PagedResponse<LogListResponse>> getPaymentLogs(
            @RequestParam(defaultValue = Constants.Pagination.DEFAULT_PAGE) int page,
            @RequestParam(defaultValue = Constants.Pagination.DEFAULT_SIZE) int size) {
        PagedResponse<LogListResponse> logs = logService.getLogsByEntityType("PAYMENT", page - 1, size);
        return ResponseEntity.ok(logs);
    }
}
