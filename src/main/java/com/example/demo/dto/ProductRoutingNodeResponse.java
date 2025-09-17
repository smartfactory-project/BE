package com.example.demo.dto;

public class ProductRoutingNodeResponse {
    private Long routingId;
    private Integer processSeq;
    private String processType;
    private String lineId;
    private Integer duration;

    public ProductRoutingNodeResponse(Long routingId, Integer processSeq, String processType, String lineId, Integer duration) {
        this.routingId = routingId;
        this.processSeq = processSeq;
        this.processType = processType;
        this.lineId = lineId;
        this.duration = duration;
    }

    public Long getRoutingId() {
        return routingId;
    }

    public Integer getProcessSeq() {
        return processSeq;
    }

    public String getProcessType() {
        return processType;
    }

    public String getLineId() {
        return lineId;
    }

    public Integer getDuration() {
        return duration;
    }
}
