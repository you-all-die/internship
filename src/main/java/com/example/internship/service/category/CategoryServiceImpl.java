package com.example.internship.service.category;

import com.example.internship.dto.category.CategoryDto;
import com.example.internship.entity.Category;
import com.example.internship.repository.CategoryRepository;

import com.example.internship.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<CategoryDto.Response.Full> findAll() {
        return categoryRepository.findAll().stream()
                .map(this::convertToFullDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<CategoryDto.Response.Full> findSubcategories(Long parentId) {
        return categoryRepository.findByParentId(parentId).stream()
                .map(this::convertToFullDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<CategoryDto.Response.IdAndName> getIdAndName() {
        return categoryRepository.findAll().stream().map(this::convertToIdAndNameDto)
                .collect(Collectors.toUnmodifiableList());
    }

    @Override
    public Optional<CategoryDto.Response.Full> findById(Long id) throws EntityNotFoundException {
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
    public void save(CategoryDto.Request.Full category) {
        categoryRepository.save(modelMapper.map(category, Category.class));
    }

    @Override
    public List<CategoryDto.Response.Full> findByName(String name) {
        return categoryRepository.findByNameContainsIgnoreCase(name).stream()
                .map(this::convertToFullDto)
                .collect(Collectors.toList());
    }

    private CategoryDto.Response.Full convertToFullDto(Category category) {
        return modelMapper.map(category, CategoryDto.Response.Full.class);
    }

    private CategoryDto.Response.IdAndName convertToIdAndNameDto(Category category) {
        return modelMapper.map(category, CategoryDto.Response.IdAndName.class);
    }

    @PostConstruct
    private void configureMapper() {
        modelMapper.createTypeMap(Category.class, CategoryDto.Response.Full.class)
                .addMappings(mapper -> mapper.map(src -> src.getParent().getName(), CategoryDto.Response.Full::setParentName))
                .addMappings(mapper -> mapper.map(src -> src.getParent().getId(), CategoryDto.Response.Full::setParentId));

        modelMapper.createTypeMap(CategoryDto.Request.Full.class, Category.class)
                .addMappings(mapper -> mapper.<Long>map(CategoryDto.Request.Full::getParentId, (target, v) -> target.getParent().setId(v)));
    }
}

