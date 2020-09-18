package com.example.internship.service;

import com.example.internship.dto.CategorySearchResult;
import com.example.internship.entity.Category;
import com.example.internship.repository.CategoryRepository;
import com.example.internship.specification.CategorySpecification;
import com.example.internship.specification.ProductSpecification;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public List<Category> findAll() {
        return (List<Category>) categoryRepository.findAll();
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
        if (category.getParent() != null && category.getParent().getId() == (long) -1) {
            category.setParent(null);
        }
        categoryRepository.save(category);
    }

    public List<Category> findByName(String name) {
        return categoryRepository.findByNameContainsIgnoreCase(name);
    }

    public CategorySearchResult search(String name, Category parent, Integer pageSize, Integer pageNumber) {

        CategorySearchResult categorySearchResult = new CategorySearchResult();

        if (name == null) {
            name = "";
        }
        Specification<Category> specification = Specification.where(new CategorySpecification("name", name));
        if (pageNumber == null) {
            pageNumber = 0;
        }
        if (pageSize == null) {
            pageSize = 20;
        }
        if (parent == null) {
            specification = specification.and(new CategorySpecification("parent", parent));
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
}
