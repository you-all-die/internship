package com.example.internship.controller.home;

import com.example.internship.service.impl.ProductPopularRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class HomeControllers {

    @Autowired
    ProductPopularRepositoryImpl productPopularRepository;

    @GetMapping
    public String homePage(Model model) {
        //Лимит на отображение популярных товаров
        int limit = 5;
        model.addAttribute("products", productPopularRepository.findProductPopular(limit));
        return "home/index";
    }

    @GetMapping("login")
    public String loginPage() { return "login/index"; }
}
