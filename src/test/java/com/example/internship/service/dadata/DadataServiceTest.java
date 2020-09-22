package com.example.internship.service.dadata;

import com.kuliginstepan.dadata.client.domain.Suggestion;
import com.kuliginstepan.dadata.client.domain.address.Address;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestPropertySource("classpath:test.properties")
@DisplayName("Тесты сервиса Dadata.ru")
class DadataServiceTest {

    @Autowired DadataService dadataService;

    @Test
    @DisplayName("Получение адресной подсказки от dadata.ru")
    void testAddressSuggestion() {
        final List<Suggestion<Address>> suggestions = dadataService
                .getSuggestionForAddress("краснодар красная 158")
                .collectList()
                .block();
        assertAll(
                () -> {
                    assertNotNull(suggestions);
                    assertFalse(suggestions.isEmpty());
                }
        );
    }
}