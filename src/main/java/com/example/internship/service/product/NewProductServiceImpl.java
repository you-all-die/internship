package com.example.internship.service.product;

import com.example.internship.dto.product.ProductDto;
import com.example.internship.entity.Product;
import com.example.internship.repository.ProductRepository;
import com.example.internship.service.NewProductService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NewProductServiceImpl implements NewProductService {
    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<ProductDto.Response.Full> findAll() {
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
    public List<ProductDto.Response.Full> findByName(String name) {
        return productRepository.findByNameContainsIgnoreCase(name).stream()
                .map(this::convertToFullDto).collect(Collectors.toUnmodifiableList());
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
