package com.example.demo.assembly.controller;

import com.example.demo.assembly.dto.PositionDto;
import com.example.demo.assembly.service.PositionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RestController
@RequestMapping("/api/position")
public class PositionController {

    private final PositionService positionService;

    public PositionController(PositionService positionService) {
        this.positionService = positionService;
    }

    /** 최신 한 건 반환 */
    @GetMapping
    public ResponseEntity<?> latest() {
        PositionDto dto = positionService.latest();
        if (dto == null) return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        return ResponseEntity.ok(dto);
    }

    /** SSE 스트림 (EventSource로 구독) */
    @GetMapping("/stream")
    public SseEmitter stream() {
        // 30분 타임아웃
        return positionService.registerEmitter(30 * 60 * 1000L);
    }
}
