package com.example.internship.service;

import com.example.internship.dto.product.ProductDto;

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
    void save(ProductDto.Request.AllWithCategoryId productDto);
    void delete(long id);
}
