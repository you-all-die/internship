package com.ss.internetstore.controllers;

import com.ss.internetstore.models.Category;
import com.ss.internetstore.models.Product;
import com.ss.internetstore.repo.CategoryRepository;
import com.ss.internetstore.repo.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;

@Controller
public class ProductController {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CategoryRepository categoryRepository;

    @Value("${upload.path}")
    private String uploadPath;


    //Добавление нового товара
    @PostMapping("/newproduct")
    public String addnewproduct(@RequestParam int category_Id, @RequestParam String name,
                                @RequestParam BigDecimal price,
                                @RequestParam String description,
                                @RequestParam("picturefile") MultipartFile picturefile,
                                Model model) throws IOException {
        Product product = new Product(category_Id, name,price, description);
        if (picturefile != null){
            File imgDir = new File(uploadPath);
            if(!imgDir.exists()){
                imgDir.mkdir();
            }

            String uiD = UUID.randomUUID().toString();
            String filename = uiD+picturefile.getOriginalFilename();
            picturefile.transferTo(new File(uploadPath + filename));
            product.setPicture(filename);
        }
        productRepository.save(product);
        return "redirect:/";
    }

    //Подробное описание товара
    @GetMapping("/product/{id}")
    public String product_description(@PathVariable(value = "id") long id, Model model){
        if (!productRepository.existsById(id)){
            return "redirect:/";
        }
        Optional<Product> product = productRepository.findById(id);
        ArrayList<Product> prod = new ArrayList<>();
        product.ifPresent(prod::add);
        model.addAttribute("product", prod);
        return "product";
    }

    //Удаление товара
    @PostMapping("/product/{id}/delete")
    public String product_delete(@PathVariable(value = "id") long id, Model model){
        Product product = productRepository.findById(id).orElseThrow();
        productRepository.delete(product);
        return "redirect:/";
    }

    //Добавление нового товара
    @GetMapping("/newproduct")
    public String category_product(Model model){
        Iterable<Category> category = categoryRepository.findAll();
        model.addAttribute("category", category);
        return "newproduct";
    }
}
