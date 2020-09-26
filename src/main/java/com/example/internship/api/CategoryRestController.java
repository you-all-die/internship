package com.example.internship.api;

import com.example.internship.dto.CategoryDto;
import com.example.internship.dto.CategorySearchResult;
import com.example.internship.service.category.CategoryService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Ivan Gubanov
 */
@RestController
@RequestMapping("/api/category")
@AllArgsConstructor
public class CategoryRestController {

    private final CategoryService categoryService;

    @GetMapping(value = "/find-all-sort-by-id")
    @ApiOperation(value = "Возвращает все категории отсортированные по id.", response = List.class)
    public List<CategoryDto> findAllSortById() {
        return categoryService.findAllSortById();
    }

    @PostMapping(value = "/find-by-name")
    @ApiOperation(value = "Возвращает категорию по ее названию", response = List.class)
    public List<CategoryDto> findByName(@RequestBody String name) {
        return categoryService.findByName(name);
    }

    @DeleteMapping(value = "/remove/{id}")
    @ApiOperation(value = "Удаляем категорию по id")
    public void removeCategory(@PathVariable Long id) {
        categoryService.removeCategory(id);
    }

    @GetMapping(value = "/find-all")
    @ApiOperation(value = "Возвращает все категории", response = List.class)
    public List<CategoryDto> findAll() {
        return categoryService.findAll();
    }

    @PostMapping(value = "/find-by-id")
    @ApiOperation(value = "Возвращает категорию по id",
            response = CategoryDto.class)
    public CategoryDto findById(@RequestBody Long id) {
        return categoryService.findById(id);
    }

    @PostMapping(value = "/add-category")
    @ApiOperation(value = "Сохраняет категорию в БД")
    public void addCategory(@RequestBody CategoryDto category) {
        categoryService.addCategory(category);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Возвращает информацию о категории, по значению id.",
            response = CategoryDto.class)
    public CategoryDto categoryData(@PathVariable Long id) {
        return categoryService.findById(id);
    }

    @GetMapping("/search")
    @ApiOperation(value = "Возвращает список категории согласно заданным критериям поиска.",
            notes = "В запросе search могут указываться:\n" +
                    "- pageSize количество возвращаемых категорий (значение по умолчанию 20)\n" +
                    "- pageNumber номер страницы (значение по умолчанию 1)\n" +
                    "- parentId поиск по id parent('0'- поиск категорий без родителя)\n" +
                    "- searchText - поиск по наименованию",
            response = CategorySearchResult.class)
    public CategorySearchResult categorySearch(@RequestParam(name = "searchText", required = false)
                                               @ApiParam(value = "поиск по наименованию")
                                                       String searchText,
                                               @RequestParam(name = "parentId", required = false)
                                               @ApiParam(value = "поиск id parent")
                                                       Long parentId,
                                               @RequestParam(name = "pageSize", required = false, defaultValue = "20")
                                               @ApiParam(value = "размер страницы")
                                                       Integer pageSize,
                                               @RequestParam(name = "pageNumber", required = false, defaultValue = "0")
                                               @ApiParam(value = "номер страницы")
                                                       Integer pageNumber) {

        return categoryService.search(searchText, parentId, pageSize, pageNumber);
    }

    @GetMapping("/parentCategoriesWithChildren")
    @ApiOperation(value = "Возвращает список родительских категорий, у которых есть наследники",
            response = CategoryDto.class)
    public List<CategoryDto> searchParentCategories() {
        return categoryService.getParentCategoriesWithChildren();
    }

}
