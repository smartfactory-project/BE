package com.example.demo.schedule.controller;

import com.example.demo.schedule.dto.ScheduleCreateRequest;
import com.example.demo.schedule.dto.ScheduleResponse;
import com.example.demo.schedule.dto.TaskCreateRequest;
import com.example.demo.schedule.dto.TaskResponse;
import com.example.demo.schedule.service.ScheduleService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/schedules")
@CrossOrigin(origins = "*", allowedHeaders = "*", methods = {
        RequestMethod.GET,
        RequestMethod.POST,
        RequestMethod.PUT,
        RequestMethod.PATCH,
        RequestMethod.DELETE,
        RequestMethod.OPTIONS
})
public class ScheduleController {

    private final ScheduleService scheduleService;

    public ScheduleController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    // 스케줄 관련 엔드포인트
    @PostMapping
    public ResponseEntity<ScheduleResponse> createSchedule(@Valid @RequestBody ScheduleCreateRequest req) {
        ScheduleResponse created = scheduleService.createSchedule(req);
        return ResponseEntity
                .created(URI.create("/api/schedules/" + created.getId()))
                .body(created);
    }

    @GetMapping
    public ResponseEntity<List<ScheduleResponse>> getSchedules(
            @RequestParam(required = false) String type,
            @RequestParam(defaultValue = "2025") int year,
            @RequestParam(defaultValue = "1") int month) {
        List<ScheduleResponse> schedules;
        if (type != null && !type.equals("all")) {
            schedules = scheduleService.getSchedulesByType(type, year, month);
        } else {
            schedules = scheduleService.getSchedulesByMonth(year, month);
        }
        return ResponseEntity.ok(schedules);
    }

    @GetMapping("/today")
    public ResponseEntity<List<ScheduleResponse>> getTodaySchedules() {
        List<ScheduleResponse> schedules = scheduleService.getTodaySchedules();
        return ResponseEntity.ok(schedules);
    }

    @GetMapping("/upcoming")
    public ResponseEntity<List<ScheduleResponse>> getUpcomingSchedules(
            @RequestParam(defaultValue = "7") int days) {
        List<ScheduleResponse> schedules = scheduleService.getUpcomingSchedules(days);
        return ResponseEntity.ok(schedules);
    }

    @PutMapping("/{scheduleId}")
    public ResponseEntity<ScheduleResponse> updateSchedule(
            @PathVariable Integer scheduleId,
            @Valid @RequestBody ScheduleCreateRequest req) {
        ScheduleResponse updated = scheduleService.updateSchedule(scheduleId, req);
        return ResponseEntity.ok(updated);
    }

    // ✅ 새로 추가: 일정 상태 변경 (완료/취소 등)
    @PutMapping("/{scheduleId}/status")
    public ResponseEntity<ScheduleResponse> updateScheduleStatus(
            @PathVariable Integer scheduleId,
            @RequestBody Map<String, String> request) {
        String status = request.get("status");
        if (status == null || status.trim().isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        ScheduleResponse updated = scheduleService.updateScheduleStatus(scheduleId, status);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{scheduleId}")
    public ResponseEntity<Void> deleteSchedule(@PathVariable Integer scheduleId) {
        scheduleService.deleteSchedule(scheduleId);
        return ResponseEntity.noContent().build();
    }

    // 할일 관련 엔드포인트
    @PostMapping("/tasks")
    public ResponseEntity<TaskResponse> createTask(@Valid @RequestBody TaskCreateRequest req) {
        TaskResponse created = scheduleService.createTask(req);
        return ResponseEntity
                .created(URI.create("/api/schedules/tasks/" + created.getId()))
                .body(created);
    }

    @GetMapping("/tasks")
    public ResponseEntity<List<TaskResponse>> getTasks() {
        List<TaskResponse> tasks = scheduleService.getTasks();
        return ResponseEntity.ok(tasks);
    }

    @GetMapping("/tasks/today")
    public ResponseEntity<List<TaskResponse>> getTodayTasks() {
        List<TaskResponse> tasks = scheduleService.getTodayTasks();
        return ResponseEntity.ok(tasks);
    }

    @GetMapping("/tasks/pending")
    public ResponseEntity<List<TaskResponse>> getPendingTasks() {
        List<TaskResponse> tasks = scheduleService.getPendingTasks();
        return ResponseEntity.ok(tasks);
    }

    @PutMapping("/tasks/{taskId}")
    public ResponseEntity<TaskResponse> updateTask(
            @PathVariable Integer taskId,
            @Valid @RequestBody TaskCreateRequest req) {
        TaskResponse updated = scheduleService.updateTask(taskId, req);
        return ResponseEntity.ok(updated);
    }

    @PatchMapping("/tasks/{taskId}/status")
    public ResponseEntity<TaskResponse> updateTaskStatus(
            @PathVariable Integer taskId,
            @RequestBody Map<String, String> request) {
        String status = request.get("status");
        if (status == null || status.trim().isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        TaskResponse updated = scheduleService.updateTaskStatus(taskId, status);
        return ResponseEntity.ok(updated);
    }

    @PutMapping("/tasks/{taskId}/status")
    public ResponseEntity<TaskResponse> updateTaskStatusPut(
            @PathVariable Integer taskId,
            @RequestBody Map<String, String> request) {
        String status = request.get("status");
        if (status == null || status.trim().isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        TaskResponse updated = scheduleService.updateTaskStatus(taskId, status);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/tasks/{taskId}")
    public ResponseEntity<Void> deleteTask(@PathVariable Integer taskId) {
        scheduleService.deleteTask(taskId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/stats")
    public ResponseEntity<Map<String, Object>> getScheduleStats() {
        Map<String, Object> stats = scheduleService.getScheduleStats();
        return ResponseEntity.ok(stats);
    }
}