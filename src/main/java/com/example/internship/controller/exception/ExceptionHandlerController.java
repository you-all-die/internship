package com.example.internship.controller.exception;

import com.example.internship.exception.OrderNotFoundException;
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
    public String serverError(Exception exception) {

        log.error(exception.getMessage());

        return "exception/error";
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public String userNotFoundError(EntityNotFoundException exception) {

        log.error(exception.getMessage());

        return "exception/userNotFound";
    }

    @ExceptionHandler(OrderNotFoundException.class)
    public String orderNotFoundError(OrderNotFoundException exception) {

        log.error(exception.getMessage());

        return "exception/orderNotFound";
    }
}
