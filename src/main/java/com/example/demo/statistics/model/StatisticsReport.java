package com.example.demo.statistics.model;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "statistics_report")
public class StatisticsReport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String simulationName;
    private String simulationFile;
    private LocalDateTime simulationTime;
    private String objectName;
    private double working;
    private double setup;
    private double waiting;
    private double blocking;
    private double powering;
    private double failed;
    private double stopped;
    private double paused;
    private double unplanned;
    private double portion;


    // 물류/재고 흐름
    private Integer entries;              // 입고 횟수
    private Integer exits;                // 출고 횟수
    private Integer minContents;          // 최소 재고
    private Integer maxContents;          // 최대 재고
    private Double relativeEmpty;         // 비어있는 비율(%)
    private Double relativeFull;          // 가득참 비율(%)
    private Double occupationNoInterrupt; // 점유율(중단X)
    private Double occupationWithInterrupt; // 점유율(중단O)

    // 시간 통계(합계, 평균, 표준편차)
    private Double workingTimeSum;
    private Double workingTimeMean;
    private Double workingTimeStd;
    private Double waitingTimeSum;
    private Double waitingTimeMean;
    private Double waitingTimeStd;
    private Double blockedTimeSum;
    private Double blockedTimeMean;
    private Double blockedTimeStd;
    private Double failedTimeSum;
    private Double failedTimeMean;
    private Double failedTimeStd;

    private LocalDateTime createdAt = LocalDateTime.now();

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getSimulationName() { return simulationName; }
    public void setSimulationName(String simulationName) { this.simulationName = simulationName; }

    public String getSimulationFile() { return simulationFile; }
    public void setSimulationFile(String simulationFile) { this.simulationFile = simulationFile; }

    public LocalDateTime getSimulationTime() { return simulationTime; }
    public void setSimulationTime(LocalDateTime simulationTime) { this.simulationTime = simulationTime; }

    public String getObjectName() { return objectName; }
    public void setObjectName(String objectName) { this.objectName = objectName; }

    public double getWorking() { return working; }
    public void setWorking(double working) { this.working = working; }

    public double getSetup() { return setup; }
    public void setSetup(double setup) { this.setup = setup; }

    public double getWaiting() { return waiting; }
    public void setWaiting(double waiting) { this.waiting = waiting; }

    public double getBlocking() { return blocking; }
    public void setBlocking(double blocking) { this.blocking = blocking; }

    public double getPowering() { return powering; }
    public void setPowering(double powering) { this.powering = powering; }

    public double getFailed() { return failed; }
    public void setFailed(double failed) { this.failed = failed; }

    public double getStopped() { return stopped; }
    public void setStopped(double stopped) { this.stopped = stopped; }

    public double getPaused() { return paused; }
    public void setPaused(double paused) { this.paused = paused; }

    public double getUnplanned() { return unplanned; }
    public void setUnplanned(double unplanned) { this.unplanned = unplanned; }

    public double getPortion() { return portion; }
    public void setPortion(double portion) { this.portion = portion; }

    public Integer getEntries() { return entries; }
    public void setEntries(Integer entries) { this.entries = entries; }

    public Integer getExits() { return exits; }
    public void setExits(Integer exits) { this.exits = exits; }

    public Integer getMinContents() { return minContents; }
    public void setMinContents(Integer minContents) { this.minContents = minContents; }

    public Integer getMaxContents() { return maxContents; }
    public void setMaxContents(Integer maxContents) { this.maxContents = maxContents; }

    public Double getRelativeEmpty() { return relativeEmpty; }
    public void setRelativeEmpty(Double relativeEmpty) { this.relativeEmpty = relativeEmpty; }

    public Double getRelativeFull() { return relativeFull; }
    public void setRelativeFull(Double relativeFull) { this.relativeFull = relativeFull; }

    public Double getOccupationNoInterrupt() { return occupationNoInterrupt; }
    public void setOccupationNoInterrupt(Double occupationNoInterrupt) { this.occupationNoInterrupt = occupationNoInterrupt; }

    public Double getOccupationWithInterrupt() { return occupationWithInterrupt; }
    public void setOccupationWithInterrupt(Double occupationWithInterrupt) { this.occupationWithInterrupt = occupationWithInterrupt; }

    public Double getWorkingTimeSum() { return workingTimeSum; }
    public void setWorkingTimeSum(Double workingTimeSum) { this.workingTimeSum = workingTimeSum; }

    public Double getWorkingTimeMean() { return workingTimeMean; }
    public void setWorkingTimeMean(Double workingTimeMean) { this.workingTimeMean = workingTimeMean; }

    public Double getWorkingTimeStd() { return workingTimeStd; }
    public void setWorkingTimeStd(Double workingTimeStd) { this.workingTimeStd = workingTimeStd; }

    public Double getWaitingTimeSum() { return waitingTimeSum; }
    public void setWaitingTimeSum(Double waitingTimeSum) { this.waitingTimeSum = waitingTimeSum; }

    public Double getWaitingTimeMean() { return waitingTimeMean; }
    public void setWaitingTimeMean(Double waitingTimeMean) { this.waitingTimeMean = waitingTimeMean; }

    public Double getWaitingTimeStd() { return waitingTimeStd; }
    public void setWaitingTimeStd(Double waitingTimeStd) { this.waitingTimeStd = waitingTimeStd; }

    public Double getBlockedTimeSum() { return blockedTimeSum; }
    public void setBlockedTimeSum(Double blockedTimeSum) { this.blockedTimeSum = blockedTimeSum; }

    public Double getBlockedTimeMean() { return blockedTimeMean; }
    public void setBlockedTimeMean(Double blockedTimeMean) { this.blockedTimeMean = blockedTimeMean; }

    public Double getBlockedTimeStd() { return blockedTimeStd; }
    public void setBlockedTimeStd(Double blockedTimeStd) { this.blockedTimeStd = blockedTimeStd; }

    public Double getFailedTimeSum() { return failedTimeSum; }
    public void setFailedTimeSum(Double failedTimeSum) { this.failedTimeSum = failedTimeSum; }

    public Double getFailedTimeMean() { return failedTimeMean; }
    public void setFailedTimeMean(Double failedTimeMean) { this.failedTimeMean = failedTimeMean; }

    public Double getFailedTimeStd() { return failedTimeStd; }
    public void setFailedTimeStd(Double failedTimeStd) { this.failedTimeStd = failedTimeStd; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}