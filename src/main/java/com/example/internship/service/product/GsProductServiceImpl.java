package com.example.internship.service.product;

import com.example.internship.dto.product.ProductDto;
import com.example.internship.entity.Category;
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
    public List<ProductDto.Response.AllWithCategoryId> findAllByCategoryId(long categoryId) {
        return productRepository.findAll().stream()
                .map(this::convertToAllWithCategoryId)
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

    private ProductDto.Response.AllWithCategoryId convertToAllWithCategoryId(Product product) {
        return modelMapper.map(product, ProductDto.Response.AllWithCategoryId.class);
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
    }
}
