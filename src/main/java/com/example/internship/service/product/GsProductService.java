package com.example.internship.service.product;

import com.example.internship.dto.product.ProductDto;
import com.example.internship.dto.product.SearchResult;
import com.example.internship.entity.Product;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

/**
 * @author Самохвалов Юрий Алексеевич
 */
public interface GsProductService {
    List<ProductDto.Response.AllWithCategoryId> findAll();
    List<ProductDto.Response.AllWithCategoryId> findAllByCategoryId(long categoryId);
    List<ProductDto.Response.Ids> findAllIdByCategoryId(long categoryId);
    Optional<ProductDto.Response.AllWithCategoryId> findById(long id);
    Optional<BigDecimal> findMinimalPrice();
    Optional<BigDecimal> findMaximalPrice();
    void save(Product product);
    Product save(ProductDto.Response.AllWithCategoryId productDto);
    void delete(long id);
    long count();
    SearchResult findByCriteria(
            String nameLike,
            Long categoryId,
            BigDecimal lowerPriceLimit,
            BigDecimal upperPriceLimit,
            Integer pageNumber,
            Integer pageSize,
            Boolean descendingOrder);
    void deleteAll();
}
