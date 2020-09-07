package com.example.adminapplication.service.impl;

import com.example.adminapplication.dto.ProductDto;
import com.example.adminapplication.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

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
}
