package com.example.internship.service.impl;

import com.example.internship.dto.ProductDto;
import com.example.internship.dto.ProductSearchResult;
import com.example.internship.entity.Product;
import com.example.internship.repository.ProductRepository;
import com.example.internship.service.ProductService;
import com.example.internship.specification.ProductSpecification;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepo;

    private final ModelMapper mapper;

    @Override
    public List<ProductDto> findAll() {
        return productRepo.findAll(Sort.by("id")).stream().map(this::convertToDto).collect(Collectors.toList());
    }

    @Override
    public void removeProduct(Long id) {
        if (productRepo.existsById(id)) productRepo.deleteById(id);
    }

    @Override
    public void addProduct(ProductDto productDto) {
        productRepo.save(convertToModel(productDto));
    }

    @Override
    public Product saveProduct(Product product) {

        return productRepo.save(product);
    }

    @Override
    public List<ProductDto> findByName(String name) {
        return productRepo.findByNameContainsIgnoreCase(name)
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public Product findByIdProduct(Long id) {
        return productRepo.findById(id).orElseThrow();
    }

    @Override
    public void removeAll() {
        productRepo.deleteAll();
    }

    @Override
    public ProductDto getProductById(Long id) {
        return convertToDto(productRepo.findById(id).get());
    }

    @Override
    // Продукт по id
    public Optional<ProductDto> findById(Long id) {
        if (productRepo.findById(id).isPresent()) {
            return Optional.ofNullable(convertToDto(productRepo.findById(id).get()));
        }
        return Optional.empty();
    }

    @Override
    public ProductSearchResult search(String name, Long categoryId, BigDecimal priceFrom, BigDecimal priceTo,
                                      Integer pageSize, Integer pageNumber) {

        ProductSearchResult searchResult = new ProductSearchResult();

        // Формируем условия для запроса к БД
        if (name == null) {
            name = "";
        }
        Specification<Product> specification = Specification.where(
                // Поиск по имени
                new ProductSpecification("name", name));
        // Поиск по цене ОТ
        if (priceFrom != null) {
            specification = specification.and(new ProductSpecification("priceFrom", priceFrom));
        }
        // Поиск по цене ДО
        if (priceTo != null) {
            specification = specification.and(new ProductSpecification("priceTo", priceTo));
        }
        // Поиск по id катероии
        if (categoryId != null) {
            specification = specification.and(new ProductSpecification("categoryId", categoryId));
        }
        if (pageNumber == null) {
            pageNumber = 0;
        }
        if (pageSize == null) {
            pageSize = 20;
        }
        // Формируем результат поиска
        searchResult.setProducts(productRepo.findAll(specification, PageRequest.of(pageNumber, pageSize, Sort.by("id")))
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList()));
        searchResult.setPageNumber(pageNumber);
        searchResult.setPageSize(pageSize);
        searchResult.setTotalProducts(productRepo.count(specification));

        return searchResult;
    }

    private ProductDto convertToDto(Product product) {
        return mapper.map(product, ProductDto.class);
    }

    private Product convertToModel(ProductDto productDto) {
        return mapper.map(productDto, Product.class);
    }

}
