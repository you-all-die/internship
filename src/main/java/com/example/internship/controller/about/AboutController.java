package com.example.internship.controller.about;

import com.example.internship.service.OutletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/about")
public class AboutController {

    @Value("${yandex.maps.version:2.1}")
    private String yandexMapsApiVersion;

    @Value("${yandex.maps.apikey}")
    private String yandexMapsApiKey;

    @Value("${yandex.maps.mode:release}")
    private String yandexMapsMode;

    @Autowired
    private OutletService outletService;

    @GetMapping("")
    public String showAboutPage(Model model) {
        model.addAttribute("cities", outletService.getCities());
        model.addAttribute("outlets", outletService.getAll());
        model.addAttribute("version", yandexMapsApiVersion);
        model.addAttribute("apikey", yandexMapsApiKey);
        model.addAttribute("mode", yandexMapsMode);
        return "about/index";
    }
}
