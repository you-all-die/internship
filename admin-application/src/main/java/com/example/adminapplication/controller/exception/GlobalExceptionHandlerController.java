package com.example.adminapplication.controller.exception;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.ResourceAccessException;

/**
 * @author Ivan Gubanov
 */
@ControllerAdvice
@AllArgsConstructor
public class GlobalExceptionHandlerController {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandlerController.class);

    @ExceptionHandler({ResourceAccessException.class})
    public String connectionError(ResourceAccessException exception) {
        log.error(exception.getMessage());
        return "/errors/connection";
    }
}
