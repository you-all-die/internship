package com.example.internship.service.product;

import com.example.internship.dto.ProductSearchResult;
import com.example.internship.dto.product.ProductDto;
import com.example.internship.entity.Product;
import com.example.internship.repository.ProductRepository;
import com.example.internship.service.ProductService;
import com.example.internship.specification.ProductSpecification;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author Мурашов Алексей
 */
@Service
@Qualifier("productDtoServiceImpl")
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;
    private final ProductSearchResult searchResult;

    @Override
    public List<ProductDto.Response.All> findAll() {
        return productRepository.findAll().stream().map(this::convertToFullDto).collect(Collectors.toUnmodifiableList());
    }

    @Override
    public Optional<ProductDto.Response.All> findById(Long id) {
        Optional<Product> product = productRepository.findById(id);
        return product.map(this::convertToFullDto);
    }

    @Override
    public List<ProductDto.Response.All> findByName(String name) {
        return productRepository.findByNameContainsIgnoreCase(name).stream()
                .map(this::convertToFullDto).collect(Collectors.toUnmodifiableList());
    }

    /**
     * @author Самохвалов Юрий Алексеевич
     *
     * Возвращает 5 первых попавшихся товаров.
     *
     * Наивная реализация, если вы знаете лучшее решение,
     * сообщите, пожалуйста, мне.
     *
     * @param limit максимальное количество возвращаемых продуктов
     * @return список продуктов
     */
    @Override
    public List<ProductDto.Response.All> findPopular(long limit) {
        return productRepository.findAll().stream().limit(limit)
                .map(this::convertToFullDto)
                .collect(Collectors.toUnmodifiableList());
    }

    @Override
    public void save(ProductDto.Response.All productDto) {
        productRepository.save(modelMapper.map(productDto, Product.class));
    }


    @Override
    public ProductSearchResult search(Optional<String> name,
                                      Optional<Long> categoryId,
                                      Optional<BigDecimal> priceFrom,
                                      Optional<BigDecimal> priceTo,
                                      Integer pageSize, Integer pageNumber
    ) {
        // Формируем условия для запроса к БД
        Specification<Product> specification = Specification.where(
                // Поиск по имени
                new ProductSpecification("name", name.orElse("")))
                // Поиск по цене ОТ
                .and(new ProductSpecification("priceFrom", priceFrom.orElse(new BigDecimal(0))));
        // Поиск по цене ДО
        if (priceTo.isPresent()) {
            specification = specification.and(new ProductSpecification("priceTo", priceTo.get()));
        }
        // Поиск по id катероии
        if (categoryId.isPresent()) {
            specification = specification.and(new ProductSpecification("categoryId", categoryId.get()));
        }
        // Формируем результат поиска
        searchResult.setProducts(
                productRepository.findAll(specification, PageRequest.of(pageNumber, pageSize)).stream()
                .map(this::convertToFullDto)
                .collect(Collectors.toList()));
        searchResult.setPageNumber(pageNumber);
        searchResult.setPageSize(pageSize);
        searchResult.setTotalProducts(productRepository.findAll(specification).size());

        return searchResult;
    }
    @Override
    public void delete(Long id) {
        productRepository.deleteById(id);
    }

    @PostConstruct
    private void configureMapper() {
        modelMapper.createTypeMap(Product.class, ProductDto.Response.All.class)
                .addMappings(mapper -> {
                    mapper.map(src -> src.getCategory().getId(), ProductDto.Response.All::setCategoryId);
                    mapper.map(src -> src.getCategory().getName(), ProductDto.Response.All::setCategoryName);
                    mapper.map(src -> src.getStatus().getId(), ProductDto.Response.All::setStatusId);
                    mapper.map(src -> src.getStatus().getDescription(), ProductDto.Response.All::setStatusDescription);
                });

        modelMapper.createTypeMap(ProductDto.Response.All.class, Product.class)
                .addMappings(mapper -> {
                    mapper.<Long>map(ProductDto.Response.All::getCategoryId, (target, v) -> target.getCategory().setId(v));
                    mapper.<String>map(ProductDto.Response.All::getCategoryName, (target, v) -> target.getCategory().setName(v));
                    mapper.<Long>map(ProductDto.Response.All::getStatusId, (target, v) -> target.getStatus().setId(v));
                    mapper.<String>map(ProductDto.Response.All::getStatusDescription, (target, v) -> target.getStatus().setDescription(v));
                });
    }

    private ProductDto.Response.All convertToFullDto(Product product) {
        return modelMapper.map(product, ProductDto.Response.All.class);
    }
}
