package com.example.adminapplication.controller.main;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Ivan Gubanov
 */
@Controller
@RequestMapping("/")
public class MainController {

    @GetMapping
    public String main() {
        return "main/main";
    }
}
