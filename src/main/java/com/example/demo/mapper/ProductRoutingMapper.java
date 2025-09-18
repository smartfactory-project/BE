package com.example.demo.mapper;

import com.example.demo.model.ProductRouting;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ProductRoutingMapper {
    List<ProductRouting> selectByProductId(@Param("productId") String productId);

}
