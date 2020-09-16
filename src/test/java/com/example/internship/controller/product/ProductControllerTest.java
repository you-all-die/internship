package com.example.internship.controller.product;

import com.example.internship.dto.category.CategoryDto;
import com.example.internship.dto.product.ProductDto;
import com.example.internship.entity.Category;
import com.example.internship.entity.Product;
import com.example.internship.service.category.GsCategoryService;
import com.example.internship.service.product.GsProductService;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestPropertySource(locations = "classpath:test.properties")
@SpringBootTest
class ProductControllerTest {

    @Autowired
    private ProductController productController;
    @Autowired
    private GsCategoryService categoryService;

    private static List<Product> productList = new ArrayList<>();

    @BeforeAll
    static void beforeAll(
            @Autowired GsCategoryService categoryService,
            @Autowired GsProductService productService,
            @Autowired ModelMapper modelMapper
    ) {
        // Одна общая категория
        Category category = new Category();
        category.setId(1L);
        category.setName("Phones");
        CategoryDto.Request.All categoryDto = modelMapper.map(category, CategoryDto.Request.All.class);
        categoryService.save(categoryDto);

        // Генерация списка продуктов
        for (long i = 0; i < 100; i++) {
            Product product = new Product();
            product.setId(i);
            product.setCategory(category);
            product.setName("Phone " + i);
            product.setDescription("Description " + i);
            product.setPrice(BigDecimal.valueOf(100).multiply(BigDecimal.valueOf(i + 1)));
            ProductDto.Request.AllWithCategoryId productDto = modelMapper.map(product, ProductDto.Request.AllWithCategoryId.class);
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
    void testControllerLoaded() {
        assertNotNull(productController, "Контроллер не загружен!");
    }
}