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

/**
 * @author Мурашов Алексей
 */
@Controller
@RequestMapping("/admin/category")
@RequiredArgsConstructor
public class CategoriesController {
    private final CategoryService categoryService;

    @GetMapping(value = "")
    public String showCategories(Model model, @RequestParam(value = "name", required = false) String categoryName) {
        List<CategoryDto.Response.All> list;
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
        CategoryDto.Response.All category = new CategoryDto.Response.All();
        model.addAttribute("category", category);
        List<CategoryDto.Response.IdAndName> parentCategories = categoryService.findIdAndName();
        model.addAttribute("parentCategories", parentCategories);
        return "/admin/category";
    }

    @GetMapping({"/edit"})
    public String editExistingCategory(@RequestParam("categoryId") Long id, Model model) {
            Optional<CategoryDto.Response.All> category = categoryService.findById(id);
        if(category.isPresent()) {
            model.addAttribute("category", category.get());
            List<CategoryDto.Response.IdAndName> parentCategories = categoryService.findIdAndName();
            model.addAttribute("parentCategories", parentCategories);
            return "/admin/category";
        }
        else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found");
        }
    }

    @PostMapping(value="/save")
    public String saveCategory(@ModelAttribute("category") CategoryDto.Request.All category, BindingResult result, Model model) {
        categoryService.save(category);
        return "redirect:/admin/category";
    }

    @GetMapping({"/{id}/subcategories"})
    public String showSubCategories(Model model, @PathVariable(value = "id", required = false) Long parentId) {
        List<CategoryDto.Response.All> list = categoryService.findSubcategories(parentId);
        System.out.println(list);
        return "redirect:/admin/category";
    }

    @GetMapping(value = "/top")
    public String showTopLevelCategories(Model model) {
        List<CategoryDto.Response.All> list = categoryService.findTopLevelCategories();
        System.out.println(list);
        return "redirect:/admin/category";
    }
}
