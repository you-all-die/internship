package com.example.internship.api;

import com.example.internship.dto.CategorySearchResult;
import com.example.internship.entity.Category;
import com.example.internship.service.CategoryService;
import org.junit.Ignore;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author Aleksey Moiseychev
 */
@TestPropertySource(locations = "classpath:test.properties")
@SpringBootTest
public class CetrgoryRestControllerTest {

    @Autowired
    private CategoryRestController categoryRestController;

    private static final CategorySearchResult categorySearchResult = new CategorySearchResult();
    private static final Category categoryTestOne = new Category();
    private static final Category categoryTestTwo = new Category();
    //private static final Category categoryParent = new Category();
    List<Category> categories = new LinkedList<>();

    @BeforeAll
    public static void beforeAll(@Autowired CategoryService categoryService) {

        categoryTestOne.setName("Phones");
        categoryTestOne.setParent(null);
        categoryService.addCategory(categoryTestOne);

        categoryTestTwo.setName("Tablets");
        categoryTestTwo.setParent(null);
        categoryService.addCategory(categoryTestTwo);
    }

    @AfterAll
    public static void afterAll(@Autowired CategoryService categoryService) {
        categoryService.removeAll();
    }

    @BeforeEach
    public void beforeEach() {
        categorySearchResult.setPageNumber(0);
        categorySearchResult.setPageSize(20);
        categorySearchResult.setTotalCategory(2L);

        categories.add(categoryTestOne);
        categories.add(categoryTestTwo);
        categorySearchResult.setCategory(categories);
    }

    /**
     * Проверка метода findAllSortById
     * - возвращаем все категории отсортированные по id
     */
    @Test
    public void testFindAllSortById() {
        assertEquals(categorySearchResult.getCategory(), categoryRestController.findAllSortById());
    }

    /**
     * Проверка метода findByName
     * - возвращает категорию по имени
     */
    @Test
    public void testFindByName() {
        assertEquals(categories.subList(0, 1), categoryRestController.findByName("Phones"));
    }

    /**
     * Проверка метода removeCategory
     * - удаляет категорию по выбранному id
     */
    @Test
    public void testRemoveCategory() {
        categoryRestController.removeCategory(1L);
        categories.remove(0);
        assertEquals(categories, categoryRestController.findAll());
    }

    /**
     * Проверка метода FindAll
     * - возвращает все категории
     */
    @Test
    public void testFindAll() {
        assertEquals(categorySearchResult.getCategory(), categoryRestController.findAll());
    }

    /**
     * Проверка метода FindById
     * - возвращает категорию по id
     */
    @Test
    public void testFindById() {
        assertEquals(categories.get(0), categoryRestController.findById(1L));
    }

    /**
     * Проверка метода AddCategory
     * - добавляет новую категорию
     */
    @Test
    public void testAddCategory() {

        Category categoryTestThree = new Category();
        categoryTestThree.setName("TV");
        categoryTestThree.setParent(null);
        categoryRestController.addCategory(categoryTestThree);

        assertEquals(categoryTestThree, categoryRestController.findById(3L));
        categoryRestController.removeCategory(3L);
    }

    /**
     * Проверка метода CategoryData
     * - Возвращает категорию по id
     */
    @Test
    public void testCategoryData() {
        assertEquals(categories.get(0), categoryRestController.categoryData(1L));
    }

    /**
     * Проверка метода CategorySearchName
     * - Возвращает все категории содержащие в имени строку
     */
    @Test
    public void testCategogySearchName() {
        categorySearchResult.setCategory(categories.subList(0, 1));
        categorySearchResult.setTotalCategory(1L);
        assertEquals(categorySearchResult, categoryRestController.categorySearch("Phones", null, null, null));
    }

    /**
     * Проверка метода CategorySearchEmpty с нулевыми параметрами
     * - Возвращает все продукты
     */
    @Test
    public void testCategorySearchEmpty() {
        assertEquals(categorySearchResult, categoryRestController.categorySearch(null, null, null,
                null));
    }

    /**
     * Проверка метода categorySearchPageSize
     * - задаем количество элементов на странице
     */
    @Ignore
    public void testCategorySearchPageSize() {
        categorySearchResult.setPageSize(2);
        assertEquals(categorySearchResult, categoryRestController.categorySearch(null, null, 2, null));

    }

    /**
     * Проверка метода categorySearchPageNumber
     * - задаем номер страницы
     */
    @Ignore
    public void testCategorySearchPageNumber() {
        assertEquals(categorySearchResult, categoryRestController.categorySearch(null, null, null, 0));
    }

    @Ignore
    public void testCategorySearchParent() {
        assertEquals(categorySearchResult, categoryRestController.categorySearch(null, null, null, null));

    }
}
