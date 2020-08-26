package com.example.internship.controller.home;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class AdminController {

    @GetMapping("/admin")
    public String urlAdmin (Model model) {
        return "admin";
    }
    @GetMapping("/admin/control-product")
    public String controlProduct (Model model) {
        return "control-product";
    }
    @GetMapping("/control-category")
    public String controlCategory (Model model) {
        return "control-category";
    }

}
