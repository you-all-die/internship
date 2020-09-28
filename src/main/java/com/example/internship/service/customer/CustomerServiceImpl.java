package com.example.internship.service.customer;

import com.example.internship.api.dto.CustomerSearchResponse;
import com.example.internship.dto.CustomerDto;
import com.example.internship.entity.Customer;
import com.example.internship.repository.CustomerRepository;
import com.example.internship.specification.CustomerSpecification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    private final PasswordEncoder passwordEncoder;

    private final ModelMapper mapper;

    private final HttpServletRequest request;

    private final HttpServletResponse response;

    @Override
    public final Iterable<Customer> getAll() {
        return customerRepository.findAll();
    }

    @Override
    public final Optional<Customer> getById(Long id) {
        return customerRepository.findById(id);
    }

    @Override
    public com.example.internship.refactoringdto.CustomerDto getByIdRef(Long id) {
        Optional<Customer> customer = customerRepository.findById(id);

        return customer.map(this::convertToDtoRef).orElse(null);
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

    @Override
    public Optional<CustomerDto> getFromAuthentication(Authentication authentication) {
        if (Objects.isNull(authentication)) {
            return Optional.empty();
        }
        Customer customer = customerRepository.findByEmail(authentication.getName());
        return customer != null ? Optional.of(convertToDto(customer)) : Optional.empty();
    }

    @Override
    public com.example.internship.refactoringdto.CustomerDto update(Long customerId, com.example.internship.refactoringdto.CustomerDto customerDto) {
        Customer customer = customerRepository.findById(customerId).orElse(null);

        if (Objects.isNull(customer)) return null;

        customer.setFirstName(customerDto.getFirstName());
        customer.setMiddleName(customerDto.getMiddleName());
        customer.setLastName(customerDto.getLastName());
        customer.setEmail(customerDto.getEmail());
        customer.setPhone(customerDto.getPhone());
        customerRepository.save(customer);

        customerDto.setId(customerId);

        return customerDto;
    }

    @Override
    public final void save(Customer customer) {
        customerRepository.save(customer);
    }

    @Override
    public final void delete(Long id) {
        customerRepository.deleteById(id);
    }

    @Override
    public final void deleteAll() {
        customerRepository.deleteAll();
    }

    @Override
    public CustomerDto createAnonymousCustomer() {
        return convertToDto(customerRepository.save(new Customer()));
    }

    @Override
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

    @Override
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

    @Override
    public void customerIdDeleteFromCookie() {
        // Создаем новый куки id покупателя
        Cookie customerIdCookie = new Cookie("customerId", null);
        // Время жизни = 0
        customerIdCookie.setMaxAge(0);
        // Перезаписываем куки
        response.addCookie(customerIdCookie);
    }

    @Override
    public void customerIdAddToCookie(Long customerId) {
        response.addCookie(new Cookie("customerId", customerId.toString()));
    }

    @Override
    public boolean isAnonymousCustomer(Long customerId) {
        Customer customer = customerRepository.findById(customerId).orElse(null);

        return customer != null && customer.getEmail() == null;
    }

    // TODO: Используется только в CheckoutController, есть смысл написать конвертацию в ДТО CheckoutController!
    public CustomerDto getCustomerDto (Customer customer) { return convertToDto(customer); }

    @Override
    public CustomerSearchResponse search(String firstName, String middleName, String lastName, String email,
                                         Integer pageSize, Integer pageNumber) {

        CustomerSearchResponse customerSearchResponse = new CustomerSearchResponse();
        Specification<Customer> specification;

        // Формируем условия для запроса
        specification = draftSpecification(null,"firstName", firstName);
        specification = draftSpecification(specification,"middleName", middleName);
        specification = draftSpecification(specification,"lastName", lastName);
        specification = draftSpecification(specification,"email", email);
        specification = draftSpecification(specification,"emailNotNull", "islNotNull");

        // Результат поиска
        customerSearchResponse.setCustomers(customerRepository.findAll(specification, PageRequest.of(pageNumber, pageSize))
                .stream().map(this::convertToDtoRef)
                .collect(Collectors.toList()));
        customerSearchResponse.setPageNumber(pageNumber);
        customerSearchResponse.setPageSize(pageSize);
        customerSearchResponse.setTotalCustomers(customerRepository.count(specification));

        return customerSearchResponse;
    }

    @Override
    public void updateLastActivity(Long customerId) {
        customerRepository.setLastActivityForCustomers(customerId);
    }

    /**
     * Метод проверяет в на наличие пользователя с такой почтой.
     *
     * @param email почта пользователя.
     * @return Customer
     */
    @Override
    public boolean checkEmail(final String email) {
        return customerRepository.existsByEmail(email);
    }

    //Метод проверки поля и добавления условия в запрос
    /**
     * Проверка полей и добавление в запрос.
     *
     * @param specification ???
     * @param columnName ???
     * @param optionalName ???
     * @return ???
     */
    private Specification<Customer> draftSpecification(Specification<Customer> specification, String columnName,
                                                       String optionalName) {
        if (optionalName != null) {
            if (specification == null) {
                specification = Specification.where(new CustomerSpecification(columnName, optionalName));
            } else {
                specification = specification.and(new CustomerSpecification(columnName, optionalName));
            }
        }
        return specification;
    }

    /**
     * Конвертирует сущность пользователя в ДТО.
     *
     * @param customer сущность пользователя.
     * @return ДТО пользователя.
     *
     * @deprecated
     */
    private CustomerDto convertToDto(Customer customer) {
        return mapper.map(customer, CustomerDto.class);
    }

    /**
     * Конвертирует сущность пользователя в ДТО.
     *
     * @param customer сущность пользователя.
     * @return ДТО пользователя.
     */
    private com.example.internship.refactoringdto.CustomerDto convertToDtoRef(Customer customer) {
        return mapper.map(customer, com.example.internship.refactoringdto.CustomerDto.class);
    }

    /**
     * Конвертирует ДТО пользователя в сущность.
     *
     * @param customerDto ДТО пользователя.
     * @return сущность пользователя.
     */
    private Customer convertToEntity(com.example.internship.refactoringdto.CustomerDto customerDto) {
        return mapper.map(customerDto, Customer.class);
    }

    /**
     * Конвертирует ДТО пользователя в сущность.
     *
     * @param customerDto ДТО пользователя.
     * @return сущность пользователя.
     *
     * @deprecated
     */
    private Customer convertToModel(CustomerDto customerDto) {
        return mapper.map(customerDto, Customer.class);
    }

}
