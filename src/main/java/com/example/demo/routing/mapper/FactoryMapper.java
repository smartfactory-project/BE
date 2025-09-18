package com.example.demo.routing.mapper;

import com.example.demo.routing.dto.FactoryResponse;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface FactoryMapper {
    List<FactoryResponse> findAllFactories();
}
