package com.example.demo.quality.mapper;

import com.example.demo.quality.model.LineMonth;
import com.example.demo.quality.model.Performance;
import com.example.demo.quality.model.Production;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ProductionMapper {
    List<Production> getMonthlyProduction();

    List<LineMonth> getMonthlyLine();

        List<Performance> performanceMonthly();
}
