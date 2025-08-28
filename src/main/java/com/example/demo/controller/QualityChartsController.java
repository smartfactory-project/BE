package com.example.demo.controller;

import com.example.demo.model.Quality;
import com.example.demo.model.QualityPer;
import com.example.demo.service.QualityChartsService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/quality")
@CrossOrigin(origins = "*")
public class QualityChartsController {

    private final QualityChartsService qualityChartsService;

    public QualityChartsController(QualityChartsService qualityChartsService){
        this.qualityChartsService = qualityChartsService;
    }

    @GetMapping("/distribution")
    public List<Quality> getQualityChart(){
        return qualityChartsService.getQualityChart();
    }

    @GetMapping("/qualityPer")
    public List<QualityPer> getQualityPerChart(){
        return qualityChartsService.getQualityPerChart();
    }
}
