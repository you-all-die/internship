package com.example.internship.service.product;

import com.example.internship.dto.category.CategoryDto;
import com.example.internship.dto.product.ProductDto;
import com.example.internship.dto.product.SearchResult;
import com.example.internship.entity.Category;
import com.example.internship.helper.PageHelper;
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
    private static final String CATEGORY_NAME = "Category";
    private static final String MSG_CATEGORY_IS_NULL = "Категория должна быть null";
    private static final String MSG_CATEGORY_IS = "Категория должна быть %d";
    private static final String MSG_PAGE_SIZE_IS = "Размер страницы должен быть %d";
    private static final String MSG_TOP_CATEGORY_LIST_IS_NOT_EMPTY = "Список категорий не должен быть пустым";
    private static final String MSG_BREADCRUMB_LIST_IS_EMPTY = "Список хлебных крошек должен быть пуст";
    private static final String MSG_BREADCRUMBS_SIZE_IS = "Количество хлебных крошек должно быть %d";
    private static final String MSG_PAGE_NUMBER_IS = "Номер текущей страницы должен быть %d";
    private static final String MSG_PRODUCT_NUMBER_IS = "Количество продуктов на странице должно быть %d";
    private static final String MSG_TOTAL_IS = "Общее количество продуктов должно быть %d";
    private static final String MSG_LOWER_LIMIT_IS = "Нижняя граница цен должна быть %s";
    private static final String MSG_UPPER_LIMIT_IS = "Верхняя граница цен должна быть %s";
    private static final String MSG_MINIMAL_PRICES_ARE_EQUAL = "Минимальная цена должна быть равна нижней границе цен";
    private static final String MSG_MAXIMAL_PRICES_ARE_EQUAL = "Максимальная цена должна быть равна верхней границе цен";
    private static final String MSG_DESCENDING_ORDER_IS = "Флаг сортировки по убыванию должен быть равен %s";
    private static final String MSG_PAGES_TOTAL_IS = "Количество страниц должно быть %d";

    private static Long categoryId;
    private static Long subcategoryId;

    @Autowired GsProductService productService;

    @BeforeAll
    static void beforeAll(
            @Autowired GsCategoryService categoryService,
            @Autowired GsProductService productService
    ) {
        // Одна общая категория
        final CategoryDto.Request.All categoryDto = new CategoryDto.Request.All();
        categoryDto.setName(CATEGORY_NAME);
        final Category category = categoryService.save(categoryDto);
        categoryId = category.getId();

        // Субкатегория для проверки крошек
        final CategoryDto.Request.All subcategoryDto = new CategoryDto.Request.All();
        subcategoryDto.setParentId(categoryId);
        subcategoryDto.setName("Sub" + CATEGORY_NAME);
        final Category subcategory = categoryService.save(subcategoryDto);
        subcategoryId = subcategory.getId();

        // Генерация списка продуктов
        for (long i = 1; i <= PRODUCT_NUMBER; i++) {
            ProductDto.Request.All productDto = new ProductDto.Request.All();
            productDto.setCategoryId(categoryId);
            productDto.setName("Phone " + i);
            productDto.setDescription("Description " + i);
            productDto.setPrice(BigDecimal.valueOf(i));
            productService.save(productDto);
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
        int pageSizeExpected = 20;
        int pageNumberExpected = 0;
        int totalExpected = (int) PRODUCT_NUMBER;
        int productsExpected = 20;
        int pagesTotalExpected = PageHelper.calculateTotalPages(totalExpected, pageSizeExpected);
        Boolean orderExpected = null;
        final SearchResult result = productService.findByCriteria(
                null,
                null,
                null,
                null,
                null,
                null,
                null);
        assertAll(
                () -> assertNull(result.getCategoryId(), MSG_CATEGORY_IS_NULL),
                () -> assertEquals(productsExpected, result.getProducts().size(), () -> String.format(MSG_PRODUCT_NUMBER_IS, productsExpected)),
                () -> assertFalse(result.getTopCategories().isEmpty(), MSG_TOP_CATEGORY_LIST_IS_NOT_EMPTY),
                () -> assertTrue(result.getBreadcrumbs().isEmpty(), MSG_BREADCRUMB_LIST_IS_EMPTY),
                () -> assertEquals(pageNumberExpected, result.getPageNumber(), () -> String.format(MSG_PAGE_NUMBER_IS, pageNumberExpected)),
                () -> assertEquals(pagesTotalExpected, result.getTotalPages(), () -> String.format(MSG_PAGES_TOTAL_IS, pagesTotalExpected)),
                () -> assertEquals(result.getPageSize(), 20, () -> String.format(MSG_PAGE_SIZE_IS, pageSizeExpected)),
                () -> assertEquals(totalExpected, result.getTotalProducts(),() -> String.format(MSG_TOTAL_IS, totalExpected)),
                () -> assertEquals(result.getMinimalPrice(), result.getLowerPriceLimit(), MSG_MINIMAL_PRICES_ARE_EQUAL),
                () -> assertEquals(result.getMaximalPrice(), result.getUpperPriceLimit(), MSG_MAXIMAL_PRICES_ARE_EQUAL),
                () -> assertEquals(orderExpected, result.getDescendingOrder(), () -> String.format(MSG_DESCENDING_ORDER_IS, orderExpected))
        );
    }

    @Test
    @DisplayName("Поиск по подстроке")
    void testSearchString() {
        int productsExpected = 1;
        int pageSizeExpected = 20;
        int pageNumberExpected = 0;
        int pagesTotalExpected = 1;
        int totalExpected = 1;
        Boolean orderExpected = null;
        final SearchResult result = productService.findByCriteria(
                "Phone 99", // должен найтись только один продукт с таким названием
                null,
                null,
                null,
                null,
                null,
                null);
        assertAll(
                () -> assertNull(result.getCategoryId(), MSG_CATEGORY_IS_NULL),
                () -> assertEquals(productsExpected, result.getProducts().size(), () -> String.format(MSG_PRODUCT_NUMBER_IS, productsExpected)),
                () -> assertFalse(result.getTopCategories().isEmpty(), MSG_TOP_CATEGORY_LIST_IS_NOT_EMPTY),
                () -> assertTrue(result.getBreadcrumbs().isEmpty(), MSG_BREADCRUMB_LIST_IS_EMPTY),
                () -> assertEquals(pageNumberExpected, result.getPageNumber(), () -> String.format(MSG_PAGE_NUMBER_IS, pageNumberExpected)),
                () -> assertEquals(pagesTotalExpected, result.getTotalPages(), () -> String.format(MSG_PAGES_TOTAL_IS, pagesTotalExpected)),
                () -> assertEquals(pageSizeExpected, result.getPageSize(), () -> String.format(MSG_PAGE_SIZE_IS, pageSizeExpected)),
                () -> assertEquals(totalExpected, result.getTotalProducts(),() -> String.format(MSG_TOTAL_IS, totalExpected)),
                () -> assertEquals(result.getMinimalPrice(), result.getLowerPriceLimit(), MSG_MINIMAL_PRICES_ARE_EQUAL),
                () -> assertEquals(result.getMaximalPrice(), result.getUpperPriceLimit(), MSG_MAXIMAL_PRICES_ARE_EQUAL),
                () -> assertEquals(orderExpected, result.getDescendingOrder(), () -> String.format(MSG_DESCENDING_ORDER_IS, orderExpected))
        );
    }

    @Test
    @DisplayName("Поиск по несуществующей подстроке")
    void testNonExistingSearchString() {
        int productsExpected = 0;
        int pageSizeExpected = 20;
        int pageNumberExpected = 0;
        int pagesTotalExpected = 0;
        int totalExpected = 0;
        Boolean orderExpected = null;
        final SearchResult result = productService.findByCriteria(
                "Такого продукта уж точно нет в базе!!!",
                null,
                null,
                null,
                null,
                null,
                null);
        assertAll(
                () -> assertNull(result.getCategoryId(), MSG_CATEGORY_IS_NULL),
                () -> assertEquals(productsExpected, result.getProducts().size(), () -> String.format(MSG_PRODUCT_NUMBER_IS, productsExpected)),
                () -> assertFalse(result.getTopCategories().isEmpty(), MSG_TOP_CATEGORY_LIST_IS_NOT_EMPTY),
                () -> assertTrue(result.getBreadcrumbs().isEmpty(), MSG_BREADCRUMB_LIST_IS_EMPTY),
                () -> assertEquals(pageNumberExpected, result.getPageNumber(), () -> String.format(MSG_PAGE_NUMBER_IS, pageNumberExpected)),
                () -> assertEquals(pagesTotalExpected, result.getTotalPages(), () -> String.format(MSG_PAGES_TOTAL_IS, pagesTotalExpected)),
                () -> assertEquals(pageSizeExpected, result.getPageSize(), () -> String.format(MSG_PAGE_SIZE_IS, pageSizeExpected)),
                () -> assertEquals(totalExpected, result.getTotalProducts(),() -> String.format(MSG_TOTAL_IS, totalExpected)),
                () -> assertEquals(result.getMinimalPrice(), result.getLowerPriceLimit(), MSG_MINIMAL_PRICES_ARE_EQUAL),
                () -> assertEquals(result.getMaximalPrice(), result.getUpperPriceLimit(), MSG_MAXIMAL_PRICES_ARE_EQUAL),
                () -> assertEquals(orderExpected, result.getDescendingOrder(), () -> String.format(MSG_DESCENDING_ORDER_IS, orderExpected))
        );
    }

    @Test
    @DisplayName("Поиск по категории")
    void testCategoryId() {
        int breadcrumbsSizeExpected = 1;
        int productsExpected = 20;
        int pageSizeExpected = 20;
        int pageNumberExpected = 0;
        int totalExpected = (int) PRODUCT_NUMBER;
        int pagesTotalExpected = PageHelper.calculateTotalPages(totalExpected, pageSizeExpected);
        Boolean orderExpected = null;
        final SearchResult result = productService.findByCriteria(
                null,
                categoryId,
                null,
                null,
                null,
                null,
                null);
        assertAll(
                () -> assertEquals(categoryId, result.getCategoryId(), () -> String.format(MSG_CATEGORY_IS, categoryId)),
                () -> assertEquals(productsExpected, result.getProducts().size(), () -> String.format(MSG_PRODUCT_NUMBER_IS, productsExpected)),
                () -> assertFalse(result.getTopCategories().isEmpty(), MSG_TOP_CATEGORY_LIST_IS_NOT_EMPTY),
                () -> assertEquals(breadcrumbsSizeExpected, result.getBreadcrumbs().size(), () -> String.format(MSG_BREADCRUMBS_SIZE_IS, breadcrumbsSizeExpected)),
                () -> assertEquals(pagesTotalExpected, result.getTotalPages(), () -> String.format(MSG_PAGES_TOTAL_IS, pagesTotalExpected)),
                () -> assertEquals(pageNumberExpected, result.getPageNumber(), () -> String.format(MSG_PAGE_NUMBER_IS, pageNumberExpected)),
                () -> assertEquals(pageSizeExpected, result.getPageSize(), () -> String.format(MSG_PAGE_SIZE_IS, pageSizeExpected)),
                () -> assertEquals(totalExpected, result.getTotalProducts(), () -> String.format(MSG_TOTAL_IS, totalExpected)),
                () -> assertEquals(result.getMinimalPrice(), result.getLowerPriceLimit(), MSG_MINIMAL_PRICES_ARE_EQUAL),
                () -> assertEquals(result.getMaximalPrice(), result.getUpperPriceLimit(), MSG_MAXIMAL_PRICES_ARE_EQUAL),
                () -> assertEquals(orderExpected, result.getDescendingOrder(), () -> String.format(MSG_DESCENDING_ORDER_IS, orderExpected))
        );
    }

    @Test
    @DisplayName("Поиск по несуществующей категории")
    void testNonExistingCategoryId() {
        Long categoryIdExpected = Long.MAX_VALUE;
        int breadcrumbsSizeExpected = 0;
        int productsExpected = 0;
        int pageSizeExpected = 20;
        int pageNumberExpected = 0;
        int totalExpected = 0;
        int pagesTotalExpected = PageHelper.calculateTotalPages(totalExpected, pageSizeExpected);
        Boolean orderExpected = null;
        final SearchResult result = productService.findByCriteria(
                null,
                Long.MAX_VALUE,
                null,
                null,
                null,
                null,
                null);
        assertAll(
                () -> assertEquals(categoryIdExpected, result.getCategoryId(), () -> String.format(MSG_CATEGORY_IS, categoryIdExpected)),
                () -> assertEquals(productsExpected, result.getProducts().size(), () -> String.format(MSG_PRODUCT_NUMBER_IS, productsExpected)),
                () -> assertFalse(result.getTopCategories().isEmpty(), MSG_TOP_CATEGORY_LIST_IS_NOT_EMPTY),
                () -> assertEquals(breadcrumbsSizeExpected, result.getBreadcrumbs().size(), () -> String.format(MSG_BREADCRUMBS_SIZE_IS, breadcrumbsSizeExpected)),
                () -> assertEquals(pagesTotalExpected, result.getTotalPages(), () -> String.format(MSG_PAGES_TOTAL_IS, pagesTotalExpected)),
                () -> assertEquals(pageNumberExpected, result.getPageNumber(), () -> String.format(MSG_PAGE_NUMBER_IS, pageNumberExpected)),
                () -> assertEquals(pageSizeExpected, result.getPageSize(), () -> String.format(MSG_PAGE_SIZE_IS, pageSizeExpected)),
                () -> assertEquals(totalExpected, result.getTotalProducts(),() -> String.format(MSG_TOTAL_IS, totalExpected)),
                () -> assertEquals(result.getMinimalPrice(), result.getLowerPriceLimit(), MSG_MINIMAL_PRICES_ARE_EQUAL),
                () -> assertEquals(result.getMaximalPrice(), result.getUpperPriceLimit(), MSG_MAXIMAL_PRICES_ARE_EQUAL),
                () -> assertEquals(orderExpected, result.getDescendingOrder(), () -> String.format(MSG_DESCENDING_ORDER_IS, orderExpected))
        );
    }

    @Test
    @DisplayName("Поиск c ограничением цен")
    void testLimits() {
        int breadcrumbsSizeExpected = 0;
        int productsExpected = 10;
        int pageSizeExpected = 20;
        int pageNumberExpected = 0;
        int totalExpected = 10;
        int pagesTotalExpected = PageHelper.calculateTotalPages(totalExpected, pageSizeExpected);
        Boolean orderExpected = null;
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
                () -> assertNull(result.getCategoryId(), MSG_CATEGORY_IS_NULL),
                () -> assertEquals(productsExpected, result.getProducts().size(), () -> String.format(MSG_PRODUCT_NUMBER_IS, productsExpected)),
                () -> assertFalse(result.getTopCategories().isEmpty(), MSG_TOP_CATEGORY_LIST_IS_NOT_EMPTY),
                () -> assertEquals(breadcrumbsSizeExpected, result.getBreadcrumbs().size(), () -> String.format(MSG_BREADCRUMBS_SIZE_IS, breadcrumbsSizeExpected)),
                () -> assertEquals(pagesTotalExpected, result.getTotalPages(), () -> String.format(MSG_PAGES_TOTAL_IS, pagesTotalExpected)),
                () -> assertEquals(pageNumberExpected, result.getPageNumber(), () -> String.format(MSG_PAGE_NUMBER_IS, pageNumberExpected)),
                () -> assertEquals(pageSizeExpected, result.getPageSize(), () -> String.format(MSG_PAGE_SIZE_IS, pageSizeExpected)),
                () -> assertEquals(totalExpected, result.getTotalProducts(),() -> String.format(MSG_TOTAL_IS, totalExpected)),
                () -> assertEquals(result.getLowerPriceLimit().compareTo(min), 0, () -> String.format(MSG_LOWER_LIMIT_IS, min.toString())),
                () -> assertEquals(result.getUpperPriceLimit().compareTo(max), 0, () -> String.format(MSG_UPPER_LIMIT_IS, max.toString())),
                () -> assertTrue(result.getProducts().stream().allMatch(p -> inLimits(p, min, max))),
                () -> assertEquals(orderExpected, result.getDescendingOrder(), () -> String.format(MSG_DESCENDING_ORDER_IS, orderExpected))
        );
    }

    @Test
    @DisplayName("Поиск c неправильным ограничением цен")
    void testIllegalLimits() {
        int breadcrumbsSizeExpected = 0;
        int productsExpected = 0;
        int pageSizeExpected = 20;
        int pageNumberExpected = 0;
        int totalExpected = 0;
        int pagesTotalExpected = PageHelper.calculateTotalPages(totalExpected, pageSizeExpected);
        Boolean orderExpected = null;
        BigDecimal min = BigDecimal.TEN;
        BigDecimal max = BigDecimal.ONE;
        final SearchResult result = productService.findByCriteria(
                null,
                null,
                min,
                max,
                null,
                null,
                null);
        assertAll(
                () -> assertNull(result.getCategoryId(), MSG_CATEGORY_IS_NULL),
                () -> assertEquals(productsExpected, result.getProducts().size(), () -> String.format(MSG_PRODUCT_NUMBER_IS, productsExpected)),
                () -> assertFalse(result.getTopCategories().isEmpty(), MSG_TOP_CATEGORY_LIST_IS_NOT_EMPTY),
                () -> assertEquals(breadcrumbsSizeExpected, result.getBreadcrumbs().size(), () -> String.format(MSG_BREADCRUMBS_SIZE_IS, breadcrumbsSizeExpected)),
                () -> assertEquals(pagesTotalExpected, result.getTotalPages(), () -> String.format(MSG_PAGES_TOTAL_IS, pagesTotalExpected)),
                () -> assertEquals(pageNumberExpected, result.getPageNumber(), () -> String.format(MSG_PAGE_NUMBER_IS, pageNumberExpected)),
                () -> assertEquals(pageSizeExpected, result.getPageSize(), () -> String.format(MSG_PAGE_SIZE_IS, pageSizeExpected)),
                () -> assertEquals(totalExpected, result.getTotalProducts(),() -> String.format(MSG_TOTAL_IS, totalExpected)),
                () -> assertEquals(result.getLowerPriceLimit().compareTo(min), 0, () -> String.format(MSG_LOWER_LIMIT_IS, min.toString())),
                () -> assertEquals(result.getUpperPriceLimit().compareTo(max), 0, () -> String.format(MSG_UPPER_LIMIT_IS, max.toString())),
                () -> assertEquals(orderExpected, result.getDescendingOrder(), () -> String.format(MSG_DESCENDING_ORDER_IS, orderExpected))
        );
    }

    @Test
    @DisplayName("Переход на существующую страницу")
    void testExistingPageNumber() {
        int breadcrumbsSizeExpected = 0;
        int productsExpected = 20;
        int pageSizeExpected = 20;
        int pageNumberExpected = 2;
        int totalExpected = (int) PRODUCT_NUMBER;
        int pagesTotalExpected = PageHelper.calculateTotalPages(totalExpected, pageSizeExpected);
        Boolean orderExpected = null;
        final SearchResult result = productService.findByCriteria(
                null,
                null,
                null,
                null,
                pageNumberExpected,
                null,
                null);
        assertAll(
                () -> assertNull(result.getCategoryId(), MSG_CATEGORY_IS_NULL),
                () -> assertEquals(productsExpected, result.getProducts().size(), () -> String.format(MSG_PRODUCT_NUMBER_IS, productsExpected)),
                () -> assertFalse(result.getTopCategories().isEmpty(), MSG_TOP_CATEGORY_LIST_IS_NOT_EMPTY),
                () -> assertEquals(breadcrumbsSizeExpected, result.getBreadcrumbs().size(), () -> String.format(MSG_BREADCRUMBS_SIZE_IS, breadcrumbsSizeExpected)),
                () -> assertEquals(pagesTotalExpected, result.getTotalPages(), () -> String.format(MSG_PAGES_TOTAL_IS, pagesTotalExpected)),
                () -> assertEquals(pageNumberExpected, result.getPageNumber(), () -> String.format(MSG_PAGE_NUMBER_IS, pageNumberExpected)),
                () -> assertEquals(pageSizeExpected, result.getPageSize(), () -> String.format(MSG_PAGE_SIZE_IS, pageSizeExpected)),
                () -> assertEquals(totalExpected, result.getTotalProducts(),() -> String.format(MSG_TOTAL_IS, totalExpected)),
                () -> assertEquals(result.getMinimalPrice(), result.getLowerPriceLimit(), MSG_MINIMAL_PRICES_ARE_EQUAL),
                () -> assertEquals(result.getMaximalPrice(), result.getUpperPriceLimit(), MSG_MAXIMAL_PRICES_ARE_EQUAL),
                () -> assertEquals(orderExpected, result.getDescendingOrder(), () -> String.format(MSG_DESCENDING_ORDER_IS, orderExpected))
        );
    }

    @Test
    @DisplayName("Переход на несуществующую страницу")
    void testNonExistingPageNumber() {
        int breadcrumbsSizeExpected = 0;
        int productsExpected = 0;
        int pageSizeExpected = 20;
        int pageNumberExpected = 1000;
        int totalExpected = (int) PRODUCT_NUMBER;
        int pagesTotalExpected = PageHelper.calculateTotalPages(totalExpected, pageSizeExpected);
        Boolean orderExpected = null;
        final SearchResult result = productService.findByCriteria(
                null,
                null,
                null,
                null,
                pageNumberExpected, // Заведомо несуществующий номер страницы
                null,
                null);
        assertAll(
                () -> assertNull(result.getCategoryId(), MSG_CATEGORY_IS_NULL),
                () -> assertEquals(productsExpected, result.getProducts().size(), () -> String.format(MSG_PRODUCT_NUMBER_IS, productsExpected)),
                () -> assertFalse(result.getTopCategories().isEmpty(), MSG_TOP_CATEGORY_LIST_IS_NOT_EMPTY),
                () -> assertEquals(breadcrumbsSizeExpected, result.getBreadcrumbs().size(), () -> String.format(MSG_BREADCRUMBS_SIZE_IS, breadcrumbsSizeExpected)),
                () -> assertEquals(pagesTotalExpected, result.getTotalPages(), () -> String.format(MSG_PAGES_TOTAL_IS, pagesTotalExpected)),
                () -> assertEquals(pageNumberExpected, result.getPageNumber(), () -> String.format(MSG_PAGE_NUMBER_IS, pageNumberExpected)),
                () -> assertEquals(pageSizeExpected, result.getPageSize(), () -> String.format(MSG_PAGE_SIZE_IS, pageSizeExpected)),
                () -> assertEquals(totalExpected, result.getTotalProducts(),() -> String.format(MSG_TOTAL_IS, totalExpected)),
                () -> assertEquals(result.getMinimalPrice(), result.getLowerPriceLimit(), MSG_MINIMAL_PRICES_ARE_EQUAL),
                () -> assertEquals(result.getMaximalPrice(), result.getUpperPriceLimit(), MSG_MAXIMAL_PRICES_ARE_EQUAL),
                () -> assertEquals(orderExpected, result.getDescendingOrder(), () -> String.format(MSG_DESCENDING_ORDER_IS, orderExpected))
        );
    }

    @Test
    @DisplayName("Изменение размера страницы")
    void testChangePageSize() {
        int breadcrumbsSizeExpected = 0;
        int productsExpected = 1;
        int pageSizeExpected = 1;
        int pageNumberExpected = 0;
        int totalExpected = (int) PRODUCT_NUMBER;
        int pagesTotalExpected = PageHelper.calculateTotalPages(totalExpected, pageSizeExpected);
        Boolean orderExpected = null;
        final SearchResult result = productService.findByCriteria(
                null,
                null,
                null,
                null,
                null,
                pageSizeExpected,
                null);
        assertAll(
                () -> assertNull(result.getCategoryId(), MSG_CATEGORY_IS_NULL),
                () -> assertEquals(productsExpected, result.getProducts().size(), () -> String.format(MSG_PRODUCT_NUMBER_IS, productsExpected)),
                () -> assertFalse(result.getTopCategories().isEmpty(), MSG_TOP_CATEGORY_LIST_IS_NOT_EMPTY),
                () -> assertEquals(breadcrumbsSizeExpected, result.getBreadcrumbs().size(), () -> String.format(MSG_BREADCRUMBS_SIZE_IS, breadcrumbsSizeExpected)),
                () -> assertEquals(pagesTotalExpected, result.getTotalPages(), () -> String.format(MSG_PAGES_TOTAL_IS, pagesTotalExpected)),
                () -> assertEquals(pageNumberExpected, result.getPageNumber(), () -> String.format(MSG_PAGE_NUMBER_IS, pageNumberExpected)),
                () -> assertEquals(pageSizeExpected, result.getPageSize(), () -> String.format(MSG_PAGE_SIZE_IS, pageSizeExpected)),
                () -> assertEquals(totalExpected, result.getTotalProducts(),() -> String.format(MSG_TOTAL_IS, totalExpected)),
                () -> assertEquals(result.getMinimalPrice(), result.getLowerPriceLimit(), MSG_MINIMAL_PRICES_ARE_EQUAL),
                () -> assertEquals(result.getMaximalPrice(), result.getUpperPriceLimit(), MSG_MAXIMAL_PRICES_ARE_EQUAL),
                () -> assertEquals(orderExpected, result.getDescendingOrder(), () -> String.format(MSG_DESCENDING_ORDER_IS, orderExpected))
        );
    }

    @Test
    @DisplayName("Проверка хлебных крошек")
    void testBreadcrumbs() {
        int breadcrumbsSizeExpected = 2;
        int productsExpected = 0;
        int pageSizeExpected = 20;
        int pageNumberExpected = 0;
        int totalExpected = 0;
        int pagesTotalExpected = PageHelper.calculateTotalPages(totalExpected, pageSizeExpected);
        Boolean orderExpected = null;
        final SearchResult result = productService.findByCriteria(
                null,
                subcategoryId,
                null,
                null,
                null,
                null,
                null);
        assertAll(
                () -> assertEquals(subcategoryId, result.getCategoryId(), () -> String.format(MSG_CATEGORY_IS, subcategoryId)),
                () -> assertEquals(productsExpected, result.getProducts().size(), () -> String.format(MSG_PRODUCT_NUMBER_IS, productsExpected)),
                () -> assertFalse(result.getTopCategories().isEmpty(), MSG_TOP_CATEGORY_LIST_IS_NOT_EMPTY),
                () -> assertEquals(breadcrumbsSizeExpected, result.getBreadcrumbs().size(), () -> String.format(MSG_BREADCRUMBS_SIZE_IS, breadcrumbsSizeExpected)),
                () -> assertEquals(pagesTotalExpected, result.getTotalPages(), () -> String.format(MSG_PAGES_TOTAL_IS, pagesTotalExpected)),
                () -> assertEquals(pageNumberExpected, result.getPageNumber(), () -> String.format(MSG_PAGE_NUMBER_IS, pageNumberExpected)),
                () -> assertEquals(pageSizeExpected, result.getPageSize(), () -> String.format(MSG_PAGE_SIZE_IS, pageSizeExpected)),
                () -> assertEquals(totalExpected, result.getTotalProducts(), () -> String.format(MSG_TOTAL_IS, totalExpected)),
                () -> assertEquals(result.getMinimalPrice(), result.getLowerPriceLimit(), MSG_MINIMAL_PRICES_ARE_EQUAL),
                () -> assertEquals(result.getMaximalPrice(), result.getUpperPriceLimit(), MSG_MAXIMAL_PRICES_ARE_EQUAL),
                () -> assertEquals(orderExpected, result.getDescendingOrder(), () -> String.format(MSG_DESCENDING_ORDER_IS, orderExpected))
        );
    }

    @Test
    @DisplayName("Сложный запрос")
    void testComplexCriteria() {
        int breadcrumbsSizeExpected = 1;
        int productsExpected = 1;
        int pageSizeExpected = (int) PRODUCT_NUMBER;
        int pageNumberExpected = 0;
        int totalExpected = 1;
        int pagesTotalExpected = PageHelper.calculateTotalPages(totalExpected, pageSizeExpected);
        BigDecimal min = BigDecimal.ZERO;
        BigDecimal max = BigDecimal.valueOf(PRODUCT_NUMBER);
        final SearchResult result = productService.findByCriteria(
                "Phone 99",
                categoryId,
                min,
                max,
                pageNumberExpected,
                pageSizeExpected,
                true);
        assertAll(
                () -> assertEquals(categoryId, result.getCategoryId(), () -> String.format(MSG_CATEGORY_IS, categoryId)),
                () -> assertEquals(productsExpected, result.getProducts().size(), () -> String.format(MSG_PRODUCT_NUMBER_IS, productsExpected)),
                () -> assertFalse(result.getTopCategories().isEmpty(), MSG_TOP_CATEGORY_LIST_IS_NOT_EMPTY),
                () -> assertEquals(breadcrumbsSizeExpected, result.getBreadcrumbs().size(), () -> String.format(MSG_BREADCRUMBS_SIZE_IS, breadcrumbsSizeExpected)),
                () -> assertEquals(pagesTotalExpected, result.getTotalPages(), () -> String.format(MSG_PAGES_TOTAL_IS, pagesTotalExpected)),
                () -> assertEquals(pageNumberExpected, result.getPageNumber(), () -> String.format(MSG_PAGE_NUMBER_IS, pageNumberExpected)),
                () -> assertEquals(pageSizeExpected, result.getPageSize(), () -> String.format(MSG_PAGE_SIZE_IS, pageSizeExpected)),
                () -> assertEquals(totalExpected, result.getTotalProducts(), () -> String.format(MSG_TOTAL_IS, totalExpected)),
                () -> assertEquals(result.getLowerPriceLimit().compareTo(min), 0, () -> String.format(MSG_LOWER_LIMIT_IS, min.toString())),
                () -> assertEquals(result.getUpperPriceLimit().compareTo(max), 0, () -> String.format(MSG_UPPER_LIMIT_IS, max.toString())),
                () -> assertEquals(true, result.getDescendingOrder(), () -> String.format(MSG_DESCENDING_ORDER_IS, true))
        );
    }

    private boolean inLimits(ProductDto.Response.AllWithCategoryId p, BigDecimal min, BigDecimal max) {
        return p.getPrice().compareTo(min) >= 0 && p.getPrice().compareTo(max) <= 0;
    }
}