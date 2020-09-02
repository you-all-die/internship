package com.example.internship.controller.admin.category;

import com.example.internship.dto.category.CategoryDto;
import com.example.internship.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;


@Controller
@RequestMapping("/admin/category")
@RequiredArgsConstructor
public class AdminCategoriesController {
    private final CategoryService categoryService;

    @GetMapping({""})
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

    @PostMapping(value="")
    public String findCategory(@RequestParam("name") String name, Model model) {
        return "redirect:/admin/category?name=" + name;
    }

    @PostMapping(value="/delete")
    public String deleteCategory(@RequestParam("categoryId") Long id, Model model) {
        //здесь должны быть проверки на возможность удаления продукта, а пока просто удаляем
        categoryService.delete(id);
        return "redirect:/admin/category";
    }

    @PostMapping(value="/edit")
    public String editCategory(@RequestParam("categoryId") Long id, Model model) {
        return "redirect:/admin/category/edit?categoryId=" + id;
    }

    @PostMapping(value="/add")
    public String addCategory(Model model) {
        return "redirect:/admin/category/add";
    }

    @GetMapping({"/add"})
    public String addNewCategory(Model model) {
        CategoryDto.Response.Full category = new CategoryDto.Response.Full();
        model.addAttribute("category", category);
        List<CategoryDto.Response.IdAndName> parentCategories = categoryService.getIdAndName();
        model.addAttribute("parentCategories", parentCategories);
        return "/admin/category";
    }

    @GetMapping({"/edit"})
    public String editExistingCategory(@RequestParam("categoryId") Long id, Model model) {
            Optional<CategoryDto.Response.Full> category = categoryService.findById(id);
        if(category.isPresent()) {
            model.addAttribute("category", category.get());
            List<CategoryDto.Response.IdAndName> parentCategories = categoryService.getIdAndName();
            model.addAttribute("parentCategories", parentCategories);
            return "/admin/category";
        }
        else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found");
        }
    }

    @PostMapping(value="/save")
    public String saveCategory(@ModelAttribute("category") CategoryDto.Request.Full category, BindingResult result, Model model) {
        categoryService.save(category);
        return "redirect:/admin/category";
    }

    @GetMapping({"/{id}/subcategories"})
    public String showSubCategories(Model model, @PathVariable(value = "id", required = false) Long parentId) {
        List<CategoryDto.Response.Full> list = categoryService.findSubcategories(parentId);
        System.out.println(list);
        return "redirect:/admin/category";
    }
}
