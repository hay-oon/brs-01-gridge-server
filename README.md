# Gridge Test Server

## 데이터베이스 ERD

![Database ERD](docs/brs-gridge-test-server-erd.png)

## API 문서

- EC2 Server: [EC2 Server](http://3.35.209.40)
- Swagger: [Swagger UI](http://3.35.209.40/swagger-ui/index.html)
- Postman: [Postman Collection](https://.postman.co/workspace/My-Workspace~de85d44d-15c1-450d-87ea-2694673fde04/collection/40062499-a8294960-220f-438f-9912-14e5bc360a29?action=share&creator=40062499&active-environment=40062499-6cf49d97-c33e-4f36-9298-9f87ae1fdae9)
- Notion: [Notion](https://www.notion.so/API-260d9fa672ba80349a3ac37207dd2ffb?source=copy_link)

## (중요) 프로젝트에는 개발 및 테스트를 위한 샘플 데이터가 포함되어 있습니다 !

- **사용자**: 일반 사용자 6명 + 관리자 1명
- **게시글**: 다양한 카테고리의 샘플 게시글
- **댓글**: 게시글별 댓글 데이터
- **팔로우 관계**: 사용자 간 팔로우 관계
- **결제 데이터**: 구독 및 결제 이력
- **기본 계정 정보**:
  - 일반 사용자: `user1` ~ `user6` (비밀번호: `password`)
  - 관리자: `admin` (비밀번호: `password`)

## 프로젝트 구조

```
gridge-test-server/
├── src/
│   └── main/
│       ├── java/com/brs/gridge/
│       │   ├── common/                    # 공통 유틸리티
│       │   │   ├── Constants.java         # 상수 정의
│       │   │   ├── ErrorResponse.java     # 에러 응답 DTO
│       │   │   └── ValidationMessages.java # 유효성 검증 메시지
│       │   ├── config/                    # 설정 클래스
│       │   │   ├── JwtProperties.java     # JWT 설정
│       │   │   ├── PortOneProperties.java # PortOne 결제 설정
│       │   │   ├── SecurityConfig.java    # Spring Security 설정
│       │   │   └── SwaggerConfig.java     # API 문서화 설정
│       │   ├── controller/                # REST API 컨트롤러
│       │   │   ├── dto/                   # 요청/응답 DTO
│       │   │   │   ├── ApiResponse.java   # 공통 API 응답
│       │   │   │   ├── CreatePostRequest.java
│       │   │   │   ├── SignupRequest.java
│       │   │   │   └── ... (기타 DTO들)
│       │   │   ├── AuthController.java    # 인증 관련 API
│       │   │   ├── PostController.java    # 게시글 관련 API
│       │   │   ├── UserController.java    # 사용자 관련 API
│       │   │   ├── CommentController.java # 댓글 관련 API
│       │   │   ├── ReportController.java  # 신고 관련 API
│       │   │   ├── SubscriptionController.java # 구독 관련 API
│       │   │   └── LogController.java     # 로그 관련 API
│       │   ├── domain/                    # 도메인 모델
│       │   │   ├── entity/                # JPA 엔티티
│       │   │   │   ├── User.java          # 사용자 엔티티
│       │   │   │   ├── Post.java          # 게시글 엔티티
│       │   │   │   ├── Comment.java       # 댓글 엔티티
│       │   │   │   ├── PaymentHistory.java # 결제 이력 엔티티
│       │   │   │   └── ... (기타 엔티티들)
│       │   │   └── vo/                    # Value Object
│       │   │       ├── UserStatus.java    # 사용자 상태
│       │   │       ├── PostStatus.java    # 게시글 상태
│       │   │       └── ... (기타 VO들)
│       │   ├── repository/                # 데이터 접근 계층
│       │   │   ├── UserRepository.java    # 사용자 리포지토리
│       │   │   ├── PostRepository.java    # 게시글 리포지토리
│       │   │   └── ... (기타 리포지토리들)
│       │   ├── security/                  # 보안 관련
│       │   │   ├── JwtTokenProvider.java  # JWT 토큰 관리
│       │   │   └── JwtAuthenticationFilter.java # JWT 인증 필터
│       │   ├── service/                   # 비즈니스 로직 계층
│       │   │   ├── AuthService.java       # 인증 서비스
│       │   │   ├── PostService.java       # 게시글 서비스
│       │   │   ├── UserService.java       # 사용자 서비스
│       │   │   ├── SubscriptionService.java # 구독 서비스
│       │   │   └── ... (기타 서비스들)
│       │   └── GridgeApplication.java     # 메인 애플리케이션
│       └── resources/
│           ├── application.properties     # 애플리케이션 설정
│           └── test-data.sql             # 테스트 데이터
├── build.gradle                          # Gradle 빌드 설정
├── gradlew                              # Gradle Wrapper (Unix)
├── gradlew.bat                          # Gradle Wrapper (Windows)
└── README.md                            # 프로젝트 문서
```

## 폴더별 기능 정의

### 📁 common/

- **Constants.java**: 애플리케이션 전반에서 사용되는 상수들을 중앙 관리
- **ErrorResponse.java**: API 에러 응답을 위한 공통 DTO
- **ValidationMessages.java**: 유효성 검증 메시지 상수

### 📁 config/

- **SecurityConfig.java**: Spring Security 설정 (JWT, CORS, 인증/인가)
- **JwtProperties.java**: JWT 토큰 관련 설정 프로퍼티
- **PortOneProperties.java**: PortOne 결제 시스템 설정
- **SwaggerConfig.java**: API 문서화 설정

### 📁 controller/

- **AuthController.java**: 회원가입, 로그인, 비밀번호 재설정
  - `POST /api/auth/signup` - 회원가입
  - `POST /api/auth/login` - 로그인
  - `POST /api/auth/logout` - 로그아웃
  - `PATCH /api/auth/password` - 비밀번호 재설정
- **PostController.java**: 게시글 CRUD, 좋아요, 북마크, 신고
  - `POST /api/post` - 게시글 작성
  - `GET /api/posts` - 팔로우 게시물 조회 (페이지네이션)
  - `GET /api/my-posts` - 내가 작성한 게시물 조회
  - `PATCH /api/post/{postId}` - 게시글 수정
  - `DELETE /api/post/{postId}` - 게시글 삭제
  - `POST /api/post/{postId}/like` - 게시글 좋아요/취소
  - `POST /api/post/{postId}/bookmark` - 게시글 북마크/취소
  - `POST /api/post/{postId}/report` - 게시글 신고
- **UserController.java**: 사용자 관리 (관리자용)
  - `GET /api/admin/users` - 회원 목록 조회
  - `GET /api/admin/user/{userId}` - 회원 상세 조회
  - `PATCH /api/admin/user/{userId}/status` - 회원 상태 변경
- **CommentController.java**: 댓글 CRUD
  - `POST /api/post/{postId}/comment` - 댓글 작성
  - `GET /api/post/{postId}/comments` - 댓글 목록 조회
  - `PATCH /api/post/{postId}/comment/{commentId}` - 댓글 수정
  - `DELETE /api/post/{postId}/comment/{commentId}` - 댓글 삭제
- **SubscriptionController.java**: 구독 관리, 결제 처리
  - `POST /api/subscription/create` - 구독 결제 생성
  - `GET /api/admin/subscriptions` - 전체 구독 내역 조회 (관리자용)
- **ReportController.java**: 신고 관리 (관리자용)
  - `GET /api/admin/reports` - 신고 목록 조회
  - `DELETE /api/admin/report/{reportId}` - 신고 삭제
- **LogController.java**: 시스템 로그 조회 (관리자용)
  - `GET /api/admin/logs` - 시스템 로그 조회

### 📁 domain/

- **entity/**: JPA 엔티티 (데이터베이스 테이블과 매핑)
  - `User.java` - 사용자 정보
  - `Post.java` - 게시글 정보
  - `Comment.java` - 댓글 정보
  - `Attachment.java` - 첨부파일 정보
  - `PostLike.java` - 게시글 좋아요
  - `PostBookmark.java` - 게시글 북마크
  - `Follow.java` - 팔로우 관계
  - `Report.java` - 신고 정보
  - `PaymentHistory.java` - 결제 이력
  - `SubscriptionHistory.java` - 구독 이력
  - `Message.java` - 메시지 정보
  - `Log.java` - 시스템 로그
  - `SocialAccount.java` - 소셜 계정 연동
  - `PrivacyAgreement.java` - 개인정보 처리방침 동의
- **vo/**: Value Object (상태, 타입 등)
  - `UserStatus.java` - 사용자 상태 (ACTIVE, INACTIVE, BLOCKED)
  - `UserRole.java` - 사용자 역할 (USER, ADMIN)
  - `LoginType.java` - 로그인 타입 (LOCAL, SOCIAL)
  - `PostStatus.java` - 게시글 상태 (VISIBLE, HIDDEN, DELETED)
  - `CommentStatus.java` - 댓글 상태 (ACTIVE, DELETED)
  - `PaymentStatus.java` - 결제 상태 (SUCCESS, FAIL, PENDING, CANCELED)
  - `Provider.java` - 소셜 로그인 제공자 (KAKAO, GOOGLE, NAVER)
  - `FileType.java` - 파일 타입 (IMAGE, VIDEO)

### 📁 repository/

- Spring Data JPA 리포지토리 인터페이스
- 데이터베이스 쿼리 메서드 정의

### 📁 security/

- JWT 토큰 생성/검증 로직
- 인증 필터 구현

### 📁 service/

- 비즈니스 로직 구현
- 트랜잭션 관리
- 외부 API 연동

## 환경 설정

### 주요 기술 스택

- **언어**: Java 17
- **프레임워크**: Spring Boot 3.5.5
- **데이터베이스**: MySQL 8.0
- **빌드 도구**: Gradle 8.0
- **보안**: Spring Security + JWT
- **ORM**: Spring Data JPA
- **API 문서화**: SpringDoc OpenAPI (Swagger)
- **결제**: PortOne API
- **컨테이너**: Docker & Docker Compose

## 실행 방법

### 1. 사전 요구사항

- Java 17 이상
- MySQL 8.0 이상 (또는 Docker)
- Gradle 8.0 이상

### 2. 데이터베이스 설정

#### Docker를 사용하여 설정하길 권장

```bash
# Docker Compose로 MySQL 컨테이너 시작
docker-compose up -d mysql

# 데이터베이스 생성 확인
docker exec -it gridge-mysql mysql -u admin -p1234 -e "SHOW DATABASES;"
```

### 3. 애플리케이션 설정

#### application.properties 설정

프로젝트의 `src/main/resources/application.properties` 파일 설정을 확인

### 4. 애플리케이션 실행

#### Gradle을 사용한 실행

```bash
# 프로젝트 루트 디렉토리에서
./gradlew bootRun
```

#### JAR 파일 빌드 및 실행

```bash
# JAR 파일 빌드
./gradlew bootJar

# 빌드된 JAR 파일 실행
java -jar build/libs/gridge-0.0.1-SNAPSHOT.jar
```

#### Docker를 사용한 전체 스택 실행

```bash
# 전체 애플리케이션 스택 실행 (MySQL + Spring Boot App)
docker-compose up -d

# 로그 확인
docker-compose logs -f app
```

### 5. 초기 데이터 설정

애플리케이션 첫 실행 시 테스트 데이터를 로드하려면

1. `application.properties`에서 `spring.sql.init.mode=always`로 설정
2. 애플리케이션 실행
3. 테스트 데이터 로드 완료 후 `spring.sql.init.mode=never`로 변경

## 주요 기능

### 인증/인가

- **JWT 기반 인증**: Access Token (30분) + Refresh Token (7일)
- **역할 기반 접근 제어**: USER, ADMIN 권한 분리
- **소셜 로그인 지원**: 카카오, 구글, 네이버 연동 준비
- **비밀번호 재설정**: 인증된 사용자의 비밀번호 변경

### 게시글 관리

- **게시글 CRUD**: 작성, 조회, 수정, 삭제
- **미디어 첨부**: 이미지/동영상 파일 업로드 지원
- **상호작용 기능**: 좋아요, 북마크, 댓글
- **팔로우 시스템**: 팔로우한 사용자의 게시글 피드 제공
- **신고 기능**: 부적절한 게시글 신고 시스템
- **페이지네이션**: 효율적인 대용량 데이터 처리

### 댓글 시스템

- **댓글 CRUD**: 댓글 작성, 조회, 수정, 삭제
- **실시간 상호작용**: 게시글별 댓글 목록 제공
- **상태 관리**: 활성/삭제 상태 관리

### 구독 시스템

- **월 구독 서비스**: 9,900원 정기 결제
- **PortOne 결제 연동**: 다양한 결제 수단 지원
  - 신용카드, 카카오페이, 토스페이, 네이버페이
- **구독 상태 관리**: 활성, 만료, 취소 상태 추적
- **결제 이력 관리**: 모든 결제 내역 기록 및 조회

### 소셜 기능

- **팔로우/팔로잉**: 사용자 간 관계 설정
- **메시지 시스템**: 사용자 간 1:1 메시지 기능
- **프로필 관리**: 사용자 프로필 이미지 및 정보 관리

### 관리자 기능

- **사용자 관리**: 회원 목록 조회, 상세 정보, 상태 변경
- **게시글/댓글 관리**: 전체 콘텐츠 모니터링 및 관리
- **신고 처리**: 신고된 게시글 검토 및 처리
- **시스템 로그 조회**: 모든 사용자 활동 로그 추적
- **구독 관리**: 전체 구독 현황 및 결제 내역 조회

### 시스템 모니터링

- **활동 로그**: 사용자별 모든 활동 기록
- **에러 추적**: 시스템 오류 및 예외 상황 로깅
- **성능 모니터링**: API 응답 시간 및 시스템 성능 추적

### 주요 API 엔드포인트

#### 인증 관련 (`/api/auth`)

- 회원가입, 로그인, 로그아웃, 비밀번호 재설정

#### 게시글 관련 (`/api`)

- 게시글 CRUD, 좋아요, 북마크, 신고 기능

#### 댓글 관련 (`/api/post/{postId}`)

- 게시글별 댓글 CRUD 기능

#### 구독 관련 (`/api/subscription`)

- 구독 결제 생성 및 관리

#### 관리자 관련 (`/api/admin`)

- 사용자, 게시글, 신고, 로그 관리

## 라이선스

이 프로젝트는 MIT 라이선스 하에 배포됩니다. 자세한 내용은 `LICENSE` 파일을 참조하세요.
