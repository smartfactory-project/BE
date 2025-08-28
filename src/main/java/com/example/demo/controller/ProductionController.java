package com.example.demo.controller;


import com.example.demo.model.LineMonth;
import com.example.demo.model.Performance;
import com.example.demo.model.Production;
import com.example.demo.service.ProductionService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.sound.sampled.Line;
import java.util.List;

@RestController
@RequestMapping("/api/production")
@CrossOrigin(origins = "*")
public class ProductionController {

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
