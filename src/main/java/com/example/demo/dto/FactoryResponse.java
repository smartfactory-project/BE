package com.example.demo.dto;

public class FactoryResponse {
    private int facId;
    private String facName;

    public FactoryResponse() {
    }

    public FactoryResponse(int facId, String facName) {
        this.facId = facId;
        this.facName = facName;
    }

    public int getFacId() {
        return facId;
    }

    public void setFacId(int facId) {
        this.facId = facId;
    }

    public String getFacName() {
        return facName;
    }

    public void setFacName(String facName) {
        this.facName = facName;
    }
}
