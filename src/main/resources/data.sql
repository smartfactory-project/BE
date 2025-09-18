INSERT INTO FACTORY (facId, facName) VALUES
(1, '울산'),
(2, '아산'),
(3, '전주');

/* =========================================================
   0) 가정 다시 확인
   - 공정: PRESS / BODY / PAINT / ASSY
   - 각 공정마다 라인 2개 (..-01, ..-02)
   - 각 라인당 4~5개 STATION
   - 모델: SONATA, IONIQ5
   ========================================================= */



/* =========================================================
   1) ITEM (BOM FK 때문에 최소 품목 정의)
   ========================================================= */
INSERT INTO ITEM (item_id, item_name, item_type, unit, created_at) VALUES
('I-DOOR',        'Front Door',         'COMP', 'EA', NOW()),
('I-PAINT-WHITE', 'White Paint',        'COMP', 'L',  NOW()),
('I-BOLT-M6',     'Bolt M6',            'COMP', 'EA', NOW()),
('I-BATTERY',     'Battery Pack',       'COMP', 'EA', NOW());

/* =========================================================
   2) PRODUCT_MODEL (차종/모델 마스터)
   ========================================================= */

INSERT INTO PRODUCT_MODEL (product_id, model_code, created_at) VALUES
('PM-SONATA', 'SONATA', NOW()),
('PM-IONIQ5', 'IONIQ5', NOW()),
('PM-AVANTE', 'AVANTE', NOW()),
('PM-PALISADE', 'PALISADE', NOW()),
('PM-KONA', 'KONA', NOW()),
('PM-STARIA', 'STARIA', NOW()),
('PM-CASPER_EV', 'CASPER_EV', NOW());
/* =========================================================
   3) STATION (라인/스테이션 정의)
   - line_id는 코드(문자). 각 공정별로 2개 라인씩 정의.
   - 라인 내 4~5개의 station 생성.
   ========================================================= */

-- PRESS 공정: 두 라인 (PRESS-01, PRESS-02)
INSERT INTO STATION (station_id, line_id, factory_id, process_type, line_name, station_name, station_type, station, created_at) VALUES
('ST-PRESS01-01','PRESS-01',1,'PRESS','Press Line 01','Blanking','PRESS_BLANK', 1, NOW()),
('ST-PRESS01-02','PRESS-01',1,'PRESS','Press Line 01','Main Press #1','PRESS_MAIN', 2, NOW()),
('ST-PRESS01-03','PRESS-01',1,'PRESS','Press Line 01','Main Press #2','PRESS_MAIN', 3, NOW()),
('ST-PRESS01-04','PRESS-01',1,'PRESS','Press Line 01','Trimming','PRESS_TRIM', 4, NOW()),
('ST-PRESS02-01','PRESS-02',1,'PRESS','Press Line 02','Blanking','PRESS_BLANK', 1, NOW()),
('ST-PRESS02-02','PRESS-02',1,'PRESS','Press Line 02','Main Press #1','PRESS_MAIN', 2, NOW()),
('ST-PRESS02-03','PRESS-02',1,'PRESS','Press Line 02','Piercing','PRESS_PIERCE', 3, NOW()),
('ST-PRESS02-04','PRESS-02',1,'PRESS','Press Line 02','Trimming','PRESS_TRIM', 4, NOW());

-- BODY 공정: 두 라인 (BODY-01, BODY-02)
INSERT INTO STATION (station_id, line_id, factory_id, process_type, line_name, station_name, station_type, station, created_at) VALUES
('ST-BODY01-01','BODY-01',1,'BODY','Body Line 01','Spot Weld #1','WELD_SPOT', 1, NOW()),
('ST-BODY01-02','BODY-01',1,'BODY','Body Line 01','Spot Weld #2','WELD_SPOT', 2, NOW()),
('ST-BODY01-03','BODY-01',1,'BODY','Body Line 01','Laser Weld','WELD_LASER', 3, NOW()),
('ST-BODY01-04','BODY-01',1,'BODY','Body Line 01','Sealing','SEAL', 4, NOW()),
('ST-BODY02-01','BODY-02',1,'BODY','Body Line 02','Spot Weld #1','WELD_SPOT', 1, NOW()),
('ST-BODY02-02','BODY-02',1,'BODY','Body Line 02','Spot Weld #2','WELD_SPOT', 2, NOW()),
('ST-BODY02-03','BODY-02',1,'BODY','Body Line 02','Quality Check','QC_BODY', 3, NOW()),
('ST-BODY02-04','BODY-02',1,'BODY','Body Line 02','Sealing','SEAL', 4, NOW());

-- PAINT 공정: 두 라인 (PAINT-01, PAINT-02)
INSERT INTO STATION (station_id, line_id, factory_id, process_type, line_name, station_name, station_type, station, created_at) VALUES
('ST-PAINT01-01','PAINT-01',1,'PAINT','Paint Line 01','Pretreatment','PAINT_PRE', 1, NOW()),
('ST-PAINT01-02','PAINT-01',1,'PAINT','Paint Line 01','E-Coat','PAINT_ECOAT', 2, NOW()),
('ST-PAINT01-03','PAINT-01',1,'PAINT','Paint Line 01','Spray Booth','PAINT_SPRAY', 3, NOW()),
('ST-PAINT01-04','PAINT-01',1,'PAINT','Paint Line 01','Bake Oven','PAINT_OVEN', 4, NOW()),
('ST-PAINT02-01','PAINT-02',1,'PAINT','Paint Line 02','Pretreatment','PAINT_PRE', 1, NOW()),
('ST-PAINT02-02','PAINT-02',1,'PAINT','Paint Line 02','E-Coat','PAINT_ECOAT', 2, NOW()),
('ST-PAINT02-03','PAINT-02',1,'PAINT','Paint Line 02','Spray Booth','PAINT_SPRAY', 3, NOW()),
('ST-PAINT02-04','PAINT-02',1,'PAINT','Paint Line 02','Bake Oven','PAINT_OVEN', 4, NOW());

-- ASSY 공정: 두 라인 (ASSY-01, ASSY-02)
INSERT INTO STATION (station_id, line_id, factory_id, process_type, line_name, station_name, station_type, station, created_at) VALUES
('ST-ASSY01-01','ASSY-01',1,'ASSY','Assembly Line 01','Trim #1','ASSY_TRIM', 1, NOW()),
('ST-ASSY01-2','ASSY-01',1,'ASSY','Assembly Line 01','Chassis #1','ASSY_CHASSIS', 2, NOW()),
('ST-ASSY01-3','ASSY-01',1,'ASSY','Assembly Line 01','Torque #1','ASSY_TORQUE', 3, NOW()),
('ST-ASSY01-4','ASSY-01',1,'ASSY','Assembly Line 01','Vision Check','ASSY_VISION', 4, NOW()),
('ST-ASSY02-1','ASSY-02',1,'ASSY','Assembly Line 02','Trim #1','ASSY_TRIM', 1, NOW()),
('ST-ASSY02-2','ASSY-02',1,'ASSY','Assembly Line 02','Chassis #1','ASSY_CHASSIS', 2, NOW()),
('ST-ASSY02-3','ASSY-02',1,'ASSY','Assembly Line 02','Torque #1','ASSY_TORQUE', 3, NOW()),
('ST-ASSY02-4','ASSY-02',1,'ASSY','Assembly Line 02','End-of-Line Test','ASSY_EOL', 4, NOW());

/* =========================================================
   4) WORK_ORDER (모델별 지시 예시)
   ========================================================= */
INSERT INTO WORK_ORDER (order_id, product_id, qty_plan, created_at, planned_s, planned_e) VALUES
('WO-SONATA-2025-01', 'PM-SONATA', 120, NOW(), NOW(), DATE_ADD(NOW(), INTERVAL 1 DAY)),
('WO-IONIQ5-2025-01', 'PM-IONIQ5',  80, NOW(), NOW(), DATE_ADD(NOW(), INTERVAL 1 DAY));

/* =========================================================
   5) PRODUCT (차량 인스턴스 예시 – 몇 대만 샘플)
   ========================================================= */
INSERT INTO PRODUCT (p_id, order_id, product_id, color_code, created_at) VALUES
('PI-SONATA-0001','WO-SONATA-2025-01','PM-SONATA','WH',NOW()),
('PI-SONATA-0002','WO-SONATA-2025-01','PM-SONATA','BK',NOW()),
('PI-IONIQ5-0001','WO-IONIQ5-2025-01','PM-IONIQ5','UW',NOW());

/* =========================================================
   6) PRODUCT_ROUTING (모델별 공정 순서: Press→Body→Paint→Assy)
   - line_id는 정보용 코드 (FK 없음)
   ========================================================= */
-- SONATA
INSERT INTO PRODUCT_ROUTING (line_id, product_id, process_seq, process_type, duration) VALUES
('PRESS-01','PM-SONATA',1,'PRESS',30),
('BODY-01', 'PM-SONATA',2,'BODY', 120),
('PAINT-01','PM-SONATA',3,'PAINT',90),
('ASSY-01', 'PM-SONATA',4,'ASSY', 150);

-- IONIQ5
INSERT INTO PRODUCT_ROUTING (line_id, product_id, process_seq, process_type, duration) VALUES
('PRESS-02','PM-IONIQ5',1,'PRESS',28),
('BODY-02', 'PM-IONIQ5',2,'BODY', 110),
('PAINT-02','PM-IONIQ5',3,'PAINT',95),
('ASSY-02', 'PM-IONIQ5',4,'ASSY', 140);

/* =========================================================
   7) BOM (모델별 자재 구성 – 최소 예시)
   ========================================================= */
-- SONATA
INSERT INTO BOM (product_id, item_id, qty_per) VALUES
('PM-SONATA','I-DOOR',        4),
('PM-SONATA','I-PAINT-WHITE', 2.5),
('PM-SONATA','I-BOLT-M6',     120);

-- IONIQ5
INSERT INTO BOM (product_id, item_id, qty_per) VALUES
('PM-IONIQ5','I-DOOR',        4),
('PM-IONIQ5','I-BATTERY',     1),
('PM-IONIQ5','I-BOLT-M6',     150);

/* =========================================================
   8) STATION_EQUIPMENT (각 스테이션에 설비 1~2개 매핑 예시)
   ========================================================= */
-- PRESS-01
INSERT INTO STATION_EQUIPMENT (station_id, equipment_id, installed_at) VALUES
('ST-PRESS01-01','EQ-FEEDER-01',NOW()),
('ST-PRESS01-02','EQ-PRESS2000T-01',NOW()),
('ST-PRESS01-03','EQ-PRESS2000T-02',NOW()),
('ST-PRESS01-04','EQ-TRIM-01',NOW());

-- BODY-01
INSERT INTO STATION_EQUIPMENT (station_id, equipment_id, installed_at) VALUES
('ST-BODY01-01','EQ-ROBOT-FANUC-01',NOW()),
('ST-BODY01-02','EQ-ROBOT-ABB-01',NOW()),
('ST-BODY01-03','EQ-LASER-01',NOW()),
('ST-BODY01-04','EQ-SEALER-01',NOW());

-- PAINT-01
INSERT INTO STATION_EQUIPMENT (station_id, equipment_id, installed_at) VALUES
('ST-PAINT01-01','EQ-PRETREAT-01',NOW()),
('ST-PAINT01-02','EQ-ECOAT-01',NOW()),
('ST-PAINT01-03','EQ-PAINT-ROBOT-01',NOW()),
('ST-PAINT01-04','EQ-OVEN-01',NOW());

-- ASSY-01
INSERT INTO STATION_EQUIPMENT (station_id, equipment_id, installed_at) VALUES
('ST-ASSY01-01','EQ-TRIM-TBL-01',NOW()),
('ST-ASSY01-3','EQ-TORQUE-01',NOW()),
('ST-ASSY01-4','EQ-VISION-01',NOW());

-- 나머지 라인(BODY-02, PAINT-02, PRESS-02, ASSY-02)도 동일한 패턴으로 일부만 예시
INSERT INTO STATION_EQUIPMENT (station_id, equipment_id, installed_at) VALUES
('ST-BODY02-01','EQ-ROBOT-KUKA-01',NOW()),
('ST-PAINT02-03','EQ-PAINT-ROBOT-02',NOW()),
('ST-PRESS02-02','EQ-PRESS1600T-01',NOW()),
('ST-ASSY02-3','EQ-TORQUE-02',NOW());

/* =========================================================
   9) PROCESS_STATUS (작업지시 × 모델 단위 상태 초기화)
   ========================================================= */
INSERT INTO PROCESS_STATUS (order_id, product_id, status, start_ts, end_ts) VALUES
('WO-SONATA-2025-01','PM-SONATA','WAIT',NULL,NULL),
('WO-IONIQ5-2025-01','PM-IONIQ5','WAIT',NULL,NULL);




/* ========== STATION (예시 1개 라인) ========== */
INSERT INTO STATION (station_id, line_id, factory_id, process_type, line_name, station_name, station_type, station, created_at)
VALUES
('ST-PRESS-01', 'LINE-PRESS-01', 1, 'PRESS', 'Press Line 01', 'Press Station #1', 'PRESS_MAIN', 1, NOW()),
('ST-PRESS-02', 'LINE-PRESS-01', 1, 'PRESS', 'Press Line 01', 'Press Station #2', 'PRESS_SUB', 1, NOW());

/* ========== STATION_EQUIPMENT ========== */
INSERT INTO STATION_EQUIPMENT (station_id, equipment_id, installed_at)
VALUES
('ST-PRESS-01', 'EQ-PRESS-01', NOW()),
('ST-PRESS-02', 'EQ-PRESS-02', NOW());

INSERT INTO PRODUCT_MODEL (product_id, model_code, created_at)
VALUES ('PROD-IONIQ5', 'IONIQ5', NOW());

/* ========== WORK_ORDER (생산 지시) ========== */
INSERT INTO WORK_ORDER (order_id, product_id, qty_plan, created_at, planned_s, planned_e)
VALUES
('WO-2025-0001', 'PROD-IONIQ5', 100, NOW(), NOW(), DATE_ADD(NOW(), INTERVAL 1 DAY));

/* ========== PRODUCTION_LOG (양품/불량 기록) ========== */
INSERT INTO PRODUCTION_LOG (station_id, order_id, event_type, good_qty, defect_qty, defect_type, defect_desc, defect_severity, recorded_at)
VALUES
('ST-PRESS-01', 'WO-2025-0001', 'GOOD', 90, 0, NULL, NULL, NULL, NOW()),
('ST-PRESS-01', 'WO-2025-0001', 'DEFECT', 0, 10, 'CRACK', '금형 균열 발생', 'HIGH', NOW());

/* ========== EQUIPMENT_STATE_LOG (설비稼動 이력) ========== */
INSERT INTO EQUIPMENT_STATE_LOG (station_id, equipment_id, state, start_ts, end_ts)
VALUES
('ST-PRESS-01', 'EQ-PRESS-01', 'RUN',   '2025-09-16 08:00:00', '2025-09-16 12:00:00'),
('ST-PRESS-01', 'EQ-PRESS-01', 'DOWN',  '2025-09-16 12:00:00', '2025-09-16 13:00:00'),
('ST-PRESS-01', 'EQ-PRESS-01', 'RUN',   '2025-09-16 13:00:00', '2025-09-16 17:00:00');


INSERT INTO CAR_NAME (car_id, car_name, image_url, factory_id, created_at) VALUES
('PM-SONATA',   '쏘나타 더 엣지', 'https://www.hyundai.com/contents/repn-car/side-45/main-sonata-the-edge-25my-45side.png', 1, NOW()),
('PM-IONIQ5',   '아이오닉 5',     'https://www.hyundai.com/contents/repn-car/side-45/main-ioniq6-25fl-45side.png',          1, NOW()),
('PM-AVANTE',   '아반떼N',        'https://www.hyundai.com/contents/repn-car/side-45/main-avante-n-26my-45side.png',        1, NOW()),
('PM-PALISADE', '펠리세이드',     'https://www.hyundai.com/contents/repn-car/side-45/main-palisade-25fc-hybrid-45side.png', 1, NOW()),
('PM-KONA',     '코나',            'https://www.hyundai.com/contents/repn-car/side-45/main-kona-hybrid-24my-45side.png',    1, NOW()),
('PM-STARIA',   '스타리아 킨더',  'https://www.hyundai.com/contents/repn-car/side-45/main-staria-kinder-24my-45side.png',  1, NOW()),
('PM-CASPER_EV',   '캐스퍼 일렉트릭',  'https://www.hyundai.com/contents/repn-car/side-w/casper_ev_26my_well_side.png',  2, NOW());


