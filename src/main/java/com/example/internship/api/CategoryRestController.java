package com.example.internship.api;

import com.example.internship.dto.CategorySearchResult;
import com.example.internship.entity.Category;
import com.example.internship.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * @author Ivan Gubanov
 */
@RestController
@RequestMapping("/api/category/")
@AllArgsConstructor
public class CategoryRestController {

    private final CategoryService categoryService;

    @PostMapping(value = "/find-all-sort-by-id")
    @Operation(summary = "Возвращает все категории отсортированные по id.")
    public List<Category> findAllSortById() {
        return categoryService.findAllSortById();
    }

    @PostMapping(value = "/find-by-name")
    @Operation(summary = "Возвращает категорию по ее названию")
    public List<Category> findByName(@RequestBody String name) {
        return categoryService.findByName(name);
    }

    @PostMapping(value = "/remove-category")
    @Operation(summary = "Удаляем категорию по id")
    public void removeCategory(@RequestBody Long id) {
        categoryService.removeCategory(id);
    }

    @PostMapping(value = "/find-all")
    @Operation(summary = "Возвращает все категории")
    public List<Category> findAll() {
        return categoryService.findAll();
    }

    @PostMapping(value = "/find-by-id")
    @Operation(summary = "Возвращает категорию по id")
    public Category findById(@RequestBody Long id) {
        return categoryService.findById(id);
    }

    @PostMapping(value = "/add-category")
    @Operation(summary = "Сохраняет категорию в БД")
    public void addCategory(@RequestBody Category category) {
        categoryService.addCategory(category);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Возвращает информацию о продукте, по значениею его id.")
    public Category productData(@PathVariable Long id) {
        return categoryService.findById(id);
    }

    @GetMapping("/search")
    @Operation(summary = "Возвращает список категории согласно заданным критериям поиска.")
    public CategorySearchResult categorySearch(@RequestParam(name = "searchText", required = false)
                                               @Parameter(description = "поиск по наименованию")
                                                       Optional<String> searchText,
                                               @RequestParam(name = "parentId", required = false)
                                               @Parameter(description = "поиск id parent")
                                                       Optional<Long> parentId,
                                               @RequestParam(name = "pageSize", required = false, defaultValue = "20")
                                               @Parameter(description = "размер страницы")
                                                       Integer pageSize,
                                               @RequestParam(name = "pageNumber", required = false, defaultValue = "0")
                                               @Parameter(description = "номер страницы")
                                                       Integer pageNumber) {

        return categoryService.search(searchText, parentId, pageSize, pageNumber);
    }


}
