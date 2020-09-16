package com.example.adminapplication.service.impl;

import com.example.adminapplication.dto.CategoryDto;
import com.example.adminapplication.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientException;

import java.util.List;

/**
 * @author Ivan Gubanov
 */
@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final WebClient webClient;

    private String uri = "/category";

    @Override
    public List<CategoryDto> findAll() {

        return webClient.post()
                .uri(uri + "/find-all")
                .retrieve()
                .bodyToMono(List.class)
                .block();
    }

    @Override
    public CategoryDto findById(Long id) {

        return webClient.post()
                .uri(uri + "/find-by-id")
                .bodyValue(id)
                .retrieve()
                .bodyToMono(CategoryDto.class)
                .block();
    }

    @Override
    public List<CategoryDto> findAllSortById() {

        return webClient.post()
                .uri(uri + "/find-all-sort-by-id")
                .retrieve()
                .bodyToMono(List.class)
                .block();
    }

    @Override
    public void removeCategory(Long id) {

        webClient.post()
                .uri(uri +"/remove-category")
                .bodyValue(id)
                .retrieve()
                .toBodilessEntity()
                .block();;
    }

    @Override
    public void addCategory(CategoryDto category) {

        webClient.post()
                .uri(uri +"/add-category")
                .bodyValue(category)
                .retrieve()
                .toBodilessEntity()
                .block();;
    }

    @Override
    public List<CategoryDto> findByName(String name) {

        return webClient.post()
                .uri(uri + "/find-by-name")
                .bodyValue(name)
                .retrieve()
                .bodyToMono(List.class)
                .block();
    }
}
