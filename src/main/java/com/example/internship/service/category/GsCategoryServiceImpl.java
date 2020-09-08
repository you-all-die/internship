package com.example.internship.service.category;

import com.example.internship.dto.category.CategoryDto;
import com.example.internship.entity.Category;
import com.example.internship.repository.CategoryRepository;
import com.example.internship.service.GsCategoryService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GsCategoryServiceImpl implements GsCategoryService {

    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<CategoryDto.Response.AllWithParentSubcategories> findAll() {
        return categoryRepository.findAll().stream()
                .map(this::convertToAllWithParentAndSubcategories)
                .collect(Collectors.toUnmodifiableList());
    }

    @Override
    public List<CategoryDto.Response.AllWithParentSubcategories> findTopCategories() {
        return categoryRepository.findAllByParentNull().stream()
                .map(this::convertToAllWithParentAndSubcategories)
                .collect(Collectors.toUnmodifiableList());
    }

    @Override
    public List<Long> findAncestors(Category category) {
        List<Long> ancestors = new LinkedList<>();
        Category parent = category.getParent();
        while (parent != null) {
            ancestors.add(parent.getId());
            parent = parent.getParent();
        }
        return ancestors;
    }

    @Override
    public List<Long> findDescendants(Category category) {
        List<Long> descendants = new LinkedList<>();
        category.getSubcategories().forEach(subcategory -> {
                    descendants.add(subcategory.getId());
                    descendants.addAll(findDescendants(subcategory));
                }
        );
        return descendants;
    }

    @Override
    public List<Long> findDescendants(Long categoryId) {
        final Optional<Category> categoryOptional = findById(categoryId);
        if (categoryOptional.isEmpty()) {
            return Collections.emptyList();
        }
        return findDescendants(categoryOptional.get());
    }

    @Override
    public Optional<Category> findById(long id) {
        return categoryRepository.findById(id);
    }

    @Override
    public void save(CategoryDto.Request.All categoryDto) {
        categoryRepository.save(modelMapper.map(categoryDto, Category.class));
    }

    @Override
    public void delete(long id) {
        categoryRepository.deleteById(id);
    }

    private CategoryDto.Response.All convertToAllDto(Category category) {
        return modelMapper.map(category, CategoryDto.Response.All.class);
    }

    private CategoryDto.Response.AllWithParentSubcategories convertToAllWithParentAndSubcategories(Category category) {
        return modelMapper.map(category, CategoryDto.Response.AllWithParentSubcategories.class);
    }
}
