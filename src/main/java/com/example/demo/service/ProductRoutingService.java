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

    @Transactional
    public int replaceAll(String productId, ProductRoutingUpsertRequest req) {
        ensureProductModelExists(productId);
        validateNodes(req.getNodes(), true);

        routingMapper.deleteByProductId(productId);

        List<ProductRouting> bulk = new ArrayList<>();
        for (ProductRoutingNodeRequest n : req.getNodes()) {
            ProductRouting pr = new ProductRouting();
            pr.setProductId(productId);
            pr.setLineId(n.getLineId());
            pr.setProcessSeq(n.getProcessSeq());
            pr.setProcessType(n.getProcessType());
            pr.setDuration(n.getDuration());
            bulk.add(pr);
        }
        if (!bulk.isEmpty()) {
            routingMapper.insertNodes(bulk);
        }
        return bulk.size();
    }

    public void validateOnly(String productId, ProductRoutingUpsertRequest req) {
        ensureProductModelExists(productId);
        validateNodes(req.getNodes(), false);
    }

    private void ensureProductModelExists(String productId) {
        Integer cnt = routingMapper.countProductModel(productId);
        if (cnt == null || cnt == 0) {
            throw new IllegalArgumentException("Unknown productId: " + productId);
        }
    }

    private void validateNodes(List<ProductRoutingNodeRequest> nodes, boolean checkLineIdExists) {
        if (nodes == null || nodes.isEmpty()) {
            throw new IllegalArgumentException("nodes must not be empty.");
        }

        Set<Integer> seqs = new HashSet<>();
        int max = 0;
        for (ProductRoutingNodeRequest n : nodes) {
            if (n.getProcessSeq() == null || n.getProcessSeq() <= 0) {
                throw new IllegalArgumentException("processSeq must be positive.");
            }
            seqs.add(n.getProcessSeq());
            max = Math.max(max, n.getProcessSeq());
        }
        if (seqs.size() != nodes.size() || max != nodes.size()) {
            throw new IllegalArgumentException("processSeq must be continuous starting from 1 without gaps.");
        }

        int lastIdx = -1;
        Map<Integer, ProductRoutingNodeRequest> bySeq = new HashMap<>();
        for (ProductRoutingNodeRequest n : nodes) {
            bySeq.put(n.getProcessSeq(), n);
        }

        for (int i = 1; i <= nodes.size(); i++) {
            ProductRoutingNodeRequest n = bySeq.get(i);
            String type = (n.getProcessType() == null ? "" : n.getProcessType().toUpperCase(Locale.ROOT));
            int idx = ORDER.indexOf(type);
            if (idx < 0) throw new IllegalArgumentException("Invalid processType: " + type);
            if (idx < lastIdx) throw new IllegalArgumentException("Order must follow PRESS→BODY→PAINT→ASSY.");
            lastIdx = idx;

            if (n.getLineId() == null || n.getLineId().isBlank()) {
                throw new IllegalArgumentException("lineId required at seq " + i);
            }
        }

        if (checkLineIdExists) {
            for (ProductRoutingNodeRequest n : nodes) {
                Integer exists = routingMapper.countLineIdExists(n.getLineId());
                if (exists == null || exists == 0) {
                    throw new IllegalArgumentException("Unknown lineId: " + n.getLineId());
                }
            }
        }
    }
}
