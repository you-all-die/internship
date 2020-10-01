package com.example.adminapplication.service.impl;

import com.example.adminapplication.dto.CategoryDto;
import com.example.adminapplication.dto.CategorySearchRequest;
import com.example.adminapplication.dto.CategorySearchResult;
import com.example.adminapplication.service.CategoryService;
import com.example.internship.client.api.CategoryRestControllerApi;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Ivan Gubanov
 */
@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRestControllerApi categoryApi;
    private final ModelMapper modelMapper;


    //Поиск категории по ID
    @Override
    public CategoryDto findById(Long id) {
        return modelMapper.map(categoryApi.getCategory(id).block(), CategoryDto.class);
    }


    //Показать все категории, отсортированные по ID
    @Override
    public List<CategoryDto> findAll() {
        return categoryApi.findAllCategories().map(this::convertToDto).collectList().block();
    }


    //Удаление категории по ID
    @Override
    public void removeCategory(Long id) {
        categoryApi.deleteCategory(id).block();
    }


    //Добавление новой категории
    @Override
    public void addCategory(CategoryDto category) {
        categoryApi.addCategory(convertToModel(category)).block();
    }

    //Запрос на поиск по критериям
    @Override
    public CategorySearchResult searchResult(CategorySearchRequest request) {
        return modelMapper.map(
                categoryApi.categorySearch(request.getName().isBlank() ? null : request.getName(),
                        request.getParentCategoryId(),
                        request.getPageSize() > 0 ? request.getPageSize() : null,
                        request.getPageNumber() > 0 ? request.getPageNumber() : null)
                        .block(),
                CategorySearchResult.class);
    }

    //Запрос на поиск родительских категорий
    @Override
    public List<CategoryDto> getParentCategoriesWithChildren() {
        return categoryApi.searchParentCategories().map(this::convertToDto).collectList().block();
    }

    private CategoryDto convertToDto(com.example.internship.client.model.CategoryDto categoryDto) {
        return modelMapper.map(categoryDto, CategoryDto.class);
    }

    private com.example.internship.client.model.CategoryDto convertToModel(CategoryDto categoryDto) {
        return modelMapper.map(categoryDto, com.example.internship.client.model.CategoryDto.class);
    }
}
