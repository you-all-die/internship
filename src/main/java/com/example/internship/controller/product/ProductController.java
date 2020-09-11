package com.example.internship.controller.product;

import com.example.internship.dto.product.SearchResult;
import com.example.internship.entity.Product;
import com.example.internship.service.CartService;
import com.example.internship.service.ProductService;
import com.example.internship.service.category.GsCategoryService;
import com.example.internship.service.product.GsProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

/**
 * @author Самохвалов Юрий Алексеевич
 */
@Controller
@RequestMapping("/product")
@RequiredArgsConstructor
@Slf4j
public class ProductController {

    private final CartService cartService;
    private final ProductService productService;
    private final GsProductService gsProductService;
    private final GsCategoryService categoryService;

    /**
     * @author Роман Каравашкин
     */
    @GetMapping("/{id}") // убрал ненужный /product (СЮА)
    public String showProduct(@PathVariable("id") long id, Model model) {
        model.addAttribute("product", productService.getProductById(id));
        return "products/product";
    }

    @GetMapping
    public String viewProductPage(
            Model model,
            @RequestParam(required = false) String searchString,
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) BigDecimal lowerPriceLimit,
            @RequestParam(required = false) BigDecimal upperPriceLimit,
            @RequestParam(required = false) Integer pageNumber,
            @RequestParam(required = false) Integer pageSize,
            @RequestParam(required = false) Boolean descendingOrder
    ) {
        final SearchResult data = gsProductService.findByCriteria(
                searchString, categoryId, lowerPriceLimit, upperPriceLimit, pageNumber, pageSize, descendingOrder
        );
        model.addAttribute("data", data);
        return "product/index";
    }

    @PostMapping("/cart")
    public void addToCart(
            @RequestParam("productId") Product product,
            @CookieValue(value = "customerId", defaultValue = "") String customerId
    ) {
            cartService.add(product, Long.valueOf(customerId));
    }
}
