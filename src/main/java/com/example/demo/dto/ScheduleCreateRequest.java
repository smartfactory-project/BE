package com.example.demo.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

public class ScheduleCreateRequest {

    @NotBlank
    @Size(max = 200)
    private String title;

    private String description;

    @NotBlank
    private String type; // meeting, task, review, training, milestone

    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul") // ✅ JSON 직렬화/역직렬화
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") // ✅ 스프링 MVC 바인딩
    private LocalDateTime startDateTime;

    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endDateTime;

    private String location;

    private Boolean isUrgent = false;

    // Getters and Setters
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public LocalDateTime getStartDateTime() {
        return startDateTime;
    }

    public void setStartDateTime(LocalDateTime startDateTime) {
        this.startDateTime = startDateTime;
    }

    public LocalDateTime getEndDateTime() {
        return endDateTime;
    }

    public void setEndDateTime(LocalDateTime endDateTime) {
        this.endDateTime = endDateTime;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Boolean getIsUrgent() {
        return isUrgent;
    }

    public void setIsUrgent(Boolean urgent) {
        isUrgent = urgent;
    }
}
