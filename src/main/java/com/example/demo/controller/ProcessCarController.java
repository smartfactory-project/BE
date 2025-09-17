package com.example.demo.controller;

import com.example.demo.dto.CarNameResponse;
import com.example.demo.service.CarNameService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/process/routings/car")
public class ProcessCarController {

    private final CarNameService carNameService;

    public ProcessCarController(CarNameService carNameService) {
        this.carNameService = carNameService;
    }

    /**
     * factory_id로 해당 공장의 차량 목록 조회
     * 예: GET /process/routings/car?factoryId=1
     */
    @GetMapping
    public ResponseEntity<List<CarNameResponse>> getByFactory(@RequestParam("factoryId") int factoryId) {
        return ResponseEntity.ok(carNameService.getByFactoryId(factoryId));
    }
}
