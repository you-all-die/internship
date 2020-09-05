package com.example.internship.service.category;

import com.example.internship.dto.category.CategoryDto;
import com.example.internship.entity.Category;
import com.example.internship.repository.CategoryRepository;
import com.example.internship.service.GsCategoryService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GsCategoryServiceImpl implements GsCategoryService {

    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<CategoryDto.Response.All> findAll() {
        return categoryRepository.findAll().stream()
                .map(this::convertToAllDto)
                .collect(Collectors.toUnmodifiableList());
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
}
