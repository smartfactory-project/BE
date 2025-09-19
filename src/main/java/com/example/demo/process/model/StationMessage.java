package com.example.demo.process.model;

import lombok.Data;

@Data
public class StationMessage {
    private String station_id;
    private String status;
    private int output;
    private int defects;
    private long timestamp;
}