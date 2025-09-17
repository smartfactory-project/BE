package com.example.demo.service;

import com.example.demo.dto.CarNameResponse;
import com.example.demo.mapper.CarNameMapper;
import com.example.demo.model.CarName;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CarNameService {

    private final CarNameMapper carNameMapper;

    public CarNameService(CarNameMapper carNameMapper) {
        this.carNameMapper = carNameMapper;
    }

    public List<CarNameResponse> getAll() {
        List<CarName> rows = carNameMapper.selectAll();
        List<CarNameResponse> list = new ArrayList<>();
        for (CarName r : rows) {
            list.add(new CarNameResponse(r.getCarId(), r.getCarName(), r.getImageUrl()));
        }
        return list;
    }

    public CarNameResponse getByCarName(String carName) {
        CarName row = carNameMapper.selectByCarName(carName);
        if (row == null) {
            throw new IllegalArgumentException("Unknown car_name: " + carName);
        }
        return new CarNameResponse(row.getCarId(), row.getCarName(), row.getImageUrl());
    }
}
