package com.example.internship.controller.about;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/about")
@Slf4j
public class AboutController {

    @Value("${yandex.maps.apikey}")
    private String yandexMapsApiKey;

    @Value("${yandex.maps.mode:release}")
    private String yandexMapsMode;

    @GetMapping("")
    public String showAboutPage(Model model) {
        model.addAttribute("apikey", yandexMapsApiKey);
        model.addAttribute("mode", yandexMapsMode);
        return "about/index";
    }
}
