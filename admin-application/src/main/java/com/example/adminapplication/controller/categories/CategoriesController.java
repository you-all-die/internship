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
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@Controller
@AllArgsConstructor
public class CategoriesController {

    private final CategoryService categoryService;

    @GetMapping(value = "/categories")
    public String saveDataForm(@RequestParam(value = "name", required = false) String categoryName,
                               @RequestParam(value = "parentCategoryId", required = false) Long parentCategoryId,
                               @RequestParam(value = "pageNumber", defaultValue = "0") Integer pageNumber,
                               @RequestParam(value = "pageSize", defaultValue = "20") Integer pageSize,
                               Model model) {

            if (categoryName==null) categoryName="";
            model.addAttribute("valueSearchParentCategoryId", parentCategoryId);
            model.addAttribute("valueSearchPageNumber", pageNumber);
            model.addAttribute("valueSearchPageSize", pageSize);
            model.addAttribute("valueSearchName", categoryName);
            model.addAttribute("categoryList", categoryService.searchResult(categoryName, parentCategoryId, pageSize, pageNumber));

        return "categories/categories_api";
    }

    //Обработка поискового блока c параметрами
    @PostMapping(value = "/categories")
    public String findCategory(@RequestParam(value = "name", required = false) String name,
                               @RequestParam(value = "parentCategoryId", required = false) Long parentCategoryId,
                               @RequestParam(value = "pageNumber", required = false) Integer pageNumber,
                               @RequestParam(value = "pageSize", required = false) Integer pageSize,
                               Model model) {
        //Конструктор запроса
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString("/categories");
        //Добавление параметра: поиск по наименованию
        if(!name.isEmpty()) builder.queryParam("name", name.toLowerCase());
        //Добавление параметра: поиск по ID родительской категории
        if(parentCategoryId !=null) builder.queryParam("parentCategoryId", parentCategoryId);
        //Добавление параметра: номер страницы
        if(pageNumber < 0) builder.queryParam("pageNumber", 0);
        else builder.queryParam("pageNumber", pageNumber);
        //Добавление параметра: размер страницы
        if(pageSize < 1) builder.queryParam("pageSize", 20);
        else builder.queryParam("pageSize", pageSize);

        return "redirect:" + builder.toUriString();

        //Декодировка русских букв для корректного поиска
        // name = URLEncoder.encode(name, StandardCharsets.UTF_8);
    }

    @PostMapping(value = "/category/delete")
    public String deleteCategory(@RequestParam("categoryId") Long id, Model model) {
        //здесь должны быть проверки на возможность удаления продукта, а пока просто удаляем
        categoryService.removeCategory(id);
        return "redirect:/categories";
    }

    @PostMapping(value = "/category/edit")
    public String editCategory(@RequestParam("categoryId") Long id, Model model) {
        return "redirect:/category/edit?categoryId=" + id;
    }

    @PostMapping(value = "categories/add")
    public String addCategory(Model model) {
        return "redirect:/categories/add";
    }

    @GetMapping({"categories/add"})
    public String addNewCategory(Model model) {

        CategoryDto category = new CategoryDto();
        model.addAttribute("category", category);
        CategoryDto defaultParent = new CategoryDto((long) -1, "Без категории", null);
        List<CategoryDto> parentCategories = categoryService.findAll();
        parentCategories.add(0, defaultParent);
        model.addAttribute("parentCategories", parentCategories);

        return "categories/category";
    }

    @GetMapping({"/category/edit"})
    public String editExistingCategory(@RequestParam("categoryId") Long id, Model model) {

        CategoryDto category = categoryService.findById(id);
        model.addAttribute("category", category);
        CategoryDto defaultParent = new CategoryDto((long) -1, "Без категории", null);
        List<CategoryDto> parentCategories = categoryService.findAll();
        parentCategories.add(0, defaultParent);
        model.addAttribute("parentCategories", parentCategories);

        return "categories/category";
    }

    @PostMapping(value = "/category/save")
    public String saveCategory(@ModelAttribute("category") CategoryDto category, BindingResult result, Model model) {

        categoryService.addCategory(category);
        return "redirect:/categories";
    }
}
