-- 테스트용 사용자 데이터
INSERT INTO users (username, password, birthday, phone, email, profile_image_url, status, role, login_type, created_at, updated_at) VALUES
('user1', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iKyVhUx0U4pAO0ZZH6J4oQz8QK8K', '1990-01-01', '010-1111-1111', 'user1@example.com', 'https://example.com/profile1.jpg', 'ACTIVE', 'USER', 'LOCAL', NOW(), NOW()),
('user2', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iKyVhUx0U4pAO0ZZH6J4oQz8QK8K', '1992-05-15', '010-2222-2222', 'user2@example.com', 'https://example.com/profile2.jpg', 'ACTIVE', 'USER', 'LOCAL', NOW(), NOW()),
('user3', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iKyVhUx0U4pAO0ZZH6J4oQz8QK8K', '1988-12-20', '010-3333-3333', 'user3@example.com', 'https://example.com/profile3.jpg', 'ACTIVE', 'USER', 'LOCAL', NOW(), NOW()),
-- ('testuser', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iKyVhUx0U4pAO0ZZH6J4oQz8QK8K', '1995-03-10', '010-4444-4444', 'testuser@example.com', 'https://example.com/testuser.jpg', 'ACTIVE', 'USER', 'LOCAL', NOW(), NOW());

-- Follow 관계 생성 (testuser가 user1, user2, user3을 follow)
INSERT INTO follows (follower_id, followee_id, created_at) VALUES
(4, 1, NOW()), -- testuser follows user1
(4, 2, NOW()), -- testuser follows user2
(4, 3, NOW()); -- testuser follows user3

-- 테스트용 게시물 데이터
INSERT INTO posts (user_id, content, status, place_name, like_count, created_at, updated_at) VALUES
(1, '오늘 날씨가 정말 좋네요! 산책하기 딱 좋은 날씨입니다.', 'VISIBLE', '한강공원', 5, NOW() - INTERVAL 2 HOUR, NOW() - INTERVAL 2 HOUR),
(1, '맛있는 파스타를 먹었어요. 레시피를 공유하고 싶습니다.', 'VISIBLE', '이탈리안 레스토랑', 12, NOW() - INTERVAL 1 DAY, NOW() - INTERVAL 1 DAY),
(2, '새로운 카페를 발견했어요. 분위기가 정말 좋습니다.', 'VISIBLE', '카페 모모', 8, NOW() - INTERVAL 3 HOUR, NOW() - INTERVAL 3 HOUR),
(2, '운동을 시작한지 1주일이 되었어요. 꾸준히 해보겠습니다!', 'VISIBLE', '피트니스 센터', 15, NOW() - INTERVAL 2 DAY, NOW() - INTERVAL 2 DAY),
(3, '책을 읽고 있는데 정말 재미있어요. 추천하고 싶습니다.', 'VISIBLE', '집', 3, NOW() - INTERVAL 5 HOUR, NOW() - INTERVAL 5 HOUR),
(3, '여행 계획을 세우고 있어요. 어디로 갈까요?', 'VISIBLE', '카페', 7, NOW() - INTERVAL 1 DAY, NOW() - INTERVAL 1 DAY);

-- 테스트용 첨부파일 데이터
INSERT INTO attachments (post_id, file_type, file_url) VALUES
(1, 'IMAGE', 'https://example.com/images/weather1.jpg'),
(1, 'IMAGE', 'https://example.com/images/weather2.jpg'),
(2, 'IMAGE', 'https://example.com/images/pasta1.jpg'),
(3, 'IMAGE', 'https://example.com/images/cafe1.jpg'),
(4, 'IMAGE', 'https://example.com/images/gym1.jpg'),
(5, 'IMAGE', 'https://example.com/images/book1.jpg'),
(6, 'IMAGE', 'https://example.com/images/travel1.jpg');

-- 약관 동의 데이터 (사용자 생성시 자동으로 생성되어야 하지만 테스트용으로 추가)
INSERT INTO privacy_agreements (user_id, version, agreed_at) VALUES
(1, '1.0', NOW()),
(2, '1.0', NOW()),
(3, '1.0', NOW()),
(4, '1.0', NOW());
