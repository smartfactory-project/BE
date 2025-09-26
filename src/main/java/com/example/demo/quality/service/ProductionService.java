package com.example.demo.quality.service;


import com.example.demo.quality.mapper.ProductionMapper;
import com.example.demo.quality.model.LineMonth;
import com.example.demo.quality.model.Performance;
import com.example.demo.quality.model.Production;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import com.example.demo.quality.model.WeeklyAvgProduction;
import com.example.demo.quality.model.SimulationRow;

@Service
public class ProductionService {
    // 주간별 그룹별 평균 생산량 반환
    public WeeklyAvgProduction getWeeklyAvgProduction() {
        // 실제 DB 쿼리 필요: simulation_name, exists 컬럼 전체 조회
        List<SimulationRow> rows = productionMapper.getAllSimulationData();
        WeeklyAvgProduction result = new WeeklyAvgProduction();

        // press1~5, body1~5, paint1~5별로 exists 평균 계산
        for (String group : new String[]{"press", "body", "paint"}) {
            for (int i = 1; i <= 5; i++) {
                String simName = group + i;
                double avg = rows.stream()
                    .filter(r -> simName.equals(r.getSimulationName()))
                    .mapToDouble(SimulationRow::getExists)
                    .average().orElse(0);
                switch (group) {
                    case "press" -> {
                        switch (i) {
                            case 1 -> result.setPress1(avg);
                            case 2 -> result.setPress2(avg);
                            case 3 -> result.setPress3(avg);
                            case 4 -> result.setPress4(avg);
                            case 5 -> result.setPress5(avg);
                        }
                    }
                    case "body" -> {
                        switch (i) {
                            case 1 -> result.setBody1(avg);
                            case 2 -> result.setBody2(avg);
                            case 3 -> result.setBody3(avg);
                            case 4 -> result.setBody4(avg);
                            case 5 -> result.setBody5(avg);
                        }
                    }
                    case "paint" -> {
                        switch (i) {
                            case 1 -> result.setPaint1(avg);
                            case 2 -> result.setPaint2(avg);
                            case 3 -> result.setPaint3(avg);
                            case 4 -> result.setPaint4(avg);
                            case 5 -> result.setPaint5(avg);
                        }
                    }
                }
            }
        }
        return result;
    }
    private final ProductionMapper productionMapper;

    private static final Logger logger = LoggerFactory.getLogger(ProductionService.class);

    // 생성자 주입
    public ProductionService(ProductionMapper productionMapper) {
        this.productionMapper = productionMapper;
    }

    public List<Production> getMonthlyProduction() {
        List<Production> data = productionMapper.getMonthlyProduction();

        // 콘솔에 조회된 데이터 출력
        logger.info("Monthly Production Data: {}", data);

        return data;
    }

    public List<LineMonth> getMonthlyLine() {
        List<LineMonth> data = productionMapper.getMonthlyLine();

        // 콘솔에 조회된 데이터 출력
        logger.info("Monthly Production Data: {}", data);

        return data;
    }

    public List<Performance> performanceMonthly(){
        List<Performance> data = productionMapper.performanceMonthly();

        // 콘솔에 조회된 데이터 출력
        logger.info("performance Production Data: {}", data);

        return data;
    }
}
