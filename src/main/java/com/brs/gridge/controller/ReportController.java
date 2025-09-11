package com.brs.gridge.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.brs.gridge.controller.dto.ApiResponse;
import com.brs.gridge.controller.dto.PagedResponse;
import com.brs.gridge.controller.dto.ReportListResponse;
import com.brs.gridge.service.ReportService;

import org.springframework.security.access.prepost.PreAuthorize;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/")
@RequiredArgsConstructor
public class ReportController {

    private final ReportService reportService;
    
    // 신고 목록 조회 API
    @GetMapping("admin/reports")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<PagedResponse<ReportListResponse>> getReports(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {
        PagedResponse<ReportListResponse> reports = reportService.getReports(page - 1, size);
        return ResponseEntity.ok(reports);
    }

    // 신고 삭제 API
    @DeleteMapping("admin/report/{reportId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse> deleteReport(@PathVariable Long reportId) {
        ApiResponse response = reportService.deleteReport(reportId);
        return ResponseEntity.ok(response);
    }
}   
