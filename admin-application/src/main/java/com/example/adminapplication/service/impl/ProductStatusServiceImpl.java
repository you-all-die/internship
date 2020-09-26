package com.example.adminapplication.service.impl;

import com.example.adminapplication.dto.ProductStatusDto;
import com.example.adminapplication.service.ProductStatusService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

/**
 * @author Ivan Gubanov
 */
@Service
@RequiredArgsConstructor
public class ProductStatusServiceImpl implements ProductStatusService {

    private final WebClient webClient;

    @Override
    public List<ProductStatusDto> findAll() {

        return webClient.get()
                .uri("/product-status/find-all")
                .retrieve()
                .bodyToFlux(ProductStatusDto.class)
                .collectList()
                .block();
    }
}
