package com.pwc.testtask.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
@Slf4j
public class RouteAppExceptionHandler {

    @ExceptionHandler(RouteException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handle(final RouteException ex) {
        log.error(ex.getMessage(), ex);
        return Map.of("error", ex.getMessage());
    }
}
