package com.example.internship.api;

import com.example.internship.dto.CategoryDto;
import com.example.internship.dto.CategorySearchResult;
import com.example.internship.service.category.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
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

    @GetMapping(value = "/all")
    @Operation(summary = "Возвращает все категории")
    public List<CategoryDto> findAllCategories() {
        return categoryService.findAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Возвращает информацию о категории по значениею id",
            parameters = {
                    @Parameter(in = ParameterIn.PATH,
                            description = "Id категории, по которой запрашивается информация",
                            name = "id",
                            required = true)
            })
    public CategoryDto getCategory(@PathVariable Long id) {
        return categoryService.findById(id);
    }

    @PostMapping(value = "/")
    @Operation(summary = "Сохраняет категорию в БД")
    public void addCategory(@RequestBody CategoryDto category) {
        System.out.println(category);
        categoryService.addCategory(category);
    }

    @GetMapping(value = "/findByName")
    @Operation(summary = "Возвращает категорию по ее названию",
            parameters = {
                    @Parameter(in = ParameterIn.QUERY,
                            description = "Имя категории",
                            name = "name",
                            required = true)
            })
    public List<CategoryDto> findCategoryByName(@RequestParam(name = "name") String name) {
        return categoryService.findByName(name);
    }

    @DeleteMapping(value = "/{id}")
    @Operation(summary = "Удаляем категорию по id")
    public void deleteCategory(@PathVariable Long id) {
        categoryService.removeCategory(id);
    }

    @GetMapping("/search")
    @Operation(summary = "Возвращает список категории согласно заданным критериям поиска",
            parameters = {
                    @Parameter(description = "Имя категории",
                            name = "name"),
                    @Parameter(description = "Родителская категория",
                            name = "parentId"),
                    @Parameter(description = "Количество записей на странице",
                            name = "pageSize"),
                    @Parameter(description = "Номер страницы",
                            name = "pageNumber")
            })
    public CategorySearchResult categorySearch(
            @RequestParam(name = "name", required = false) String name,
            @RequestParam(name = "parentId", required = false) Long parentId,
            @RequestParam(name = "pageSize", required = false, defaultValue = "20") Integer pageSize,
            @RequestParam(name = "pageNumber", required = false, defaultValue = "0") Integer pageNumber) {

        return categoryService.search(name, parentId, pageSize, pageNumber);
    }

    @GetMapping("/parentCategoriesWithChildren")
    @Operation(summary = "Возвращает список родительских категорий, у которых есть наследники")
    public List<CategoryDto> searchParentCategories() {
        return categoryService.getParentCategoriesWithChildren();
    }

}
