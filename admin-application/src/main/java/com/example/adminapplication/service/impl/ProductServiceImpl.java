package com.example.adminapplication.service.impl;

import com.example.adminapplication.dto.ProductDto;
import com.example.adminapplication.dto.ProductSearchResult;
import com.example.adminapplication.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.math.BigDecimal;

/**
 * @author Ivan Gubanov
 */
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final WebClient webClient;

    private String uri = "/product";

    @Override
    public void removeProduct(Long id) {

        webClient.post()
                .uri(uri + "/remove-product")
                .bodyValue(id)
                .retrieve()
                .toBodilessEntity()
                .block();
    }

    @Override
    public ProductDto saveProduct(ProductDto product) {

        return webClient.post()
                .uri(uri + "/save-product")
                .bodyValue(product)
                .retrieve()
                .bodyToMono(ProductDto.class)
                .block();
    }

    @Override
    public ProductDto findByIdProduct(Long id) {

        return webClient.post()
                .uri(uri + "/find-by-id")
                .bodyValue(id)
                .retrieve()
                .bodyToMono(ProductDto.class)
                .block();
    }

    @Override
    public ProductSearchResult productSearch(String searchText, Long categoryId, BigDecimal priceFrom,
                                             BigDecimal priceTo, Integer pageSize, Integer pageNumber) {

        StringBuilder uri = new StringBuilder(this.uri).append("/search?");

        if (StringUtils.isNotBlank(searchText)) {
            uri.append("searchText=").append(searchText).append("&");
        }
        if (categoryId != null && categoryId > 0) {
            uri.append("categoryId=").append(categoryId).append("&");
        }
        if (priceFrom != null && priceFrom.compareTo(BigDecimal.ZERO) > 0) {
            uri.append("priceFrom=").append(priceFrom).append("&");
        }
        if (priceTo != null && priceTo.compareTo(BigDecimal.ZERO) > 0) {
            uri.append("priceTo=").append(priceTo).append("&");
        }
        if (pageSize != null && pageSize > 0) {
            uri.append("pageSize=").append(pageSize).append("&");
        }
        if (pageNumber != null && pageNumber > 0) {
            uri.append("pageNumber=").append(pageNumber - 1).append("&");
        }
        ProductSearchResult result = webClient.get()
                .uri(uri.toString())
                .retrieve()
                .bodyToMono(ProductSearchResult.class)
                .block();

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
