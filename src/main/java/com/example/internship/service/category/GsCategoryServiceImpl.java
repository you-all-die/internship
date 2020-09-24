package com.example.internship.service.category;

import com.example.internship.dto.category.CategoryDto;
import com.example.internship.dto.category.CategoryDto.Response.AllWithSubcategories;
import com.example.internship.entity.Category;
import com.example.internship.repository.CategoryRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.spi.MappingContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toUnmodifiableList;

/**
 * @author Самохвалов Юрий Алексеевич
 */
@Service
@RequiredArgsConstructor
public class GsCategoryServiceImpl implements GsCategoryService {

    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true, noRollbackFor = Exception.class)
    public List<AllWithSubcategories> findTopCategories() {
        return categoryRepository.findAllByParentNull().stream()
                .map(this::convertToAllWithSubcategories)
                .collect(toUnmodifiableList());
    }

    @Override
    public List<CategoryDto.Response.All> findAncestors(final Long categoryId) {
        if (null == categoryId) {
            return Collections.emptyList();
        }
        final Optional<Category> categoryOptional = categoryRepository.findById(categoryId);
        if (categoryOptional.isEmpty()) {
            return Collections.emptyList();
        }
        return findAncestors(categoryOptional.get());
    }

    /**
     * Возвращает список идентификаторов категории и всех её наследников.
     * Для категории с идентификатором <b>null</b> возвращает null.
     * Если категории с указанным идентификатором не существует, возвращает пустой список.
     *
     * @param categoryId идентификатор категории
     * @return список идентификаторов категорий или null
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true, noRollbackFor = Exception.class)
    public List<Long> findDescendants(final Long categoryId) {
        if (null == categoryId) {
            return null;
        }
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
    public Category save(CategoryDto.Request.All dto) {
        Category entity = modelMapper.map(dto, Category.class);
        return categoryRepository.save(entity);
    }

    @Override
    public void deleteAll() {
        categoryRepository.deleteAll();
    }

    private List<CategoryDto.Response.All> findAncestors(final Category category) {
        if (null == category) {
            return Collections.emptyList();
        }
        final List<Category> ancestors = new ArrayList<>();
        ancestors.add(category);
        Category parent = category.getParent();
        while (parent != null) {
            ancestors.add(0, parent);
            parent = parent.getParent();
        }
        return ancestors.stream().map(this::convertToAll).collect(toUnmodifiableList());
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = true, noRollbackFor = Exception.class)
    private List<Long> findDescendants(@NonNull final Category category) {
        List<Long> descendants = new ArrayList<>();
        descendants.add(category.getId());
        category.getSubcategories().forEach(subcategory -> {
                    descendants.add(subcategory.getId());
                    descendants.addAll(findDescendants(subcategory));
                }
        );
        return descendants;
    }

    @PostConstruct
    public void configureModelMapper() {
        modelMapper
                .createTypeMap(CategoryDto.Request.All.class, Category.class)
                .addMappings(mapper -> mapper.skip(Category::setParent))
                .setPostConverter(this::convertRequestAllToEntity);
        modelMapper
                .createTypeMap(Category.class, AllWithSubcategories.class)
                .addMappings(mapper -> mapper.skip(AllWithSubcategories::setSubcategories))
                .setPostConverter(this::categoryToAllWithCategoriesPostConverter);
    }

    private AllWithSubcategories categoryToAllWithCategoriesPostConverter(MappingContext<Category, AllWithSubcategories> context) {
        Category entity = context.getSource();
        AllWithSubcategories dto = context.getDestination();
        final List<AllWithSubcategories> subcategories = entity.getSubcategories().stream()
                .map(this::convertToAllWithSubcategories)
                .collect(toUnmodifiableList());
        dto.setSubcategories(subcategories);
        return dto;
    }

    private Category convertRequestAllToEntity(MappingContext<CategoryDto.Request.All, Category> context) {
        final CategoryDto.Request.All dto = context.getSource();
        final Category entity = context.getDestination();
        final Long parentId = dto.getParentId();
        if (null == parentId) {
            entity.setParent(null);
        } else {
            final Optional<Category> categoryOptional = categoryRepository.findById(dto.getParentId());
            entity.setParent(categoryOptional.orElse(null));
        }
        return entity;
    }

    private AllWithSubcategories convertToAllWithSubcategories(Category category) {
        return modelMapper.map(category, AllWithSubcategories.class);
    }

    private CategoryDto.Response.All convertToAll(Category category) {
        return modelMapper.map(category, CategoryDto.Response.All.class);
    }
}
