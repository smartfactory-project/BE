package com.example.demo.mapper;

import com.example.demo.model.LineMonth;
import com.example.demo.model.Performance;
import com.example.demo.model.Production;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ProductionMapper {
    List<Production> getMonthlyProduction();

    List<LineMonth> getMonthlyLine();

        List<Performance> performanceMonthly();
}
