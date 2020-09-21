package com.example.internship.service.category;

import com.example.internship.dto.CategorySearchResult;
import com.example.internship.dto.category.CategoryDto;
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

    public List<CategoryDto.Response.AllWithParentIdParentName> findAll() {
        return categoryRepository.findAll().stream().map(this::convertToDto).collect(Collectors.toList());
    }

    public CategoryDto.Response.AllWithParentIdParentName findById(Long id) {
        return convertToDto(categoryRepository.findById(id).orElse(new Category()));
    }

    public List<CategoryDto.Response.AllWithParentIdParentName> findAllSortById() {
        return categoryRepository.findAllByOrderByIdAscParentIdAsc().stream()
                .map(this::convertToDto).collect(Collectors.toList());
    }

    public void removeCategory(Long id) {
        categoryRepository.deleteById(id);
    }

    public void addCategory(CategoryDto.Request.All category) {
//        if (null == category.getParent().getId()) {
//            category.setParent(null);
//        }
        categoryRepository.save(this.convertToEntity(category));
    }

    @Override
    public void addCategory(Category category) {
        categoryRepository.save(category);
    }

    public List<CategoryDto.Response.AllWithParentIdParentName> findByName(String name) {
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
        modelMapper.createTypeMap(Category.class, CategoryDto.Response.AllWithParentIdParentName.class)
                .addMappings(mapper -> mapper.map(src -> src.getParent().getName(),
                        CategoryDto.Response.AllWithParentIdParentName::setParentName))
                .addMappings(mapper -> mapper.map(src -> src.getParent().getId(),
                        CategoryDto.Response.AllWithParentIdParentName::setParentId));

        modelMapper.createTypeMap(CategoryDto.Request.All.class, Category.class)
                .addMappings(mapper -> mapper.<Long>map(CategoryDto.Request.All::getParentId, (target, v) -> target.getParent().setId(v)));
    }

    private CategoryDto.Response.AllWithParentIdParentName convertToDto(Category category) {
        return modelMapper.map(category, CategoryDto.Response.AllWithParentIdParentName.class);
    }

    private Category convertToEntity(CategoryDto.Request.All categoryDto) {
        return modelMapper.map(categoryDto, Category.class);
    }
}
