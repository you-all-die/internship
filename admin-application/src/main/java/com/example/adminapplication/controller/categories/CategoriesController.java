package com.example.adminapplication.controller.categories;

import com.example.adminapplication.dto.CategoryDto;
import com.example.adminapplication.service.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@AllArgsConstructor
public class CategoriesController {

    private CategoryService categoryService;

    @GetMapping(value="/categories")
    public String saveDataForm(Model model, @RequestParam(value = "name", required = false) String categoryName) {
        List<CategoryDto> list;
        if (categoryName == null) {
            list = categoryService.findAllSortById();
        } else {
            list = categoryService.findByName(categoryName);
        }
        model.addAttribute("categoryList", list);
        return "categories/categories";
    }

    @PostMapping(value="/categories")
    public String findCategory(@RequestParam("name") String name, Model model) {
        return "redirect:/categories?name=" + name;
    }

    @PostMapping(value="/category/delete")
    public String deleteCategory(@RequestParam("categoryId") Long id, Model model) {
        //здесь должны быть проверки на возможность удаления продукта, а пока просто удаляем
        categoryService.removeCategory(id);
        return "redirect:/categories";
    }

    @PostMapping(value="/category/edit")
    public String editCategory(@RequestParam("categoryId") Long id, Model model) {
        return "redirect:/category/edit?categoryId=" + id;
    }

    @PostMapping(value="/category/add")
    public String addCategory(Model model) {
        return "redirect:/category/add";
    }

    @GetMapping({"/category/add"})
    public String addNewCategory(Model model) {
        CategoryDto category = new CategoryDto();
        model.addAttribute("category", category);
        CategoryDto defaultParent = new CategoryDto((long) -1, "Без категории", null );
        List<CategoryDto> parentCategories = categoryService.findAll();
        parentCategories.add(0, defaultParent);
        model.addAttribute("parentCategories", parentCategories);
        return "categories/category";
    }

    @GetMapping({"/category/edit"})
    public String editExistingCategory(@RequestParam("categoryId") Long id, Model model) {
        CategoryDto category = categoryService.findById(id);
        model.addAttribute("category", category);
        CategoryDto defaultParent = new CategoryDto((long) -1, "Без категории", null );
        List<CategoryDto> parentCategories = categoryService.findAll();
        parentCategories.add(0, defaultParent);
        model.addAttribute("parentCategories", parentCategories);
        return "categories/category";
    }

    @PostMapping(value="/category/save")
    public String saveCategory(@ModelAttribute("category") CategoryDto category, BindingResult result, Model model) {
        categoryService.addCategory(category);
        return "redirect:/categories";
    }
}
