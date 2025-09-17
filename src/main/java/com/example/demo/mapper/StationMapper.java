package com.example.demo.mapper;

import com.example.demo.model.Station;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface StationMapper {
    List<Station> selectByLineAndFactory(@Param("lineId") String lineId,
                                         @Param("factoryId") int factoryId);
}
