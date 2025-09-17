package com.example.demo.dto;

public class StationResponse {
    private String stationId;
    private String lineId;
    private int factoryId;
    private String processType;
    private String lineName;
    private String stationName;
    private String stationType;
    private int station;

    public StationResponse(String stationId, String lineId, int factoryId, String processType,
                           String lineName, String stationName, String stationType, int station) {
        this.stationId = stationId;
        this.lineId = lineId;
        this.factoryId = factoryId;
        this.processType = processType;
        this.lineName = lineName;
        this.stationName = stationName;
        this.stationType = stationType;
        this.station = station;
    }

    public String getStationId() { return stationId; }
    public String getLineId() { return lineId; }
    public int getFactoryId() { return factoryId; }
    public String getProcessType() { return processType; }
    public String getLineName() { return lineName; }
    public String getStationName() { return stationName; }
    public String getStationType() { return stationType; }
    public int getStation() { return station; }
}
