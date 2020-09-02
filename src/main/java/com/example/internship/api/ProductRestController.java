package com.example.internship.api;

import com.example.internship.dto.ProductDto;
import com.example.internship.dto.ProductSearchResult;
import com.example.internship.service.ProductService;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
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
            response = ProductSearchResult.class)
    public ProductSearchResult productSearch(@RequestParam(name = "searchText", required = false)
                                                  Optional<String> searchText,
                                             @RequestParam(name = "categoryId", required = false)
                                                  Optional<Long> categoryId,
                                             @RequestParam(name = "priceFrom", required = false)
                                                  Optional<BigDecimal> priceFrom,
                                             @RequestParam(name = "priceTo", required = false)
                                                  Optional<BigDecimal> priceTo,
                                             @RequestParam(name = "pageSize", required = false, defaultValue = "20")
                                                  Integer pageSize,
                                             @RequestParam(name = "pageNumber", required = false, defaultValue = "0")
                                                  Integer pageNumber) {

        return productService.search(searchText, categoryId, priceFrom, priceTo, pageSize, pageNumber);
    }
}
