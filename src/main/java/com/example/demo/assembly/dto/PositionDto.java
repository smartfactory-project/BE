package com.example.demo.assembly.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record PositionDto(
        long ts,
        Scene scene,
        World world
) {
    @JsonIgnoreProperties(ignoreUnknown = true)
    public record Scene(Point actual, Point production) {}

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record World(Point actual, Point production) {}

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record Point(double x, double y) {}
}
