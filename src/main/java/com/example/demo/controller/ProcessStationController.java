package com.example.demo.controller;

import com.example.demo.dto.StationResponse;
import com.example.demo.service.StationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/process/routings/stations")
public class ProcessStationController {

    private final StationService stationService;

    public ProcessStationController(StationService stationService) {
        this.stationService = stationService;
    }

    /**
     * 라인/공장 기준 스테이션 목록 조회
     * 예) GET /process/routings/stations?lineId=PRESS-01&factoryId=1
     */
    @GetMapping
    public ResponseEntity<List<StationResponse>> getByLineAndFactory(
            @RequestParam("lineId") String lineId,
            @RequestParam("factoryId") Integer factoryId) {

        if (factoryId == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(stationService.getByLineAndFactory(lineId, factoryId));
    }
}
