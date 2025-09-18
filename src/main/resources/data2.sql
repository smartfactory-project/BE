SELECT
    SUM(TIMESTAMPDIFF(MINUTE, start_ts, end_ts)) AS total_time,
    SUM(CASE WHEN state = 'RUN' THEN TIMESTAMPDIFF(MINUTE, start_ts, end_ts) ELSE 0 END) AS run_time
FROM EQUIPMENT_STATE_LOG
WHERE station_id = 'ST-PRESS-01';

-- => availability = run_time / total_time * 100

/* 품질 계산 */
SELECT
    SUM(good_qty) AS good_qty,
    SUM(defect_qty) AS defect_qty,
    SUM(good_qty + defect_qty) AS total_qty
FROM PRODUCTION_LOG
WHERE station_id = 'ST-PRESS-01'
  AND order_id = 'WO-2025-0001';



INSERT INTO LINE_KPI (line_id, factory_id, availability, performance, quality, oee, created_at)
VALUES
('LINE-PRESS-01', 1, 85.00, 95.00, 90.00, 72.67, NOW());


select * from line_kpi;