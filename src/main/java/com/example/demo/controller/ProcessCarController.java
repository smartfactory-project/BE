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

    /** 전체 차량 리스트 */
    @GetMapping
    public ResponseEntity<List<CarNameResponse>> getAll() {
        return ResponseEntity.ok(carNameService.getAll());
    }

    /** 단일 차량 조회 (옵션: 유지) */
    @GetMapping("/{carName}")
    public ResponseEntity<CarNameResponse> getByCarName(@PathVariable String carName) {
        return ResponseEntity.ok(carNameService.getByCarName(carName));
    }
}
