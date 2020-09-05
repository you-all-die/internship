package com.example.internship.controller.product;

import com.example.internship.dto.product.ProductDto;
import com.example.internship.entity.Product;
import com.example.internship.service.CartService;
import com.example.internship.service.GsProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
public class GsProductRestController {

    private final CartService cartService;
    private final GsProductService productService;
    private final SpringTemplateEngine templateEngine;

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
        return productService.findAllIdByCategoryId(categoryId);
    }

    @PostMapping("/card")
    public String generateHtmlCards(
        @RequestParam("categoryId") Long categoryId
    ) {
        Context thymeleaf = new Context();
        Map<String, Object> model = new HashMap<>();
        model.put("products", productService.findAllByCategoryId(categoryId));
        thymeleaf.setVariables(model);
        return templateEngine.process("/product/card :: card", thymeleaf);
    }
}
