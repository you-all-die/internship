package com.example.internship.service.category;

import com.example.internship.dto.CategoryDto;
import com.example.internship.dto.CategorySearchResult;
import com.example.internship.entity.Category;
import com.example.internship.repository.CategoryRepository;
import com.example.internship.repository.projection.ParentCategoryProjection;
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

    public Category addCategory(CategoryDto category) {
       return categoryRepository.save(this.convertToEntity(category));
    }

    public List<CategoryDto> findByName(String name) {
        return categoryRepository.findByNameContainsIgnoreCase(name).stream()
                .map(this::convertToDto).collect(Collectors.toList());
    }

    //Метод поиска категорий с параметрами
    public CategorySearchResult search(String name, Long parentId, Integer pageSize, Integer pageNumber) {

        CategorySearchResult categorySearchResult = new CategorySearchResult();

        Specification<Category> specification;

        // Формируем условия для запроса
        specification = draftSpecification(null,"name", name);

        /*Условие для поиска по родительской категории
         * Если получаем 0 -> поиск категории без потомков
         * Если пулучаем значение больше 0 -> поиск по выбранному родителю
         */
        if (parentId != null) {
            if (parentId > 0) specification =
                    draftSpecification(specification, "parentId", parentId.toString());
            if (parentId == 0) specification =
                    draftSpecification(specification, "parentIdNull", parentId.toString());
        }

        categorySearchResult.setCategory(categoryRepository.findAll(specification,
                PageRequest.of(pageNumber, pageSize)).stream()
                .map(this::convertToDto).collect(Collectors.toList()));

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

    private CategoryDto convertFromProjection(ParentCategoryProjection projection) {
        return modelMapper.map(projection, CategoryDto.class);
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

    //Поиск родительских категорий в общем списке
    public List<CategoryDto> getParentCategoriesWithChildren(){
        return categoryRepository.getParentCategoriesWithChildren().stream()
                .map(this::convertFromProjection).collect(Collectors.toList());
    }
}
