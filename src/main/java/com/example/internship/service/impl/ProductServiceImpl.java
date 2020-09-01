package com.example.internship.service.impl;

import com.example.internship.dto.ProductDto;
import com.example.internship.entity.Product;
import com.example.internship.repository.ProductRepository;
import com.example.internship.service.ProductService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
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
    // Продукт по id его категории
    public List<ProductDto> findByCategoryId(Long categoryId) {
        return productRepo.findByCategoryId(categoryId)
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    // Продукт по названию и id его категории
    public List<ProductDto> findByNameAndAndCategoryId(String name, Long categoryId) {
        return productRepo.findByNameContainsIgnoreCaseAndAndCategoryId(name, categoryId)
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    // Возвращает продукты только с ценой ОТ и ДО
    public List<ProductDto> filterByPrice(List<ProductDto> products, BigDecimal priceFrom, BigDecimal priceTo) {
        if (products != null) {
            return products.stream()
                    .filter(p -> p.getPrice().compareTo(priceFrom) >= 0 && p.getPrice().compareTo(priceTo) <= 0)
                    .collect(Collectors.toList());
        }

        return null;
    }

    @Override
    // Возвращает продукты только с ценой ОТ
    public List<ProductDto> filterByPrice(List<ProductDto> products, BigDecimal priceFrom) {
        if (products != null) {
            return products.stream()
                    .filter(p -> p.getPrice().compareTo(priceFrom) >= 0)
                    .collect(Collectors.toList());
        }

        return null;
    }


    private ProductDto convertToDto(Product product) {
        return mapper.map(product, ProductDto.class);
    }

    private Product convertToModel(ProductDto productDto) {
        return mapper.map(productDto, Product.class);
    }
}
