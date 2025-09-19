package com.example.demo.error.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.common.exception.NotFoundException;
import com.example.demo.error.dto.ErrorCodeCreateRequest;
import com.example.demo.error.dto.ErrorCodeResponse;
import com.example.demo.error.dto.ErrorCodeUpdateRequest;
import com.example.demo.error.mapper.ErrorCodeMapper;
import com.example.demo.error.model.ErrorCode;

@Service
@Transactional(readOnly = true)
public class ErrorCodeService {

    private final ErrorCodeMapper errorCodeMapper;

    @Autowired
    public ErrorCodeService(ErrorCodeMapper errorCodeMapper) {
        this.errorCodeMapper = errorCodeMapper;
    }

    // 전체 조회
    public List<ErrorCodeResponse> getAllErrorCodes() {
        List<ErrorCode> errorCodes = errorCodeMapper.selectAll();
        return errorCodes.stream()
                .map(ErrorCodeResponse::new)
                .collect(Collectors.toList());
    }

    // 활성화된 에러 코드만 조회
    public List<ErrorCodeResponse> getActiveErrorCodes() {
        List<ErrorCode> errorCodes = errorCodeMapper.selectActiveOnly();
        return errorCodes.stream()
                .map(ErrorCodeResponse::new)
                .collect(Collectors.toList());
    }

    // 공정별 조회
    public List<ErrorCodeResponse> getErrorCodesByProcessType(String processType) {
        List<ErrorCode> errorCodes = errorCodeMapper.selectByProcessType(processType);
        return errorCodes.stream()
                .map(ErrorCodeResponse::new)
                .collect(Collectors.toList());
    }

    // 카테고리별 조회
    public List<ErrorCodeResponse> getErrorCodesByCategory(String category) {
        List<ErrorCode> errorCodes = errorCodeMapper.selectByCategory(category);
        return errorCodes.stream()
                .map(ErrorCodeResponse::new)
                .collect(Collectors.toList());
    }

    // 단건 조회
    public ErrorCodeResponse getErrorCode(String errorCode) {
        ErrorCode entity = errorCodeMapper.selectByCode(errorCode);
        if (entity == null) {
            throw new NotFoundException("에러 코드를 찾을 수 없습니다: " + errorCode);
        }
        return new ErrorCodeResponse(entity);
    }

    // 생성
    @Transactional
    public ErrorCodeResponse createErrorCode(ErrorCodeCreateRequest request) {
        // 중복 체크
        if (errorCodeMapper.existsByCode(request.getErrorCode())) {
            throw new IllegalArgumentException("이미 존재하는 에러 코드입니다: " + request.getErrorCode());
        }

        // 엔티티 생성
        ErrorCode errorCode = new ErrorCode();
        errorCode.setErrorCode(request.getErrorCode());
        errorCode.setErrorName(request.getErrorName());
        errorCode.setErrorDesc(request.getErrorDesc());
        errorCode.setErrorCategory(request.getErrorCategory());
        errorCode.setProcessType(request.getProcessType());
        errorCode.setDefaultSeverity(request.getDefaultSeverity());
        errorCode.setAutoRecovery(request.getAutoRecovery());
        errorCode.setSolutionGuide(request.getSolutionGuide());
        errorCode.setThresholdValue(request.getThresholdValue());
        errorCode.setUnit(request.getUnit());
        errorCode.setIsActive(request.getIsActive());

        // 저장
        int result = errorCodeMapper.insert(errorCode);
        if (result == 0) {
            throw new RuntimeException("에러 코드 생성에 실패했습니다");
        }

        // 저장된 데이터 조회 후 반환
        return getErrorCode(request.getErrorCode());
    }

    // 수정
    @Transactional
    public ErrorCodeResponse updateErrorCode(String errorCode, ErrorCodeUpdateRequest request) {
        // 존재 여부 확인
        ErrorCode existing = errorCodeMapper.selectByCode(errorCode);
        if (existing == null) {
            throw new NotFoundException("에러 코드를 찾을 수 없습니다: " + errorCode);
        }

        // 엔티티 업데이트
        existing.setErrorName(request.getErrorName());
        existing.setErrorDesc(request.getErrorDesc());
        existing.setErrorCategory(request.getErrorCategory());
        existing.setProcessType(request.getProcessType());
        existing.setDefaultSeverity(request.getDefaultSeverity());
        existing.setAutoRecovery(request.getAutoRecovery());
        existing.setSolutionGuide(request.getSolutionGuide());
        existing.setThresholdValue(request.getThresholdValue());
        existing.setUnit(request.getUnit());
        existing.setIsActive(request.getIsActive());

        // 저장
        int result = errorCodeMapper.update(existing);
        if (result == 0) {
            throw new RuntimeException("에러 코드 수정에 실패했습니다");
        }

        // 수정된 데이터 반환
        return getErrorCode(errorCode);
    }

    // 삭제 (논리 삭제)
    @Transactional
    public void deactivateErrorCode(String errorCode) {
        if (!errorCodeMapper.existsByCode(errorCode)) {
            throw new NotFoundException("에러 코드를 찾을 수 없습니다: " + errorCode);
        }

        int result = errorCodeMapper.deactivate(errorCode);
        if (result == 0) {
            throw new RuntimeException("에러 코드 비활성화에 실패했습니다");
        }
    }

    // 활성화
    @Transactional
    public void activateErrorCode(String errorCode) {
        if (!errorCodeMapper.existsByCode(errorCode)) {
            throw new NotFoundException("에러 코드를 찾을 수 없습니다: " + errorCode);
        }

        int result = errorCodeMapper.activate(errorCode);
        if (result == 0) {
            throw new RuntimeException("에러 코드 활성화에 실패했습니다");
        }
    }

    // 물리 삭제 (관리자용)
    @Transactional
    public void deleteErrorCode(String errorCode) {
        if (!errorCodeMapper.existsByCode(errorCode)) {
            throw new NotFoundException("에러 코드를 찾을 수 없습니다: " + errorCode);
        }

        int result = errorCodeMapper.delete(errorCode);
        if (result == 0) {
            throw new RuntimeException("에러 코드 삭제에 실패했습니다");
        }
    }

    // 통계 정보
    public int getTotalCount() {
        return errorCodeMapper.countAll();
    }

    public int getCountByProcessType(String processType) {
        return errorCodeMapper.countByProcessType(processType);
    }
}