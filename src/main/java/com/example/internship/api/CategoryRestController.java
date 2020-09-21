package com.example.internship.api;

import com.example.internship.dto.CategorySearchResult;
import com.example.internship.entity.Category;
import com.example.internship.service.category.CategoryService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
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

    @GetMapping("/{id}")
    @ApiOperation(value = "Возвращает информацию о продукте, по значениею его id.", response = Category.class)
    public Category productData(@PathVariable Long id) {
        return categoryService.findById(id);
    }

    @GetMapping("/search")
    @ApiOperation(value = "Возвращает список категории согласно заданным критериям поиска.",
            notes = "В запросе search могут указываться:\n" +
                    "- pageSize количество возвращаемых категорий (значение по умолчанию 20)\n" +
                    "- pageNumber номер страницы (значение по умолчанию 1)\n" +
                    "- parentId поиск по id parent\n" +
                    "- searchText - поиск по наименованию",
            response = CategorySearchResult.class)
    public CategorySearchResult categorySearch(@RequestParam(name = "searchText", required = false)
                                               @ApiParam(value = "поиск по наименованию")
                                                       Optional<String> searchText,
                                               @RequestParam(name = "parentId", required = false)
                                               @ApiParam(value = "поиск id parent")
                                                       Optional<Long> parentId,
                                               @RequestParam(name = "pageSize", required = false, defaultValue = "20")
                                               @ApiParam(value = "размер страницы")
                                                       Integer pageSize,
                                               @RequestParam(name = "pageNumber", required = false, defaultValue = "0")
                                               @ApiParam(value = "номер страницы")
                                                       Integer pageNumber) {

        return categoryService.search(searchText, parentId, pageSize, pageNumber);
    }


}
