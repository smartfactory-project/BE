package com.example.demo.mapper;

import com.example.demo.dto.FactoryResponse;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface FactoryMapper {
    List<FactoryResponse> findAllFactories();
}
