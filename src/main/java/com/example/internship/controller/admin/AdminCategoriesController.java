package com.example.internship.controller.admin;

import com.example.internship.dto.category.CategoryDto;
import com.example.internship.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.EntityNotFoundException;
import java.util.List;


@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminCategoriesController {
    private final CategoryService categoryService;

    @GetMapping({"/categories"})
    public String showCategories(Model model, @RequestParam(value = "name", required = false) String categoryName) {
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
        CategoryDto.Response.Full category = new CategoryDto.Response.Full();
        model.addAttribute("category", category);
        List<CategoryDto.Response.IdAndName> parentCategories = categoryService.getIdAndName();
        model.addAttribute("parentCategories", parentCategories);
        return "/admin/category";
    }

    @GetMapping({"/category/edit"})
    public String editExistingCategory(@RequestParam("categoryId") Long id, Model model) {
        try {
            CategoryDto.Response.Full category = categoryService.findById(id);
            model.addAttribute("category", category);
            List<CategoryDto.Response.IdAndName> parentCategories = categoryService.getIdAndName();
            model.addAttribute("parentCategories", parentCategories);
            return "/admin/category";
        }
        catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.toString());
        }
    }

    @PostMapping(value="/category/save")
    public String saveCategory(@ModelAttribute("category") CategoryDto.Request.Full category, BindingResult result, Model model) {
        categoryService.addCategory(category);
        return "redirect:/admin/categories";
    }

    @GetMapping({"/category/{id}/subcategories"})
    public String showSubCategories(Model model, @PathVariable(value = "id", required = false) Long parentId) {
        List<CategoryDto.Response.Full> list = categoryService.findSubcategories(parentId);
        System.out.println(list);
        return "redirect:/admin/categories";
    }
}
