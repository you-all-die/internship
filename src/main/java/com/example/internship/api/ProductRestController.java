package com.example.internship.api;

import com.example.internship.dto.ProductDto;
import com.example.internship.dto.ProductSearchResult;
import com.example.internship.entity.Product;
import com.example.internship.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

/**
 * @author Ivan Gubanov
 */
@RestController
@RequestMapping("/api/product/")
@AllArgsConstructor
public class ProductRestController {

    private final ProductService productService;

    @GetMapping("/{id}")
    @Operation(description = "Возвращает информацию о продукте, по значениею его id.")
    public Optional<ProductDto> productData(@PathVariable Long id) {
        return productService.findById(id);
    }

    @GetMapping("/search")
    @Operation(description = "Возвращает список продуктов согласно заданным критериям поиска.")
    public ProductSearchResult productSearch(@RequestParam(name = "searchText", required = false)
                                             @Parameter(description = "поиск по наименованию")
                                                     String searchText,
                                             @RequestParam(name = "categoryId", required = false)
                                             @Parameter(description = "поиск id категории продукта")
                                                     Long categoryId,
                                             @RequestParam(name = "priceFrom", required = false)
                                             @Parameter(description = "поиск по минимальной цене")
                                                     BigDecimal priceFrom,
                                             @RequestParam(name = "priceTo", required = false)
                                             @Parameter(description = "поиск по максимальной цене")
                                                     BigDecimal priceTo,
                                             @RequestParam(name = "pageSize", required = false, defaultValue = "20")
                                             @Parameter(description = "размер страницы")
                                                     Integer pageSize,
                                             @RequestParam(name = "pageNumber", required = false, defaultValue = "0")
                                             @Parameter(description = "номер страницы")
                                                     Integer pageNumber) {

        return productService.search(searchText, categoryId, priceFrom, priceTo, pageSize, pageNumber);
    }


    @PostMapping("/find-all")
    @Operation(description = "Возвращает все продукты")
    public List<ProductDto> findAll() {
        return productService.findAll();
    }

    @PostMapping("/find-by-name")
    @Operation(description = "Возвращает продукта по названию")
    public List<ProductDto> findByName(@RequestBody String name) {
        return productService.findByName(name);
    }

    @PostMapping("/remove-product")
    @Operation(description = "Удаляет продукт по id")
    public void removeProduct(@RequestBody Long id) {
        productService.removeProduct(id);
    }

    @PostMapping("/find-by-id")
    @Operation(description = "Возвращает продукт по значению id")
    public Product findById(@RequestBody Long id) {
        return productService.findByIdProduct(id);
    }

    @PostMapping("/save-product")
    @Operation(description = "Сохраняет продукт")
    public Product saveProduct(@RequestBody Product product) {

        return productService.saveProduct(product);
    }
}
