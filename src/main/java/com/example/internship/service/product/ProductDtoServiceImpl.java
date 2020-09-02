package com.example.internship.service.product;

import com.example.internship.dto.ProductSearchResult;
import com.example.internship.dto.product.ProductDto;
import com.example.internship.entity.Product;
import com.example.internship.repository.ProductRepository;
import com.example.internship.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author Мурашов Алексей
 */
@Service
@Qualifier("productDtoServiceImpl")
@RequiredArgsConstructor
public class ProductDtoServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<com.example.internship.dto.ProductDto> findAll() {
        return Collections.emptyList();
    }

    @Override
    public void removeProduct(Long id) {

    }

    @Override
    public void addProduct(com.example.internship.dto.ProductDto productDto) {

    }

    @Override
    public List<com.example.internship.dto.ProductDto> findByName(String name) {
        return Collections.emptyList();
    }

    @Override
    public List<ProductDto.Response.Full> getAll() {
        return productRepository.findAll().stream()
                .map(this::convertToFullDto).collect(Collectors.toUnmodifiableList());
    }

    @Override
    public void delete(Long id) {
        productRepository.deleteById(id);
    }

    @Override
    public void save(ProductDto.Response.Full productDto) {
        productRepository.save(modelMapper.map(productDto, Product.class));
    }

    @Override
    public List<ProductDto.Response.Full> getByName(String name) {
        return productRepository.findByNameContainsIgnoreCase(name).stream()
                .map(this::convertToFullDto).collect(Collectors.toUnmodifiableList());
    }

    @Override
    public Optional<ProductDto.Response.Full> getById(Long id) {
        Optional<Product> product = productRepository.findById(id);
        return product.map(this::convertToFullDto);
    }

    @Override
    public com.example.internship.dto.ProductDto getProductById(Long id) {
        return new com.example.internship.dto.ProductDto();
    }

    @Override
    public Optional<com.example.internship.dto.ProductDto> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public ProductSearchResult search(Optional<String> name, Optional<Long> categoryId, Optional<BigDecimal> priceFrom, Optional<BigDecimal> priceTo, Integer pageSize, Integer pageNumber) {
        return new ProductSearchResult();
    }

    @PostConstruct
    private void configureMapper() {
        modelMapper.createTypeMap(Product.class, ProductDto.Response.Full.class)
                .addMappings(mapper -> {
                    mapper.map(src -> src.getCategory().getId(), ProductDto.Response.Full::setCategoryId);
                    mapper.map(src -> src.getCategory().getName(), ProductDto.Response.Full::setCategoryName);
                    mapper.map(src -> src.getStatus().getId(), ProductDto.Response.Full::setStatusId);
                    mapper.map(src -> src.getStatus().getDescription(), ProductDto.Response.Full::setStatusDescription);
                });

        modelMapper.createTypeMap(ProductDto.Response.Full.class, Product.class)
                .addMappings(mapper -> {
                    mapper.<Long>map(ProductDto.Response.Full::getCategoryId, (target, v) -> target.getCategory().setId(v));
                    mapper.<String>map(ProductDto.Response.Full::getCategoryName, (target, v) -> target.getCategory().setName(v));
                    mapper.<Long>map(ProductDto.Response.Full::getStatusId, (target, v) -> target.getStatus().setId(v));
                    mapper.<String>map(ProductDto.Response.Full::getStatusDescription, (target, v) -> target.getStatus().setDescription(v));
                });
    }

    private ProductDto.Response.Full convertToFullDto(Product product) {
        return modelMapper.map(product, ProductDto.Response.Full.class);
    }
}
