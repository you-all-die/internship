package com.example.internship.controllers;
import com.example.internship.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class MainController {
    @Autowired
    ProductService productService;


    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("products", productService.find_all());
        return "index";
    }


}
