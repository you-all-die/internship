package com.example.internship.controller.product;

import com.example.internship.dto.product.ProductDto;
import com.example.internship.entity.Product;
import com.example.internship.service.CartService;
import com.example.internship.service.GsCategoryService;
import com.example.internship.service.GsProductService;
import com.example.internship.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Самохвалов Юрий Алексеевич
 */
@Controller
@RequestMapping("/product") // Вынес /product в маппинг(СЮА)
@RequiredArgsConstructor // Заменил All на Required (СЮА)
@Slf4j
public class GsProductController {

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

    @GetMapping("")
    public String showProducts(
            Model model
    ) {
        model.addAttribute("products", gsProductService.findAll());
        model.addAttribute("categories", categoryService.findAll());
        return "product/index";
    }


    @PostMapping("/cart")
    public void addToCart(
            @RequestParam("productId") Product product,
            @CookieValue(value = "customerId", defaultValue = "") String customerId
    ) {
        cartService.add(product, Long.valueOf(customerId));
    }

    @PostMapping("/filter")
    public List<ProductDto.Response.Ids> filter(
            @RequestParam("categoryId") Long categoryId
    ) {
        return gsProductService.findAllIdByCategoryId(categoryId);
    }

    @GetMapping("/cards")
    public String showCards(
            @RequestParam(value = "categoryId", required = false) Long categoryId,
            Model model
    ) {
        model.addAttribute(
                "products",
                categoryId == null ? gsProductService.findAll() : gsProductService.findAllByCategoryId(categoryId));
        return "product/cards :: cards";
    }
}
