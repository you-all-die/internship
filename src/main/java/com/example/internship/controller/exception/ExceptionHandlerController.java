package com.example.internship.controller.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.persistence.EntityNotFoundException;

/**
 * @author Modenov D.A
 */

@ControllerAdvice
@Slf4j
public class ExceptionHandlerController {

    @ExceptionHandler(Exception.class)
    public String connectionError(Exception exception) {

        log.error(exception.getMessage());
        log.error("### Exception", exception);
        return "exception/error";
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public String connectionError(EntityNotFoundException exception) {

        log.error(exception.getMessage());
        log.error("### Exception", exception);
        return "exception/notFound";
    }
}
