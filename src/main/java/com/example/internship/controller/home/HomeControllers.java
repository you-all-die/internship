package com.example.internship.controller.home;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class HomeControllers {

    @GetMapping
    public String homePage() {
        return "home/index";
    }

    @GetMapping("about")
    public String aboutPage() {
        return "about/index";
    }
}
