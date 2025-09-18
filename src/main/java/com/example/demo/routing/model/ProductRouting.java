package com.example.demo.routing.model;

public class ProductRouting {
    private Long routingId;     // PK (AUTO_INCREMENT)
    private String lineId;      // 라인 코드 (예: PRESS-01)
    private String productId;   // PRODUCT_MODEL.product_id
    private Integer processSeq; // 1..N
    private String processType; // PRESS/BODY/PAINT/ASSY
    private Integer duration;   // 분 단위 (nullable)

    public Long getRoutingId() {
        return routingId;
    }

    public void setRoutingId(Long routingId) {
        this.routingId = routingId;
    }

    public String getLineId() {
        return lineId;
    }

    public void setLineId(String lineId) {
        this.lineId = lineId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public Integer getProcessSeq() {
        return processSeq;
    }

    public void setProcessSeq(Integer processSeq) {
        this.processSeq = processSeq;
    }

    public String getProcessType() {
        return processType;
    }

    public void setProcessType(String processType) {
        this.processType = processType;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }
}
