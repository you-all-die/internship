package com.example.internship.service.product;

import com.example.internship.dto.category.CategoryDto;
import com.example.internship.dto.product.ProductDto;
import com.example.internship.dto.product.ProductDto.Response.AllWithCategoryId;
import com.example.internship.dto.product.SearchResult;
import com.example.internship.entity.Category;
import com.example.internship.entity.Product;
import com.example.internship.helper.PageHelper;
import com.example.internship.repository.ProductRepository;
import com.example.internship.service.category.GsCategoryService;
import com.example.internship.specification.product.ProductSpecification;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.spi.MappingContext;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
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
    public Optional<AllWithCategoryId> findById(long id) {
        Optional<Product> product = productRepository.findById(id);
        return product.map(this::convertToAllWithCategoryId);
    }

    @Override
    public Optional<BigDecimal> findMinimalPrice() {
        return productRepository.findMinimalPrice();
    }

    @Override
    public Optional<BigDecimal> findMaximalPrice() {
        return productRepository.findMaximalPrice();
    }

    @Override
    public Product save(ProductDto.Request.All productDto) {
        Product productEntity = modelMapper.map(productDto, Product.class);
        return productRepository.save(productEntity);
    }

    @Override
    public void delete(long id) {
        productRepository.deleteById(id);
    }

    @Override
    public long count() {
        return productRepository.count();
    }

    @Override
    public SearchResult findByCriteria(
            String nameLike,
            Long categoryId,
            BigDecimal lowerPriceLimit,
            BigDecimal upperPriceLimit,
            Integer pageNumber,
            Integer pageSize,
            Boolean descendingOrder
    ) {
        List<Long> categoryIds = categoryService.findDescendants(categoryId);

        final BigDecimal minimalPrice = productRepository.findMinimalPrice().orElse(BigDecimal.ZERO);
        final BigDecimal maximalPrice = productRepository.findMaximalPrice().orElse(BigDecimal.ZERO);
        lowerPriceLimit = (null == lowerPriceLimit) ? minimalPrice : lowerPriceLimit;
        upperPriceLimit = (null == upperPriceLimit || upperPriceLimit.equals(BigDecimal.ZERO)) ? maximalPrice : upperPriceLimit;

        final Specification<Product> specification = new ProductSpecification.Builder()
                .nameLike(nameLike)
                .ofCategories(categoryIds)
                .priceBetween(lowerPriceLimit, upperPriceLimit)
                .build();

        final long totalProducts = productRepository.count(specification);

        final Sort.Direction direction = (null != descendingOrder && descendingOrder) ? Sort.Direction.DESC : Sort.Direction.ASC;
        final Sort sortOrder = Sort.by(direction, "price");

        if (null == pageNumber) {
            pageNumber = 0;
        }
        if (null == pageSize) {
            pageSize = 20;
        }
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sortOrder);

        final List<CategoryDto.Response.AllWithParentSubcategories> topCategories = categoryService.findTopCategories();

        final List<CategoryDto.Response.All> ancestors = categoryService.findAncestors(categoryId);

        final Integer totalPages = PageHelper.calculateTotalPages((int) totalProducts, pageSize);

        final List<AllWithCategoryId> filteredProducts = productRepository
                .findAll(specification, pageable)
                .stream()
                .map(this::convertToAllWithCategoryId)
                .collect(Collectors.toUnmodifiableList());

        return SearchResult.builder()
                .categoryId(categoryId)
                .products(filteredProducts)
                .topCategories(topCategories)
                .breadcrumbs(ancestors)
                .pageNumber(pageNumber)
                .totalPages(totalPages)
                .pageSize(pageSize)
                .totalProducts(totalProducts)
                .minimalPrice(minimalPrice)
                .maximalPrice(maximalPrice)
                .lowerPriceLimit(lowerPriceLimit)
                .upperPriceLimit(upperPriceLimit)
                .descendingOrder(descendingOrder)
                .build();
    }

    @Override
    public void deleteAll() {
        productRepository.deleteAll();
    }

    @PostConstruct
    private void configureMapper() {
        modelMapper
                .createTypeMap(Product.class, AllWithCategoryId.class)
                .addMappings(mapper -> mapper.map(entity -> entity.getCategory().getId(), AllWithCategoryId::setCategoryId));
        modelMapper
                .createTypeMap(AllWithCategoryId.class, Product.class)
                .addMappings(mapper -> mapper.map(dto -> categoryService.findById(dto.getCategoryId()), Product::setCategory));
        modelMapper
                .createTypeMap(ProductDto.Request.All.class, Product.class)
                .addMappings(mapper -> mapper.skip(Product::setCategory))
                .setPostConverter(this::convertRequestAllToEntity);
    }

    /* конвертер */
    private AllWithCategoryId convertToAllWithCategoryId(Product product) {
        return modelMapper.map(product, AllWithCategoryId.class);
    }

    /* конвертер */
    private Product convertRequestAllToEntity(MappingContext<ProductDto.Request.All, Product> context) {
        Long categoryId = context.getSource().getCategoryId();
        Product product = context.getDestination();
        if (categoryId == null) {
            product.setCategory(null);
        } else {
            final Optional<Category> categoryOptional = categoryService.findById(categoryId);
            product.setCategory(categoryOptional.orElse(null));
        }
        return product;
    }
}
