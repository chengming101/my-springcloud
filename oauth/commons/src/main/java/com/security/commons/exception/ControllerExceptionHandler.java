package com.security.commons.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.ServletRequest;

/**
 * 全局异常处理
 */
@RestControllerAdvice
@Slf4j
public class ControllerExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.BAD_GATEWAY)
    public String busiHandle(Exception e, ServletRequest request) {
        log.error("========" + e.getMessage());
        return e.getMessage();
    }

}
