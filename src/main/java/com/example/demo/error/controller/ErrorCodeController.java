package com.example.demo.error.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.error.dto.ErrorCodeCreateRequest;
import com.example.demo.error.dto.ErrorCodeResponse;
import com.example.demo.error.dto.ErrorCodeUpdateRequest;
import com.example.demo.error.service.ErrorCodeService;

import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/api/error-codes", produces = "application/json;charset=UTF-8")
@CrossOrigin(origins = "*")
public class ErrorCodeController {

    private final ErrorCodeService errorCodeService;

    @Autowired
    public ErrorCodeController(ErrorCodeService errorCodeService) {
        this.errorCodeService = errorCodeService;
    }

    // 전체 조회
    @GetMapping
    public ResponseEntity<List<ErrorCodeResponse>> getAllErrorCodes() {
        List<ErrorCodeResponse> errorCodes = errorCodeService.getAllErrorCodes();
        return ResponseEntity.ok(errorCodes);
    }

    // 활성화된 에러 코드만 조회
    @GetMapping("/active")
    public ResponseEntity<List<ErrorCodeResponse>> getActiveErrorCodes() {
        List<ErrorCodeResponse> errorCodes = errorCodeService.getActiveErrorCodes();
        return ResponseEntity.ok(errorCodes);
    }

    // 공정별 조회
    @GetMapping("/process/{processType}")
    public ResponseEntity<List<ErrorCodeResponse>> getErrorCodesByProcessType(
            @PathVariable String processType) {
        List<ErrorCodeResponse> errorCodes = errorCodeService.getErrorCodesByProcessType(processType);
        return ResponseEntity.ok(errorCodes);
    }

    // 카테고리별 조회
    @GetMapping("/category/{category}")
    public ResponseEntity<List<ErrorCodeResponse>> getErrorCodesByCategory(
            @PathVariable String category) {
        List<ErrorCodeResponse> errorCodes = errorCodeService.getErrorCodesByCategory(category);
        return ResponseEntity.ok(errorCodes);
    }

    // 단건 조회
    @GetMapping("/{errorCode}")
    public ResponseEntity<ErrorCodeResponse> getErrorCode(@PathVariable String errorCode) {
        ErrorCodeResponse response = errorCodeService.getErrorCode(errorCode);
        return ResponseEntity.ok(response);
    }

    // 생성
    @PostMapping
    public ResponseEntity<ErrorCodeResponse> createErrorCode(
            @Valid @RequestBody ErrorCodeCreateRequest request) {
        ErrorCodeResponse response = errorCodeService.createErrorCode(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // 수정
    @PutMapping("/{errorCode}")
    public ResponseEntity<ErrorCodeResponse> updateErrorCode(
            @PathVariable String errorCode,
            @Valid @RequestBody ErrorCodeUpdateRequest request) {
        ErrorCodeResponse response = errorCodeService.updateErrorCode(errorCode, request);
        return ResponseEntity.ok(response);
    }

    // 비활성화 (논리 삭제)
    @PatchMapping("/{errorCode}/deactivate")
    public ResponseEntity<Map<String, String>> deactivateErrorCode(@PathVariable String errorCode) {
        errorCodeService.deactivateErrorCode(errorCode);
        return ResponseEntity.ok(Map.of("message", "에러 코드가 비활성화되었습니다", "errorCode", errorCode));
    }

    // 활성화
    @PatchMapping("/{errorCode}/activate")
    public ResponseEntity<Map<String, String>> activateErrorCode(@PathVariable String errorCode) {
        errorCodeService.activateErrorCode(errorCode);
        return ResponseEntity.ok(Map.of("message", "에러 코드가 활성화되었습니다", "errorCode", errorCode));
    }

    // 물리 삭제 (관리자용)
    @DeleteMapping("/{errorCode}")
    public ResponseEntity<Map<String, String>> deleteErrorCode(@PathVariable String errorCode) {
        errorCodeService.deleteErrorCode(errorCode);
        return ResponseEntity.ok(Map.of("message", "에러 코드가 삭제되었습니다", "errorCode", errorCode));
    }

    // 통계 정보
    @GetMapping("/stats")
    public ResponseEntity<Map<String, Object>> getStats() {
        return ResponseEntity.ok(Map.of(
            "totalCount", errorCodeService.getTotalCount(),
            "pressCount", errorCodeService.getCountByProcessType("PRESS"),
            "bodyCount", errorCodeService.getCountByProcessType("BODY"),
            "paintCount", errorCodeService.getCountByProcessType("PAINT"),
            "assyCount", errorCodeService.getCountByProcessType("ASSY"),
            "commonCount", errorCodeService.getCountByProcessType("COMMON")
        ));
    }
}