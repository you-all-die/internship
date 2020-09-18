package com.example.internship.service.customer;

import com.example.internship.dto.customer.SearchResult;
import com.example.internship.entity.Customer;
import com.example.internship.specification.customer.CustomerSpecificator;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CustomerServiceTest {

    private static final String MSG_NAMES_ARE = "Ожидалось %s";
    @Autowired
    private static CustomerService customerService;

    @BeforeAll
    static void beforeAll() {
        for (int i = 1; i <= 100; i++) {
            Customer customer = new Customer();
            customer.setLastName("Customer");
            customer.setFirstName(Integer.toString(i));
            customer.setEmail("Customer" + i + "@mail.mu");
            customer.setPhone(String.format("+7 000 %3d-00-00", i));
            customerService.save(customer);
        }
    }

    @AfterEach
    void tearDown() {
        customerService.deleteAll();
    }

    @Test
    @DisplayName("Генерация анонимного покупателя")
    void generateFullNameForAnonymous() {
        Customer anonymous = new Customer();
        assertEquals("", customerService.generateFullName(anonymous), "Должна возвращаться пустая строка");
    }

    @Test
    @DisplayName("У покупателя заполнена только фамилия")
    void generateFullNameWithLastName() {
        Customer customer = new Customer();
        customer.setLastName("Фамилия");
        String expected = "Фамилия";
        assertEquals(expected, customerService.generateFullName(customer), () -> String.format(MSG_NAMES_ARE, expected));
    }

    @Test
    @DisplayName("У покупателя заполнено только имя")
    void generateFullNameWithFirstName() {
        Customer customer = new Customer();
        customer.setFirstName("Имя");
        String expected = "Имя";
        assertEquals(expected, customerService.generateFullName(customer), () -> String.format(MSG_NAMES_ARE, expected));
    }

    @Test
    @DisplayName("У покупателя заполнено только отчество")
    void generateFullNameWithMiddleName() {
        Customer customer = new Customer();
        customer.setMiddleName("Отчество");
        String expected = "Отчество";
        assertEquals(expected, customerService.generateFullName(customer), () -> String.format(MSG_NAMES_ARE, expected));
    }

    @Test
    @DisplayName("У покупателя заполнено только фамилия и имя")
    void generateFullNameWithLastAndFirstName() {
        Customer customer = new Customer();
        customer.setLastName("Фамилия");
        customer.setFirstName("Имя");
        String expected = "Фамилия Имя";
        assertEquals(expected, customerService.generateFullName(customer), () -> String.format(MSG_NAMES_ARE, expected));
    }

    @Test
    @DisplayName("У покупателя заполнено только имя и отчество")
    void generateFullNameWithFirstAndMiddleName() {
        Customer customer = new Customer();
        customer.setFirstName("Имя");
        customer.setMiddleName("Отчество");
        String expected = "Имя Отчество";
        assertEquals(expected, customerService.generateFullName(customer), () -> String.format(MSG_NAMES_ARE, expected));
    }

    @Test
    @DisplayName("У покупателя заполнено только фамилия и отчество")
    void generateFullNameWithLastAndMiddleName() {
        Customer customer = new Customer();
        customer.setLastName("Фамилия");
        customer.setMiddleName("Отчество");
        String expected = "Фамилия Отчество";
        assertEquals(expected, customerService.generateFullName(customer), () -> String.format(MSG_NAMES_ARE, expected));
    }

    @Test
    @DisplayName("У покупателя заполнены все имена")
    void generateFullNameWithAllName() {
        Customer customer = new Customer();
        customer.setLastName("Фамилия");
        customer.setFirstName("Имя");
        customer.setMiddleName("Отчество");
        String expected = "Фамилия Имя Отчество";
        assertEquals(expected, customerService.generateFullName(customer), () -> String.format(MSG_NAMES_ARE, expected));
    }

    @Test
    @DisplayName("Запрос списка покупателей с нулевыми критериями")
    void findByCriteriaWithNullParameters() {
        final SearchResult result = customerService.findByCriteria(null, null, null, null);

    }
}