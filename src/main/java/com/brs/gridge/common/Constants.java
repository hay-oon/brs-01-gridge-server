package com.brs.gridge.common;

public final class Constants {
    
    private Constants() {
        // 인스턴스화 방지
    }
    
    // 페이지네이션 관련
    public static final class Pagination {
        public static final String DEFAULT_PAGE = "1";
        public static final String DEFAULT_SIZE = "10";
        public static final int MIN_PAGE = 1;
        public static final int MIN_SIZE = 1;
        public static final int MAX_SIZE = 100;
    }
    
    // 데이터베이스 컬럼 길이 제한
    public static final class DatabaseLimits {
        public static final int CONTENT_MAX_LENGTH = 2200;
        public static final int PLACE_NAME_MAX_LENGTH = 100;
        public static final int FILE_URL_MAX_LENGTH = 500;
        public static final int USERNAME_MAX_LENGTH = 50;
        public static final int EMAIL_MAX_LENGTH = 100;
        public static final int PHONE_MAX_LENGTH = 20;
    }
    
    // 비즈니스 로직 상수
    public static final class Business {
        public static final long MONTHLY_SUBSCRIPTION_AMOUNT = 9900L;
        public static final int SUBSCRIPTION_MONTHS = 1;
        public static final int PRIVACY_AGREEMENT_VALIDITY_DAYS = 365;
    }
    
    // HTTP 관련
    public static final class Http {
        public static final String[] ALLOWED_METHODS = {"GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS"};
        public static final String[] ALLOWED_HEADERS = {"Authorization", "Cache-Control", "Content-Type"};
        public static final String[] EXPOSED_HEADERS = {"Authorization"};
        public static final long CORS_MAX_AGE = 3600L;
    }
    
    // 파일 관련
    public static final class File {
        public static final int MAX_FILE_SIZE_MB = 10;
        public static final int MAX_ATTACHMENT_COUNT = 10;
        public static final int MIN_ATTACHMENT_COUNT = 1;
        public static final String IMAGE_EXTENSION_PATTERN = ".*\\.(jpg|jpeg|png|gif|bmp|webp|svg)$";
        public static final String VIDEO_EXTENSION_PATTERN = ".*\\.(mp4|avi|mov|wmv|flv|webm|mkv|m4v)$";
        public static final String URL_PATTERN = "^https?://.*";
    }
    
    // 정규식 패턴
    public static final class Regex {
        public static final String EMAIL_PATTERN = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        public static final String PHONE_PATTERN = "^01[0-9]-?[0-9]{3,4}-?[0-9]{4}$";
        public static final String USERNAME_PATTERN = "^[a-zA-Z0-9_]{3,20}$";
    }
    
}
