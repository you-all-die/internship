package com.example.internship.service;

import com.example.internship.dto.CustomerDto;
import com.example.internship.entity.Customer;

import java.util.Optional;

public interface CustomerService {

    Iterable<Customer> getAll();

    Optional<Customer> getById(long id);
    Optional<CustomerDto> getDtoById(Long id);

    void save(Customer customer);

    void delete(long id);
    // Создание нового анонимного покупателя
    CustomerDto createAnonymousCustomer();
    // Регистрация покупателя
    CustomerDto registrationCustomer(CustomerDto customerDto);
    // Получение id покупателя из куки
    Optional<Long> customerIdFromCookie();
    // Удаление id покупателя из куки
    void customerIdDeleteFromCookie();
    // Запись (перезапись) id покупателя в куки
    void customerIdAddToCookie(Long customerId);
    // Проверяем, что id покупателя есть в базе и он еще не зарегестрирован
    boolean isAnonymousCustomer(Long customerId);
}
