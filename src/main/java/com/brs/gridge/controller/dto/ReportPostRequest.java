package com.brs.gridge.controller.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReportPostRequest {

    @NotBlank(message = "신고 사유는 필수입니다")
    @Size(max = 500, message = "신고 사유는 500자를 초과할 수 없습니다")
    private String reason;

    public static ReportPostRequest of(String reason) {
        return ReportPostRequest.builder()
                .reason(reason)
                .build();
    }
}
