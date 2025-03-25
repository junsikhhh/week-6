CREATE TABLE members(
  	id BIGINT AUTO_INCREMENT PRIMARY KEY,  -- 사용자 ID (8바이트, 자동 증가)
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    nickname VARCHAR(10) NOT NULL UNIQUE,
    profile_image VARCHAR(255) DEFAULT 'default-image.url'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- CREATE TABLE posts (
--     id BIGINT AUTO_INCREMENT PRIMARY KEY,  -- 게시글 ID (8바이트)
--     user_id BIGINT NOT NULL,  -- FK (회원 ID)
--     title VARCHAR(26) NOT NULL,
--     content LONGTEXT NOT NULL,
--     created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
-- 	image NULL,
--     like_count INT UNSIGNED NOT NULL DEFAULT 0,test_table
--     view_count INT UNSIGNED NOT NULL DEFAULT 0,
--     comment_count INT UNSIGNED NOT NULL DEFAULT 0,
--     FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
-- );

-- CREATE TABLE comments (
--     id BIGINT AUTO_INCREMENT PRIMARY KEY,  -- 댓글 ID
--     post_id BIGINT NOT NULL,
--     user_id BIGINT NOT NULL,
--     content TEXT NOT NULL,
--     created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
--     FOREIGN KEY (post_id) REFERENCES posts(id) ON DELETE CASCADE,
--     FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
-- );

-- CREATE TABLE likes (
--     id BIGINT AUTO_INCREMENT PRIMARY KEY,  -- 좋아요 ID
--     user_id BIGINT NOT NULL,
--     post_id BIGINT NOT NULL,
--     UNIQUE (user_id, post_id),  -- 같은 사용자가 같은 게시글에 한 번만 좋아요 가능
--     FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
--     FOREIGN KEY (post_id) REFERENCES posts(id) ON DELETE CASCADE
-- );
