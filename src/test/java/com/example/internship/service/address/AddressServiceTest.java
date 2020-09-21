package com.example.internship.service.address;

import com.example.internship.entity.Address;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@TestPropertySource("classpath:test.properties")
@DisplayName("Тесты Адресного сервиса")
class AddressServiceTest {

    @Autowired AddressService addressService;

    @Test
    @DisplayName("Пустой адрес возвращает пустое значение")
    void emptyAddressShouldReturnsEmptyValue() {
        Address address = new Address();
        assertTrue(StringUtils.isBlank(addressService.generateFullAddress(address)));
    }
}