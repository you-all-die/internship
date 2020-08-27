package com.example.internship.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
public class AdminController {

    @GetMapping("/admin")
    public String urlAdmin (Model model) {
        return "admin/admin";
    }
}
