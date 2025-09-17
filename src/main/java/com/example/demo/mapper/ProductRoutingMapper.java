package com.example.demo.mapper;

import com.example.demo.model.ProductRouting;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ProductRoutingMapper {
    List<ProductRouting> selectByProductId(@Param("productId") String productId);
    int deleteByProductId(@Param("productId") String productId);

    int insertNode(ProductRouting node);
    int insertNodes(@Param("nodes") List<ProductRouting> nodes);

    int updateNode(ProductRouting node);
    int deleteByRoutingId(@Param("routingId") Long routingId);

    // 라인 존재 확인 (STATION.line_id 기준)
    Integer countLineIdExists(@Param("lineId") String lineId);

    // product 모델 존재 확인 (PRODUCT_MODEL)
    Integer countProductModel(@Param("productId") String productId);
}
