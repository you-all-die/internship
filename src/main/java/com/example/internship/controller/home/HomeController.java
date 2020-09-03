package com.example.internship.controller.home;

import com.example.internship.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
@RequiredArgsConstructor
public class HomeController {

    private final ProductService productService;

    @GetMapping
    public String homePage(Model model) {
        //Лимит на отображение популярных товаров
        int limit = 5;
        model.addAttribute("products", productService.findPopular(limit));
        return "home/index";
    }

    @GetMapping("login")
    public String loginPage() { return "login/index"; }
}
