package com.example.demo.statistics.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.*;
import com.example.demo.statistics.model.StatisticsReport;

import com.example.demo.statistics.service.StatisticsReportService;

@RestController
@RequestMapping("/api/statistics")
public class StatisticsReportController {

    private final StatisticsReportService statisticsReportService;

    public StatisticsReportController(StatisticsReportService statisticsReportService) {
        this.statisticsReportService = statisticsReportService;
    }

    @CrossOrigin(origins = "*")
    @PostMapping("/upload")
    public ResponseEntity<?> uploadReport(@RequestParam("file") MultipartFile file) {
        try {
            // 파일명에서 확장자 제거 (예: press1.htm -> press1)
            String originalFilename = file.getOriginalFilename();
            String simulationName = originalFilename != null ? originalFilename.replaceFirst("\\.[^.]+$", "") : "unknown";
            statisticsReportService.parseAndSaveReport(file, simulationName);
            return ResponseEntity.ok().body("업로드 및 저장 성공");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("업로드 실패: " + e.getMessage());
        }
    }

    // 생산현황 개요(overview) API
    @CrossOrigin(origins = "*")
    @GetMapping("/overview")
    public ResponseEntity<?> getOverview(@RequestParam(value = "factory", required = false) String factory) {
        // simulation_name 파라미터 받기 (예: press1)
        String simulationName = factory != null ? factory : null;
    double todayOutput = simulationName != null ? statisticsReportService.getAverageExitsBySimulationName(simulationName) : 0.0;
    int targetOutput = 100;
    // 품질 합격률: 100 - failed 평균
    double avgFailed = simulationName != null ? statisticsReportService.getAverageFailedBySimulationName(simulationName) : 0.0;
    double qualityRate = 100.0 - avgFailed;
    double avgRunningRate = simulationName != null ? statisticsReportService.getAverageWorkingBySimulationName(simulationName) : 0.0;
    double targetRunningRate = Math.round(avgRunningRate * 0.95 * 10) / 10.0;
    Map<String, Object> result = new LinkedHashMap<>();
    result.put("todayOutput", todayOutput);
    result.put("targetOutput", targetOutput);
    result.put("qualityRate", qualityRate);
    result.put("runningRate", avgRunningRate);
    result.put("targetRunningRate", targetRunningRate);
    return ResponseEntity.ok(result);
    }

    // 생산 라인 상세 현황 API
    @CrossOrigin(origins = "*")
    @GetMapping("/lines")
    public ResponseEntity<?> getLineDetails() {
        List<StatisticsReport> reports = statisticsReportService.getLatestReports();
        List<Map<String, Object>> lines = new ArrayList<>();
        for (StatisticsReport r : reports) {
            Map<String, Object> line = new LinkedHashMap<>();
            line.put("id", r.getId());
            line.put("name", r.getObjectName());
            line.put("status", r.getWorking() > 80 ? "running" : (r.getFailed() > 5 ? "warning" : "maintenance"));
            line.put("efficiency", r.getWorking());
            line.put("currentOutput", Optional.ofNullable(r.getExits()).orElse(0));
            line.put("targetOutput", 100); // 라인별 목표 생산량 고정
            line.put("quality", Optional.ofNullable(r.getRelativeFull()).orElse(0.0));
            line.put("workers", 6); // 예시
            line.put("shift", "주간");
            line.put("lastMaintenance", "3일 전");
            line.put("nextMaintenance", "1주 후");
            line.put("issues", r.getFailed() > 5 ? Arrays.asList("고장률 높음") : Collections.emptyList());
            lines.add(line);
        }
        return ResponseEntity.ok(lines);
    }
}