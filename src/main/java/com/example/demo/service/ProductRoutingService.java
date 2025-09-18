package com.example.demo.service;

import com.example.demo.dto.*;
import com.example.demo.mapper.ProductRoutingMapper;
import com.example.demo.model.ProductRouting;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class ProductRoutingService {

    private final ProductRoutingMapper routingMapper;

    // 명시적 생성자
    public ProductRoutingService(ProductRoutingMapper routingMapper) {
        this.routingMapper = routingMapper;
    }

    private static final List<String> ORDER = List.of("PRESS", "BODY", "PAINT", "ASSY");

    public ProductRoutingGetResponse getByProductId(String productId) {
        List<ProductRouting> rows = routingMapper.selectByProductId(productId);
        List<ProductRoutingNodeResponse> nodes = new ArrayList<>();
        for (ProductRouting r : rows) {
            nodes.add(new ProductRoutingNodeResponse(
                    r.getRoutingId(),
                    r.getProcessSeq(),
                    r.getProcessType(),
                    r.getLineId(),
                    r.getDuration()
            ));
        }
        return new ProductRoutingGetResponse(productId, nodes);
    }
}
