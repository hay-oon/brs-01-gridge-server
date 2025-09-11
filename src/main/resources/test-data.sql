-- 사용자 데이터
-- 비밀번호: password
INSERT INTO users (username, password, birthday, phone, email, profile_image_url, status, role, login_type, created_at, updated_at, last_login_at) VALUES
-- 일반 사용자들
('user1', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', '1990-01-01', '010-1111-1111', 'user1@example.com', 'https://example.com/profile1.jpg', 'ACTIVE', 'USER', 'LOCAL', NOW() - INTERVAL 30 DAY, NOW() - INTERVAL 1 DAY, NOW() - INTERVAL 1 HOUR),
('user2', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', '1992-05-15', '010-2222-2222', 'user2@example.com', 'https://example.com/profile2.jpg', 'ACTIVE', 'USER', 'LOCAL', NOW() - INTERVAL 25 DAY, NOW() - INTERVAL 2 HOUR, NOW() - INTERVAL 30 MINUTE),
('user3', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', '1988-12-20', '010-3333-3333', 'user3@example.com', 'https://example.com/profile3.jpg', 'ACTIVE', 'USER', 'LOCAL', NOW() - INTERVAL 20 DAY, NOW() - INTERVAL 1 HOUR, NOW() - INTERVAL 2 HOUR),
('user4', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', '1995-03-10', '010-4444-4444', 'user4@example.com', 'https://example.com/profile4.jpg', 'ACTIVE', 'USER', 'LOCAL', NOW() - INTERVAL 15 DAY, NOW() - INTERVAL 3 HOUR, NOW() - INTERVAL 1 DAY),
('user5', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', '1993-07-22', '010-5555-5555', 'user5@example.com', 'https://example.com/profile5.jpg', 'INACTIVE', 'USER', 'LOCAL', NOW() - INTERVAL 10 DAY, NOW() - INTERVAL 5 DAY, NOW() - INTERVAL 5 DAY),
('user6', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', '1991-11-08', '010-6666-6666', 'user6@example.com', 'https://example.com/profile6.jpg', 'BLOCKED', 'USER', 'LOCAL', NOW() - INTERVAL 8 DAY, NOW() - INTERVAL 1 DAY, NOW() - INTERVAL 2 DAY),
-- 관리자
('admin', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', '1985-04-15', '010-0000-0000', 'admin@gridge.com', 'https://example.com/admin.jpg', 'ACTIVE', 'ADMIN', 'LOCAL', NOW() - INTERVAL 60 DAY, NOW() - INTERVAL 1 HOUR, NOW() - INTERVAL 10 MINUTE);

-- 소셜 계정 데이터
INSERT INTO social_account (user_id, provider, provider_user_id, connected_at) VALUES
(1, 'KAKAO', 'kakao_12345', NOW() - INTERVAL 25 DAY),
(2, 'GOOGLE', 'google_67890', NOW() - INTERVAL 20 DAY),
(3, 'NAVER', 'naver_11111', NOW() - INTERVAL 15 DAY),
(4, 'KAKAO', 'kakao_22222', NOW() - INTERVAL 10 DAY),
(5, 'GOOGLE', 'google_33333', NOW() - INTERVAL 8 DAY);

-- 약관 동의 데이터
INSERT INTO privacy_agreements (user_id, version, agreed_at, expired_at) VALUES
(1, '1.0', NOW() - INTERVAL 30 DAY, NOW() + INTERVAL 335 DAY),
(2, '1.0', NOW() - INTERVAL 25 DAY, NOW() + INTERVAL 340 DAY),
(3, '1.0', NOW() - INTERVAL 20 DAY, NOW() + INTERVAL 345 DAY),
(4, '1.0', NOW() - INTERVAL 15 DAY, NOW() + INTERVAL 350 DAY),
(5, '1.0', NOW() - INTERVAL 10 DAY, NOW() + INTERVAL 355 DAY),
(6, '1.0', NOW() - INTERVAL 8 DAY, NOW() + INTERVAL 357 DAY),
(7, '1.0', NOW() - INTERVAL 60 DAY, NOW() + INTERVAL 305 DAY);

-- Follow 관계 생성
INSERT INTO follows (follower_id, followee_id, created_at) VALUES
-- user1이 user2, user3을 팔로우
(1, 2, NOW() - INTERVAL 20 DAY),
(1, 3, NOW() - INTERVAL 18 DAY),
-- user2가 user1, user4를 팔로우
(2, 1, NOW() - INTERVAL 15 DAY),
(2, 4, NOW() - INTERVAL 12 DAY),
-- user3이 user1, user2, user4를 팔로우
(3, 1, NOW() - INTERVAL 10 DAY),
(3, 2, NOW() - INTERVAL 8 DAY),
(3, 4, NOW() - INTERVAL 5 DAY),
-- user4가 user1, user2, user3을 팔로우
(4, 1, NOW() - INTERVAL 12 DAY),
(4, 2, NOW() - INTERVAL 10 DAY),
(4, 3, NOW() - INTERVAL 8 DAY),
-- user5가 user1, user2를 팔로우
(5, 1, NOW() - INTERVAL 5 DAY),
(5, 2, NOW() - INTERVAL 3 DAY);

-- 테스트용 게시물 데이터
INSERT INTO posts (user_id, content, status, place_name, like_count, created_at, updated_at) VALUES
-- user1의 게시물들
(1, '오늘 날씨가 정말 좋네요! 산책하기 딱 좋은 날씨입니다. 한강공원에서 산책하며 힐링하는 시간을 가졌어요.', 'VISIBLE', '한강공원', 15, NOW() - INTERVAL 2 HOUR, NOW() - INTERVAL 2 HOUR),
(1, '맛있는 파스타를 먹었어요. 레시피를 공유하고 싶습니다. 크림소스가 정말 일품이었어요!', 'VISIBLE', '이탈리안 레스토랑', 22, NOW() - INTERVAL 1 DAY, NOW() - INTERVAL 1 DAY),
(1, '새로운 취미를 시작했어요. 요가를 배우고 있는데 몸이 많이 유연해졌어요!', 'VISIBLE', '요가 스튜디오', 8, NOW() - INTERVAL 3 DAY, NOW() - INTERVAL 3 DAY),
-- user2의 게시물들
(2, '새로운 카페를 발견했어요. 분위기가 정말 좋습니다. 커피맛도 최고예요!', 'VISIBLE', '카페 모모', 18, NOW() - INTERVAL 3 HOUR, NOW() - INTERVAL 3 HOUR),
(2, '운동을 시작한지 1주일이 되었어요. 꾸준히 해보겠습니다! 헬스장에서 열심히 운동 중!', 'VISIBLE', '피트니스 센터', 25, NOW() - INTERVAL 2 DAY, NOW() - INTERVAL 2 DAY),
(2, '영화를 봤는데 정말 재미있었어요. 추천하고 싶습니다!', 'VISIBLE', 'CGV 강남', 12, NOW() - INTERVAL 4 DAY, NOW() - INTERVAL 4 DAY),
-- user3의 게시물들
(3, '책을 읽고 있는데 정말 재미있어요. 추천하고 싶습니다. 이 책은 정말 감동적이에요!', 'VISIBLE', '집', 6, NOW() - INTERVAL 5 HOUR, NOW() - INTERVAL 5 HOUR),
(3, '여행 계획을 세우고 있어요. 어디로 갈까요? 제주도 여행을 계획 중이에요!', 'VISIBLE', '카페', 14, NOW() - INTERVAL 1 DAY, NOW() - INTERVAL 1 DAY),
(3, '요리 실력이 늘고 있어요. 오늘은 파스타를 만들어봤어요!', 'VISIBLE', '집', 9, NOW() - INTERVAL 6 DAY, NOW() - INTERVAL 6 DAY),
-- user4의 게시물들
(4, '새로운 직장에 다니기 시작했어요. 정말 기대돼요!', 'VISIBLE', '회사', 20, NOW() - INTERVAL 1 HOUR, NOW() - INTERVAL 1 HOUR),
(4, '친구들과 만나서 즐거운 시간을 보냈어요. 오랜만에 만난 친구들과의 시간이 정말 좋았어요!', 'VISIBLE', '맛집', 16, NOW() - INTERVAL 1 DAY, NOW() - INTERVAL 1 DAY),
-- user5의 게시물들 (비활성 사용자)
(5, '오늘은 집에서 휴식을 취했어요. 가끔은 이렇게 조용한 시간이 필요해요.', 'VISIBLE', '집', 3, NOW() - INTERVAL 5 DAY, NOW() - INTERVAL 5 DAY),
-- 삭제된 게시물
(1, '이 게시물은 삭제되었습니다.', 'DELETED', '테스트 장소', 0, NOW() - INTERVAL 7 DAY, NOW() - INTERVAL 7 DAY);

-- 테스트용 첨부파일 데이터
INSERT INTO attachments (post_id, file_type, file_url) VALUES
-- user1의 게시물 첨부파일
(1, 'IMAGE', 'https://example.com/images/weather1.jpg'),
(1, 'IMAGE', 'https://example.com/images/weather2.jpg'),
(2, 'IMAGE', 'https://example.com/images/pasta1.jpg'),
(2, 'VIDEO', 'https://example.com/videos/pasta_cooking.mp4'),
(3, 'IMAGE', 'https://example.com/images/yoga1.jpg'),
-- user2의 게시물 첨부파일
(4, 'IMAGE', 'https://example.com/images/cafe1.jpg'),
(4, 'IMAGE', 'https://example.com/images/cafe2.jpg'),
(5, 'IMAGE', 'https://example.com/images/gym1.jpg'),
(5, 'VIDEO', 'https://example.com/videos/workout.mp4'),
(6, 'IMAGE', 'https://example.com/images/movie1.jpg'),
-- user3의 게시물 첨부파일
(7, 'IMAGE', 'https://example.com/images/book1.jpg'),
(8, 'IMAGE', 'https://example.com/images/travel1.jpg'),
(8, 'IMAGE', 'https://example.com/images/travel2.jpg'),
(9, 'IMAGE', 'https://example.com/images/cooking1.jpg'),
-- user4의 게시물 첨부파일
(10, 'IMAGE', 'https://example.com/images/office1.jpg'),
(11, 'IMAGE', 'https://example.com/images/friends1.jpg'),
(11, 'IMAGE', 'https://example.com/images/friends2.jpg'),
-- user5의 게시물 첨부파일
(12, 'IMAGE', 'https://example.com/images/relax1.jpg');

-- 게시물 좋아요 데이터
INSERT INTO post_likes (user_id, post_id, created_at) VALUES
-- post 1 (user1의 한강공원 게시물) 좋아요
(2, 1, NOW() - INTERVAL 1 HOUR),
(3, 1, NOW() - INTERVAL 30 MINUTE),
(4, 1, NOW() - INTERVAL 15 MINUTE),
-- post 2 (user1의 파스타 게시물) 좋아요
(2, 2, NOW() - INTERVAL 20 HOUR),
(3, 2, NOW() - INTERVAL 18 HOUR),
(4, 2, NOW() - INTERVAL 12 HOUR),
(5, 2, NOW() - INTERVAL 8 HOUR),
-- post 4 (user2의 카페 게시물) 좋아요
(1, 4, NOW() - INTERVAL 2 HOUR),
(3, 4, NOW() - INTERVAL 1 HOUR),
(4, 4, NOW() - INTERVAL 30 MINUTE),
-- post 5 (user2의 운동 게시물) 좋아요
(1, 5, NOW() - INTERVAL 1 DAY),
(3, 5, NOW() - INTERVAL 20 HOUR),
(4, 5, NOW() - INTERVAL 15 HOUR),
(5, 5, NOW() - INTERVAL 10 HOUR),
-- post 7 (user3의 책 게시물) 좋아요
(1, 7, NOW() - INTERVAL 4 HOUR),
(2, 7, NOW() - INTERVAL 3 HOUR),
-- post 8 (user3의 여행 게시물) 좋아요
(1, 8, NOW() - INTERVAL 20 HOUR),
(2, 8, NOW() - INTERVAL 18 HOUR),
(4, 8, NOW() - INTERVAL 12 HOUR);

-- 게시물 북마크 데이터
INSERT INTO post_bookmarks (user_id, post_id, created_at) VALUES
-- user1이 북마크한 게시물들
(1, 4, NOW() - INTERVAL 2 HOUR),  -- user2의 카페 게시물
(1, 5, NOW() - INTERVAL 1 DAY),   -- user2의 운동 게시물
(1, 8, NOW() - INTERVAL 1 DAY),   -- user3의 여행 게시물
-- user2가 북마크한 게시물들
(2, 1, NOW() - INTERVAL 1 HOUR),  -- user1의 한강공원 게시물
(2, 7, NOW() - INTERVAL 3 HOUR),  -- user3의 책 게시물
-- user3이 북마크한 게시물들
(3, 2, NOW() - INTERVAL 18 HOUR), -- user1의 파스타 게시물
(3, 5, NOW() - INTERVAL 15 HOUR), -- user2의 운동 게시물
-- user4가 북마크한 게시물들
(4, 1, NOW() - INTERVAL 15 MINUTE), -- user1의 한강공원 게시물
(4, 4, NOW() - INTERVAL 30 MINUTE), -- user2의 카페 게시물
(4, 8, NOW() - INTERVAL 12 HOUR);   -- user3의 여행 게시물

-- 댓글 데이터
INSERT INTO comments (post_id, user_id, content, status, created_at, updated_at) VALUES
-- post 1 (user1의 한강공원 게시물) 댓글들
(1, 2, '날씨가 정말 좋네요! 저도 한강공원 가고 싶어요!', 'ACTIVE', NOW() - INTERVAL 1 HOUR, NOW() - INTERVAL 1 HOUR),
(1, 3, '산책하기 딱 좋은 날씨네요. 저도 나가봐야겠어요!', 'ACTIVE', NOW() - INTERVAL 30 MINUTE, NOW() - INTERVAL 30 MINUTE),
(1, 4, '한강공원 정말 좋죠! 저도 자주 가는 곳이에요.', 'ACTIVE', NOW() - INTERVAL 15 MINUTE, NOW() - INTERVAL 15 MINUTE),
-- post 2 (user1의 파스타 게시물) 댓글들
(2, 2, '파스타 레시피 공유해주세요! 정말 맛있어 보여요.', 'ACTIVE', NOW() - INTERVAL 20 HOUR, NOW() - INTERVAL 20 HOUR),
(2, 3, '크림소스 파스타 정말 좋아해요!', 'ACTIVE', NOW() - INTERVAL 18 HOUR, NOW() - INTERVAL 18 HOUR),
(2, 4, '저도 파스타 만들어봐야겠어요!', 'ACTIVE', NOW() - INTERVAL 12 HOUR, NOW() - INTERVAL 12 HOUR),
-- post 4 (user2의 카페 게시물) 댓글들
(4, 1, '카페 분위기 정말 좋네요! 주소 알려주세요!', 'ACTIVE', NOW() - INTERVAL 2 HOUR, NOW() - INTERVAL 2 HOUR),
(4, 3, '커피맛이 어떤가요?', 'ACTIVE', NOW() - INTERVAL 1 HOUR, NOW() - INTERVAL 1 HOUR),
-- post 5 (user2의 운동 게시물) 댓글들
(5, 1, '운동 화이팅! 저도 시작해봐야겠어요.', 'ACTIVE', NOW() - INTERVAL 1 DAY, NOW() - INTERVAL 1 DAY),
(5, 3, '헬스장 어디인가요?', 'ACTIVE', NOW() - INTERVAL 20 HOUR, NOW() - INTERVAL 20 HOUR),
(5, 4, '운동 꾸준히 하시는 모습 멋져요!', 'ACTIVE', NOW() - INTERVAL 15 HOUR, NOW() - INTERVAL 15 HOUR),
-- post 7 (user3의 책 게시물) 댓글들
(7, 1, '어떤 책인가요? 추천해주세요!', 'ACTIVE', NOW() - INTERVAL 4 HOUR, NOW() - INTERVAL 4 HOUR),
(7, 2, '저도 독서 좋아해요!', 'ACTIVE', NOW() - INTERVAL 3 HOUR, NOW() - INTERVAL 3 HOUR),
-- post 8 (user3의 여행 게시물) 댓글들
(8, 1, '제주도 정말 좋아요! 추천 장소 알려드릴게요!', 'ACTIVE', NOW() - INTERVAL 20 HOUR, NOW() - INTERVAL 20 HOUR),
(8, 2, '여행 계획 세우는 것도 재미있죠!', 'ACTIVE', NOW() - INTERVAL 18 HOUR, NOW() - INTERVAL 18 HOUR),
(8, 4, '저도 제주도 가고 싶어요!', 'ACTIVE', NOW() - INTERVAL 12 HOUR, NOW() - INTERVAL 12 HOUR),
-- 삭제된 댓글
(1, 5, '이 댓글은 삭제되었습니다.', 'DELETED', NOW() - INTERVAL 1 HOUR, NOW() - INTERVAL 30 MINUTE);

-- 신고 데이터
INSERT INTO reports (post_id, user_id, reason, created_at) VALUES
(1, 6, '부적절한 내용', NOW() - INTERVAL 1 DAY),
(2, 6, '스팸성 게시물', NOW() - INTERVAL 2 DAY),
(4, 6, '허위 정보', NOW() - INTERVAL 3 DAY);

-- 결제 히스토리 데이터
INSERT INTO payment_histories (user_id, amount, status, method, portone_payment_id, portone_order_id, created_at) VALUES
(1, 9900, 'SUCCESS', 'CARD', 'portone_pay_001', 'order_001', NOW() - INTERVAL 10 DAY),
(1, 9900, 'SUCCESS', 'KAKAO', 'portone_pay_002', 'order_002', NOW() - INTERVAL 5 DAY),
(2, 9900, 'SUCCESS', 'TOSS', 'portone_pay_003', 'order_003', NOW() - INTERVAL 8 DAY),
(2, 9900, 'FAIL', 'CARD', 'portone_pay_004', 'order_004', NOW() - INTERVAL 3 DAY),
(3, 9900, 'SUCCESS', 'NAVER', 'portone_pay_005', 'order_005', NOW() - INTERVAL 6 DAY),
(4, 9900, 'PENDING', 'CARD', 'portone_pay_006', 'order_006', NOW() - INTERVAL 1 HOUR),
(5, 9900, 'CANCELED', 'KAKAO', 'portone_pay_007', 'order_007', NOW() - INTERVAL 2 DAY);

-- 구독 히스토리 데이터
INSERT INTO subscription_histories (user_id, start_date, end_date, status, payment_history_id, reason, created_at) VALUES
(1, '2024-01-01', '2024-02-01', 'EXPIRED', 1, '정기결제', NOW() - INTERVAL 10 DAY),
(1, '2024-02-01', '2024-03-01', 'ACTIVE', 2, '정기결제', NOW() - INTERVAL 5 DAY),
(2, '2024-01-15', '2024-02-15', 'EXPIRED', 3, '정기결제', NOW() - INTERVAL 8 DAY),
(3, '2024-01-20', '2024-02-20', 'ACTIVE', 5, '정기결제', NOW() - INTERVAL 6 DAY),
(5, '2024-01-25', '2024-02-25', 'CANCELED', 7, '취소', NOW() - INTERVAL 2 DAY);

-- 메시지 데이터
INSERT INTO messages (sender_id, receiver_id, content, read_at, created_at) VALUES
(1, 2, '안녕하세요! 카페 추천해주셔서 감사해요!', NOW() - INTERVAL 1 HOUR, NOW() - INTERVAL 2 HOUR),
(2, 1, '천만에요! 맛있게 드세요!', NOW() - INTERVAL 30 MINUTE, NOW() - INTERVAL 1 HOUR),
(1, 3, '책 추천해주신 거 정말 좋아요!', NULL, NOW() - INTERVAL 3 HOUR),
(3, 1, '다행이에요! 다른 책도 추천해드릴게요!', NOW() - INTERVAL 2 HOUR, NOW() - INTERVAL 2 HOUR),
(2, 4, '운동 같이 해요!', NOW() - INTERVAL 1 DAY, NOW() - INTERVAL 1 DAY),
(4, 2, '좋아요! 언제 만날까요?', NOW() - INTERVAL 20 HOUR, NOW() - INTERVAL 20 HOUR),
(3, 4, '여행 계획 어떻게 세우고 계세요?', NULL, NOW() - INTERVAL 12 HOUR);

-- 로그 데이터
INSERT INTO logs (user_id, entity_type, entity_id, action, message, created_at) VALUES
(1, 'POST', 1, 'CREATE', '게시물이 생성되었습니다.', NOW() - INTERVAL 2 HOUR),
(1, 'POST', 1, 'UPDATE', '게시물이 수정되었습니다.', NOW() - INTERVAL 1 HOUR),
(2, 'POST', 4, 'CREATE', '게시물이 생성되었습니다.', NOW() - INTERVAL 3 HOUR),
(3, 'COMMENT', 1, 'CREATE', '댓글이 작성되었습니다.', NOW() - INTERVAL 1 HOUR),
(4, 'LIKE', 1, 'CREATE', '좋아요를 눌렀습니다.', NOW() - INTERVAL 15 MINUTE),
(5, 'BOOKMARK', 1, 'CREATE', '북마크에 추가했습니다.', NOW() - INTERVAL 1 HOUR),
(6, 'REPORT', 1, 'CREATE', '게시물을 신고했습니다.', NOW() - INTERVAL 1 DAY),
(7, 'USER', 6, 'UPDATE', '사용자 상태를 변경했습니다.', NOW() - INTERVAL 1 DAY),
(1, 'PAYMENT', 1, 'CREATE', '결제가 완료되었습니다.', NOW() - INTERVAL 10 DAY),
(1, 'SUBSCRIPTION', 1, 'CREATE', '구독이 시작되었습니다.', NOW() - INTERVAL 10 DAY);
