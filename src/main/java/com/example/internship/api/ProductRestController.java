package com.example.internship.api;

import com.example.internship.dto.ProductDto;
import com.example.internship.service.ProductService;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author Ivan Gubanov
 */
@RestController
@RequestMapping("/api/product/")
@AllArgsConstructor
public class ProductRestController {

    private final ProductService productService;

    @GetMapping("/{id}")
    @ApiOperation(value = "Возвращает информацию о продукте, по значениею его id.", response = ProductDto.class)
    public ProductDto productData(@PathVariable Long id) {
        return productService.findById(id);
    }

    @GetMapping("/search")
    @ApiOperation(value = "Возвращает список продуктов согласно заданным критериям поиска.",
            notes = "В запросе search могут указываться:\n" +
                    "- pageSize количество возвращаемых продуктов (значение по умолчанию 20)\n" +
                    "- pageNumber номер страницы (значение по умолчанию 1)\n" +
                    "- priceFrom минимальная цена продукта\n" +
                    "- priceTo максимальная цена продукта\n" +
                    "- categoryId поиск по id категории\n" +
                    "- searchText - поиск по наименованию",
            response = ProductDto.class)
    public List<ProductDto> productSearch(@RequestParam(name = "searchText", required = false) String searchText,
                                          @RequestParam(name = "categoryId", required = false) Long categoryId,
                                          @RequestParam(name = "priceFrom", required = false) BigDecimal priceFrom,
                                          @RequestParam(name = "priceTo", required = false) BigDecimal priceTo,
                                          @RequestParam(name = "pageSize", required = false, defaultValue = "20")
                                                  Integer pageSize,
                                          @RequestParam(name = "pageNumber", required = false, defaultValue = "1")
                                                  Integer pageNumber) {
        List<ProductDto> response;
        // Поиск по имение и категории
        if (searchText != null && categoryId != null) {
            response = productService.findByNameAndAndCategoryId(searchText, categoryId);
            // Поиск только по имени
        } else if (searchText != null) {
            response = productService.findByName(searchText);
            // Поиск только по категории
        } else if (categoryId != null) {
            response = productService.findByCategoryId(categoryId);
            // Все товары
        } else {
            response = productService.findAll();
        }
        // Форматируем по количеству и цене
        if (response != null) {
            // Диапазон цен ОТ и ДО
            if (priceFrom != null && priceTo != null) {
                response = productService.filterByPrice(response, priceFrom, priceTo);
                // Диапазон цен ОТ
            } else if (priceFrom != null) {
                response = productService.filterByPrice(response, priceFrom);
                // Диапазон цен ДО
            } else if (priceTo != null) {
                response = productService.filterByPrice(response, new BigDecimal(0), priceTo);
            }
            // Если количество продуктов больше размера страницы
            if (response.size() > pageSize) {
                // Если это последняя страница, то количество элементов для нее не может быть больше размера коллекции
                if (pageNumber * pageSize > response.size()) {
                    response = response.subList((pageNumber * pageSize) - pageSize, response.size());
                } else {
                    response = response.subList((pageNumber * pageSize) - pageSize, pageNumber * pageSize);
                }
            }
        }

        return response;
    }
}
