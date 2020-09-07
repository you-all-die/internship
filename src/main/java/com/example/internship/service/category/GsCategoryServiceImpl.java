package com.example.internship.service.category;

import com.example.internship.dto.category.CategoryDto;
import com.example.internship.entity.Category;
import com.example.internship.repository.CategoryRepository;
import com.example.internship.service.GsCategoryService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

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
    public List<CategoryDto.Response.IdOnly> findDescendants(Category category) {
        List<CategoryDto.Response.IdOnly> descendants = new LinkedList<>();
        Category parent = category.getParent();
        while (parent != null) {
            descendants.add(modelMapper.map(parent, CategoryDto.Response.IdOnly.class));
            parent = parent.getParent();
        }
        return descendants;
    }

    @Override
    public Optional<CategoryDto.Response.All> findById(long id) {
        return categoryRepository.findById(id).map(this::convertToAllDto);
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
