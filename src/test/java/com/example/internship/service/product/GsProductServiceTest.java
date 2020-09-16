package com.example.internship.service.product;

import com.example.internship.dto.product.ProductDto;
import com.example.internship.dto.product.SearchResult;
import com.example.internship.entity.Category;
import com.example.internship.entity.Product;
import com.example.internship.service.category.GsCategoryService;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@TestPropertySource("classpath:test.properties")
@SpringBootTest
@DisplayName("Тест сервиса GsProductService")
class GsProductServiceTest {

    private static final long PRODUCT_NUMBER = 100L;
    private static final long CATEGORY_ID = 1L;
    private static final long SUBCATEGORY_ID = CATEGORY_ID + 1;
    private static final String CATEGORY_NAME = "Category";

    @Autowired GsProductService productService;

    @BeforeAll
    static void beforeAll(
            @Autowired GsCategoryService categoryService,
            @Autowired GsProductService productService
    ) {
        // Одна общая категория
        Category category = new Category();
        category.setId(CATEGORY_ID);
        category.setName(CATEGORY_NAME);
        categoryService.save(category);

        // Субкатегория для проверки крошек
        Category subcategory = new Category();
        subcategory.setId(SUBCATEGORY_ID);
        subcategory.setParent(category);
        subcategory.setName("Sub" + CATEGORY_NAME);
        categoryService.save(subcategory);

        // Генерация списка продуктов
        for (long i = 1; i <= PRODUCT_NUMBER; i++) {
            Product product = new Product();
            product.setId(i);
            product.setCategory(category);
            product.setName("Phone " + i);
            product.setDescription("Description " + i);
            product.setPrice(BigDecimal.valueOf(i));
            productService.save(product);
        }
    }

    @AfterAll
    static void afterAll(
            @Autowired GsCategoryService categoryService,
            @Autowired GsProductService productService
    ) {
        // Удаляем следы пребывания в тестовой базе
        productService.deleteAll();
        categoryService.deleteAll();
    }

    @Test
    @DisplayName("Все аргументы равны null")
    void testAllNulls() {
        final SearchResult result = productService.findByCriteria(
                null,
                null,
                null,
                null,
                null,
                null,
                null);
        assertAll(
                () -> assertNull(result.getCategoryId(), "Категория должна быть null"),
                () -> assertEquals(result.getProducts().size(), 20, "Размер страницы по умолчанию не равен 20"),
                () -> assertFalse(result.getTopCategories().isEmpty(), "Список родительских категорий не должен быть пуст"),
                () -> assertTrue(result.getBreadcrumbs().isEmpty(), "Список хлебных крошек должен быть пуст"),
                () -> assertTrue(result.getPages().length > 1, "Количество страниц должно быть больше 0"),
                () -> assertEquals(result.getPageNumber(), 0, "Номер первой страницы должен быть равен 0"),
                () -> assertEquals(result.getPageSize(), 20, "Размер страницы по умолчанию должен быть равен 20"),
                () -> assertEquals(result.getTotal(), PRODUCT_NUMBER, "Общее количество продуктов должно быть равно " + PRODUCT_NUMBER),
                () -> assertEquals(result.getMinimalPrice(), result.getLowerPriceLimit(), "Минимальная цена и нижняя граница должны быть равны"),
                () -> assertEquals(result.getMaximalPrice(), result.getUpperPriceLimit(), "Максимальная цена и верхняя граница должны быть равны"),
                () -> assertNull(result.getDescendingOrder(), "Флаг сортировки должен быть null")
        );
    }

    @Test
    @DisplayName("Поиск по подстроке")
    void testSearchString() {
        final SearchResult result = productService.findByCriteria(
                "Phone 99",
                null,
                null,
                null,
                null,
                null,
                null);
        assertEquals(result.getTotal(), 1L, "Должен найтись только один продукт");
    }

    @Test
    @DisplayName("Поиск по несуществующей подстроке")
    void testNonExistingSearchString() {
        final SearchResult result = productService.findByCriteria(
                "Такого продукта уж точно нет в базе!!!",
                null,
                null,
                null,
                null,
                null,
                null);
        assertEquals(result.getTotal(), 0L, "Количество найденных продуктов должно быть равно 0");
    }

    @Test
    @DisplayName("Поиск по категории")
    void testCategoryId() {
        final SearchResult result = productService.findByCriteria(
                null,
                CATEGORY_ID,
                null,
                null,
                null,
                null,
                null);
        assertAll(
                () -> assertEquals(result.getCategoryId(), CATEGORY_ID, "Не совпадают идентификаторы категории"),
                () -> assertEquals(result.getProducts().size(), 20, "Количество продуктов на странице должно быть 20")
        );
    }

    @Test
    @DisplayName("Поиск по несуществующей категории")
    void testNonExistingCategoryId() {
        final SearchResult result = productService.findByCriteria(
                null,
                Long.MAX_VALUE,
                null,
                null,
                null,
                null,
                null);
        assertAll(
                () -> assertEquals(result.getProducts().size(), 0, "Количество найденных продуктов должно быть равно 0")
        );
    }

    @Test
    @DisplayName("Поиск c ограничением цен")
    void testLimits() {
        BigDecimal min = BigDecimal.ONE;
        BigDecimal max = BigDecimal.TEN;
        final SearchResult result = productService.findByCriteria(
                null,
                null,
                min,
                max,
                null,
                null,
                null);
        assertAll(
                () -> assertTrue(result.getProducts().stream().allMatch(p -> inLimits(p, min, max)))
        );
    }

    @Test
    @DisplayName("Поиск c неправильным ограничением цен")
    void testIllegalLimits() {
        BigDecimal min = BigDecimal.ONE;
        BigDecimal max = BigDecimal.TEN;
        final SearchResult result = productService.findByCriteria(
                null,
                null,
                max,
                min,
                null,
                null,
                null);
        assertAll(
                () -> assertEquals(result.getProducts().size(), 0, "Должно быть найдено 0 продуктов")
        );
    }

    @Test
    @DisplayName("Переход на существующую страницу")
    void testExistingPageNumber() {
        final SearchResult result = productService.findByCriteria(
                null,
                null,
                null,
                null,
                2,
                null,
                null);
        assertAll(
                () -> assertEquals(result.getPageNumber(), 2, "Должен быть совершён переход на 2 страницу")
        );
    }

    @Test
    @DisplayName("Переход на несуществующую страницу")
    void testNonExistingPageNumber() {
        final SearchResult result = productService.findByCriteria(
                null,
                null,
                null,
                null,
                1000, // Заведомо несуществующий номер страницы
                null,
                null);
        assertAll(
                () -> assertEquals(result.getProducts().size(), 0, "Не должно быть ни одного продукта")
        );
    }

    @Test
    @DisplayName("Изменение размера страницы")
    void testChangePageSize() {
        final SearchResult result = productService.findByCriteria(
                null,
                null,
                null,
                null,
                null, // Заведомо несуществующий номер страницы
                1,
                null);
        assertAll(
                () -> assertEquals(result.getProducts().size(), 1, "На странице должен быть только один продукт")
        );
    }

    @Test
    @DisplayName("Проверка хлебных крошек")
    void testBreadcrumbs() {
        final SearchResult result = productService.findByCriteria(
                null,
                SUBCATEGORY_ID,
                null,
                null,
                null, // Заведомо несуществующий номер страницы
                1,
                null);
        assertAll(
                () -> assertEquals(result.getBreadcrumbs().size(), 2, "Должна быть две хлебные крошки")
        );
    }

    @Test
    @DisplayName("Сложный запрос")
    void testComplexCriteria() {
        final SearchResult result = productService.findByCriteria(
                "Phone 99",
                CATEGORY_ID,
                BigDecimal.ZERO,
                BigDecimal.valueOf(PRODUCT_NUMBER),
                0,
                (int) PRODUCT_NUMBER,
                true);
        assertAll(
                () -> assertFalse(result.getProducts().isEmpty(), "Должен быть найден хотя бы один продукт")
        );
    }

    private boolean inLimits(ProductDto.Response.AllWithCategoryId p, BigDecimal min, BigDecimal max) {
        return p.getPrice().compareTo(min) >= 0 && p.getPrice().compareTo(max) <= 0;
    }
}