package com.example.adminapplication.service.impl;

import com.example.adminapplication.dto.ProductDto;
import com.example.adminapplication.dto.ProductSearchResult;
import com.example.adminapplication.service.ProductService;
import com.example.internship.client.api.ProductRestControllerApi;
import com.example.internship.client.model.Product;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * @author Ivan Gubanov
 */
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ModelMapper modelMapper;
    private final ProductRestControllerApi productApi;


    @Override
    public void removeProduct(Long id) {
        productApi.removeProduct(id).block();
    }

    @Override
    public void saveProduct(ProductDto product) {
        productApi.saveProduct(convertToModel(product)).block();
    }

    @Override
    public ProductDto findByIdProduct(Long id) {
        return productApi.findById(id).map(this::convertToDto).block();
    }

    @Override
    public ProductSearchResult productSearch(String searchText, Long categoryId, BigDecimal priceFrom,
                                             BigDecimal priceTo, Integer pageSize, Integer pageNumber) {

        ProductSearchResult result = convertSearchResult(productApi.productSearch(
                (null == searchText || searchText.isBlank()) ? null : searchText,
                (null == categoryId || categoryId < 1) ? null : categoryId,
                (null == priceFrom || priceFrom.compareTo(BigDecimal.ONE) < 0) ? null : priceFrom,
                (null == priceTo || priceTo.compareTo(BigDecimal.ONE) < 0) ? null : priceTo,
                (null == pageSize || pageSize < 1) ? null : pageSize,
                (null == pageNumber || pageNumber < 1) ? null : pageNumber).block());

        // Create pagination
        if (null != result) {
            if (result.getTotalProducts() / result.getPageSize() > 0) {
                result.setTotalPages((long) Math.ceil((float) result.getTotalProducts() / result.getPageSize()));
            } else {
                result.setTotalPages(0L);
            }
        }

        return result;
    }

    private ProductDto convertToDto(Product product) {
        return modelMapper.map(product, ProductDto.class);
    }

    private Product convertToModel(ProductDto productDto) {
        return modelMapper.map(productDto, Product.class);
    }

    private ProductSearchResult convertSearchResult(com.example.internship.client.model.ProductSearchResult result) {
        return modelMapper.map(result, ProductSearchResult.class);
    }
}
