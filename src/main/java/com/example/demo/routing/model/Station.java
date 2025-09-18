package com.example.demo.routing.model;

public class Station {
    private String stationId;
    private String lineId;
    private int factoryId;
    private String processType;  // PRESS/BODY/PAINT/ASSY
    private String lineName;
    private String stationName;
    private String stationType;
    private int station;         // 순번
    // created_at은 필요 시 추가

    public String getStationId() { return stationId; }
    public void setStationId(String stationId) { this.stationId = stationId; }

    public String getLineId() { return lineId; }
    public void setLineId(String lineId) { this.lineId = lineId; }

    public int getFactoryId() { return factoryId; }
    public void setFactoryId(int factoryId) { this.factoryId = factoryId; }

    public String getProcessType() { return processType; }
    public void setProcessType(String processType) { this.processType = processType; }

    public String getLineName() { return lineName; }
    public void setLineName(String lineName) { this.lineName = lineName; }

    public String getStationName() { return stationName; }
    public void setStationName(String stationName) { this.stationName = stationName; }

    public String getStationType() { return stationType; }
    public void setStationType(String stationType) { this.stationType = stationType; }

    public int getStation() { return station; }
    public void setStation(int station) { this.station = station; }
}
