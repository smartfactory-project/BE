package com.example.demo.dto;

public class ProductRoutingNodeRequest {
    private Integer processSeq;
    private String processType; // "PRESS","BODY","PAINT","ASSY"
    private String lineId;
    private Integer duration;

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

    public String getLineId() {
        return lineId;
    }

    public void setLineId(String lineId) {
        this.lineId = lineId;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }
}
