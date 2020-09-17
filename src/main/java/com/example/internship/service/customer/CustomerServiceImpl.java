package com.example.internship.service.customer;

import com.example.internship.dto.CustomerDto;
import com.example.internship.dto.CustomerSearchResult;
import com.example.internship.dto.customer.CustomerDto.Response.AllExceptPassword;
import com.example.internship.dto.customer.CustomerDto.Response.WithFullName;
import com.example.internship.dto.customer.SearchResult;
import com.example.internship.entity.Customer;
import com.example.internship.entity.Customer_;
import com.example.internship.repository.CustomerRepository;
import com.example.internship.specification.CustomerSpecification;
import com.example.internship.specification.customer.CustomerSpecificator;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toUnmodifiableList;

@Service
@Slf4j
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private static final String ANONYMOUS = "Анонимный покупатель";
    private static final int DEFAULT_PAGE_SIZE = 20;

    private final CustomerRepository customerRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper mapper;

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
        } else {
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
    public CustomerDto registrationCustomer(
            HttpServletRequest request,
            HttpServletResponse response,
            CustomerDto customerDto
    ) {
        // Получаем id покупателя из куки
        Long customerId = customerIdFromCookie(request).orElse(null);
        // Если в куках не найдено или указано неверное значения id покупателя
        if (customerId == null || !isAnonymousCustomer(customerId)) {
            // Создаем нового анонимного покупателя
            customerId = createAnonymousCustomer().getId();
            // Добавляем (перезаписываем) id в куки
            customerIdAddToCookie(response, customerId);
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
    public Optional<Long> customerIdFromCookie(
            HttpServletRequest request
    ) {
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
    public void customerIdDeleteFromCookie(
            HttpServletResponse response
    ) {
        // Создаем новый куки id покупателя
        Cookie customerIdCookie = new Cookie("customerId", null);
        // Время жизни = 0
        customerIdCookie.setMaxAge(0);
        // Перезаписываем куки
        response.addCookie(customerIdCookie);
    }

    // Запись (перезапись) id покупателя в куки
    public void customerIdAddToCookie(
            HttpServletResponse response,
            Long customerId
    ) {
        response.addCookie(new Cookie("customerId", customerId.toString()));
    }

    // Проверяем, что id покупателя есть в базе и он еще не зарегистрирован
    public boolean isAnonymousCustomer(Long customerId) {
        Customer customer = customerRepository.findById(customerId).orElse(null);

        return customer != null && customer.getEmail() == null;
    }

    public CustomerDto getCustomerDto(Customer customer) {
        return convertToDto(customer);
    }

    private CustomerDto convertToDto(Customer customer) {
        return mapper.map(customer, CustomerDto.class);
    }

    private Customer convertToModel(CustomerDto customerDto) {
        return mapper.map(customerDto, Customer.class);
    }

    //Api: поиск по критериям: ФИО, E-mail. Размер страницы, номер страницы
    @Override
    public CustomerSearchResult search(Optional<String> firstName, Optional<String> middleName,
                                       Optional<String> lastName, Optional<String> email,
                                       Integer pageSize, Integer pageNumber) {

        CustomerSearchResult customerSearchResult = new CustomerSearchResult();
        Specification<Customer> specification;

        // Формируем условия для запроса
        specification = draftSpecification(null, "firstName", firstName);
        specification = draftSpecification(specification, "middleName", middleName);
        specification = draftSpecification(specification, "lastName", lastName);
        specification = draftSpecification(specification, "email", email);

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
                                                       Optional<String> optionalName) {
        if (optionalName.isPresent()) {
            if (specification == null) {
                specification = Specification.where(new CustomerSpecification(columnName, optionalName.get()));
            } else {
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
                .map(this::convertToWithFullName)
                .collect(toUnmodifiableList());
    }

    @Override
    public SearchResult findByCriteria(
            String searchString,
            Integer pageSize,
            Integer pageNumber,
            Boolean ascendingOrder
    ) {
        final Sort.Direction direction = (null == ascendingOrder || ascendingOrder) ? Sort.Direction.ASC : Sort.Direction.DESC;
        final Sort sort = Sort.by(direction, Customer_.LAST_NAME, Customer_.FIRST_NAME, Customer_.MIDDLE_NAME);
        if (null == pageSize) {
            pageSize = DEFAULT_PAGE_SIZE;
        }
        if (null == pageNumber) {
            pageNumber = 0;
        }
        final Pageable pageable = PageRequest.of(pageNumber, pageNumber, sort);
        final CustomerSpecificator specificator = CustomerSpecificator.builder()
                .searchString(searchString)
                .build();
        final long total = customerRepository.count(specificator);
        final List<AllExceptPassword> customers = customerRepository
                .findAll(specificator, pageable)
                .stream()
                .map(this::convertToAllExceptPassword)
                .collect(toUnmodifiableList());
        return SearchResult.builder()
                .customers(customers)
                .pageNumber(pageNumber)
                .pageSize(pageSize)
                .ascendingOrder(null == ascendingOrder || ascendingOrder)
                .build();
    }

    /**
     * Генерирует полное имя покупателя.
     *
     * @param customer покупатель
     * @return Фамилия Имя Отчество покупателя или {@link CustomerServiceImpl#ANONYMOUS}
     */
    public final String generateFullName(@NonNull Customer customer) {
        StringBuilder builder = new StringBuilder();
        if (StringUtils.isNotBlank(customer.getFirstName())) {
            builder.append(customer.getFirstName());
        }
        if (StringUtils.isNotBlank(customer.getMiddleName())) {
            builder.append(" ").append(customer.getMiddleName());
        }
        if (StringUtils.isNotBlank(customer.getLastName())) {
            builder.append(" ").append(customer.getLastName());
        }
        String fullName = builder.toString().trim();
        return StringUtils.isNotBlank(fullName) ? fullName : ANONYMOUS;
    }

    @PostConstruct
    private void configureCustomerMapper() {
        mapper
                .createTypeMap(Customer.class, WithFullName.class)
                .addMappings(mapper -> mapper.skip(WithFullName::setFullName))
                .setPostConverter(toWithFullNameConverter());
    }

    private AllExceptPassword convertToAllExceptPassword(Customer customer) {
        return mapper.map(customer, AllExceptPassword.class);
    }

    private WithFullName convertToWithFullName(Customer customer) {
        return mapper.map(customer, WithFullName.class);
    }

    private Converter<Customer, WithFullName> toWithFullNameConverter() {
        return context -> {
            Customer source = context.getSource();
            WithFullName destination = context.getDestination();
            destination.setFullName(generateFullName(source));
            return context.getDestination();
        };
    }
}

