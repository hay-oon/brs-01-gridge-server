package com.brs.gridge.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.brs.gridge.controller.dto.ApiResponse;
import com.brs.gridge.controller.dto.PagedResponse;
import com.brs.gridge.controller.dto.ReportListResponse;
import com.brs.gridge.domain.entity.Report;
import com.brs.gridge.repository.ReportRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReportService {

    private final ReportRepository reportRepository;
    private final LogService logService;

    @Transactional
    public PagedResponse<ReportListResponse> getReports(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Report> reports = reportRepository.findAll(pageable);
        return PagedResponse.from(reports, ReportListResponse::from);
    }

    @Transactional
    public ApiResponse deleteReport(Long reportId) {
        if (!reportRepository.existsById(reportId)) {
            throw new IllegalArgumentException("신고를 찾을 수 없습니다: " + reportId);
        }
        
        reportRepository.deleteById(reportId);
        
        logService.logAction("ADMIN", "REPORT", reportId, "DELETE", 
            "관리자가 신고를 삭제했습니다. 신고 ID: " + reportId);
        
        return ApiResponse.of(true, "신고가 성공적으로 삭제되었습니다");
    }
}
