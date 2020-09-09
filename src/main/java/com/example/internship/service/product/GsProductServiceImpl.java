package com.example.internship.service.product;

import com.example.internship.dto.product.ProductDto;
import com.example.internship.dto.product.ProductDto.Response.AllWithCategoryId;
import com.example.internship.entity.Product;
import com.example.internship.helper.SpecificationHelper;
import com.example.internship.repository.ProductRepository;
import com.example.internship.service.GsCategoryService;
import com.example.internship.service.GsProductService;
import com.example.internship.specification.GsProductSpecification;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.util.LinkedList;
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
    private final GsCategoryService categoryService;
    private final ModelMapper modelMapper;

    @Override
    public List<AllWithCategoryId> findAll() {
        return productRepository.findAll().stream()
                .map(this::convertToAllWithCategoryId)
                .collect(Collectors.toUnmodifiableList());
    }

    @Override
    public List<AllWithCategoryId> findAllByCategoryId(long categoryId) {
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
    public Optional<AllWithCategoryId> findById(long id) {
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

    @Override
    public ProductDto.Response.SearchResult findByCriteria(
            String nameLike,
            Long categoryId,
            BigDecimal minimalPrice,
            BigDecimal maximalPrice,
            Integer pageNumber,
            Integer pageSize,
            Boolean descendingOrder
    ) {
        List<Specification<Product>> specifications = new LinkedList<>();
        if (null != nameLike && !nameLike.isBlank()) {
            specifications.add(GsProductSpecification.withNameLike(nameLike));
        }
        if (null != categoryId) {
            final List<Long> ids = categoryService.findDescendants(categoryId);
            specifications.add(GsProductSpecification.ofCategories(ids));
        }

        final Specification<Product> combinedSpecifications = SpecificationHelper.combineAll(specifications);
        final List<AllWithCategoryId> filteredProducts = productRepository.findAll(combinedSpecifications).stream()
                .map(this::convertToAllWithCategoryId)
                .collect(Collectors.toUnmodifiableList());

        ProductDto.Response.SearchResult result = new ProductDto.Response.SearchResult();
        result.setProducts(filteredProducts);
        result.setPageNumber(pageNumber);
        result.setPageSize(pageSize);
        result.setTotal(filteredProducts.size());
        return result;
    }

    private AllWithCategoryId convertToAllWithCategoryId(Product product) {
        return modelMapper.map(product, AllWithCategoryId.class);
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
                .createTypeMap(Product.class, AllWithCategoryId.class)
                .addMappings(mapper -> {
                    // подразумевается, что categoryId у продукта не может быть null
                    mapper.map(src -> src.getCategory().getId(), AllWithCategoryId::setCategoryId);
                });
        modelMapper
                .createTypeMap(Product.class, ProductDto.Response.Ids.class)
                .addMappings(mapper -> mapper.map(Product::getId, ProductDto.Response.Ids::setId));
    }
}
