package com.example.internship.controller.home;

import com.example.internship.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class HomeControllers {
    @Autowired
    ProductService productService;

    @GetMapping
    public String homePage(Model model) {
        model.addAttribute("products", productService.find_all());
        return "home/index";
    }

    @GetMapping("about")
    public String aboutPage() {
        return "about/index";
    }
}
