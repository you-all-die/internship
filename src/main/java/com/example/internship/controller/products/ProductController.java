package com.example.internship.controller.products;

import com.example.internship.dto.ProductDto;
import com.example.internship.entity.Product;
import com.example.internship.service.CartService;
import com.example.internship.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/")
@AllArgsConstructor
public class ProductController {

    private final ProductService productService;

    private final CartService cartService;

    @GetMapping("products")
    public String getProducts(Model model) {
        List<ProductDto> products = productService.findAll();
        model.addAttribute("products", products);
        return "products/products";
    }

    @PostMapping("/cart/add")
    public String addToCart(@CookieValue(value = "customerId", defaultValue = "") String customerId,
                            @RequestParam("productId") Product product) {

        cartService.add(product, Long.valueOf(customerId));

        return "redirect:/products";
    }
}
