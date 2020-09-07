package com.example.internship.api;

import com.example.internship.entity.Category;
import com.example.internship.service.CategoryService;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Ivan Gubanov
 */
@RestController
@RequestMapping("/api/category/")
@AllArgsConstructor
public class CategoryRestController {

    private final CategoryService categoryService;

    @PostMapping(value = "/find-all-sort-by-id")
    @ApiOperation(value = "Возвращает все категории отсортированные по id.", response = List.class)
    public List<Category> findAllSortById() {
        return categoryService.findAllSortById();
    }

    @PostMapping(value = "/find-by-name")
    @ApiOperation(value = "Возвращает категорию по ее названию", response = List.class)
    public List<Category> findByName(@RequestBody String name) {
        return categoryService.findByName(name);
    }

    @PostMapping(value = "/remove-category")
    @ApiOperation(value = "Удаляем категорию по id")
    public void removeCategory(@RequestBody Long id) {
        categoryService.removeCategory(id);
    }

    @PostMapping(value = "/find-all")
    @ApiOperation(value = "Возвращает все категории", response = List.class)
    public List<Category> findAll() {
        return categoryService.findAll();
    }

    @PostMapping(value = "/find-by-id")
    @ApiOperation(value = "Возвращает категорию по id", response = Category.class)
    public Category findById(@RequestBody Long id) {
        return categoryService.findById(id);
    }

    @PostMapping(value = "/add-category")
    @ApiOperation(value = "Сохраняет категорию в БД")
    public void addCategory(@RequestBody Category category) {
        categoryService.addCategory(category);
    }
}
