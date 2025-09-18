package com.example.demo.routing.service;

import com.example.demo.routing.dto.CarNameResponse;
import com.example.demo.routing.mapper.CarNameMapper;
import com.example.demo.routing.model.CarName;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CarNameService {

    private final CarNameMapper carNameMapper;

    public CarNameService(CarNameMapper carNameMapper) {
        this.carNameMapper = carNameMapper;
    }

    public List<CarNameResponse> getByFactoryId(int factoryId) {
        List<CarName> rows = carNameMapper.selectByFactoryId(factoryId);
        List<CarNameResponse> list = new ArrayList<>();
        for (CarName r : rows) {
            list.add(new CarNameResponse(r.getCarId(), r.getCarName(), r.getImageUrl()));
        }
        return list;
    }
}
