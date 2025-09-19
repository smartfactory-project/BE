-- Error Code Master 테이블 생성 및 데이터 입력
-- UTF-8 인코딩으로 작성됨

USE mes_car;

-- 문자셋 설정
SET NAMES utf8mb4;
SET CHARACTER SET utf8mb4;

-- ERROR_CODE_MASTER 테이블 생성 (완전한 DDL 파일과 일치)
CREATE TABLE IF NOT EXISTS ERROR_CODE_MASTER (
    error_code VARCHAR(20) NOT NULL PRIMARY KEY,
    error_name VARCHAR(100) NOT NULL,
    error_desc TEXT,
    error_category ENUM('DEFECT','EQUIPMENT','COMMON') DEFAULT 'DEFECT',
    process_type ENUM('PRESS','BODY','PAINT','ASSY','COMMON') NOT NULL,
    default_severity ENUM('LOW','MEDIUM','HIGH','CRITICAL') NOT NULL,
    auto_recovery BOOLEAN DEFAULT FALSE,
    solution_guide TEXT,
    threshold_value DECIMAL(10,2) DEFAULT NULL,
    unit VARCHAR(10) DEFAULT NULL,
    is_active BOOLEAN DEFAULT TRUE,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- 기존 데이터 삭제 (재실행 시 중복 방지)
DELETE FROM ERROR_CODE_MASTER;

-- 에러 코드 마스터 데이터 입력
INSERT INTO ERROR_CODE_MASTER (error_code, error_name, error_desc, error_category, process_type, default_severity, auto_recovery, solution_guide, threshold_value, unit, is_active) VALUES
('PRS001', '유압 압력 부족', '프레스 유압 시스템 압력 저하', 'EQUIPMENT', 'PRESS', 'HIGH', FALSE, '1.유압펌프 점검\n2.오일 레벨 확인\n3.배관 누유 검사', 140.0, 'bar', TRUE),
('PRS002', '금형 온도 초과', '프레스 금형 온도 과열', 'EQUIPMENT', 'PRESS', 'CRITICAL', FALSE, '1.냉각수 공급 점검\n2.생산 일시 중단\n3.금형 온도 하강 대기', 80.0, '°C', TRUE),
('PRS003', '재료 두께 불량', '강판 두께 기준치 초과', 'DEFECT', 'PRESS', 'MEDIUM', FALSE, '1.재료 검사\n2.공급업체 확인\n3.품질 재검사', 0.1, 'mm', TRUE),
('PRS004', '성형 불량', '프레스 성형 치수 불일치', 'DEFECT', 'PRESS', 'HIGH', FALSE, '1.금형 점검\n2.압력 조정\n3.재성형 실시', 2.0, 'mm', TRUE),
('BDY001', '용접 전류 이상', '스폿용접 전류값 범위 초과', 'EQUIPMENT', 'BODY', 'HIGH', FALSE, '1.용접팁 점검\n2.전류 설정 재조정\n3.접촉부 청소', 8500.0, 'A', TRUE),
('BDY002', '로봇 위치 오차', '용접 로봇 위치 정밀도 초과', 'EQUIPMENT', 'BODY', 'HIGH', FALSE, '1.로봇 캘리브레이션\n2.위치센서 점검\n3.프로그램 재확인', 0.5, 'mm', TRUE),
('BDY003', '용접 불량', '용접부 강도 기준 미달', 'DEFECT', 'BODY', 'HIGH', FALSE, '1.재용접 실시\n2.용접 조건 재설정\n3.품질 재검사', 5000.0, 'N', TRUE),
('BDY004', '치수 불량', '차체 치수 허용오차 초과', 'DEFECT', 'BODY', 'HIGH', FALSE, '1.지그 점검\n2.부품 재측정\n3.조정 작업 실시', 1.0, 'mm', TRUE),
('PNT001', '부스 압력 이상', '도장부스 압력 기준치 이탈', 'EQUIPMENT', 'PAINT', 'HIGH', FALSE, '1.배기팬 점검\n2.필터 교체\n3.압력 재조정', 20.0, 'Pa', TRUE),
('PNT002', '도장 두께 부족', '도막 두께 기준치 미달', 'DEFECT', 'PAINT', 'MEDIUM', FALSE, '1.스프레이 패턴 조정\n2.도료 점도 확인\n3.재도장 실시', 25.0, 'μm', TRUE),
('PNT003', '색상 불일치', '표준 색상과 차이 발생', 'DEFECT', 'PAINT', 'HIGH', FALSE, '1.색상 매칭 재확인\n2.도료 교체\n3.전면 재도장', 2.0, 'ΔE', TRUE),
('PNT004', '건조 온도 이상', '건조로 온도 설정값 이탈', 'EQUIPMENT', 'PAINT', 'CRITICAL', FALSE, '1.온도 센서 점검\n2.가열 시스템 점검\n3.온도 재설정', 180.0, '°C', TRUE),
('ASM001', '토크 부족', '볼트 체결 토크 기준 미달', 'DEFECT', 'ASSY', 'HIGH', FALSE, '1.토크렌치 교정\n2.재체결 실시\n3.토크값 재확인', 85.0, 'Nm', TRUE),
('ASM002', '부품 누락', '필수 부품 미장착 감지', 'DEFECT', 'ASSY', 'CRITICAL', FALSE, '1.부품 공급 확인\n2.작업 지시서 확인\n3.부품 장착 실시', NULL, NULL, TRUE),
('ASM003', '유체 누유', '오일/액체류 누유 감지', 'DEFECT', 'ASSY', 'HIGH', FALSE, '1.연결부 점검\n2.씰 교체\n3.재주입 실시', NULL, NULL, TRUE),
('ASM004', '전장 불량', '전기 시스템 이상 감지', 'DEFECT', 'ASSY', 'HIGH', FALSE, '1.배선 점검\n2.퓨즈 확인\n3.ECU 진단', NULL, NULL, TRUE),
('CMN001', 'PLC 통신 장애', 'PLC와 상위시스템 통신 두절', 'EQUIPMENT', 'COMMON', 'CRITICAL', FALSE, '1.네트워크 케이블 점검\n2.PLC 재시작\n3.통신 설정 확인', NULL, NULL, TRUE),
('CMN002', '정전/전원 이상', '전원 공급 이상', 'EQUIPMENT', 'COMMON', 'CRITICAL', FALSE, '1.전원 공급 장치 점검\n2.배전반 확인\n3.긴급 복구 작업', 220.0, 'V', TRUE),
('CMN003', '안전 센서 작동', '안전 구역 침입 감지', 'EQUIPMENT', 'COMMON', 'HIGH', FALSE, '1.안전 구역 확인\n2.작업자 대피\n3.안전 확인 후 리셋', NULL, NULL, TRUE);

-- 완료 메시지
SELECT 'ERROR_CODE_MASTER 테이블 및 데이터가 성공적으로 생성되었습니다!' AS MESSAGE;
SELECT COUNT(*) AS 데이터_수 FROM ERROR_CODE_MASTER;