package com.example.demosite.controller;

import com.example.demosite.model.Category;
import com.example.demosite.repository.CategoryService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminCategoriesController {
    @Autowired
    private CategoryService categoryService;
    final static Logger logger = Logger.getLogger(AdminCategoriesController.class);

    @GetMapping({"/categories"})
    public String saveDataForm(Model model, @RequestParam(value = "name", required = false) String categoryName) {
        List<Category> list;
        if (categoryName == null) {
            list = categoryService.findAll();
            logger.info("List size: " + list.size());
        } else {
            list = categoryService.findByName(categoryName);
            logger.info("Name: " + categoryName + " List size: " + list.size());
        }
        model.addAttribute("categoryList", list);
        return "admin/categories";
    }

    @PostMapping(value="/categories")
    public String findCategory(@RequestParam("name") String name, Model model) {
        return "redirect:/admin/categories?name=" + name;
    }

    @PostMapping(value="/category/delete")
    public String deleteCategory(@RequestParam("categoryId") Long id, Model model) {
        //здесь должны быть проверки на возможность удаления продукта, а пока просто удаляем
        categoryService.removeCategory(id);
        return "redirect:/admin/categories";
    }

    @PostMapping(value="/category/edit")
    public String editCategory(@RequestParam("categoryId") Long id, Model model) {
        return "redirect:/admin/category/edit?id=" + id;
    }

    @PostMapping(value="/category/add")
    public String addCategory(Model model) {
        return "redirect:/admin/product/add";
    }
}
