package com.intbyte.wizbuddy.common.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

@Slf4j
@RestControllerAdvice(basePackages = "com.intbyte.wizbuddy")
public class GlobalExceptionHandler {

    @ExceptionHandler(value = {NoHandlerFoundException.class, HttpRequestMethodNotSupportedException.class})
    public ResponseDTO<?> handleNoPageFoundException(Exception e) {
        log.error("handleNoPageFoundException() in GlobalExceptionHandler throw NoHandlerFoundException : {}"
                , e.getMessage());
        return ResponseDTO.fail(new CommonException(StatusEnum.WRONG_ENTRY_POINT));
    }

    @ExceptionHandler(value = {MethodArgumentTypeMismatchException.class})
    public ResponseDTO<?> handleArgumentNotValidException(MethodArgumentTypeMismatchException e) {
        log.error("handleArgumentNotValidException() in GlobalExceptionHandler throw MethodArgumentTypeMismatchException : {}"
                , e.getMessage());
        return ResponseDTO.fail(e);
    }

    @ExceptionHandler(value = {MissingServletRequestParameterException.class})
    public ResponseDTO<?> handleArgumentNotValidException(MissingServletRequestParameterException e) {
        log.error("handleArgumentNotValidException() in GlobalExceptionHandler throw MethodArgumentNotValidException : {}"
                , e.getMessage());
        return ResponseDTO.fail(e);
    }

    @ExceptionHandler(value = {CommonException.class})
    public ResponseDTO<?> handleCustomException(CommonException e) {
        log.error("handleCustomException() in GlobalExceptionHandler: {}", e.getMessage());
        return ResponseDTO.fail(e);
    }

    //필기. 서버 내부 오류시 작동
    @ExceptionHandler(value = {Exception.class})
    public ResponseDTO<?> handleServerException(Exception e) {
        log.info("occurred exception in handleServerError = {}", e.getMessage());
        e.printStackTrace();
        return ResponseDTO.fail(new CommonException(StatusEnum.INTERNAL_SERVER_ERROR));
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseDTO<?> handleDataIntegrityViolationException(DataIntegrityViolationException e) {
        log.error("handleDataIntegrityViolationException() in GlobalExceptionHandler : {}", e.getMessage());
        return ResponseDTO.fail(new CommonException(StatusEnum.DATA_INTEGRITY_VIOLATION));
    }
}