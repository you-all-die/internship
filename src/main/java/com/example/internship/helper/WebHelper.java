package com.example.internship.helper;

import javax.servlet.http.HttpServletRequest;

public enum WebHelper {
    ;

    public static boolean isAjaxRequest(final HttpServletRequest request) {
        String header = request.getHeader("X-Requested-With");
        return (null != header) && header.equals("XMLHttpRequest");
    }
}
