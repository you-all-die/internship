package com.example.internship.controller.about;

import com.example.internship.service.OutletService;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Самохвалов Юрий Алексеевич
 */
@Controller
@RequestMapping("/about")
@Slf4j
public class AboutController {

    @Value("${yandex.maps.version:2.1}")
    private String version;

    @Value("${yandex.maps.apikey}")
    private String apikey;

    @Value("${yandex.maps.mode:release}")
    private String mode;

    @Autowired
    private OutletService outletService;

    @GetMapping("")
    public String showAboutPage(@NotNull Model model) {
        model.addAttribute("cities", outletService.getCities());
        model.addAttribute("outlets", outletService.getAll());
        model.addAttribute("version", version);
        model.addAttribute("apikey", apikey);
        model.addAttribute("mode", mode);
        return "about/index";
    }

}
