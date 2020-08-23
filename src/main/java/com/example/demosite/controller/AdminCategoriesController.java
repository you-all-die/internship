package com.example.demosite.controller;

import com.example.demosite.model.Category;
import com.example.demosite.repository.CategoryRepository;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class AdminCategoriesController {
    private final CategoryRepository categoryRepository = new CategoryRepository();
    final static Logger logger = Logger.getLogger(AdminCategoriesController.class);

    @GetMapping({"/admin/categories"})
    public String saveDataForm(Model model, @RequestParam(value = "name", required = false) String categoryName) {
        List<Category> list;
        if (categoryName == null) {
            list = categoryRepository.findAll();
            logger.info("List size: " + list.size());
        } else {
            list = categoryRepository.findByName(categoryName);
            logger.info("Name: " + categoryName + " List size: " + list.size());
        }
        model.addAttribute("categoryList", list);
        return "admin/categories";
    }

    @RequestMapping(value="/delete", method= RequestMethod.GET)
    public String deleteCategory(@RequestParam("id") String categoryId, Model model) {
        //здесь должны быть проверки на возможность удаления продукта, а пока просто удаляем
        categoryRepository.removeCategory(categoryId);
        return "redirect:/admin/categories";
    }

    @RequestMapping(value="/edit", method= RequestMethod.GET)
    public String editCategory(@RequestParam("id") String categoryId, Model model) {
        return "redirect:/admin/edit?id=" + categoryId;
    }

    @RequestMapping(value="/addCategory", method= RequestMethod.GET)
    public String addProduct(Model model) {
        return "redirect:/admin/categories";
    }
}
