package com.example.demo.mapper;

import com.example.demo.model.Quality;
import com.example.demo.model.QualityPer;

import java.util.List;

public interface QualityChartsMapper {
    List<Quality> getQualityChart();

    List<QualityPer> getQualityPerChart();
}
