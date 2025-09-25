package com.example.demo.error.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.demo.error.model.ErrorCode;

@Mapper
public interface ErrorCodeMapper {
    
    // 전체 조회
    List<ErrorCode> selectAll();
    
    // 조건별 조회
    List<ErrorCode> selectByProcessType(@Param("processType") String processType);
    List<ErrorCode> selectByCategory(@Param("category") String category);
    List<ErrorCode> selectActiveOnly();
    
    // 단건 조회
    ErrorCode selectByCode(@Param("errorCode") String errorCode);
    
    // 생성
    int insert(ErrorCode errorCode);
    
    // 수정
    int update(ErrorCode errorCode);
    
    // 삭제 (실제 삭제)
    int delete(@Param("errorCode") String errorCode);
    
    // 논리 삭제 (isActive = false)
    int deactivate(@Param("errorCode") String errorCode);
    
    // 활성화
    int activate(@Param("errorCode") String errorCode);
    
    // 존재 여부 확인
    boolean existsByCode(@Param("errorCode") String errorCode);
    
    // 카운트
    int countAll();
    int countByProcessType(@Param("processType") String processType);
}