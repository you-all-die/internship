package com.example.internship.service;

import com.example.internship.dto.CategorySearchResult;
import com.example.internship.entity.Category;
import com.example.internship.repository.CategoryRepository;
import com.example.internship.specification.CategorySpecification;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
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

    public CategorySearchResult search(String name, Long parentId, Integer pageSize, Integer pageNumber) {

        CategorySearchResult categorySearchResult = new CategorySearchResult();

        Specification<Category> specification;

        // Формируем условия для запроса
        specification = draftSpecification(null,"name", name);
        if(parentId!=null) specification =
                draftSpecification(specification,"parent_id", parentId.toString());

        categorySearchResult.setCategory(categoryRepository.findAll(specification,
                PageRequest.of(pageNumber, pageSize)).stream().collect(Collectors.toList()));
        categorySearchResult.setPageNumber(pageNumber);
        categorySearchResult.setPageSize(pageSize);
        categorySearchResult.setTotalCategory(categoryRepository.count(specification));
        return categorySearchResult;
    }

    public void removeAll() {
        categoryRepository.deleteAll();
    }

    //Метод проверки поля и добавления условия в запрос
    private Specification<Category> draftSpecification(Specification<Category> specification, String columnName,
                                                       String optionalName ){
        if(optionalName !=null){
            if(specification == null){
                specification = Specification.where(new CategorySpecification(columnName, optionalName));
            }else {
                specification = specification.and(new CategorySpecification(columnName, optionalName));
            }
        }
        return specification;
    }
}
