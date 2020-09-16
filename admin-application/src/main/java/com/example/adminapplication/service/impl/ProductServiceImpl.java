package com.example.adminapplication.service.impl;

import com.example.adminapplication.dto.ProductDto;
import com.example.adminapplication.dto.ProductSearchResult;
import com.example.adminapplication.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author Ivan Gubanov
 */
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final RestTemplate restTemplate;

    @Value("${resttemplate.url}")
    private String url;

    private String url() {
        return url + "/product";
    }

    @Override
    public List<ProductDto> findAll() throws ResourceAccessException {
        return restTemplate.postForObject(url() + "/find-all", null, List.class);
    }

    @Override
    public void removeProduct(Long id) throws ResourceAccessException {
        restTemplate.postForObject(url() + "/remove-product", id, String.class);
    }

    @Override
    public void saveProduct(ProductDto product) throws ResourceAccessException {
        restTemplate.postForObject(url() + "/save-product", product, String.class);
    }

    @Override
    public List<ProductDto> findByName(String name) throws ResourceAccessException {
        return restTemplate.postForObject(url() + "/find-by-name", name, List.class);
    }

    @Override
    public ProductDto findByIdProduct(Long id) throws ResourceAccessException {
        return restTemplate.postForObject(url() + "/find-by-id", id, ProductDto.class);
    }

    @Override
    public ProductSearchResult productSearch(String searchText, Long categoryId, BigDecimal priceFrom,
                                             BigDecimal priceTo, Integer pageSize, Integer pageNumber)
            throws ResourceAccessException {

        StringBuilder url = new StringBuilder(url()).append("/search?");
        if (StringUtils.isNotBlank(searchText)) {
            url.append("searchText=").append(searchText).append("&");
        }
        if (categoryId != null && categoryId > 0) {
            url.append("categoryId=").append(categoryId).append("&");
        }
        if (priceFrom != null && priceFrom.compareTo(BigDecimal.ZERO) > 0) {
            url.append("priceFrom=").append(priceFrom).append("&");
        }
        if (priceTo != null && priceTo.compareTo(BigDecimal.ZERO) > 0) {
            url.append("priceTo=").append(priceTo).append("&");
        }
        if (pageSize != null && pageSize > 0) {
            url.append("pageSize=").append(pageSize).append("&");
        }
        if (pageNumber != null && pageNumber > 0) {
            url.append("pageNumber=").append(pageNumber - 1).append("&");
        }
        ProductSearchResult result = restTemplate.getForObject(url.toString(), ProductSearchResult.class);
        // Create pagination
        if (result != null && result.getTotalProducts() / result.getPageSize() > 0) {

            result.setTotalPages((long) Math.ceil((float) result.getTotalProducts() / result.getPageSize()));

            if (result.getPageNumber() != 0) {

                result.setPrevPage(result.getPageNumber());

                if (result.getPrevPage() > 1) {
                    result.setFirstPage(1);
                }
            }

            result.setPageNumber(result.getPageNumber() + 1);

            if (result.getPageNumber() < result.getTotalPages()) {

                result.setNextPage(result.getPageNumber() + 1);

                if (result.getNextPage() < result.getTotalPages()) {
                    result.setLastPage(result.getTotalPages().intValue());
                }
            }
        }

        return result;
    }
}
