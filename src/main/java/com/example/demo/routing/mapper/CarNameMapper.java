package com.example.demo.routing.mapper;

import com.example.demo.routing.model.CarName;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CarNameMapper {
    List<CarName> selectByFactoryId(@Param("factoryId") int factoryId);
}
