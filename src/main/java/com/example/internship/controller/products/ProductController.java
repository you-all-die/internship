package com.example.internship.controller.products;

import com.example.internship.dto.ProductDto;
import com.example.internship.service.ProductService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/")
public class ProductController {
    private final ProductService productService;

    public ProductController(
            @Qualifier("productServiceImpl") ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("products")
    public String getProducts(Model model) {
        List<ProductDto> products = productService.findAll();
        model.addAttribute("products", products);
        return "products/products";
    }
}
