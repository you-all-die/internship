package com.example.internship.service;

import com.example.internship.dto.CustomerDto;
import com.example.internship.entity.Customer;
import com.example.internship.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Optional;

@Service
public class CustomerService {
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    // Создание нового анонимного покупателя
    public Long createAnonymousCustomer() {
        return customerRepository.save(new Customer()).getId();
    }

    // Регистрация покупателя
    public Long registrationCustomer(CustomerDto customerDto, HttpServletRequest request,
                                     HttpServletResponse response) {
        // Получаем id покупателя из куки
        Long customerId = customerIdFromCookie(request).orElse(null);
        // Если в куках не найдено или указано неверное значения id покупателя
        if (customerId == null || !isAnonymousCustomer(customerId)) {
            // Создаем нового анонимного покупателя
            customerId = createAnonymousCustomer();
            // Удаляем старое значение из куки
            customerIdDeleteFromCookie(request, response);
            // Добавляем id в куки
            customerIdAddToCookie(response, customerId);
        }
        // Сохраняем значение в БД для нашего покупателя
        Customer customer = customerRepository.findById(customerId).orElse(null);
        if (customer != null) {
            customer.setEmail(customerDto.getEmail());
            customer.setFirstName(customerDto.getFirstName());
            customer.setMiddleName(customerDto.getMiddleName());
            customer.setLastName(customerDto.getLastName());
            customer.setPhone(customerDto.getPhone());
            // Кодируем пароль
            customer.setPassword(passwordEncoder.encode(customerDto.getPassword()));
            customerRepository.save(customer);
        }

        return customerId;
    }

    // Получение id покупателя из куки
    public Optional<Long> customerIdFromCookie(HttpServletRequest request) {
        // Получаем куки из запроса
        Cookie[] cookies = request.getCookies();
        // Если куки не пустые
        if (cookies != null) {
            // Ищем в куках значение customerId
            Cookie customerIdCookie = Arrays.stream(cookies)
                    .filter(x -> x.getName().equals("customerId")).findFirst().orElse(null);
            // Если значение есть
            if (customerIdCookie != null && customerIdCookie.getValue() != null) {
                return Optional.of(Long.parseLong(customerIdCookie.getValue()));
            }
        }
        return Optional.empty();
    }

    // Удаление id покупателя из куки
    public void customerIdDeleteFromCookie(HttpServletRequest request, HttpServletResponse response) {
        // Получаем все куки
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            Arrays.stream(cookies).forEach(cookie -> {
                // Если это куки для id покупателя
                if (cookie.getName().equals("customerId")) {
                    cookie.setValue(null);
                    cookie.setMaxAge(0);
                    response.addCookie(cookie);
                }
            });
        }
    }

    // Запись id покупателя в куки
    public void customerIdAddToCookie(HttpServletResponse response, Long customerId) {
        response.addCookie(new Cookie("customerId", customerId.toString()));
    }

    // Проверяем, что id покупателя есть в базе и он еще не зарегестрирован
    public boolean isAnonymousCustomer(Long customerId) {
        Customer customer = customerRepository.findById(customerId).orElse(null);

        return customer != null && customer.getEmail() == null;
    }
}
