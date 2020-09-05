package com.example.internship.controller.product;

import com.example.internship.dto.product.ProductDto;
import com.example.internship.entity.Product;
import com.example.internship.service.CartService;
import com.example.internship.service.GsProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
public class GsProductRestController {

    private final CartService cartService;
    private final GsProductService productService;

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
}
