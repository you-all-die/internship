package com.example.internship.filter;

import com.example.internship.service.customer.CustomerService;
import lombok.AllArgsConstructor;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import java.io.IOException;
import java.util.Optional;

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
        // Если куки чистые или в куках не записан id покупателя
        Optional<Long> customerId = customerService.customerIdFromCookie();
        if (customerId.isEmpty()) {
            // Создаем анонимного покупателя и добавляем его id в куки
            customerService.customerIdAddToCookie(customerService.createAnonymousCustomer().getId());
        } else {
            customerService.updateLastActivity(customerId.get());
        }
        // Завершаем работу фильтра
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
