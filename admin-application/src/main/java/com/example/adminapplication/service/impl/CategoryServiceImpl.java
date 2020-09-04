package com.example.adminapplication.service.impl;

import com.example.adminapplication.dto.CategoryDto;
import com.example.adminapplication.properties.RestTemplateProperties;
import com.example.adminapplication.service.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * @author Ivan Gubanov
 */
@Service
@AllArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final RestTemplate restTemplate;
    private final RestTemplateProperties restTemplateProperties;

    private String url() {
        return restTemplateProperties.getUrl() + "/category";
    }

    @Override
    public List<CategoryDto> findAll() {
        return restTemplate.postForObject(url() + "/find-all", null, List.class);
    }

    @Override
    public CategoryDto findById(Long id) {
        return restTemplate.postForObject(url() + "/find-by-id", id, CategoryDto.class);
    }

    @Override
    public List<CategoryDto> findAllSortById() {
        return restTemplate.postForObject(url() + "/find-all-sort-by-id", null, List.class);
    }

    @Override
    public void removeCategory(Long id) {
        restTemplate.postForObject(url() + "/remove-category", id, String.class);
    }

    @Override
    public void addCategory(CategoryDto category) {
        restTemplate.postForObject(url() + "/add-category", category, String.class);
    }

    @Override
    public List<CategoryDto> findByName(String name) {
        return restTemplate.postForObject(url() + "/find-by-name", name, List.class);
    }
}
