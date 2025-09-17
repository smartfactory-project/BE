package com.example.demo.controller;

import com.example.demo.dto.ProductRoutingGetResponse;
import com.example.demo.dto.ProductRoutingUpsertRequest;
import com.example.demo.service.ProductRoutingService;
import org.springframework.core.ReactiveAdapterRegistry;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/process/routings")
public class ProcessRoutingController {

    private final ProductRoutingService service;

    // 명시적 생성자
    public ProcessRoutingController(ProductRoutingService service) {
        this.service = service;
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
