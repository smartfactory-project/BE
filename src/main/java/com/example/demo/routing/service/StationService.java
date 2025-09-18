package com.example.demo.routing.service;

import com.example.demo.routing.dto.StationResponse;
import com.example.demo.routing.mapper.StationMapper;
import com.example.demo.routing.model.Station;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StationService {

    private final StationMapper stationMapper;

    public StationService(StationMapper stationMapper) {
        this.stationMapper = stationMapper;
    }

    public List<StationResponse> getByLineAndFactory(String lineId, int factoryId) {
        if (lineId == null || lineId.isBlank()) {
            throw new IllegalArgumentException("lineId is required.");
        }
        if (factoryId <= 0) {
            throw new IllegalArgumentException("factoryId must be positive.");
        }

        List<Station> rows = stationMapper.selectByLineAndFactory(lineId, factoryId);
        List<StationResponse> out = new ArrayList<>();
        for (Station s : rows) {
            out.add(new StationResponse(
                    s.getStationId(), s.getLineId(), s.getFactoryId(), s.getProcessType(),
                    s.getLineName(), s.getStationName(), s.getStationType(), s.getStation()
            ));
        }
        return out; // 없으면 빈 배열
    }
}
