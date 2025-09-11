package com.brs.gridge.controller.dto;

import com.brs.gridge.domain.entity.Report;

import lombok.Getter;
import java.time.LocalDateTime;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReportListResponse {
    private Long reportId;
    private Long postId;
    private String username;
    private String reason;
    private LocalDateTime createdAt;
    
    public static ReportListResponse from(Report report) {
        return ReportListResponse.builder()
                .reportId(report.getReportId())
                .postId(report.getPost().getPostId())
                .username(report.getUser().getUsername())
                .reason(report.getReason())
                .createdAt(report.getCreatedAt())
                .build();
    }
}