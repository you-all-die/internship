package com.example.internship.helper;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;

@Slf4j
public enum WebHelper {
    ;

    public static void guardAjaxOrNotFound(final HttpServletRequest request) {
        if (!isAjaxRequest(request)) {
            log.warn("An attempt to access the url " + request.getRequestURL() + " via the browser was detected.");
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Page not found");
        }
    }

    private static boolean isAjaxRequest(final HttpServletRequest request) {
        String header = request.getHeader("X-Requested-With");
        return (null != header) && header.equals("XMLHttpRequest");
    }
}
