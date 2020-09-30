package com.example.internship.service.customer;

import com.example.internship.dto.customer.CustomerDto.WithFullName;
import com.example.internship.dto.customer.SearchResult;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@TestPropertySource("classpath:test.properties")
class CustomerServiceTest {

    private static final String MSG_CUSTOMERS_SIZE_IS = "Длина списка покупателей должна быть %d";
    private static final String MSG_PAGE_NUMBER_IS = "Номер страницы должен быть %d";
    private static final String MSG_PAGE_SIZE_IS = "Размер страницы должен быть %d";
    private static final String MSG_TOTAL_IS = "Общее количество покупателей должно быть %d";
    private static final String MSG_ORDER_IS = "Порядок сортировки должен быть %s";
    private static final String MSG_TOTAL_PAGES_IS = "Количество страниц должно быть %d";

    @Autowired
    private CustomerService customerService;

    @BeforeAll
    static void beforeAll(
            @Autowired CustomerService service
    ) {
        for (int i = 1; i <= 100; i++) {
            WithFullName customer = new WithFullName();
            customer.setLastName("Customer");
            customer.setFirstName(Integer.toString(i));
            customer.setEmail("Customer" + i + "@mail.mu");
            customer.setPhone(String.format("+7 000 %03d-00-00", i));
            customer.setAddresses(Collections.emptyList());
            service.save(customer);
        }
    }

    @AfterAll
    static void afterAll(
            @Autowired CustomerService service
    ) {
        service.deleteAll();
    }

    @Test
    @DisplayName("Запрос списка покупателей с нулевыми критериями")
    void findByCriteriaWithNullParameters() {
        final SearchResult<WithFullName> result = customerService.findByCriteria(null, null, null, null);
        assertAll(
                () -> assertEquals(20, result.getCustomers().size(), () -> String.format(MSG_CUSTOMERS_SIZE_IS, 20)),
                () -> assertEquals(0, result.getPageNumber(), () -> String.format(MSG_PAGE_NUMBER_IS, 0)),
                () -> assertEquals(20, result.getPageSize(), () -> String.format(MSG_PAGE_SIZE_IS, 20)),
                () -> assertEquals(5, result.getTotalPages(), () -> String.format(MSG_TOTAL_PAGES_IS, 5)),
                () -> assertEquals(100, result.getTotalCustomers(), () -> String.format(MSG_TOTAL_IS, 100)),
                () -> assertEquals(true, result.getAscendingOrder(), () -> String.format(MSG_ORDER_IS, true))
        );
    }

    @Test
    @DisplayName("Поиск покупателей по подстроке в телефоне")
    void findByCriteriaWithSearchStringInPhone() {
        // Должен найтись один покупатель с телефоном +7 000 099-00-00
        final SearchResult<WithFullName> result = customerService.findByCriteria("099", null, null, null);
        assertAll(
                () -> assertEquals(1, result.getCustomers().size(), () -> String.format(MSG_CUSTOMERS_SIZE_IS, 1)),
                () -> assertEquals(0, result.getPageNumber(), () -> String.format(MSG_PAGE_NUMBER_IS, 0)),
                () -> assertEquals(20, result.getPageSize(), () -> String.format(MSG_PAGE_SIZE_IS, 20)),
                () -> assertEquals(1, result.getTotalPages(), () -> String.format(MSG_TOTAL_PAGES_IS, 1)),
                () -> assertEquals(1, result.getTotalCustomers(), () -> String.format(MSG_TOTAL_IS, 1)),
                () -> assertEquals(true, result.getAscendingOrder(), () -> String.format(MSG_ORDER_IS, true))
        );
    }

    @Test
    @DisplayName("Поиск покупателей по подстроке в почте")
    void findByCriteriaWithSearchStringInEmail () {
        // Должен найтись один покупатель с почтой Customer75@mail.mu
        final SearchResult<WithFullName> result = customerService.findByCriteria("75@", null, null, null);
        assertAll(
                () -> assertEquals(1, result.getCustomers().size(), () -> String.format(MSG_CUSTOMERS_SIZE_IS, 1)),
                () -> assertEquals(0, result.getPageNumber(), () -> String.format(MSG_PAGE_NUMBER_IS, 0)),
                () -> assertEquals(20, result.getPageSize(), () -> String.format(MSG_PAGE_SIZE_IS, 20)),
                () -> assertEquals(1, result.getTotalPages(), () -> String.format(MSG_TOTAL_PAGES_IS, 1)),
                () -> assertEquals(1, result.getTotalCustomers(), () -> String.format(MSG_TOTAL_IS, 1)),
                () -> assertEquals(true, result.getAscendingOrder(), () -> String.format(MSG_ORDER_IS, true))
        );
    }

    @Test
    @DisplayName("Переход на существующую страницу")
    void findByCriteriaWithSecondPage () {
        // Должна возвратиться вторая страница
        final SearchResult<WithFullName> result = customerService.findByCriteria(null, 1, null, null);
        assertAll(
                () -> assertEquals(20, result.getCustomers().size(), () -> String.format(MSG_CUSTOMERS_SIZE_IS, 20)),
                () -> assertEquals(1, result.getPageNumber(), () -> String.format(MSG_PAGE_NUMBER_IS, 1)),
                () -> assertEquals(20, result.getPageSize(), () -> String.format(MSG_PAGE_SIZE_IS, 20)),
                () -> assertEquals(5, result.getTotalPages(), () -> String.format(MSG_TOTAL_PAGES_IS, 5)),
                () -> assertEquals(100, result.getTotalCustomers(), () -> String.format(MSG_TOTAL_IS, 100)),
                () -> assertEquals(true, result.getAscendingOrder(), () -> String.format(MSG_ORDER_IS, true))
        );
    }

    @Test
    @DisplayName("Переход на несуществующую страницу")
    void findByCriteriaWithNonExistingPage() {
        final SearchResult<WithFullName> result = customerService.findByCriteria(null, 1000, null, null);
        assertAll(
                () -> assertEquals(0, result.getCustomers().size(), () -> String.format(MSG_CUSTOMERS_SIZE_IS, 0)),
                () -> assertEquals(1000, result.getPageNumber(), () -> String.format(MSG_PAGE_NUMBER_IS, 1000)),
                () -> assertEquals(20, result.getPageSize(), () -> String.format(MSG_PAGE_SIZE_IS, 20)),
                () -> assertEquals(5, result.getTotalPages(), () -> String.format(MSG_TOTAL_PAGES_IS, 5)),
                () -> assertEquals(100, result.getTotalCustomers(), () -> String.format(MSG_TOTAL_IS, 100)),
                () -> assertEquals(true, result.getAscendingOrder(), () -> String.format(MSG_ORDER_IS, true))
        );
    }

    @Test
    @DisplayName("Изменение размера страницы")
    void findByCriteriaWithPageSize() {
        final SearchResult<WithFullName> result = customerService.findByCriteria(null, null, 10, null);
        assertAll(
                () -> assertEquals(10, result.getCustomers().size(), () -> String.format(MSG_CUSTOMERS_SIZE_IS, 10)),
                () -> assertEquals(0, result.getPageNumber(), () -> String.format(MSG_PAGE_NUMBER_IS, 0)),
                () -> assertEquals(10, result.getPageSize(), () -> String.format(MSG_PAGE_SIZE_IS, 10)),
                () -> assertEquals(10, result.getTotalPages(), () -> String.format(MSG_TOTAL_PAGES_IS, 10)),
                () -> assertEquals(100, result.getTotalCustomers(), () -> String.format(MSG_TOTAL_IS, 100)),
                () -> assertEquals(true, result.getAscendingOrder(), () -> String.format(MSG_ORDER_IS, true))
        );
    }

    @Test
    @DisplayName("Сортировка по возрастанию")
    void findByCriteriaWithAscendingOrder() {
        final SearchResult<WithFullName> result = customerService.findByCriteria(null, null, 100, true);
        assertAll(
                () -> assertEquals("Customer 1", result.getCustomers().get(0).getFullName(), "Имя первого покупателя должно быть Customer 1"),
                () -> assertEquals(100, result.getCustomers().size(), () -> String.format(MSG_CUSTOMERS_SIZE_IS, 100)),
                () -> assertEquals(0, result.getPageNumber(), () -> String.format(MSG_PAGE_NUMBER_IS, 0)),
                () -> assertEquals(1, result.getTotalPages(), () -> String.format(MSG_TOTAL_PAGES_IS, 1)),
                () -> assertEquals(100, result.getPageSize(), () -> String.format(MSG_PAGE_SIZE_IS, 100)),
                () -> assertEquals(100, result.getTotalCustomers(), () -> String.format(MSG_TOTAL_IS, 100)),
                () -> assertEquals(true, result.getAscendingOrder(), () -> String.format(MSG_ORDER_IS, true))
        );
    }

    @Test
    @DisplayName("Сортировка по убыванию")
    void findByCriteriaWithDescendingOrder() {
        final SearchResult<WithFullName> result = customerService.findByCriteria(null, null, 100, false);
        assertAll(
                () -> assertEquals("Customer 1", result.getCustomers().get(99).getFullName(), "Имя последнего покупателя должно быть Customer 1"),
                () -> assertEquals(100, result.getCustomers().size(), () -> String.format(MSG_CUSTOMERS_SIZE_IS, 100)),
                () -> assertEquals(0, result.getPageNumber(), () -> String.format(MSG_PAGE_NUMBER_IS, 0)),
                () -> assertEquals(1, result.getTotalPages(), () -> String.format(MSG_TOTAL_PAGES_IS, 1)),
                () -> assertEquals(100, result.getPageSize(), () -> String.format(MSG_PAGE_SIZE_IS, 100)),
                () -> assertEquals(100, result.getTotalCustomers(), () -> String.format(MSG_TOTAL_IS, 100)),
                () -> assertEquals(false, result.getAscendingOrder(), () -> String.format(MSG_ORDER_IS, false))
        );
    }

    @Test
    @DisplayName("Запрос по всем параметрам")
    void findByCriteriaWithAllParameters() {
        // Должно найтись 19 покупателей, отсортированных в обратном порядке
        final SearchResult<WithFullName> result = customerService.findByCriteria("9", 0, 10, false);
        assertAll(
                () -> assertEquals("Customer 99", result.getCustomers().get(0).getFullName(), "Имя первого покупателя должно быть Customer 99"),
                () -> assertEquals(10, result.getCustomers().size(), () -> String.format(MSG_CUSTOMERS_SIZE_IS, 10)),
                () -> assertEquals(0, result.getPageNumber(), () -> String.format(MSG_PAGE_NUMBER_IS, 0)),
                () -> assertEquals(2, result.getTotalPages(), () -> String.format(MSG_TOTAL_PAGES_IS, 2)),
                () -> assertEquals(10, result.getPageSize(), () -> String.format(MSG_PAGE_SIZE_IS, 10)),
                () -> assertEquals(19, result.getTotalCustomers(), () -> String.format(MSG_TOTAL_IS, 19)),
                () -> assertEquals(false, result.getAscendingOrder(), () -> String.format(MSG_ORDER_IS, false))
        );
    }
}