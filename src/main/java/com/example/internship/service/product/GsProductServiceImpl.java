package com.example.internship.service.product;

import com.example.internship.dto.product.ProductDto;
import com.example.internship.entity.Product;
import com.example.internship.repository.ProductRepository;
import com.example.internship.service.GsProductService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author Самохвалов Юрий Алексеевич
 */
@Service
@RequiredArgsConstructor
public class GsProductServiceImpl implements GsProductService {

    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<ProductDto.Response.AllWithCategoryId> findAll() {
        return productRepository.findAll().stream()
                .map(this::convertToAllWithCategoryId)
                .collect(Collectors.toUnmodifiableList());
    }

    @Override
    public List<ProductDto.Response.AllWithCategoryId> findAllByCategoryId(long categoryId) {
        return productRepository.findAllByCategoryId(categoryId).stream()
                .map(this::convertToAllWithCategoryId)
                .collect(Collectors.toUnmodifiableList());
    }

    @Override
    public List<ProductDto.Response.Ids> findAllIdByCategoryId(long categoryId) {
        return productRepository.findAllByCategoryId(categoryId).stream()
                .map(this::convertToIds)
                .collect(Collectors.toUnmodifiableList());
    }

    @Override
    public Optional<ProductDto.Response.AllWithCategoryId> findById(long id) {
        Optional<Product> product = productRepository.findById(id);
        return product.map(this::convertToAllWithCategoryId);
    }

    @Override
    public void save(ProductDto.Request.AllWithCategoryId productDto) {
        productRepository.save(convertToEntity(productDto));
    }

    @Override
    public void delete(long id) {
        productRepository.deleteById(id);
    }

    private ProductDto.Response.All convertToAll(Product product) {
        return modelMapper.map(product, ProductDto.Response.All.class);
    }

    private ProductDto.Response.AllWithCategoryId convertToAllWithCategoryId(Product product) {
        return modelMapper.map(product, ProductDto.Response.AllWithCategoryId.class);
    }

    private ProductDto.Response.Ids convertToIds(Product product) {
        return modelMapper.map(product, ProductDto.Response.Ids.class);
    }

    private Product convertToEntity(ProductDto.Request.AllWithCategoryId dto) {
        return modelMapper.map(dto, Product.class);
    }

    @PostConstruct
    private void configureMapper() {
        modelMapper
                .createTypeMap(Product.class, ProductDto.Response.AllWithCategoryId.class)
                .addMappings(mapper -> {
                    // подразумевается, что categoryId у продукта не может быть null
                    mapper.map(src -> src.getCategory().getId(), ProductDto.Response.AllWithCategoryId::setCategoryId);
                });
        modelMapper
                .createTypeMap(Product.class, ProductDto.Response.Ids.class)
                .addMappings(mapper -> {
                    mapper.map(product -> product.getId(), ProductDto.Response.Ids::setId);
                });
    }
}
