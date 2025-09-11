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

    @Transactional
    public PagedResponse<ReportListResponse> getReports(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Report> reports = reportRepository.findAll(pageable);
        return PagedResponse.from(reports, ReportListResponse::from);
    }

    @Transactional
    public ApiResponse deleteReport(Long reportId) {
        reportRepository.deleteById(reportId);
        return ApiResponse.of(true, "신고가 성공적으로 삭제되었습니다");
    }
}
