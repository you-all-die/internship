package com.example.internship.service.product;

import com.example.internship.dto.product.ProductDto;
import com.example.internship.entity.Product;
import com.example.internship.repository.ProductRepository;
import com.example.internship.service.GsCategoryService;
import com.example.internship.service.GsProductService;
import com.example.internship.specification.GsProductSpecification;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
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
    private final EntityManager entityManager;

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
        final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        final CriteriaQuery<Product> query = builder.createQuery(Product.class);
        final Root<Product> root = query.from(Product.class);

        final List<Predicate> predicates = new LinkedList<>();
        if (null != nameLike && !nameLike.isBlank()) {
            predicates.add(builder.like(builder.lower(root.get("name")), getLikePattern(nameLike)));
        }
        if (null != categoryId) {
            final List<Long> categoryIds = new LinkedList<>();
            categoryIds.add(categoryId);
            categoryIds.addAll(categoryService.findDescendants(categoryId));
            predicates.add(builder.in(root.get("category").get("id")).value(categoryIds));
        }
        /* TODO сначала надо согласовать с UI, так как там границы цен условны, надо преобразовывать */
/*
        if (null != minimalPrice) {
            predicates.add(builder.greaterThanOrEqualTo(root.get("price").as(BigDecimal.class), minimalPrice));
        }
        if (null != maximalPrice) {
            predicates.add(builder.lessThanOrEqualTo(root.get("price").as(BigDecimal.class), maximalPrice));
        }
*/

        query.select(root).where(predicates.toArray(new Predicate[]{}));
        final TypedQuery<Product> typedQuery = entityManager.createQuery(query);
        final List<ProductDto.Response.AllWithCategoryId> products = typedQuery
                .getResultStream()
                .map(this::convertToAllWithCategoryId)
                .collect(Collectors.toUnmodifiableList());

        ProductDto.Response.SearchResult result = new ProductDto.Response.SearchResult();

        result.setProducts(products);
        result.setPageNumber(pageNumber);
        result.setPageSize(pageSize);
        result.setTotal(products.size());
        return result;
    }

    private static String getLikePattern(String searchString) {
        if (null == searchString || searchString.isEmpty()) {
            return "%";
        } else {
            return "%" + searchString.toLowerCase() + "%";
        }
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
                .addMappings(mapper -> mapper.map(Product::getId, ProductDto.Response.Ids::setId));
    }
}
