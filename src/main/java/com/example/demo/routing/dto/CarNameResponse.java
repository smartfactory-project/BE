package com.example.demo.routing.dto;

public class CarNameResponse {
    private String carId;
    private String carName;
    private String imageUrl;

    public CarNameResponse(String carId, String carName, String imageUrl) {
        this.carId = carId;
        this.carName = carName;
        this.imageUrl = imageUrl;
    }

    public String getCarId() { return carId; }
    public String getCarName() { return carName; }
    public String getImageUrl() { return imageUrl; }
}
