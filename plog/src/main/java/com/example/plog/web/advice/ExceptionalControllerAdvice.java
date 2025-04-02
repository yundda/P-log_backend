package com.example.plog.web.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.plog.service.exceptions.AuthorizationException;
import com.example.plog.service.exceptions.DatabaseException;
import com.example.plog.service.exceptions.InvalidValueException;
import com.example.plog.service.exceptions.NotFoundException;
import com.example.plog.web.dto.ApiResponse;

import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
public class ExceptionalControllerAdvice {
    private static <E extends Exception> ResponseEntity<ApiResponse<Void>> handleException(E exception, HttpStatus status, String errorCode) {
    log.error("{}: {}", errorCode, exception.getMessage());  // 로그 기록
    return ApiResponse.error(errorCode, exception.getMessage(),status);

    }

    // 유효성 검사 실패(400) - 중복 이메일, 중복 닉네임, 비밀번호 검증 실패 등
    @ExceptionHandler(InvalidValueException.class)
    public ResponseEntity<ApiResponse<Void>> handleInvalidValueException(InvalidValueException ive) {
        return handleException(ive,HttpStatus.BAD_REQUEST,"VF");
    }

    // 인증 실패(401) - 로그인, 권한 인증 등
    @ExceptionHandler(AuthorizationException.class)
    public ResponseEntity<ApiResponse<Void>> handleAuthorizationException(AuthorizationException ae) {
        return handleException(ae, HttpStatus.UNAUTHORIZED,"AF");
    }
    // NotFound(404)
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ApiResponse<Void>> handleNotFoundException(NotFoundException nfe) {
        return handleException(nfe, HttpStatus.NOT_FOUND,"NF");
    }
    // 데이터 베이스 오류(500)
    @ExceptionHandler(DatabaseException.class)
    public ResponseEntity<ApiResponse<Void>> handleDatabaseException(DatabaseException dbe) {
        return handleException(dbe, HttpStatus.INTERNAL_SERVER_ERROR,"DBE");
    }
    // 기타 모든 에러 처리(500)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> handleGeneralException(Exception ex) {
        return handleException(ex, HttpStatus.INTERNAL_SERVER_ERROR, "GENERIC_ERROR");
    }
}
