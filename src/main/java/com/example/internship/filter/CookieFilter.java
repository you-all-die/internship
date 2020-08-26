package com.example.internship.filter;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

@Component
// Фильтр будет срабатывать первым
@Order(1)
public class CookieFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest,
                         ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;
        // Получаем куки из запроса
        Cookie[] cookies = httpRequest.getCookies();
        if (cookies != null) {
            // Если в куках не записан id покупателя
            if (Arrays.stream(cookies)
                    .filter(x -> x.getName().equals("customerId")).findFirst().orElse(null) == null) {
                //TODO создание нового покупателя и получение его id
                Long newUserId = System.currentTimeMillis();
                // Записываем id покупателя в куки
                httpResponse.addCookie(new Cookie("customerId", newUserId.toString()));
            }
        }
        // Завершаем работу фильтра
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
