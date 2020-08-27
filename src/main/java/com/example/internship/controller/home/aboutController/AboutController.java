package com.example.internship.controller.home.aboutController;

import com.example.internship.entity.AdressShop;
import com.example.internship.repository.AdressShopRepository;
import liquibase.pro.packaged.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AboutController {

    @Autowired
    private AdressShopRepository adressShopRepository;

    @GetMapping("/aboutv1")
    public String aboutPagev1(Model model){
        Iterable<AdressShop> adressShops = adressShopRepository.findAll();
        model.addAttribute("adressShops", adressShops);
        return "about/indexv1";

    }
}
