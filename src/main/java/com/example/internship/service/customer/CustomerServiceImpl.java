package com.example.internship.service.customer;

import com.example.internship.dto.CustomerDto;
import com.example.internship.dto.CustomerSearchResult;
import com.example.internship.dto.customer.CustomerDto.Response.WithFullName;
import com.example.internship.entity.Customer;
import com.example.internship.repository.CustomerRepository;
import com.example.internship.service.CustomerService;
import com.example.internship.specification.CustomerSpecification;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toUnmodifiableList;

@Service
@Slf4j
@AllArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private static final String ANONYMOUS = "Анонимный покупатель";
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

    @Override
    public Optional<CustomerDto> getDtoById(Long id) {
        Optional<Customer> customer = customerRepository.findById(id);
        if (customer.isPresent()) {
            CustomerDto customerDto = convertToDto(customer.get());
            return Optional.of(customerDto);
        }
        else {
            return Optional.empty();
        }
    }

    public final void save(Customer customer) {
        customerRepository.save(customer);
    }

    public final void delete(long id) {
        customerRepository.deleteById(id);
    }

    // Создание нового анонимного покупателя
    public CustomerDto createAnonymousCustomer() {
        return convertToDto(customerRepository.save(new Customer()));
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

        //связывание корзины при регистрации
        customerDto.setCart(customerRepository.findById(customerId).get().getCart());
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

    public CustomerDto getCustomerDto (Customer customer) { return convertToDto(customer); }

    private CustomerDto convertToDto(Customer customer) {
        return mapper.map(customer, CustomerDto.class);
    }

    private Customer convertToModel(CustomerDto customerDto) {
        return mapper.map(customerDto, Customer.class);
    }

    //Api: поиск по критериям: ФИО, E-mail. Размер страницы, номер страницы
    @Override
    public CustomerSearchResult search(Optional<String> firstName, Optional<String> middleName,
                                       Optional<String> lastName,Optional<String> email,
                                       Integer pageSize, Integer pageNumber) {

        CustomerSearchResult customerSearchResult = new CustomerSearchResult();
        Specification<Customer> specification;

        // Формируем условия для запроса
        specification = draftSpecification(null,"firstName", firstName);
        specification = draftSpecification(specification,"middleName", middleName);
        specification = draftSpecification(specification,"lastName", lastName);
        specification = draftSpecification(specification,"email", email);

        // Результат поиска
        customerSearchResult.setCustomers(customerRepository.findAll(specification, PageRequest.of(pageNumber, pageSize))
                .stream().map(this::convertToDto)
                .collect(Collectors.toList()));
        customerSearchResult.setPageNumber(pageNumber);
        customerSearchResult.setPageSize(pageSize);
        customerSearchResult.setTotalCustomers(customerRepository.count(specification));

        return customerSearchResult;
    }

    //Метод проверки поля и добавления условия в запрос
    private Specification<Customer> draftSpecification(Specification<Customer> specification, String columnName,
                                                       Optional<String> optionalName ){
        if(optionalName.isPresent()){
            if(specification == null){
                specification = Specification.where(new CustomerSpecification(columnName, optionalName.get()));
            }else {
                specification = specification.and(new CustomerSpecification(columnName, optionalName.get()));
            }
        }
        return specification;
    }

    //======================================================================================================

    @Override
    public Collection<WithFullName> getAllWithFullNames() {
        return customerRepository
                .findAll()
                .stream()
                .map(this::convertToAllWithFullNames)
                .collect(toUnmodifiableList());
    }

    private WithFullName convertToAllWithFullNames(Customer customer) {
        return mapper.map(customer, WithFullName.class);
    }

    @PostConstruct
    private void configureCustomerMapper() {
        mapper
                .createTypeMap(Customer.class, WithFullName.class)
                .addMappings(mapper -> mapper.map(this::generateFullName, WithFullName::setFullName));
    }

    /**
     * Генерирует полное имя покупателя.
     *
     * @param customer покупатель
     * @return Фамилия Имя Отчество покупателя или {@link ANONYMOUS}
     */
    public final String generateFullName(@NonNull Customer customer) {
        StringBuilder builder = new StringBuilder();
        if (null != customer.getFirstName()) {
            builder.append(customer.getFirstName());
        }
        if (null != customer.getMiddleName()) {
            builder.append(" ").append(customer.getMiddleName());
        }
        if (null != customer.getLastName()) {
            builder.append(" ").append(customer.getLastName());
        }
        String fullName = builder.toString().trim();
        return StringUtils.isNotBlank(fullName) ? fullName : ANONYMOUS;
    }
}

