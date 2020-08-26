package com.example.internship.controller.about;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/about")
public class AboutController {

    @GetMapping("")
    public String showAboutPage() {
        return "about/index";
    }
}
