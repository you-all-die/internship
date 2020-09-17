package com.example.internship.service.customer;

import com.example.internship.dto.CustomerDto;
import com.example.internship.dto.CustomerSearchResult;
import com.example.internship.dto.customer.CustomerDto.Response.WithFullName;
import com.example.internship.entity.Customer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collection;
import java.util.Optional;

public interface CustomerService {

    Iterable<Customer> getAll();

    Collection<WithFullName> getAllWithFullNames();

    Optional<Customer> getById(long id);
    Optional<CustomerDto> getDtoById(Long id);

    void save(Customer customer);

    void delete(long id);
    // Создание нового анонимного покупателя
    CustomerDto createAnonymousCustomer();
    // Регистрация покупателя
    CustomerDto registrationCustomer(HttpServletRequest request, HttpServletResponse response, CustomerDto customerDto);
    // Получение id покупателя из куки
    Optional<Long> customerIdFromCookie(HttpServletRequest request);
    // Удаление id покупателя из куки
    void customerIdDeleteFromCookie(HttpServletResponse response);
    // Запись (перезапись) id покупателя в куки
    void customerIdAddToCookie(HttpServletResponse response, Long customerId);
    // Проверяем, что id покупателя есть в базе и он еще не зарегистрирован
    boolean isAnonymousCustomer(Long customerId);
    //Api:поиск по критериям:ФИО, E-mail
    CustomerSearchResult search(Optional<String> firstName, Optional<String> middleName,
                                       Optional<String> lastName, Optional<String> email,
                                       Integer pageSize, Integer pageNumber);
}