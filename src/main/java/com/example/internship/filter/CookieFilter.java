package com.example.internship.filter;

import com.example.internship.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private CustomerService customerService;

    @Override
    public void doFilter(ServletRequest servletRequest,
                         ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;
        // Получаем куки из запроса
        Cookie[] cookies = httpRequest.getCookies();
        // Если куки чистые или в куках не записан id покупателя
        if (customerService.customerIdFromCookie(httpRequest).isEmpty()) {
            // Создаем анонимного покупателя и добавляем его id в куки
            customerService.customerIdAddToCookie(httpResponse, customerService.createAnonymousCustomer());
        }
        // Завершаем работу фильтра
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
