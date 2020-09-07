package com.example.adminapplication.service.impl;

import com.example.adminapplication.dto.ProductStatusDto;
import com.example.adminapplication.service.ProductStatusService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * @author Ivan Gubanov
 */
@Service
@AllArgsConstructor
public class ProductStatusServiceImpl implements ProductStatusService {

    private final RestTemplate restTemplate;

    @Value("${resttemplate.url}")
    private final String url;

    private String url() {
        return url + "/product-status";
    }

    @Override
    public List<ProductStatusDto> findAll() throws ResourceAccessException {
        return restTemplate.postForObject(url() + "/find-all", null, List.class);
    }
}
