package com.ss.internetstore.controllers;

import com.ss.internetstore.models.Category;
import com.ss.internetstore.repo.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CategoryController {
    @Autowired
    CategoryRepository categoryRepository;

    @GetMapping("/newcategory")
    public String newcategory(Model model) {
        return "newcategory";
    }

    @PostMapping("/newcategory")
    public String addnewcategory(@RequestParam String name, @RequestParam int parent_Id, Model model){
        Category category = new Category(name, parent_Id);
        model.addAttribute(category);
        categoryRepository.save(category);
        return "redirect:/";
    }
}
