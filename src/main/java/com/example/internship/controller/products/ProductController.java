package com.example.internship.controller.products;

import com.example.internship.dto.product.ProductDto;
import com.example.internship.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping("products")
    public String getProducts(Model model) {
        List<ProductDto.Response.All> products = productService.findAll();
        model.addAttribute("products", products);
        return "products/products";
    }

    /**
     * @Author Роман Каравашкин
     */
    @GetMapping("product/{id}")
    public String showProduct(@PathVariable("id") long id, Model model) {
        model.addAttribute("product", productService.findById(id));
        return "products/product";
    }
}
