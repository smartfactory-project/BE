package com.example.demo.quality.mapper;

import com.example.demo.quality.model.Quality;
import com.example.demo.quality.model.QualityPer;

import java.util.List;

public interface QualityChartsMapper {
    List<Quality> getQualityChart();

    List<QualityPer> getQualityPerChart();
}
