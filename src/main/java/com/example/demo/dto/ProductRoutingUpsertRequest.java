package com.example.demo.dto;

import java.util.List;

public class ProductRoutingUpsertRequest {
    private List<ProductRoutingNodeRequest> nodes;

    public List<ProductRoutingNodeRequest> getNodes() {
        return nodes;
    }

    public void setNodes(List<ProductRoutingNodeRequest> nodes) {
        this.nodes = nodes;
    }
}
