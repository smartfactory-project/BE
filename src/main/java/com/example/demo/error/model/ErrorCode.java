package com.example.demo.error.model;

import java.time.LocalDateTime;

public class ErrorCode {
    private String errorCode;
    private String errorName;
    private String errorDesc;
    private String errorCategory;
    private String processType;
    private String defaultSeverity;
    private Boolean autoRecovery;
    private String solutionGuide;
    private Double thresholdValue;
    private String unit;
    private Boolean isActive;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // 기본 생성자
    public ErrorCode() {}

    // 전체 생성자
    public ErrorCode(String errorCode, String errorName, String errorDesc, 
                    String errorCategory, String processType, String defaultSeverity,
                    Boolean autoRecovery, String solutionGuide, Double thresholdValue,
                    String unit, Boolean isActive) {
        this.errorCode = errorCode;
        this.errorName = errorName;
        this.errorDesc = errorDesc;
        this.errorCategory = errorCategory;
        this.processType = processType;
        this.defaultSeverity = defaultSeverity;
        this.autoRecovery = autoRecovery;
        this.solutionGuide = solutionGuide;
        this.thresholdValue = thresholdValue;
        this.unit = unit;
        this.isActive = isActive;
    }

    // Getters and Setters
    public String getErrorCode() { return errorCode; }
    public void setErrorCode(String errorCode) { this.errorCode = errorCode; }

    public String getErrorName() { return errorName; }
    public void setErrorName(String errorName) { this.errorName = errorName; }

    public String getErrorDesc() { return errorDesc; }
    public void setErrorDesc(String errorDesc) { this.errorDesc = errorDesc; }

    public String getErrorCategory() { return errorCategory; }
    public void setErrorCategory(String errorCategory) { this.errorCategory = errorCategory; }

    public String getProcessType() { return processType; }
    public void setProcessType(String processType) { this.processType = processType; }

    public String getDefaultSeverity() { return defaultSeverity; }
    public void setDefaultSeverity(String defaultSeverity) { this.defaultSeverity = defaultSeverity; }

    public Boolean getAutoRecovery() { return autoRecovery; }
    public void setAutoRecovery(Boolean autoRecovery) { this.autoRecovery = autoRecovery; }

    public String getSolutionGuide() { return solutionGuide; }
    public void setSolutionGuide(String solutionGuide) { this.solutionGuide = solutionGuide; }

    public Double getThresholdValue() { return thresholdValue; }
    public void setThresholdValue(Double thresholdValue) { this.thresholdValue = thresholdValue; }

    public String getUnit() { return unit; }
    public void setUnit(String unit) { this.unit = unit; }

    public Boolean getIsActive() { return isActive; }
    public void setIsActive(Boolean isActive) { this.isActive = isActive; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}