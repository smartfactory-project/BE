package com.example.demo.service;

import com.example.demo.dto.FactoryResponse;
import com.example.demo.mapper.FactoryMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FactoryService {

    private final FactoryMapper factoryMapper;

    public FactoryService(FactoryMapper factoryMapper) {
        this.factoryMapper = factoryMapper;
    }

    public List<FactoryResponse> getFactories() {
        return factoryMapper.findAllFactories();
    }
}
