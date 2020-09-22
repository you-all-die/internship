package com.example.adminapplication.controller.categories;

import com.example.adminapplication.dto.CategoryDto;
import com.example.adminapplication.dto.CategorySearchRequest;
import com.example.adminapplication.dto.CategorySearchResult;
import com.example.adminapplication.dto.ParentCategoryDto;
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

    private final CategoryService categoryService;

    @GetMapping(value = "/categories")
    public String getCategoriesWithParameters(
            @ModelAttribute("categorySearchRequest") CategorySearchRequest categorySearchRequest, Model model){

        model.addAttribute("categorySearchRequest", categorySearchRequest);
        CategorySearchResult categorySearchResult =
                categoryService.searchResult(
                        categorySearchRequest.getName(),
                        categorySearchRequest.getParentCategoryId(),
                        categorySearchRequest.getPageSize(),
                        categorySearchRequest.getPageNumber());

        //Определяем количество страниц
        Long totalCategory = categorySearchResult.getTotalCategory();
        long totalPage = 0;
        if (categorySearchResult.getTotalCategory()>categorySearchRequest.getPageSize()) {
            totalPage = (long) Math.ceil(totalCategory * 1.0 / categorySearchRequest.getPageSize());
        }
        model.addAttribute("totalPage", totalPage);
        model.addAttribute("categoryList", categorySearchResult);

        //Список родительских категорий с потомками
        List<ParentCategoryDto> parentCategoryList = categoryService.getParentCategoriesWithChildren();
        model.addAttribute("parentCategories", parentCategoryList);

        return "categories/categories";
    }

    @PostMapping(value = "/category/delete")
    public String deleteCategory(@RequestParam("categoryId") Long id, Model model) {
        //здесь должны быть проверки на возможность удаления продукта, а пока просто удаляем
        categoryService.removeCategory(id);
        return "redirect:/categories";
    }

    @GetMapping({"categories/add"})
    public String addNewCategory(Model model) {
        CategoryDto category = new CategoryDto();
        model.addAttribute("category", category);
        List<CategoryDto> parentCategories = categoryService.findAllSortById();
        model.addAttribute("parentCategories", parentCategories);
        return "categories/category";
    }

    @GetMapping({"/category/edit"})
    public String editExistingCategory(@RequestParam("categoryId") Long id, Model model) {
        CategoryDto category = categoryService.findById(id);
        model.addAttribute("category", category);
        List<CategoryDto> parentCategories = categoryService.findAllSortById();
        model.addAttribute("parentCategories", parentCategories);
        return "categories/category";
    }

    @PostMapping(value = "/category/save")
    public String saveCategory(@ModelAttribute("category") CategoryDto category, BindingResult result, Model model) {
        categoryService.addCategory(category);
        return "redirect:/categories";
    }
}
