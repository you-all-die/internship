package com.example.internship.helper;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Тесты вспомогательного класса для объединения строк")
class JoinHelperTest {

    @Test
    @DisplayName("Объединение не пустых строк")
    void testNotBlankParameters() {
        assertEquals("1 2 3", JoinHelper.join(" ", "1", "2", "3"));
    }

    @Test
    @DisplayName("Пропуск пустых строк")
    void testIgnoreBlankParameters() {
        assertEquals("1 3", JoinHelper.join(" ", "1", " ", "3"));
    }

    @Test
    @DisplayName("Пропуск нулевых строк")
    void testIgnoreNullParameters() {
        assertEquals("1 3", JoinHelper.join(" ", "1", null, "3"));
    }

    @Test
    @DisplayName("Только нулевые параметры")
    void testNullParametersOnly() {
        assertEquals("", JoinHelper.join(" ", null, null, null));
    }
}