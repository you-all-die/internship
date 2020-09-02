package com.example.internship.service.impl;

import com.example.internship.dto.ProductDto;
import com.example.internship.entity.Product;
import com.example.internship.repository.ProductRepository;
import com.example.internship.service.ProductService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Qualifier("productServiceImpl")
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
    public List<com.example.internship.dto.product.ProductDto.Response.Full> getAll() {
        return Collections.emptyList();
    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public void save(com.example.internship.dto.product.ProductDto.Response.Full productDto) {

    }

    @Override
    public List<com.example.internship.dto.product.ProductDto.Response.Full> getByName(String name) {
        return Collections.emptyList();
    }

    @Override
    public Optional<com.example.internship.dto.product.ProductDto.Response.Full> getById(Long id) {
        return Optional.empty();
    }

    private ProductDto convertToDto(Product product) {
        return mapper.map(product, ProductDto.class);
    }

    private Product convertToModel(ProductDto productDto) {
        return mapper.map(productDto, Product.class);
    }
}
