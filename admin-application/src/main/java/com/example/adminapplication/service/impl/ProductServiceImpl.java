package com.example.adminapplication.service.impl;

import com.example.adminapplication.dto.ProductDto;
import com.example.adminapplication.properties.RestTemplateProperties;
import com.example.adminapplication.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * @author Ivan Gubanov
 */
@Service
@AllArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final RestTemplate restTemplate;
    private final RestTemplateProperties restTemplateProperties;

    private String url() {
        return restTemplateProperties.getUrl() + "/product";
    }

    @Override
    public List<ProductDto> findAll() {
        return restTemplate.postForObject(url() + "/find-all", null, List.class);
    }

    @Override
    public void removeProduct(Long id) {
        restTemplate.postForObject(url() + "/remove-product", id, String.class);
    }

    @Override
    public void saveProduct(ProductDto product) {
        restTemplate.postForObject(url() + "/save-product", product, String.class);
    }

    @Override
    public List<ProductDto> findByName(String name) {
        return restTemplate.postForObject(url() + "/find-by-name", name, List.class);
    }

    @Override
    public ProductDto findByIdProduct(Long id) {
        return restTemplate.postForObject(url() + "/find-by-id", id, ProductDto.class);
    }
}
