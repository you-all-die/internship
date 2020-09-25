package com.example.internship.api;

import com.example.internship.dto.CategoryDto;
import com.example.internship.dto.CategorySearchResult;
import com.example.internship.service.category.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Ivan Gubanov
 */
@RestController
@RequestMapping("/api/category")
@AllArgsConstructor
public class CategoryRestController {

    private final CategoryService categoryService;

    @PostMapping(value = "/find-all-sort-by-id")
    @Operation(summary = "Возвращает все категории отсортированные по id.")
    public List<CategoryDto> findAllSortById() {
        return categoryService.findAllSortById();
    }

    @PostMapping(value = "/find-by-name")
    @Operation(summary = "Возвращает категорию по ее названию")
    public List<CategoryDto> findByName(@RequestBody String name) {
        return categoryService.findByName(name);
    }

    @DeleteMapping(value = "/remove/{id}")
    @Operation(summary = "Удаляем категорию по id")
    public void removeCategory(@PathVariable Long id) {
        categoryService.removeCategory(id);
    }

    @GetMapping(value = "/find-all")
    @Operation(summary = "Возвращает все категории")
    public List<CategoryDto> findAll() {
        return categoryService.findAll();
    }

    @PostMapping(value = "/find-by-id")
    @Operation(summary = "Возвращает категорию по id")
    public CategoryDto findById(@RequestBody Long id) {
        return categoryService.findById(id);
    }

    @PostMapping(value = "/add-category")
    @Operation(summary = "Сохраняет категорию в БД")
    public void addCategory(@RequestBody CategoryDto category) {
        categoryService.addCategory(category);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Возвращает информацию о продукте, по значениею его id.")
    public CategoryDto categoryData(@PathVariable Long id) {
        return categoryService.findById(id);
    }

    @GetMapping("/search")
    @Operation(summary = "Возвращает список категории согласно заданным критериям поиска.")
    public CategorySearchResult categorySearch(@RequestParam(name = "searchText", required = false)
                                               @Parameter(description = "поиск по наименованию")
                                                       String searchText,
                                               @RequestParam(name = "parentId", required = false)
                                               @Parameter(description = "поиск id parent")
                                                       Long parentId,
                                               @RequestParam(name = "pageSize", required = false, defaultValue = "20")
                                               @Parameter(description = "размер страницы")
                                                       Integer pageSize,
                                               @RequestParam(name = "pageNumber", required = false, defaultValue = "0")
                                               @Parameter(description = "номер страницы")
                                                       Integer pageNumber) {

        return categoryService.search(searchText, parentId, pageSize, pageNumber);
    }

    @GetMapping("/parentCategoriesWithChildren")
    @Operation(summary = "Возвращает список родительских категорий, у которых есть наследники")
    public List<CategoryDto> searchParentCategories() {
        return categoryService.getParentCategoriesWithChildren();
    }

}
