package com.example.internship.service;

import com.example.internship.dto.CustomerDto;
import com.example.internship.dto.CustomerSearchResult;
import com.example.internship.entity.Customer;

import java.util.Optional;

public interface CustomerService {

    /**
     * Получение всех пользователей.
     *
     * @return Перечисление пользователей.
     */
    Iterable<Customer> getAll();

    /**
     * Возвращает пользователя по id.
     *
     * @param id идентификатор пользователя.
     * @return пользователя.
     */
    Optional<Customer> getById(Long id);

    /**
     * Возвращает ДТО пользователя по id.
     *
     * @param id идентификатор пользователя.
     * @return ДТО пользователя.
     */
    Optional<CustomerDto> getDtoById(Long id);

    /**
     * Сохраняет пользователя.
     *
     * @param customer пользователь.
     */
    void save(Customer customer);

    /**
     * Удаляет пользователя по id.
     *
     * @param id идентификатор пользователя.
     */
    void delete(Long id);

    /**
     * Удаляет всех пользователей.
     */
    void deleteAll();


    /**
     * Создает нового анонимного пользователя.
     *
     * @return ДТО пользователя с присвоенным id.
     */
    CustomerDto createAnonymousCustomer();

    /**
     * Регистрация покупателя.
     *
     * @param customerDto ДТО пользователя.
     * @return ДТО пользователя из параметра.
     */
    CustomerDto registrationCustomer(CustomerDto customerDto);

    /**
     * Возвращает id пользователя из куки.
     *
     * @return id пользователя.
     */
    Optional<Long> customerIdFromCookie();

    /**
     * Удаляет id пользователя из куки.
     */
    void customerIdDeleteFromCookie();

    /**
     * Создает или перезаписывает id пользователя в куки.
     *
     * @param customerId id пользователя.
     */
    void customerIdAddToCookie(Long customerId);

    /**
     * Проверяет анонимный ли пользователь и есть ли он в бд.
     *
     * @param customerId id пользователя.
     * @return true - если существует, false если нет.
     */
    boolean isAnonymousCustomer(Long customerId);

    /**
     * Поиск пользователя по критериям.
     *
     * @param firstName имя пользователя.
     * @param middleName отчество пользователя.
     * @param lastName фамилия пользователя.
     * @param email email пользователя.
     * @param pageSize размер страницы.
     * @param pageNumber номер страницы.
     * @return форму данных поиска.
     */
    CustomerSearchResult search(String firstName, String middleName, String lastName, String email,
                                       Integer pageSize, Integer pageNumber);
}
