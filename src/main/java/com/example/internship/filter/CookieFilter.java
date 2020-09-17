package com.example.internship.filter;

import com.example.internship.service.customer.CustomerService;
import lombok.AllArgsConstructor;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
// Фильтр будет срабатывать первым
@Order(1)
@AllArgsConstructor
public class CookieFilter implements Filter {

    private final CustomerService customerService;

    @Override
    public void doFilter(ServletRequest servletRequest,
                         ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {
        if (servletRequest instanceof HttpServletRequest && servletResponse instanceof HttpServletResponse) {
            // Если куки чистые или в куках не записан id покупателя
            if (customerService.customerIdFromCookie((HttpServletRequest) servletRequest).isEmpty()) {
                // Создаем анонимного покупателя и добавляем его id в куки
                customerService.customerIdAddToCookie((HttpServletResponse) servletResponse, customerService.createAnonymousCustomer().getId());
            }
        }
        // Завершаем работу фильтра
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
