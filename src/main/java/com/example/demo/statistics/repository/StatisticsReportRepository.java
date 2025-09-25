
package com.example.demo.statistics.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.statistics.model.StatisticsReport;

public interface StatisticsReportRepository extends JpaRepository<StatisticsReport, Long> {
    // simulation_name, object_name 기준 조회 (upsert용)
    List<StatisticsReport> findBySimulationNameAndObjectName(String simulationName, String objectName);
    // simulation_name으로 전체 조회
    List<StatisticsReport> findBySimulationName(String simulationName);

    // simulation_name, object_name, simulation_time으로 조회 (upsert용)
    List<StatisticsReport> findBySimulationNameAndObjectNameAndSimulationTime(String simulationName, String objectName, java.time.LocalDateTime simulationTime);
}