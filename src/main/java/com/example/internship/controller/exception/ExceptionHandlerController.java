package com.example.internship.controller.exception;

import com.example.internship.exception.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Locale;


/**
 * @author Modenov D.A
 */

@ControllerAdvice
@RequiredArgsConstructor
@Slf4j
public class ExceptionHandlerController {

    private final MessageSource messageSource;

    @ExceptionHandler(Exception.class)
    public String serverError(Exception exception) {

        log.error(exception.getMessage(), exception);

        return "exception/error";
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public String userNotFoundError(EntityNotFoundException exception, Model model) {

        model.addAttribute("header", messageSource.getMessage(exception.getMessageCode(), null, Locale.getDefault()));

        log.error(exception.getMessage(), exception);

        return "exception/notFound";
    }

}
