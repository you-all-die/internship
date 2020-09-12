package com.example.internship.controller.product;

import com.example.internship.entity.Product;
import com.example.internship.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("product")
@RequiredArgsConstructor
public class ProductToCartRestController {

    private final CartService cartService;

    @PostMapping("/cart")
    public void addProductToCart(
            @RequestParam("productId") Product product,
            @CookieValue(value = "customerId", defaultValue = "") String customerId
    ) {
        cartService.add(product, Long.valueOf(customerId));
    }
}
