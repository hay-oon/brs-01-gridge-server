package com.brs.gridge.common;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.validation.FieldError;

import lombok.extern.slf4j.Slf4j;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {
        
        // 유효성 검증 실패한 필드들과 오류 메시지를 저장할 Map 생성
        Map<String, String> errors = new HashMap<>();
        
        // 모든 유효성 검증 오류를 순회하면서 필드명과 오류 메시지를 추출
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            // FieldError로 캐스팅하여 필드명 추출
            String fieldName = ((FieldError) error).getField();
            // 해당 필드의 유효성 검증 실패 메시지 추출
            String errorMessage = error.getDefaultMessage();
            // Map에 필드명을 키로, 오류 메시지를 값으로 저장
            errors.put(fieldName, errorMessage);
        });
        
        log.warn("Validation failed: {}", errors);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ErrorResponse.of("입력값이 올바르지 않습니다", errors.toString()));
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUsernameNotFoundException(UsernameNotFoundException ex) {
        log.warn("User not found: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ErrorResponse.of("사용자를 찾을 수 없습니다", ex.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleAllExceptions(Exception ex) {
        log.error("Unexpected error occurred", ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ErrorResponse.of("서버 내부 오류가 발생했습니다", "시스템 관리자에게 문의하세요"));
    }
}
