package com.example.internship.service.category;

import com.example.internship.dto.category.CategoryDto;
import com.example.internship.entity.Category;
import com.example.internship.repository.CategoryRepository;

import com.example.internship.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author Мурашов Алексей
 */
@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<CategoryDto.Response.All> findAll() {
        return categoryRepository.findAll().stream()
                .map(this::convertToFullDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<CategoryDto.Response.All> findSubcategories(Long parentId) {
        return categoryRepository.findByParentId(parentId).stream()
                .map(this::convertToFullDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<CategoryDto.Response.IdAndName> findIdAndName() {
        return categoryRepository.findAll().stream().map(this::convertToIdAndNameDto)
                .collect(Collectors.toUnmodifiableList());
    }

    @Override
    public Optional<CategoryDto.Response.All> findById(Long id) {
        Optional<Category> category = categoryRepository.findById(id);
        return category.map(this::convertToFullDto);
    }

    public List<Category> findAllSortById() {
        return categoryRepository.findAllByOrderByIdAscParentIdAsc();
    }

    @Override
    public void delete(Long id) {
        categoryRepository.deleteById(id);
    }

    @Override
    public void save(CategoryDto.Request.All category) {
        categoryRepository.save(modelMapper.map(category, Category.class));
    }

    @Override
    public List<CategoryDto.Response.All> findByName(String name) {
        return categoryRepository.findByNameContainsIgnoreCase(name).stream()
                .map(this::convertToFullDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<CategoryDto.Response.All> findTopLevelCategories() {
        return categoryRepository.findByParentIdIsNull().stream()
                .map(this::convertToFullDto)
                .collect(Collectors.toList());
    }

    private CategoryDto.Response.All convertToFullDto(Category category) {
        return modelMapper.map(category, CategoryDto.Response.All.class);
    }

    private CategoryDto.Response.IdAndName convertToIdAndNameDto(Category category) {
        return modelMapper.map(category, CategoryDto.Response.IdAndName.class);
    }

    @PostConstruct
    private void configureMapper() {
        modelMapper.createTypeMap(Category.class, CategoryDto.Response.All.class)
                .addMappings(mapper -> {
                    mapper.map(src -> src.getParent().getName(), CategoryDto.Response.All::setParentName);
                    mapper.map(src -> src.getParent().getId(), CategoryDto.Response.All::setParentId);
                });

        modelMapper.createTypeMap(CategoryDto.Request.All.class, Category.class)
                .addMappings(mapper -> mapper.<Long>map(CategoryDto.Request.All::getParentId, (target, v) -> target.getParent().setId(v)));
    }
}

