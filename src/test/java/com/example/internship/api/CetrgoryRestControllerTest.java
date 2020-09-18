package com.example.internship.api;

import com.example.internship.dto.CategorySearchResult;
import com.example.internship.entity.Category;
import com.example.internship.service.CategoryService;
import org.junit.Ignore;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestPropertySource(locations = "classpath:test.properties")
@SpringBootTest
public class CetrgoryRestControllerTest {

    @Autowired
    private CategoryRestController categoryRestController;

    //private static final Category category = new Category();
    private static final CategorySearchResult categorySearchResult = new CategorySearchResult();
    private static final Category categoryTestOne = new Category();
    private static final Category categoryTestTwo = new Category();
    List<Category> categories = new LinkedList<>();

    /*
    @Autowired
    private ProductRestController productRestController;

    private static final Product product = new Product();
    private static final ProductDto productOne = new ProductDto();
    private static final ProductDto productTwo = new ProductDto();
    private static final ProductSearchResult expected = new ProductSearchResult();
     */

    @BeforeAll
    public static void beforeAll(@Autowired CategoryService categoryService) {



        categoryTestOne.setName("Phones");
        categoryTestOne.setParent(null);
        categoryService.addCategory(categoryTestOne);


        categoryTestTwo.setName("Tablets");
        categoryTestTwo.setParent(null);
        categoryService.addCategory(categoryTestTwo);

      //  categoryTestOne.setId(1L);
      //  categoryTestOne.setId(2L);

    }

    @AfterAll
    public static void afterAll(@Autowired CategoryService categoryService) {
        categoryService.removeAll();
    }

    @BeforeEach
    public void beforeEach() {
        categorySearchResult.setPageNumber(0);
        categorySearchResult.setPageSize(20);
        categorySearchResult.setTotalCategory(1L);

        categories.add(categoryTestOne);
        categories.add(categoryTestTwo);
        categorySearchResult.setCategory(categories);
    }

    @Test
    public void testCategoryData() {
        assertEquals(categoryTestOne, categoryRestController.categoryData(1L));
    }

    @Test
    public void testOneCategogySearchName() {
        List<Category> categoryTestTree=new LinkedList<>();
        categoryTestTree.add(categoryTestOne);
        categorySearchResult.setCategory(categoryTestTree);
        System.out.println(categorySearchResult.toString());
        //Поставил значение TotalCategory
        categorySearchResult.setTotalCategory(1L);
        assertEquals(categorySearchResult, categoryRestController.categorySearch("Phones", null, null, null));
    }

    @Test
    public void testCategorySearchEmpty() {
        System.out.println(categoryRestController.categorySearch(null,null,null,null).toString());
        System.out.println(categorySearchResult.toString());
        assertEquals(categorySearchResult, categoryRestController.categorySearch(null, null, null,
                null));
    }
}
