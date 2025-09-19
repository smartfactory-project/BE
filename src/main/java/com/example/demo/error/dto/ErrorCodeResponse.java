package com.example.demo.error.dto;

import com.example.demo.error.model.ErrorCode;
import java.time.LocalDateTime;

public class ErrorCodeResponse {
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
    public ErrorCodeResponse() {}

    // ErrorCode 엔티티를 받는 생성자
    public ErrorCodeResponse(ErrorCode errorCode) {
        this.errorCode = errorCode.getErrorCode();
        this.errorName = errorCode.getErrorName();
        this.errorDesc = errorCode.getErrorDesc();
        this.errorCategory = errorCode.getErrorCategory();
        this.processType = errorCode.getProcessType();
        this.defaultSeverity = errorCode.getDefaultSeverity();
        this.autoRecovery = errorCode.getAutoRecovery();
        this.solutionGuide = errorCode.getSolutionGuide();
        this.thresholdValue = errorCode.getThresholdValue();
        this.unit = errorCode.getUnit();
        this.isActive = errorCode.getIsActive();
        this.createdAt = errorCode.getCreatedAt();
        this.updatedAt = errorCode.getUpdatedAt();
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