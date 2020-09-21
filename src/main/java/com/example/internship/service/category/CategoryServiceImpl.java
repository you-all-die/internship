package com.example.internship.service.category;

import com.example.internship.dto.CategoryDto;
import com.example.internship.dto.CategorySearchResult;
import com.example.internship.entity.Category;
import com.example.internship.repository.CategoryRepository;
import com.example.internship.specification.CategorySpecification;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;

    public List<CategoryDto> findAll() {
        return categoryRepository.findAll().stream().map(this::convertToDto).collect(Collectors.toList());
    }

    public CategoryDto findById(Long id) {
        return convertToDto(categoryRepository.findById(id).orElse(new Category()));
    }

    public List<CategoryDto> findAllSortById() {
        return categoryRepository.findAllByOrderByIdAscParentIdAsc().stream()
                .map(this::convertToDto).collect(Collectors.toList());
    }

    public void removeCategory(Long id) {
        categoryRepository.deleteById(id);
    }

    public void addCategory(CategoryDto category) {
//        if (null == category.getParent().getId()) {
//            category.setParent(null);
//        }
        categoryRepository.save(this.convertToEntity(category));
    }

    @Override
    public void addCategory(Category category) {
        categoryRepository.save(category);
    }

    public List<CategoryDto> findByName(String name) {
        return categoryRepository.findByNameContainsIgnoreCase(name).stream()
                .map(this::convertToDto).collect(Collectors.toList());
    }

    public CategorySearchResult search(Optional<String> name, Optional<Long> parentId, Integer pageSize, Integer pageNumber) {

        CategorySearchResult categorySearchResult = new CategorySearchResult();

        Specification<Category> specification = Specification.where(

                new CategorySpecification("name", name.orElse("")));

        if (parentId.isPresent()) {
            specification = specification.and(new CategorySpecification("parentId", parentId.get()));
        }

        categorySearchResult.setCategory(categoryRepository.findAll(specification, PageRequest.of(pageNumber, pageSize)).stream().collect(Collectors.toList()));
        categorySearchResult.setPageNumber(pageNumber);
        categorySearchResult.setPageSize(pageSize);
        categorySearchResult.setTotalCategory(categoryRepository.count(specification));

        return categorySearchResult;
    }

    public void removeAll() {
        categoryRepository.deleteAll();
    }

    @PostConstruct
    private void configureMapper() {
        modelMapper.createTypeMap(Category.class, CategoryDto.class)
                .addMappings(mapper -> {
                    mapper.map(src -> src.getParent().getName(),
                            CategoryDto::setParentName);
                    mapper.map(src -> src.getParent().getId(),
                            CategoryDto::setParentId);
                });

        modelMapper.createTypeMap(CategoryDto.class, Category.class)
                .addMappings(mapper -> {
                    mapper.<Long>map(CategoryDto::getParentId, (target, v) -> target.getParent().setId(v));
                    mapper.<String>map(CategoryDto::getParentName, (target, v) -> target.getParent().setName(v));
                });
    }

    private CategoryDto convertToDto(Category category) {
        return modelMapper.map(category, CategoryDto.class);
    }

    private Category convertToEntity(CategoryDto categoryDto) {
        return modelMapper.map(categoryDto, Category.class);
    }
}
