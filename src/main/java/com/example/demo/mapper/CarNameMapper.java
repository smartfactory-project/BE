package com.example.demo.mapper;

import com.example.demo.model.CarName;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CarNameMapper {
    CarName selectByCarName(@Param("carName") String carName);
    List<CarName> selectAll();
}
