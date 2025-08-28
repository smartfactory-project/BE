package com.example.demo.mapper;

import com.example.demo.model.Schedule;
import com.example.demo.model.Task;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface ScheduleMapper {
        // 스케줄 관련
        int insertSchedule(Schedule schedule);

        List<Schedule> selectSchedulesByUserId(@Param("userId") Integer userId,
                        @Param("startDate") LocalDateTime startDate,
                        @Param("endDate") LocalDateTime endDate);

        Schedule selectScheduleById(@Param("scheduleId") Integer scheduleId);

        int updateSchedule(Schedule schedule);

        // ✅ 새로 추가: 일정 상태 업데이트
        int updateScheduleStatus(@Param("scheduleId") Integer scheduleId, @Param("status") String status);

        int deleteSchedule(@Param("scheduleId") Integer scheduleId);

        // 일정 필터링
        List<Schedule> selectSchedulesByUserIdAndType(@Param("userId") Integer userId,
                        @Param("type") String type,
                        @Param("startDate") LocalDateTime startDate,
                        @Param("endDate") LocalDateTime endDate);

        // 오늘의 일정
        List<Schedule> selectTodaySchedules(@Param("userId") Integer userId);

        // 다가오는 일정 (7일 이내)
        List<Schedule> selectUpcomingSchedules(@Param("userId") Integer userId, @Param("days") int days);

        // 할일 관련
        int insertTask(Task task);

        List<Task> selectTasksByUserId(@Param("userId") Integer userId);

        Task selectTaskById(@Param("taskId") Integer taskId);

        int updateTask(Task task);

        int updateTaskStatus(@Param("taskId") Integer taskId, @Param("status") String status);

        int deleteTask(@Param("taskId") Integer taskId);

        // 오늘 해야 할 일들
        List<Task> selectTodayTasks(@Param("userId") Integer userId);

        // 완료되지 않은 할일들
        List<Task> selectPendingTasks(@Param("userId") Integer userId);

        // 통계 관련
        int countSchedulesByStatus(@Param("userId") Integer userId, @Param("status") String status);

        int countTasksByStatus(@Param("userId") Integer userId, @Param("status") String status);
}