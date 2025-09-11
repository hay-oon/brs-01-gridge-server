package com.brs.gridge.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.brs.gridge.controller.dto.LogListResponse;
import com.brs.gridge.controller.dto.PagedResponse;
import com.brs.gridge.domain.entity.Log;
import com.brs.gridge.domain.entity.User;
import com.brs.gridge.repository.LogRepository;
import com.brs.gridge.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LogService {
    
    private final LogRepository logRepository;
    private final UserRepository userRepository;
    
    // 로그 기록용 메서드
    @Transactional
    public void logAction(String username, String entityType, Long entityId, String action, String message) {
        User user = userRepository.findByUsername(username)
                .orElse(null);
        
        Log log = Log.createLog(user, entityType, entityId, action, message);
        
        logRepository.save(log);
    }

    // 로그 조회용 메서드
    // 전체 로그 조회
    @Transactional(readOnly = true)
    public PagedResponse<LogListResponse> getAllLogs(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Log> logs = logRepository.findAllByOrderByCreatedAtDesc(pageable);
        return PagedResponse.from(logs, LogListResponse::from);
    }
    
    // 엔티티 타입별 로그 조회
    @Transactional(readOnly = true)
    public PagedResponse<LogListResponse> getLogsByEntityType(String entityType, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Log> logs = logRepository.findByEntityType(entityType, pageable);
        return PagedResponse.from(logs, LogListResponse::from);
    }
    
}
