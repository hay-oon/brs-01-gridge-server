package com.brs.gridge.common;

/**
 * 유효성 검증 메시지를 관리하는 클래스
 */
public final class ValidationMessages {
    
    private ValidationMessages() {
        // 인스턴스화 방지
    }
    
    // ==================== 공통 유효성 검증 메시지 ====================
    public static final String NOT_NULL = "은(는) 필수입니다";
    public static final String NOT_EMPTY = "은(는) 비어있을 수 없습니다";
    public static final String NOT_BLANK = "은(는) 공백일 수 없습니다";
    
    // ==================== 페이지네이션 관련 ====================
    public static final String PAGE_MIN = "page는 1 이상이어야 합니다";
    public static final String SIZE_MIN = "size는 1 이상이어야 합니다";
    public static final String SIZE_MAX = "size는 100 이하여야 합니다";
    
    // ==================== 사용자 관련 ====================
    public static final String USERNAME_PATTERN = "사용자명은 영문, 숫자, 언더스코어만 사용 가능하며 3-20자여야 합니다";
    public static final String USERNAME_DUPLICATE = "이미 존재하는 사용자 이름입니다";
    public static final String USER_NOT_FOUND = "사용자를 찾을 수 없습니다";
    public static final String PASSWORD_LENGTH = "비밀번호는 8자 이상이어야 합니다";
    public static final String EMAIL_PATTERN = "올바른 이메일 형식이 아닙니다";
    public static final String PHONE_PATTERN = "올바른 전화번호 형식이 아닙니다";
    public static final String BIRTHDAY_PATTERN = "올바른 생년월일 형식이 아닙니다";
    
    // ==================== 게시글 관련 ====================
    public static final String POST_NOT_FOUND = "게시글을 찾을 수 없습니다";
    public static final String POST_ACCESS_DENIED = "게시글에 접근할 권한이 없습니다";
    public static final String POST_ALREADY_LIKED = "이미 좋아요를 누른 게시글입니다";
    public static final String POST_NOT_LIKED = "좋아요를 누르지 않은 게시글입니다";
    public static final String POST_ALREADY_BOOKMARKED = "이미 북마크한 게시글입니다";
    public static final String POST_NOT_BOOKMARKED = "북마크하지 않은 게시글입니다";
    public static final String CONTENT_LENGTH = "내용은 2200자를 초과할 수 없습니다";
    public static final String PLACE_NAME_LENGTH = "장소명은 100자를 초과할 수 없습니다";
    
    // ==================== 댓글 관련 ====================
    public static final String COMMENT_NOT_FOUND = "댓글을 찾을 수 없습니다";
    public static final String COMMENT_ACCESS_DENIED = "댓글에 접근할 권한이 없습니다";
    
    // ==================== 신고 관련 ====================
    public static final String REPORT_ALREADY_EXISTS = "이미 신고한 게시글입니다";
    public static final String REPORT_NOT_FOUND = "신고를 찾을 수 없습니다";
    
    // ==================== 결제 관련 ====================
    public static final String PAYMENT_AMOUNT_MISMATCH = "결제 금액이 일치하지 않습니다";
    public static final String PAYMENT_AMOUNT_INVALID = "금액이 올바르지 않습니다";
    public static final String PAYMENT_NOT_FOUND = "결제 정보를 찾을 수 없습니다";
    
    // ==================== 구독 관련 ====================
    public static final String SUBSCRIPTION_ALREADY_EXISTS = "이미 구독 중입니다";
    public static final String SUBSCRIPTION_NOT_FOUND = "구독 정보를 찾을 수 없습니다";
    
    // ==================== 인증 관련 ====================
    public static final String AUTH_INVALID_CREDENTIALS = "인증 정보가 올바르지 않습니다";
    public static final String AUTH_TOKEN_EXPIRED = "토큰이 만료되었습니다";
    public static final String AUTH_TOKEN_INVALID = "유효하지 않은 토큰입니다";
    public static final String AUTH_ACCESS_DENIED = "접근 권한이 없습니다";
    
    // ==================== 개인정보 관련 ====================
    public static final String PRIVACY_AGREEMENT_REQUIRED = "개인정보 처리방침 동의는 필수입니다";
}
