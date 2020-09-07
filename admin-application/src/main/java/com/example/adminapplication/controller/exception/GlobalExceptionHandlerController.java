package com.example.adminapplication.controller.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.ResourceAccessException;

/**
 * @author Ivan Gubanov
 */
@ControllerAdvice
public class GlobalExceptionHandlerController {

    @ExceptionHandler({ResourceAccessException.class})
    public String connectionError() {
        return "/errors/connection";
    }
}
