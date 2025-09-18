package com.example.demo.controller;

import com.example.demo.dto.ProductRoutingGetResponse;
import com.example.demo.dto.ProductRoutingUpsertRequest;
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

    /** 모델 라우팅 전체 치환(저장) */
    @PutMapping("/{productId}")
    public ResponseEntity<?> replaceAll(@PathVariable String productId,
                                        @RequestBody ProductRoutingUpsertRequest req) {
        int count = service.replaceAll(productId, req);
        return ResponseEntity.ok(Map.of("productId", productId, "replacedCount", count));
    }

    /** 저장 전 검증 */
    @PostMapping("/{productId}/validate")
    public ResponseEntity<?> validate(@PathVariable String productId,
                                      @RequestBody ProductRoutingUpsertRequest req) {
        service.validateOnly(productId, req);
        return ResponseEntity.ok(Map.of("valid", true));
    }
}
