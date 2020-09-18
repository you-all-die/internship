package com.example.internship.controller.product;

import com.example.internship.service.cart.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("product")
@RequiredArgsConstructor
public class ProductToCartRestController {

    private final CartService cartService;

    @PostMapping("/cart")
    public void addProductToCart(
            @RequestParam("productId") Long productId,
            @CookieValue(value = "customerId", defaultValue = "") String customerId
    ) {
        cartService.add(productId, Long.valueOf(customerId));
    }
}
