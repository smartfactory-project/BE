package com.example.demo.routing.mapper;

import com.example.demo.routing.model.ProductRouting;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ProductRoutingMapper {
    List<ProductRouting> selectByProductId(@Param("productId") String productId);

}
