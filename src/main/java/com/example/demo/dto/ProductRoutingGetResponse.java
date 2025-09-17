package com.example.demo.dto;

import java.util.List;

public class ProductRoutingGetResponse {
    private String productId;
    private List<ProductRoutingNodeResponse> nodes;

    public ProductRoutingGetResponse(String productId, List<ProductRoutingNodeResponse> nodes) {
        this.productId = productId;
        this.nodes = nodes;
    }

    public String getProductId() {
        return productId;
    }

    public List<ProductRoutingNodeResponse> getNodes() {
        return nodes;
    }
}
