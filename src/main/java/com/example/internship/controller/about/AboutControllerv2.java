package com.example.internship.controller.about;

import com.example.internship.entity.AddressShop;

import com.example.internship.repository.AddressShopRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AboutControllerv2 {

    @Autowired
    private AddressShopRepository addressShopRepository;

    @GetMapping("/aboutv1")
    public String aboutPagev1(Model model){
        Iterable<AddressShop> addressShops = addressShopRepository.findAll();
        model.addAttribute("addressShops", addressShops);
        return "about/indexv1";
    }
}
