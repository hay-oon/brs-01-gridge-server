package com.brs.gridge.controller.dto;

import com.brs.gridge.domain.entity.Log;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class LogListResponse {
    
    private Long logId;
    private Long userId;
    private String username;
    private String entityType;
    private Long entityId;
    private String action;
    private String message;
    private LocalDateTime createdAt;
    
    public static LogListResponse from(Log log) {
        return LogListResponse.builder()
                .logId(log.getLogId())
                .userId(log.getUser() != null ? log.getUser().getUserId() : null)
                .username(log.getUser() != null ? log.getUser().getUsername() : null)
                .entityType(log.getEntityType())
                .entityId(log.getEntityId())
                .action(log.getAction())
                .message(log.getMessage())
                .createdAt(log.getCreatedAt())
                .build();
    }
}
