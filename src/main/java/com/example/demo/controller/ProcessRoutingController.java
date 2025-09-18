package com.example.demo.controller;

import com.example.demo.dto.ProductRoutingGetResponse;
import com.example.demo.service.ProductRoutingService;
import org.springframework.core.ReactiveAdapterRegistry;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.demo.dto.FactoryResponse;
import com.example.demo.service.FactoryService;
import java.util.Map;
import java.util.List;
@RestController
@RequestMapping("/process/routings")
public class ProcessRoutingController {

    private final ProductRoutingService service;
    private final FactoryService factoryService;
    // 명시적 생성자
    public ProcessRoutingController(ProductRoutingService service, FactoryService factoryService) {
        this.service = service;
        this.factoryService = factoryService;
    }

    /** 공장 목록 조회 */
    @GetMapping("/factories")
    public ResponseEntity<List<FactoryResponse>> getFactories() {
        List<FactoryResponse> factories = factoryService.getFactories();
        return ResponseEntity.ok(factories);
    }
    /** 모델별 라우팅 조회 */
    @GetMapping("/{productId}")
    public ResponseEntity<ProductRoutingGetResponse> get(@PathVariable String productId) {
        return ResponseEntity.ok(service.getByProductId(productId));
    }
}
