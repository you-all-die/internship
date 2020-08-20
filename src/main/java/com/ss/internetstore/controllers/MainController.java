package com.ss.internetstore.controllers;
import com.ss.internetstore.models.Product;
import com.ss.internetstore.repo.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class MainController {
    @Autowired
    private ProductRepository productRepository;

    public MainController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @GetMapping("/")
    public String index(Model model) {
        Iterable<Product> products = productRepository.findAll();
        model.addAttribute("value", products);
        return "index";
    }

    @PostMapping("/")
    public String addProduct(@RequestParam String newvalue, Model model){
        Product addnewproduct = new Product(newvalue);
        productRepository.save(addnewproduct);
        return "index";
    }
}
