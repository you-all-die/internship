package com.example.internship.service.impl;

import com.example.internship.dto.category.CategoryDto;
import com.example.internship.entity.Category;
import com.example.internship.repository.CategoryRepository;

import lombok.RequiredArgsConstructor;
import org.modelmapper.Conditions;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl {
    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;

    public List<CategoryDto.Response.Full> findAll() {
        return categoryRepository.findAll().stream()
                .map(this::convertToFullDto)
                .collect(Collectors.toList());
    }

    public Category findById(Long id) {
        return categoryRepository.findById(id).orElse(new Category());
    }

    public List<Category> findAllSortById() {
        return categoryRepository.findAllByOrderByIdAscParentIdAsc();
    }

    public void removeCategory(Long id) {
        categoryRepository.deleteById(id);
    }

    public void addCategory(Category category) {
        categoryRepository.save(category);
    }

    public List<CategoryDto.Response.Full> findByName(String name) {
        return categoryRepository.findByNameContainsIgnoreCase(name).stream()
                .map(this::convertToFullDto)
                .collect(Collectors.toList());
    }

    private CategoryDto.Response.Full convertToFullDto(Category category) {
        configureMapper();
        return modelMapper.map(category, CategoryDto.Response.Full.class);
    }

    private void configureMapper() {
        TypeMap<Category, CategoryDto.Response.Full> typeMap = modelMapper.createTypeMap(Category.class, CategoryDto.Response.Full.class);
        typeMap.addMapping(src -> src.getParent() == null ? null : src.getParent().getId(), CategoryDto.Response.Full::setId);
    }
}

