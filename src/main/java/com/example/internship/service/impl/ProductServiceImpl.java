package com.example.internship.service.impl;

import com.example.internship.dto.ProductDto;
import com.example.internship.entity.Product;
import com.example.internship.repository.ProductRepository;
import com.example.internship.service.ProductService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service

@RequiredArgsConstructor
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
    public ProductDto getProductById(Long id) {

        return convertToDto(productRepo.findById(id).get());
    }

    private ProductDto convertToDto(Product product) {
        return mapper.map(product, ProductDto.class);
    }

    private Product convertToModel(ProductDto productDto) {
        return mapper.map(productDto, Product.class);
    }

}
