package com.example.internship.service.impl;

import com.example.internship.dto.CustomerDto;
import com.example.internship.entity.Cart;
import com.example.internship.entity.Customer;
import com.example.internship.repository.CustomerRepository;
import com.example.internship.service.CustomerService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Optional;

@Service
@Slf4j
@AllArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    private final PasswordEncoder passwordEncoder;

    private final ModelMapper mapper;

    private final HttpServletRequest request;

    private final HttpServletResponse response;

    public final Iterable<Customer> getAll() {
        return customerRepository.findAll();
    }

    public final Optional<Customer> getById(long id) {
        return customerRepository.findById(id);
    }

    public final void save(Customer customer) {
        customerRepository.save(customer);
    }

    public final void delete(long id) {
        customerRepository.deleteById(id);
    }

    // Создание нового анонимного покупателя
    public CustomerDto createAnonymousCustomer() {
        Customer customer = new Customer();
        customer.setCart(new Cart());
        return convertToDto(customerRepository.save(customer));
    }

    // Регистрация покупателя
    public CustomerDto registrationCustomer(CustomerDto customerDto) {
        // Получаем id покупателя из куки
        Long customerId = customerIdFromCookie().orElse(null);
        // Если в куках не найдено или указано неверное значения id покупателя
        if (customerId == null || !isAnonymousCustomer(customerId)) {
            // Создаем нового анонимного покупателя
            customerId = createAnonymousCustomer().getId();
            // Добавляем (перезаписываем) id в куки
            customerIdAddToCookie(customerId);
        }
        // Кодируем пароль
        customerDto.setPassword(passwordEncoder.encode(customerDto.getPassword()));
        // Сохраняем значение в БД для нашего покупателя
        customerDto.setId(customerId);
        customerRepository.save(convertToModel(customerDto));

        return customerDto;
    }

    // Получение id покупателя из куки
    public Optional<Long> customerIdFromCookie() {
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
    public void customerIdDeleteFromCookie() {
        // Создаем новый куки id покупателя
        Cookie customerIdCookie = new Cookie("customerId", null);
        // Время жизни = 0
        customerIdCookie.setMaxAge(0);
        // Перезаписываем куки
        response.addCookie(customerIdCookie);
    }

    // Запись (перезапись) id покупателя в куки
    public void customerIdAddToCookie(Long customerId) {
        response.addCookie(new Cookie("customerId", customerId.toString()));
    }

    // Проверяем, что id покупателя есть в базе и он еще не зарегестрирован
    public boolean isAnonymousCustomer(Long customerId) {
        Customer customer = customerRepository.findById(customerId).orElse(null);

        return customer != null && customer.getEmail() == null;
    }

    @Override
    public Optional<Customer> checkCustomerCart(String customerId) {
        Optional<Customer> customer = customerRepository.findById(Long.valueOf(customerId));

        if (customer.isEmpty()) return Optional.empty();

        if (customer.get().getCart() == null){
            log.error("Cart for customer: " + customerId + "not exist!");
            customer.get().setCart(new Cart());
            customerRepository.save(customer.get());
            return Optional.empty();
        }

        if (customer.get().getCart().getOrderLines() == null) {
            return Optional.empty();
        }

        return customer;
    }

    private CustomerDto convertToDto(Customer customer) {
        return mapper.map(customer, CustomerDto.class);
    }

    private Customer convertToModel(CustomerDto customerDto) {
        return mapper.map(customerDto, Customer.class);
    }
}
