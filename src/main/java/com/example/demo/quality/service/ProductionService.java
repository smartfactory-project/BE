package com.example.demo.quality.service;


import com.example.demo.quality.mapper.ProductionMapper;
import com.example.demo.quality.model.LineMonth;
import com.example.demo.quality.model.Performance;
import com.example.demo.quality.model.Production;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductionService {
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
