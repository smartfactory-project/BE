-- MES 시스템 데이터베이스 스키마
-- Manufacturing Execution System

-- 데이터베이스 생성
CREATE DATABASE IF NOT EXISTS mes_system CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

USE mes_system;

-- 사용자 테이블 (인증 시스템용)
CREATE TABLE IF NOT EXISTS users (
    user_id INT AUTO_INCREMENT PRIMARY KEY COMMENT '사용자 고유 ID',
    username VARCHAR(50) NOT NULL UNIQUE COMMENT '사용자명',
    email VARCHAR(100) NOT NULL UNIQUE COMMENT '이메일',
    password VARCHAR(255) NOT NULL COMMENT '암호화된 비밀번호',
    role ENUM('USER', 'ADMIN', 'OPERATOR', 'MANAGER') NOT NULL DEFAULT 'USER' COMMENT '사용자 역할',
    department VARCHAR(100) COMMENT '부서',
    is_active BOOLEAN NOT NULL DEFAULT TRUE COMMENT '계정 활성화 상태',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '가입일시',
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '수정일시',
    INDEX idx_username (username),
    INDEX idx_email (email)
) COMMENT '사용자 테이블';

-- 알림 테이블
CREATE TABLE IF NOT EXISTS notification (
    notification_id INT AUTO_INCREMENT PRIMARY KEY COMMENT '알림 ID',
    user_id INT NOT NULL COMMENT '사용자 ID',
    title VARCHAR(200) NOT NULL COMMENT '알림 제목',
    content TEXT COMMENT '알림 내용',
    is_read BOOLEAN NOT NULL DEFAULT FALSE COMMENT '읽음 여부',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '생성일시',
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '수정일시',
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE,
    INDEX idx_user_created (user_id, created_at),
    INDEX idx_is_read (is_read)
) COMMENT '알림 테이블';

-- 게시글 테이블
CREATE TABLE IF NOT EXISTS post (
    post_id     INT AUTO_INCREMENT PRIMARY KEY COMMENT '게시글 ID',
    user_id     INT NOT NULL COMMENT '작성자 ID',
    title       VARCHAR(200) NOT NULL COMMENT '제목',
    content     TEXT COMMENT '내용',
    category    VARCHAR(50) NOT NULL DEFAULT 'discussion' COMMENT '카테고리',
    view_count  INT NOT NULL DEFAULT 0 COMMENT '조회수',
    created_at  TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '작성일시',
    updated_at  TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '수정일시',
    is_active   BOOLEAN NOT NULL DEFAULT TRUE COMMENT '활성 상태',
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE,
    INDEX idx_created_at (created_at),
    INDEX idx_user_id (user_id)
) COMMENT='게시글 테이블';

CREATE TABLE IF NOT EXISTS post_tag (
    post_id INT NOT NULL,
    tag     VARCHAR(50) NOT NULL,
    PRIMARY KEY (post_id, tag),
    FOREIGN KEY (post_id) REFERENCES post(post_id) ON DELETE CASCADE,
    INDEX idx_tag (tag)
) COMMENT='게시글 태그 테이블';

-- 댓글 테이블
CREATE TABLE IF NOT EXISTS comment (
    comment_id INT AUTO_INCREMENT PRIMARY KEY COMMENT '댓글 ID',
    post_id INT NOT NULL COMMENT '게시글 ID',
    user_id INT NOT NULL COMMENT '작성자 ID',
    parent_comment_id INT COMMENT '부모 댓글 ID (대댓글용)',
    content TEXT NOT NULL COMMENT '댓글 내용',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '작성일시',
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '수정일시',
    is_active BOOLEAN NOT NULL DEFAULT TRUE COMMENT '활성 상태',
    FOREIGN KEY (post_id) REFERENCES post(post_id) ON DELETE CASCADE,
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE,
    FOREIGN KEY (parent_comment_id) REFERENCES comment(comment_id) ON DELETE CASCADE,
    INDEX idx_post_id (post_id),
    INDEX idx_user_id (user_id),
    INDEX idx_parent_comment (parent_comment_id)
) COMMENT '댓글 테이블';

-- 좋아요 테이블
CREATE TABLE IF NOT EXISTS post_like (
    post_id INT NOT NULL COMMENT '게시글 ID',
    user_id INT NOT NULL COMMENT '사용자 ID',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '좋아요 날짜',
    PRIMARY KEY (post_id, user_id),
    FOREIGN KEY (post_id) REFERENCES post(post_id) ON DELETE CASCADE,
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE
) COMMENT '게시글 좋아요 테이블';

-- 북마크 테이블 (두 버전 통합: AUTO_INCREMENT ID 포함하되 UNIQUE 제약 유지)
CREATE TABLE IF NOT EXISTS post_bookmark (
    bookmark_id INT AUTO_INCREMENT PRIMARY KEY COMMENT '북마크 ID',
    post_id INT NOT NULL COMMENT '게시글 ID',
    user_id INT NOT NULL COMMENT '사용자 ID',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '북마크 날짜',
    FOREIGN KEY (post_id) REFERENCES post(post_id) ON DELETE CASCADE,
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE,
    UNIQUE KEY unique_user_post (user_id, post_id)
) COMMENT '게시글 북마크 테이블';

-- 공지사항 테이블
CREATE TABLE IF NOT EXISTS notice (
    notice_id INT AUTO_INCREMENT PRIMARY KEY COMMENT '공지사항 ID',
    user_id INT NOT NULL COMMENT '작성자 ID',
    title VARCHAR(200) NOT NULL COMMENT '제목',
    content TEXT COMMENT '내용',
    view_count INT NOT NULL DEFAULT 0 COMMENT '조회수',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '작성일시',
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '수정일시',
    is_pinned BOOLEAN NOT NULL DEFAULT FALSE COMMENT '고정 여부',
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE,
    INDEX idx_created_at (created_at),
    INDEX idx_is_pinned (is_pinned)
) COMMENT '공지사항 테이블';

-- 작업 지시서 테이블 (통합된 PRIMARY KEY)
CREATE TABLE IF NOT EXISTS work_order (
    line_id INT NOT NULL COMMENT '라인 ID',
    product_id INT NOT NULL COMMENT '제품 ID',
    work_order_id VARCHAR(50) NOT NULL COMMENT '작업지시서 번호',
    user_id INT COMMENT '담당자 ID',
    qty_plan INT NOT NULL COMMENT '계획 수량',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '생성일시',
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '수정일시',
    PRIMARY KEY (line_id, product_id, work_order_id),
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE SET NULL,
    INDEX idx_work_order_id (work_order_id)
) COMMENT '작업 지시서 테이블';

-- 제품 테이블
CREATE TABLE IF NOT EXISTS product (
    product_id INT AUTO_INCREMENT PRIMARY KEY COMMENT '제품 ID',
    name VARCHAR(100) NOT NULL COMMENT '제품명',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '등록일시',
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '수정일시'
) COMMENT '제품 테이블';

-- 생산 데이터 테이블
CREATE TABLE IF NOT EXISTS production_data (
    line_id INT NOT NULL COMMENT '라인 ID',
    work_order_id VARCHAR(50) NOT NULL COMMENT '작업지시서 번호',
    downtime_info VARCHAR(200) COMMENT '다운타임 정보',
    good_qty INT NOT NULL DEFAULT 0 COMMENT '양품 수량',
    defect_qty INT NOT NULL DEFAULT 0 COMMENT '불량품 수량',
    availability_pct DECIMAL(5,2) COMMENT '가동률 (%)',
    performance_pct DECIMAL(5,2) COMMENT '성능률 (%)',
    quality_pct DECIMAL(5,2) COMMENT '품질률 (%)',
    timestamp DATETIME NOT NULL COMMENT '데이터 시간',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '등록일시',
    PRIMARY KEY (line_id, work_order_id, timestamp),
    INDEX idx_timestamp (timestamp),
    INDEX idx_work_order (work_order_id)
) COMMENT '생산 데이터 테이블';

-- 기계 테이블
CREATE TABLE IF NOT EXISTS machine (
    machine_id INT AUTO_INCREMENT PRIMARY KEY COMMENT '기계 ID',
    product_id INT COMMENT '제품 ID',
    line_id INT COMMENT '라인 ID',
    machine_name VARCHAR(100) NOT NULL COMMENT '기계명',
    location VARCHAR(100) COMMENT '위치',
    duration INT COMMENT '가동시간 (분)',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '등록일시',
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '수정일시',
    FOREIGN KEY (product_id) REFERENCES product(product_id) ON DELETE SET NULL,
    INDEX idx_line_id (line_id),
    INDEX idx_machine_name (machine_name)
) COMMENT '기계 테이블';

-- 기계 상태 테이블
CREATE TABLE IF NOT EXISTS machine_state (
    machine_id INT NOT NULL COMMENT '기계 ID',
    state ENUM('RUNNING', 'IDLE', 'DOWN', 'MAINTENANCE') NOT NULL COMMENT '기계 상태',
    timestamp DATETIME NOT NULL COMMENT '상태 시간',
    FOREIGN KEY (machine_id) REFERENCES machine(machine_id) ON DELETE CASCADE,
    INDEX idx_machine_timestamp (machine_id, timestamp),
    INDEX idx_state (state)
) COMMENT '기계 상태 테이블';

-- 스케줄 테이블 (확장된 버전 - Schedule 브랜치의 기능 포함)
CREATE TABLE IF NOT EXISTS schedule (
    schedule_id INT AUTO_INCREMENT PRIMARY KEY COMMENT '스케줄 ID',
    user_id INT NOT NULL COMMENT '담당자 ID',
    title VARCHAR(200) NOT NULL COMMENT '제목',
    description TEXT COMMENT '설명',
    type ENUM('meeting', 'task', 'review', 'training', 'milestone') NOT NULL DEFAULT 'meeting' COMMENT '일정 타입',
    start_datetime DATETIME NOT NULL COMMENT '시작일시',
    end_datetime DATETIME NOT NULL COMMENT '종료일시',
    location VARCHAR(200) COMMENT '장소',
    status ENUM('planned', 'in_progress', 'completed', 'cancelled') NOT NULL DEFAULT 'planned' COMMENT '일정 상태',
    is_urgent BOOLEAN NOT NULL DEFAULT FALSE COMMENT '긴급 여부',
    is_active BOOLEAN NOT NULL DEFAULT TRUE COMMENT '활성 상태',
    google_calendar_id VARCHAR(255) COMMENT '구글 캘린더 ID',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '생성일시',
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '수정일시',
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE,
    INDEX idx_user_start_date (user_id, start_datetime),
    INDEX idx_start_datetime (start_datetime),
    INDEX idx_type (type),
    INDEX idx_status (status)
) COMMENT '일정 테이블';

-- 할일 테이블 (Schedule 브랜치에서 추가)
CREATE TABLE IF NOT EXISTS task (
    task_id INT AUTO_INCREMENT PRIMARY KEY COMMENT '할일 ID',
    user_id INT NOT NULL COMMENT '사용자 ID',
    title VARCHAR(200) NOT NULL COMMENT '제목',
    description TEXT COMMENT '설명',
    priority ENUM('low', 'medium', 'high', 'urgent') NOT NULL DEFAULT 'medium' COMMENT '우선순위',
    status ENUM('pending', 'in_progress', 'completed') NOT NULL DEFAULT 'pending' COMMENT '상태',
    due_date DATETIME COMMENT '마감일',
    is_active BOOLEAN NOT NULL DEFAULT TRUE COMMENT '활성 상태',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '생성일시',
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '수정일시',
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE,
    INDEX idx_user_due_date (user_id, due_date),
    INDEX idx_status (status),
    INDEX idx_priority (priority)
) COMMENT '할일 테이블';

-- 날씨 테이블
CREATE TABLE IF NOT EXISTS weather (
    weather_id INT AUTO_INCREMENT PRIMARY KEY COMMENT '날씨 ID',
    location VARCHAR(100) NOT NULL COMMENT '위치',
    temperature_c DECIMAL(5,2) COMMENT '온도 (섭씨)',
    humidity DECIMAL(5,2) COMMENT '습도 (%)',
    recorded_at DATETIME NOT NULL COMMENT '측정 시간',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '등록일시',
    INDEX idx_location_recorded (location, recorded_at)
) COMMENT '날씨 데이터 테이블';

-- 공장 온도 테이블
CREATE TABLE IF NOT EXISTS factory_temperature (
    temp_id INT AUTO_INCREMENT PRIMARY KEY COMMENT '온도 ID',
    factory_name VARCHAR(100) NOT NULL COMMENT '공장명',
    internal_temperature DECIMAL(5,2) NOT NULL COMMENT '내부 온도',
    recorded_at DATETIME NOT NULL COMMENT '측정 시간',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '등록일시',
    INDEX idx_factory_recorded (factory_name, recorded_at)
) COMMENT '공장 온도 데이터 테이블';

-- 모니터링 공장 테이블
CREATE TABLE IF NOT EXISTS monitoring_factory (
    monitoring_id INT AUTO_INCREMENT PRIMARY KEY COMMENT '모니터링 ID',
    is_current BOOLEAN NOT NULL DEFAULT FALSE COMMENT '현재 상태',
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '업데이트 시간'
) COMMENT '공장 모니터링 상태 테이블';

-- 샘플 데이터 삽입 (관리자 계정만)
-- 관리자 계정 (비밀번호: admin123)
INSERT IGNORE INTO users (username, email, password, role, department, is_active) VALUES
('admin', 'admin@mes.com', '$2a$10$9tgX3CnemVwFZ.VuBfBMq.pFdule/.1K3tdgS5p6S5m.6nu9PKq/C', 'ADMIN', 'IT', true);