package com.example.internship.api;

import com.example.internship.dto.CategoryDto;
import com.example.internship.dto.ProductDto;
import com.example.internship.dto.ProductSearchResult;
import com.example.internship.entity.Category;
import com.example.internship.entity.Product;
import com.example.internship.entity.ProductStatus;
import com.example.internship.repository.CategoryRepository;
import com.example.internship.repository.ProductRepository;
import com.example.internship.service.ProductService;
import com.example.internship.service.ProductStatusService;
import com.example.internship.service.category.CategoryService;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author Ivan Gubanov
 */

@TestPropertySource(locations = "classpath:test.properties")
@SpringBootTest
public class ProductRestControllerTest {

    @Autowired
    private ProductRestController productRestController;

    private static final Product product = new Product();
    private static final ProductDto productOne = new ProductDto();
    private static final ProductDto productTwo = new ProductDto();
    private static final CategoryDto categoryOne = new CategoryDto();
    private static final CategoryDto categoryTwo = new CategoryDto();
    private static final ProductSearchResult expected = new ProductSearchResult();

    @BeforeAll
    public static void beforeAll(@Autowired ProductStatusService productStatusService,
                                 @Autowired CategoryRepository categoryRepository,
                                 @Autowired ProductRepository productRepository, @Autowired ModelMapper mapper) {

        categoryOne.setName("Best phones");
        categoryOne.setParentId(null);
        categoryOne.setParentName(null);

        Category categoryOneSave = categoryRepository.save(mapper.map(categoryOne, Category.class));
        categoryOne.setId(categoryOneSave.getId());

        categoryTwo.setName("Super best phones");
        categoryTwo.setParentId(null);
        categoryTwo.setParentName(null);

        Category categoryTwoSave = categoryRepository.save(mapper.map(categoryTwo, Category.class));
        categoryTwo.setId(categoryTwoSave.getId());

        Category categoryThree = new Category();
        categoryThree.setName("Super best phones");
        categoryThree.setParent(null);

        ProductStatus productStatus = new ProductStatus();
        productStatus.setDescription("For sale");
        productStatusService.add(productStatus);

        productOne.setCategory(mapper.map(categoryOne, CategoryDto.class));
        productOne.setName("Iphone 1");
        productOne.setDescription("Iphone 1 is best phone");
        productOne.setExtension("iphone1.jpg");
        productOne.setPrice(new BigDecimal("100.00"));
        productOne.setStatus(productStatus);

        Product productOneSave = productRepository.save(mapper.map(productOne, Product.class));
        productOne.setId(productOneSave.getId());

        productTwo.setCategory(mapper.map(categoryTwo, CategoryDto.class));
        productTwo.setName("Iphone 2");
        productTwo.setDescription("Iphone 2 is best Iphone 1");
        productTwo.setExtension("iphone2.jpg");
        productTwo.setPrice(new BigDecimal("200.00"));
        productTwo.setStatus(productStatus);

        Product productTwoSave = productRepository.save(mapper.map(productTwo, Product.class));
        productTwo.setId(productTwoSave.getId());

        product.setCategory(categoryThree);
        product.setName("Iphone 3");
        product.setDescription("Iphone 3 is best Iphone 2");
        product.setExtension("iphone3.jpg");
        product.setPrice(new BigDecimal("300.00"));
        product.setStatus(productStatus);

    }

    @AfterAll
    public static void afterAll(@Autowired ProductService productService,
                                @Autowired CategoryService categoryService,
                                @Autowired ProductStatusService productStatusService) {
        productService.removeAll();
        categoryService.removeAll();
        productStatusService.removeAll();
    }

    @BeforeEach
    public void beforeEach() {
        expected.setPageNumber(0);
        expected.setPageSize(20);
        expected.setTotalProducts(2L);
        expected.setProducts(List.of(productOne, productTwo));
    }

    /**
     * Проверка метода productData:
     * - Возвращает объект продукта по значению его id
     */
    @Test
    public void testProductData() {
        assertEquals(productOne, productRestController.productData(1L).orElseThrow());
    }

    /**
     * Проверка метода productSearch с нулевыми параметрами:
     * - Возвращает все продукты
     */
    @Test
    public void testProductSearchEmpty() {
        assertEquals(expected, productRestController.productSearch(null, null, null,
                null, null, null));
    }

    /**
     * Проверка метода productSearch поиск по имени:
     * - Возвращает все продукты содержащие в имени строку
     */
    @Test
    public void testOneProductSearchName() {
        assertEquals(expected, productRestController.productSearch("Iphone", null, null,
                null, null, null));
    }

    /**
     * Проверка метода productSearch поиск по имени:
     * - Возвращает все продукты содержащие в имени строку
     */
    @Test
    public void testTwoProductSearchName() {
        expected.setTotalProducts(1L);
        expected.setProducts(List.of(productTwo));
        assertEquals(expected, productRestController.productSearch("Iphone 2", null, null,
                null, null, null));
    }

    /**
     * Проверка метода productSearch поиск по id категории:
     * - Возвращает все продукты данной категории
     */
    @Test
    public void testProductSearchCategoryId() {
        expected.setTotalProducts(1L);
        expected.setProducts(List.of(productOne));
        assertEquals(expected, productRestController.productSearch(null, categoryOne.getId(), null,
                null, null, null));
    }

    /**
     * Проверка метода productSearch поиск по начальной цене:
     * - Возвращает все с ценой >= указанной
     */
    @Test
    public void testOneProductSearchPriceFrom() {
        assertEquals(expected, productRestController.productSearch(null, null,
                new BigDecimal(100), null, null, null));
    }

    /**
     * Проверка метода productSearch поиск по начальной цене:
     * - Возвращает все с ценой >= указанной
     */
    @Test
    public void testTwoProductSearchPriceFrom() {
        expected.setTotalProducts(1L);
        expected.setProducts(List.of(productTwo));
        assertEquals(expected, productRestController.productSearch(null, null,
                new BigDecimal(101), null, null, null));
    }

    /**
     * Проверка метода productSearch поиск по конечной цене:
     * - Возвращает все с ценой <= указанной
     */
    @Test
    public void testOneProductSearchPriceTo() {
        assertEquals(expected, productRestController.productSearch(null, null, null,
                new BigDecimal(200), null, null));
    }

    /**
     * Проверка метода productSearch поиск по конечной цене:
     * - Возвращает все с ценой <= указанной
     */
    @Test
    public void testTwoProductSearchPriceTo() {
        expected.setTotalProducts(1L);
        expected.setProducts(List.of(productOne));
        assertEquals(expected, productRestController.productSearch(null, null, null,
                new BigDecimal(199), null, null));
    }

    /**
     * Проверка метода productSearch пагинация:
     * - задаем количество элементов на странице
     */
    @Test
    public void testOneProductSearchPageSize() {
        expected.setPageSize(2);
        assertEquals(expected, productRestController.productSearch(null, null, null,
                null, 2, null));
    }

    /**
     * Проверка метода productSearch пагинация:
     * - задаем количество элементов на странице
     */
    @Test
    public void testTwoProductSearchPageSize() {
        expected.setPageSize(1);
        expected.setProducts(List.of(productOne));
        assertEquals(expected, productRestController.productSearch(null, null, null,
                null, 1, null));
    }

    /**
     * Проверка метода productSearch пагинация:
     * - задаем номер страницы
     */
    @Test
    public void testOneProductSearchPageNumber() {
        assertEquals(expected, productRestController.productSearch(null, null, null,
                null, null, 0));
    }

    /**
     * Проверка метода productSearch пагинация и количество элементов на странице:
     * - задаем количество элементов на странице
     * - задаем номер страницы
     */
    @Test
    public void testTwoProductSearchPageNumber() {
        expected.setPageSize(1);
        expected.setPageNumber(1);
        expected.setProducts(List.of(productTwo));
        assertEquals(expected, productRestController.productSearch(null, null, null,
                null, 1, 1));
    }

    /**
     * Проверка метода findByName:
     * - Возвращает все продукты содержащие в имени строку
     */
    @Test
    public void testOneFindByName() {
        assertEquals(expected.getProducts(), productRestController.findByName("Iphone"));
    }

    /**
     * Проверка метода findByName:
     * - Возвращает все продукты содержащие в имени строку
     */
    @Test
    public void testTwoFindByName() {
        assertEquals(List.of(), productRestController.findByName("Samsung"));
    }

    /**
     * Проверка методов saveProduct, findById:
     * - Сохраняет продукт в БД
     * - Возвращает объект продукта по значению его id
     */
    @Test
    /**
     * +=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=
     * Здесь происходит много операций по работе с бд. Hibernate не успевает все подгрузить.
     * Добавлена аннотация @Transactional.
     +=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+= */
    @Transactional
    public void testSaveProductAndFindById() {
        productRestController.saveProduct(product);
        assertEquals(product, productRestController.findById(3L));
    }
}