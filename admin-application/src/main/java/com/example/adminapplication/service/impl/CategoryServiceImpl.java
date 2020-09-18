package com.example.adminapplication.service.impl;

import com.example.adminapplication.dto.CategoryDto;
import com.example.adminapplication.dto.CategorySearchResult;
import com.example.adminapplication.dto.ParentCategoryDto;
import com.example.adminapplication.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
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

    //Запрос на поиск по критериям
    @Override
    public CategorySearchResult searchResult(String name, Long parentId, Integer pageSize, Integer pageNumber){
        //Конструктор запроса
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString("/search");

        //Добавление параметра: поиск по наименованию
        if(!name.isEmpty()) builder.queryParam("searchText", name);
        //Добавление параметра: поиск по ID родительской категории
        if(parentId !=null) builder.queryParam("parentId", parentId);
        //Добавление параметра: номер страницы
        builder.queryParam("pageNumber", pageNumber);
        //Добавление параметра: размер страницы
        builder.queryParam("pageSize", pageSize);
        //Декодировка кириллицы
        String result = URLDecoder.decode(builder.toUriString(), StandardCharsets.UTF_8);
        //return restTemplate.getForObject(url() + result, CategorySearchResult.class);

        return webClient.get()
                .uri(uri + result)
                .retrieve()
                .bodyToMono(CategorySearchResult.class)
                .block();

    }

    //Запрос на поиск родительских категорий
    @Override
    public List<ParentCategoryDto> getParentCategory() throws ResourceAccessException {
        return webClient.get()
                .uri(uri + "/parentCategory")
                .retrieve()
                .bodyToMono(List.class)
                .block();
    }

}
