package com.example.demo.model;

public class CarName {
    private String carId;
    private String carName;
    private String imageUrl;
    private int factoryId;

    public String getCarId() { return carId; }
    public void setCarId(String carId) { this.carId = carId; }

    public String getCarName() { return carName; }
    public void setCarName(String carName) { this.carName = carName; }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

    public int getFactoryId() { return factoryId; }
    public void setFactoryId(int factoryId) { this.factoryId = factoryId; }
}
