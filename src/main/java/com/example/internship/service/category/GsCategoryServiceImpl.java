package com.example.internship.service.category;

import com.example.internship.dto.category.CategoryDto;
import com.example.internship.entity.Category;
import com.example.internship.repository.CategoryRepository;
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
    public List<CategoryDto.Response.All> findAncestors(Category category) {
        if (null == category) {
            return Collections.emptyList();
        }
        List<Category> ancestors = new LinkedList<>();
        ancestors.add(category);
        Category parent = category.getParent();
        while (parent != null) {
            ancestors.add(0, parent);
            parent = parent.getParent();
        }
        return ancestors.stream().map(this::convertToAllDto).collect(Collectors.toUnmodifiableList());
    }

    /**
     * Возвращает список идентификаторов категории и всех её наследников
     *
     * @param category категория
     * @return список идентификаторов категорий
     */
    @Override
    public List<Long> findDescendants(Category category) {
        List<Long> descendants = new LinkedList<>();
        descendants.add(category.getId());
        category.getSubcategories().forEach(subcategory -> {
                    descendants.add(subcategory.getId());
                    descendants.addAll(findDescendants(subcategory));
                }
        );
        return descendants;
    }

    /**
     * Возвращает список идентификаторов категории и всех её наследников.
     * Если категории с указанным идентификатором не существует, возвращает пустой список.
     *
     * @param categoryId идентификатор категории
     * @return список идентификаторов категорий
     */
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
