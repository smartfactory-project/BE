package com.example.demo.quality.controller;


import com.example.demo.quality.model.LineMonth;
import com.example.demo.quality.model.Performance;
import com.example.demo.quality.model.Production;
import com.example.demo.quality.service.ProductionService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import com.example.demo.quality.model.WeeklyAvgProduction;

@RestController
@RequestMapping("/api/production")
@CrossOrigin(origins = "*")
public class ProductionController {
    @GetMapping("/weekly")
    public double[] getWeeklyAvgProduction(String group) {
        // group: press, body, paint
        WeeklyAvgProduction avg = productionService.getWeeklyAvgProduction();
        if (group == null || group.isEmpty()) group = "press";
        // group별로 반환 (switch rule 적용)
        return switch (group) {
            case "press" -> new double[]{avg.getPress1(), avg.getPress2(), avg.getPress3(), avg.getPress4(), avg.getPress5()};
            case "body" -> new double[]{avg.getBody1(), avg.getBody2(), avg.getBody3(), avg.getBody4(), avg.getBody5()};
            case "paint" -> new double[]{avg.getPaint1(), avg.getPaint2(), avg.getPaint3(), avg.getPaint4(), avg.getPaint5()};
            default -> new double[0];
        };
    }

    private final ProductionService productionService; // final로 바꾸는게 좋음

    // 생성자 주입
    public ProductionController(ProductionService productionService) {
        this.productionService = productionService;
    }

    @GetMapping("/monthly")
    public List<Production> getMonthlyProduction() {
        // 서비스에서 DB 조회 후 DTO로 반환
        return productionService.getMonthlyProduction();
    }

    @GetMapping("/line")
    public List<LineMonth> getMonthlyLine() {
        // 서비스에서 DB 조회 후 DTO로 반환
        return productionService.getMonthlyLine();
    }

    @GetMapping("/performanceMonthly")
    public List<Performance> getMonthlyPerformance() {
        // 서비스에서 DB 조회 후 DTO로 반환
        return productionService.performanceMonthly();
    }
}
