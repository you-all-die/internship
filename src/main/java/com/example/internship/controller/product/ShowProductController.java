package com.example.internship.controller.product;


import com.example.internship.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/products")
@AllArgsConstructor
public class ShowProductController {

    private final ProductService productService;

    @GetMapping("/{id}")
    public String showProduct(@PathVariable("id") long id, Model model){
        model.addAttribute("product",
                productService.getProductById(id));

        return "product/product";
        //dawdawd
    }
}
