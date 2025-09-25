USE mes_car;

CREATE TABLE IF NOT EXISTS statistics_report (
    id                BIGINT AUTO_INCREMENT PRIMARY KEY,
    simulation_name   VARCHAR(100) NOT NULL,      -- 시뮬레이션명(공정명, 예: 'press', 'paint', 'body' 등)
    simulation_file   VARCHAR(255) NOT NULL,      -- 시뮬레이션 파일명(예: 'paint.spp', 'press.spp')
    simulation_time   DATETIME NOT NULL,          -- 시뮬레이션 생성 일시
    object_name       VARCHAR(100) NOT NULL,      -- 설비명
    -- 상태 비율(%)
    working           DECIMAL(5,2) NOT NULL,      -- 가동률(%)
    setup             DECIMAL(5,2) NOT NULL,      -- 셋업률(%)
    waiting           DECIMAL(5,2) NOT NULL,      -- 대기률(%)
    blocking          DECIMAL(5,2) NOT NULL,      -- 블로킹률(%)
    powering          DECIMAL(5,2) NOT NULL,      -- 전원 on/off(%)
    failed            DECIMAL(5,2) NOT NULL,      -- 고장률(%)
    stopped           DECIMAL(5,2) NOT NULL,      -- 정지률(%)
    paused            DECIMAL(5,2) NOT NULL,      -- 일시정지(%)
    unplanned         DECIMAL(5,2) NOT NULL,      -- 예상치 못한(%)
    portion           DECIMAL(5,2) NOT NULL,      -- 전체 Portion(%)
    -- 물류/재고 흐름
    entries           INT,                        -- 입고 횟수
    exits             INT,                        -- 출고 횟수
    min_contents      INT,                        -- 최소 재고
    max_contents      INT,                        -- 최대 재고
    relative_empty    DECIMAL(5,2),               -- 비어있는 비율(%)
    relative_full     DECIMAL(5,2),               -- 가득참 비율(%)
    occupation_no_interrupt DECIMAL(5,2),         -- 점유율(중단X)
    occupation_with_interrupt DECIMAL(5,2),       -- 점유율(중단O)
    -- 시간 통계(합계, 평균, 표준편차)
    working_time_sum      DOUBLE,
    working_time_mean     DOUBLE,
    working_time_std      DOUBLE,
    waiting_time_sum      DOUBLE,
    waiting_time_mean     DOUBLE,
    waiting_time_std      DOUBLE,
    blocked_time_sum      DOUBLE,
    blocked_time_mean     DOUBLE,
    blocked_time_std      DOUBLE,
    failed_time_sum       DOUBLE,
    failed_time_mean      DOUBLE,
    failed_time_std       DOUBLE,
    created_at        TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_statistics_report_simulation ON statistics_report (simulation_name, simulation_time);
CREATE INDEX idx_statistics_report_object ON statistics_report (object_name);
CREATE INDEX idx_statistics_report_file ON statistics_report (simulation_file);