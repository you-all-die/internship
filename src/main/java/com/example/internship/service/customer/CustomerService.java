package com.example.internship.service.customer;

import com.example.internship.dto.CustomerDto;
import com.example.internship.api.dto.CustomerSearchResponse;
import com.example.internship.entity.Customer;
import org.springframework.security.core.Authentication;

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
     * @param id индентификатор пользователя.
     * @return ДТО пользователя или null, если пользователь не найден.
     */
    com.example.internship.refactoringdto.CustomerDto getByIdRef(Long id);

    /**
     * Получение пользователя из аутентификации.
     *
     * @return аутентифицированного пользователя.
     */
    Optional<CustomerDto> getFromAuthentication(Authentication authentication);

    /**
     * Возвращает ДТО пользователя по id.
     *
     * @param id идентификатор пользователя.
     * @return ДТО пользователя.
     *
     * @deprecated
     */
    Optional<CustomerDto> getDtoById(Long id);

    /**
     * Обновляет данные пользователя.
     *
     * @param customerId идентификатор пользователя.
     * @param customerDto данные пользователя для обновления.
     * @return Дто пользователя с новыми данными или null, если пользователь не обновлен.
     */
    com.example.internship.refactoringdto.CustomerDto update(Long customerId, com.example.internship.refactoringdto.CustomerDto customerDto);

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
    CustomerSearchResponse search(String firstName, String middleName, String lastName, String email,
                                  Integer pageSize, Integer pageNumber);

    //обновление времени последней активности
    void updateLastActivity(Long customerId);

    /**
     * Метод чек  стайл проверяет пользователя с почтой.
     *
     * @param email почта пользователя.
     * @return возращает пользователя, если null пользователь не найден.
     */
    boolean checkEmail(String email);

    public CustomerDto convertToDto(Customer customer);

}
