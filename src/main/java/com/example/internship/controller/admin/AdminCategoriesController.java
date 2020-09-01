package com.example.internship.controller.admin;

import com.example.internship.dto.category.CategoryDto;
import com.example.internship.entity.Category;
import com.example.internship.service.impl.CategoryServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminCategoriesController {
    private final CategoryServiceImpl categoryService;

    @GetMapping({"/categories"})
    public String saveDataForm(Model model, @RequestParam(value = "name", required = false) String categoryName) {
        List<CategoryDto.Response.Full> list;
        if (categoryName == null) {
            list = categoryService.findAll();
        } else {
            list = categoryService.findByName(categoryName);
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
        return "redirect:/admin/category/edit?categoryId=" + id;
    }

    @PostMapping(value="/category/add")
    public String addCategory(Model model) {
        return "redirect:/admin/category/add";
    }

    @GetMapping({"/category/add"})
    public String addNewCategory(Model model) {
        Category category = new Category();
        model.addAttribute("category", category);
        List<CategoryDto.Response.Full> parentCategories = categoryService.findAll();
        model.addAttribute("parentCategories", parentCategories);
        return "/admin/category";
    }

    @GetMapping({"/category/edit"})
    public String editExistingCategory(@RequestParam("categoryId") Long id, Model model) {
        Category category = categoryService.findById(id);
        model.addAttribute("category", category);
        List<CategoryDto.Response.Full> parentCategories = categoryService.findAll();
        model.addAttribute("parentCategories", parentCategories);
        return "/admin/category";
    }

    @PostMapping(value="/category/save")
    public String saveCategory(@ModelAttribute("category") Category category, BindingResult result, Model model) {
        System.out.println(category);
        categoryService.addCategory(category);
        return "redirect:/admin/categories";
    }
}
