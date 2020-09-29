package com.example.internship.service.customer;

import com.example.internship.dto.CustomerDto;
import com.example.internship.dto.CustomerSearchResult;
import com.example.internship.dto.customer.CustomerDto.WithFullName;
import com.example.internship.dto.customer.SearchResult;
import com.example.internship.entity.Customer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collection;
import java.util.Optional;

public interface CustomerService {

    Iterable<Customer> getAll();

    Collection<WithFullName> getAllWithFullNames();

    Optional<Customer> getById(long id);

    Optional<WithFullName> getWithFullNameById(Long id);

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
    CustomerSearchResult search(String firstName, String middleName,
                                String lastName, String email,
                                Integer pageSize, Integer pageNumber);

    /**
     * Возвращает список пользователей, отобранных по заданным критериям.
     *
     * @param searchString   подстрока поиска в именах, телефоне, почте
     * @param pageNumber     номер страницы
     * @param pageSize       размер страницы
     * @param ascendingOrder null|true - по возрастанию
     * @return результаты поиска
     */
    SearchResult<WithFullName> findByCriteria(
            String searchString,
            Integer pageNumber,
            Integer pageSize,
            Boolean ascendingOrder
    );

    void deleteAll();

    Optional<Customer> findById(Long id);

    boolean existsById(Long id);
}
