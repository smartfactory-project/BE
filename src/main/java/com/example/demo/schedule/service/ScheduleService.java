package com.example.demo.schedule.service;

import com.example.demo.schedule.dto.ScheduleCreateRequest;
import com.example.demo.schedule.dto.ScheduleResponse;
import com.example.demo.schedule.dto.TaskCreateRequest;
import com.example.demo.schedule.dto.TaskResponse;
import com.example.demo.schedule.mapper.ScheduleMapper;
import com.example.demo.auth.mapper.UserMapper;
import com.example.demo.schedule.model.Schedule;
import com.example.demo.schedule.model.Task;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.LocalDateTime;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
import java.util.Arrays;

@Service
public class ScheduleService {

    private final ScheduleMapper scheduleMapper;
    private final UserMapper userMapper;

    public ScheduleService(ScheduleMapper scheduleMapper, UserMapper userMapper) {
        this.scheduleMapper = scheduleMapper;
        this.userMapper = userMapper;
    }

    private Integer getCurrentUserId() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Integer userId = userMapper.findIdByUsername(username);
        if (userId == null) {
            throw new IllegalStateException("사용자를 찾을 수 없습니다.");
        }
        return userId;
    }

    // 스케줄 관련 메서드들
    @Transactional
    public ScheduleResponse createSchedule(ScheduleCreateRequest req) {
        Integer userId = getCurrentUserId();

        Schedule schedule = new Schedule();
        schedule.setUserId(userId);
        schedule.setTitle(req.getTitle());
        schedule.setDescription(req.getDescription());
        schedule.setType(req.getType());
        schedule.setStartDateTime(req.getStartDateTime());
        schedule.setEndDateTime(req.getEndDateTime());
        schedule.setLocation(req.getLocation());
        schedule.setIsUrgent(req.getIsUrgent());
        schedule.setStatus("planned");
        schedule.setIsActive(true);

        int rows = scheduleMapper.insertSchedule(schedule);
        if (rows != 1 || schedule.getScheduleId() == null) {
            throw new IllegalStateException("일정 저장 실패");
        }

        Schedule saved = scheduleMapper.selectScheduleById(schedule.getScheduleId());
        return convertToScheduleResponse(saved);
    }

    public List<ScheduleResponse> getSchedulesByMonth(int year, int month) {
        Integer userId = getCurrentUserId();

        LocalDateTime startDate = LocalDateTime.of(year, month, 1, 0, 0);
        LocalDateTime endDate = startDate.plusMonths(1).minusSeconds(1);

        List<Schedule> schedules = scheduleMapper.selectSchedulesByUserId(userId, startDate, endDate);
        return schedules.stream().map(this::convertToScheduleResponse).toList();
    }

    public List<ScheduleResponse> getSchedulesByType(String type, int year, int month) {
        Integer userId = getCurrentUserId();

        LocalDateTime startDate = LocalDateTime.of(year, month, 1, 0, 0);
        LocalDateTime endDate = startDate.plusMonths(1).minusSeconds(1);

        List<Schedule> schedules = scheduleMapper.selectSchedulesByUserIdAndType(userId, type, startDate, endDate);
        return schedules.stream().map(this::convertToScheduleResponse).toList();
    }

    public List<ScheduleResponse> getTodaySchedules() {
        Integer userId = getCurrentUserId();
        List<Schedule> schedules = scheduleMapper.selectTodaySchedules(userId);
        return schedules.stream().map(this::convertToScheduleResponse).toList();
    }

    public List<ScheduleResponse> getUpcomingSchedules(int days) {
        Integer userId = getCurrentUserId();
        List<Schedule> schedules = scheduleMapper.selectUpcomingSchedules(userId, days);
        return schedules.stream().map(this::convertToScheduleResponse).toList();
    }

    @Transactional
    public ScheduleResponse updateSchedule(Integer scheduleId, ScheduleCreateRequest req) {
        Schedule existing = scheduleMapper.selectScheduleById(scheduleId);
        if (existing == null) {
            throw new IllegalArgumentException("일정을 찾을 수 없습니다.");
        }

        // 권한 체크
        Integer userId = getCurrentUserId();
        if (!existing.getUserId().equals(userId)) {
            throw new IllegalStateException("일정을 수정할 권한이 없습니다.");
        }

        existing.setTitle(req.getTitle());
        existing.setDescription(req.getDescription());
        existing.setType(req.getType());
        existing.setStartDateTime(req.getStartDateTime());
        existing.setEndDateTime(req.getEndDateTime());
        existing.setLocation(req.getLocation());
        existing.setIsUrgent(req.getIsUrgent());

        scheduleMapper.updateSchedule(existing);
        Schedule updated = scheduleMapper.selectScheduleById(scheduleId);
        return convertToScheduleResponse(updated);
    }

    // ✅ 새로 추가: 일정 상태 변경 메서드
    @Transactional
    public ScheduleResponse updateScheduleStatus(Integer scheduleId, String status) {
        try {
            Schedule existing = scheduleMapper.selectScheduleById(scheduleId);
            if (existing == null) {
                throw new IllegalArgumentException("일정을 찾을 수 없습니다.");
            }

            // 권한 체크
            Integer userId = getCurrentUserId();
            if (!existing.getUserId().equals(userId)) {
                throw new IllegalStateException("일정을 수정할 권한이 없습니다.");
            }

            // 유효한 상태값인지 확인
            if (!Arrays.asList("planned", "in_progress", "completed", "cancelled").contains(status)) {
                throw new IllegalArgumentException("유효하지 않은 상태값입니다: " + status);
            }

            // 상태 업데이트
            int updatedRows = scheduleMapper.updateScheduleStatus(scheduleId, status);
            if (updatedRows == 0) {
                throw new IllegalStateException("일정 상태 업데이트에 실패했습니다.");
            }

            // 업데이트된 일정 조회
            Schedule updated = scheduleMapper.selectScheduleById(scheduleId);
            if (updated == null) {
                throw new IllegalStateException("업데이트된 일정을 조회할 수 없습니다.");
            }

            return convertToScheduleResponse(updated);
        } catch (Exception e) {
            System.err.println("Schedule status update error for scheduleId: " + scheduleId + ", status: " + status + ", error: " + e.getMessage());
            throw e;
        }
    }

    @Transactional
    public void deleteSchedule(Integer scheduleId) {
        Schedule existing = scheduleMapper.selectScheduleById(scheduleId);
        if (existing == null) {
            throw new IllegalArgumentException("일정을 찾을 수 없습니다.");
        }

        // 권한 체크
        Integer userId = getCurrentUserId();
        if (!existing.getUserId().equals(userId)) {
            throw new IllegalStateException("일정을 삭제할 권한이 없습니다.");
        }

        scheduleMapper.deleteSchedule(scheduleId);
    }

    // 할일 관련 메서드들
    @Transactional
    public TaskResponse createTask(TaskCreateRequest req) {
        Integer userId = getCurrentUserId();

        Task task = new Task();
        task.setUserId(userId);
        task.setTitle(req.getTitle());
        task.setDescription(req.getDescription());
        task.setPriority(req.getPriority());
        task.setDueDate(req.getDueDate());
        task.setStatus("pending");
        task.setIsActive(true);

        int rows = scheduleMapper.insertTask(task);
        if (rows != 1 || task.getTaskId() == null) {
            throw new IllegalStateException("할일 저장 실패");
        }

        Task saved = scheduleMapper.selectTaskById(task.getTaskId());
        return convertToTaskResponse(saved);
    }

    public List<TaskResponse> getTasks() {
        Integer userId = getCurrentUserId();
        List<Task> tasks = scheduleMapper.selectTasksByUserId(userId);
        return tasks.stream().map(this::convertToTaskResponse).toList();
    }

    public List<TaskResponse> getTodayTasks() {
        Integer userId = getCurrentUserId();
        List<Task> tasks = scheduleMapper.selectTodayTasks(userId);
        return tasks.stream().map(this::convertToTaskResponse).toList();
    }

    public List<TaskResponse> getPendingTasks() {
        Integer userId = getCurrentUserId();
        List<Task> tasks = scheduleMapper.selectPendingTasks(userId);
        return tasks.stream().map(this::convertToTaskResponse).toList();
    }

    // 할일 전체 업데이트 (중요도 변경용)
    @Transactional
    public TaskResponse updateTask(Integer taskId, TaskCreateRequest req) {
        Task existing = scheduleMapper.selectTaskById(taskId);
        if (existing == null) {
            throw new IllegalArgumentException("할일을 찾을 수 없습니다.");
        }

        // 권한 체크
        Integer userId = getCurrentUserId();
        if (!existing.getUserId().equals(userId)) {
            throw new IllegalStateException("할일을 수정할 권한이 없습니다.");
        }

        existing.setTitle(req.getTitle());
        existing.setDescription(req.getDescription());
        existing.setPriority(req.getPriority());
        existing.setDueDate(req.getDueDate());

        int updatedRows = scheduleMapper.updateTask(existing);
        if (updatedRows == 0) {
            throw new IllegalStateException("할일 업데이트에 실패했습니다.");
        }

        Task updated = scheduleMapper.selectTaskById(taskId);
        return convertToTaskResponse(updated);
    }

    @Transactional
    public TaskResponse updateTaskStatus(Integer taskId, String status) {
        try {
            Task existing = scheduleMapper.selectTaskById(taskId);
            if (existing == null) {
                throw new IllegalArgumentException("할일을 찾을 수 없습니다.");
            }

            // 권한 체크
            Integer userId = getCurrentUserId();
            if (!existing.getUserId().equals(userId)) {
                throw new IllegalStateException("할일을 수정할 권한이 없습니다.");
            }

            // 유효한 상태값인지 확인
            if (!Arrays.asList("pending", "in_progress", "completed").contains(status)) {
                throw new IllegalArgumentException("유효하지 않은 상태값입니다: " + status);
            }

            // 상태 업데이트
            int updatedRows = scheduleMapper.updateTaskStatus(taskId, status);
            if (updatedRows == 0) {
                throw new IllegalStateException("할일 상태 업데이트에 실패했습니다.");
            }

            // 업데이트된 할일 조회
            Task updated = scheduleMapper.selectTaskById(taskId);
            if (updated == null) {
                throw new IllegalStateException("업데이트된 할일을 조회할 수 없습니다.");
            }

            return convertToTaskResponse(updated);
        } catch (Exception e) {
            // 로그 출력
            System.err.println("TaskStatus update error for taskId: " + taskId + ", status: " + status + ", error: "
                    + e.getMessage());
            throw e;
        }
    }

    @Transactional
    public void deleteTask(Integer taskId) {
        Task existing = scheduleMapper.selectTaskById(taskId);
        if (existing == null) {
            throw new IllegalArgumentException("할일을 찾을 수 없습니다.");
        }

        // 권한 체크
        Integer userId = getCurrentUserId();
        if (!existing.getUserId().equals(userId)) {
            throw new IllegalStateException("할일을 삭제할 권한이 없습니다.");
        }

        scheduleMapper.deleteTask(taskId);
    }

    // ✅ 수정된 통계 메서드 (완료율 포함)
    public Map<String, Object> getScheduleStats() {
        Integer userId = getCurrentUserId();

        Map<String, Object> stats = new HashMap<>();
        
        // 기본 통계
        stats.put("todayCount", scheduleMapper.selectTodaySchedules(userId).size());
        stats.put("upcomingCount", scheduleMapper.selectUpcomingSchedules(userId, 7).size());
        
        // 일정 완료 통계
        int totalSchedules = scheduleMapper.countSchedulesByStatus(userId, null);
        int completedSchedules = scheduleMapper.countSchedulesByStatus(userId, "completed");
        
        // 할일 완료 통계  
        int totalTasks = scheduleMapper.countTasksByStatus(userId, null);
        int completedTasks = scheduleMapper.countTasksByStatus(userId, "completed");
        
        stats.put("totalCount", totalSchedules + totalTasks);
        stats.put("completedCount", completedSchedules + completedTasks);
        
        // 완료율 계산
        int overallTotal = totalSchedules + totalTasks;
        int overallCompleted = completedSchedules + completedTasks;
        double completionRate = overallTotal > 0 ? (double) overallCompleted / overallTotal * 100 : 0;
        stats.put("completionRate", Math.round(completionRate));

        return stats;
    }

    // 변환 메서드들
    private ScheduleResponse convertToScheduleResponse(Schedule schedule) {
        return new ScheduleResponse(
                schedule.getScheduleId(),
                schedule.getUserId(),
                schedule.getTitle(),
                schedule.getDescription(),
                schedule.getType(),
                schedule.getStartDateTime(),
                schedule.getEndDateTime(),
                schedule.getLocation(),
                schedule.getStatus(),
                schedule.getIsUrgent(),
                schedule.getIsActive(),
                schedule.getCreatedAt(),
                schedule.getUpdatedAt());
    }

    private TaskResponse convertToTaskResponse(Task task) {
        return new TaskResponse(
                task.getTaskId(),
                task.getUserId(),
                task.getTitle(),
                task.getDescription(),
                task.getPriority(),
                task.getStatus(),
                task.getDueDate(),
                task.getIsActive(),
                task.getCreatedAt(),
                task.getUpdatedAt());
    }

}