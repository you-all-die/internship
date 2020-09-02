package com.example.internship.service.impl;

import com.example.internship.dto.ProductDto;
import com.example.internship.entity.Product;
import com.example.internship.repository.ProductRepository;
import com.example.internship.service.ProductService;
import com.example.internship.specification.ProductSpecification;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
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
        return productRepo.findAll().stream().map(this::convertToDto).collect(Collectors.toList());
    }

    @Override
    public void removeProduct(Long id) {
        productRepo.deleteById(id);
    }

    @Override
    public void addProduct(ProductDto productDto) {
        productRepo.save(convertToModel(productDto));
    }

    @Override
    public List<ProductDto> findByName(String name) {
        return productRepo.findByNameContainsIgnoreCase(name)
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    // Продукт по id
    public ProductDto findById(Long id) {
        return convertToDto(productRepo.findById(id).orElse(null));
    }

    @Override
    public List<ProductDto> search(Optional<String> name, Optional<Long> categoryId,
                                   Optional<BigDecimal> priceFrom, Optional<BigDecimal> priceTo,
                                   Integer pageSize, Integer pageNumber) {
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

        return productRepo.findAll(specification, PageRequest.of(pageNumber, pageSize)).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    private ProductDto convertToDto(Product product) {
        return mapper.map(product, ProductDto.class);
    }

    private Product convertToModel(ProductDto productDto) {
        return mapper.map(productDto, Product.class);
    }
}
