package com.example.adminapplication.service.impl;

import com.example.adminapplication.dto.ProductStatusDto;
import com.example.adminapplication.properties.RestTemplateProperties;
import com.example.adminapplication.service.ProductStatusService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * @author Ivan Gubanov
 */
@Service
@AllArgsConstructor
public class ProductStatusServiceImpl implements ProductStatusService {

    private final RestTemplate restTemplate;
    private final RestTemplateProperties restTemplateProperties;

    private String url() {
        return restTemplateProperties.getUrl() + "/product-status";
    }

    @Override
    public List<ProductStatusDto> findAll() {
        return restTemplate.postForObject(url() + "/find-all", null, List.class);
    }
}
