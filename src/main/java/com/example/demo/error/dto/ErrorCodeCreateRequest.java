package com.example.demo.error.dto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;  
import jakarta.validation.constraints.Size;


public class ErrorCodeCreateRequest {
    @NotBlank(message = "에러 코드는 필수입니다")
    @Size(max = 20, message = "에러 코드는 20자를 초과할 수 없습니다")
    private String errorCode;

    @NotBlank(message = "에러명은 필수입니다")
    @Size(max = 100, message = "에러명은 100자를 초과할 수 없습니다")
    private String errorName;

    private String errorDesc;

    @NotNull(message = "에러 카테고리는 필수입니다")
    private String errorCategory; // DEFECT, EQUIPMENT, COMMON

    @NotNull(message = "공정 타입은 필수입니다")
    private String processType; // PRESS, BODY, PAINT, ASSY, COMMON

    @NotNull(message = "기본 심각도는 필수입니다")
    private String defaultSeverity; // LOW, MEDIUM, HIGH, CRITICAL

    private Boolean autoRecovery = false;
    private String solutionGuide;
    private Double thresholdValue;
    private String unit;
    private Boolean isActive = true;

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
}