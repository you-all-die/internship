package com.example.adminapplication.service.impl;

import com.example.adminapplication.dto.CategoryDto;
import com.example.adminapplication.service.CategoryService;
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
public class CategoryServiceImpl implements CategoryService {

    private final RestTemplate restTemplate;

    @Value("${resttemplate.url}")
    private final String url;

    private String url() {
        return url + "/category";
    }

    @Override
    public List<CategoryDto> findAll() throws ResourceAccessException {
        return restTemplate.postForObject(url() + "/find-all", null, List.class);
    }

    @Override
    public CategoryDto findById(Long id) throws ResourceAccessException {
        return restTemplate.postForObject(url() + "/find-by-id", id, CategoryDto.class);
    }

    @Override
    public List<CategoryDto> findAllSortById() throws ResourceAccessException {
        return restTemplate.postForObject(url() + "/find-all-sort-by-id", null, List.class);
    }

    @Override
    public void removeCategory(Long id) throws ResourceAccessException {

        restTemplate.postForObject(url() + "/remove-category", id, String.class);
    }

    @Override
    public void addCategory(CategoryDto category) throws ResourceAccessException {
        restTemplate.postForObject(url() + "/add-category", category, String.class);
    }

    @Override
    public List<CategoryDto> findByName(String name) throws ResourceAccessException {
        return restTemplate.postForObject(url() + "/find-by-name", name, List.class);
    }
}
