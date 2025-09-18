package com.example.demo.quality.service;


import com.example.demo.quality.mapper.QualityChartsMapper;
import com.example.demo.quality.model.Quality;
import com.example.demo.quality.model.QualityPer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QualityChartsService {
    private final QualityChartsMapper qualityChartsMapper;

    private static final Logger logger = LoggerFactory.getLogger(ProductionService.class);

    // 생성자 주입
    public QualityChartsService(QualityChartsMapper qualityChartsMapper) {
        this.qualityChartsMapper = qualityChartsMapper;
    }

    public List<Quality> getQualityChart() {
        List<Quality> data = qualityChartsMapper.getQualityChart();

        // 콘솔에 조회된 데이터 출력
        logger.info("distribution Production Data: {}", data);

        return data;
    }


    public List<QualityPer> getQualityPerChart(){
        List<QualityPer> data = qualityChartsMapper.getQualityPerChart();

        logger.info("distribution Production Data: {}", data);

        return data;
    }
}
