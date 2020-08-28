package com.example.internship.controller.products;

import com.example.internship.dto.ProductDto;
import com.example.internship.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/")
@AllArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping("products")
    public String getProducts(Model model) {
        List<ProductDto> products = productService.findAll();
        model.addAttribute("products", products);
        return "products/products";
    }


    @GetMapping("product/{id}")
    public String showProduct(@PathVariable("id") long id, Model model){
        model.addAttribute("product",
                productService.getProductById(id));

        return "products/product/product";

    }
}
