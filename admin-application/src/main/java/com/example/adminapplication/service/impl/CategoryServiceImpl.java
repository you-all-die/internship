package com.example.adminapplication.service.impl;

import com.example.adminapplication.dto.CategoryDto;
import com.example.adminapplication.dto.CategorySearchResult;
import com.example.adminapplication.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
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

    private final String uri = "/category";


    //Поиск категории по ID
    @Override
    public CategoryDto findById(Long id) {
        return webClient.get()
                .uri(uri + "/"+id)
                .retrieve()
                .bodyToMono(CategoryDto.class)
                .block();
    }


    //Показать все категории, отсортированные по ID
    @Override
    public List<CategoryDto> findAllSortById() {
        return webClient.get()
                .uri(uri + "/find-all-sort-by-id")
                .retrieve()
                .bodyToFlux(CategoryDto.class)
                .collectList()
                .block();
    }


    //Удаление категории по ID
    @Override
    public void removeCategory(Long id) {
        webClient.delete()
                .uri(uri +"/remove/"+id)
                .retrieve()
                .bodyToMono(CategoryDto.class)
                .block();
    }


    //Добавление новой категории
    @Override
    public void addCategory(CategoryDto category) {
        webClient.post()
                .uri(uri +"/add-category")
                .bodyValue(category)
                .retrieve()
                .toBodilessEntity()
                .block();
    }

    //Запрос на поиск по критериям
    @Override
    public CategorySearchResult searchResult(String name, Long parentId, Integer pageSize, Integer pageNumber){
        //Конструктор запроса
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString("/search");

        //Добавление параметра: поиск по наименованию
        if(StringUtils.isNotBlank(name)) builder.queryParam("searchText", name);
        //Добавление параметра: поиск по ID родительской категории
        if(parentId !=null) builder.queryParam("parentId", parentId);
        //Добавление параметра: номер страницы
        builder.queryParam("pageNumber", pageNumber);
        //Добавление параметра: размер страницы
        builder.queryParam("pageSize", pageSize);
        //Декодировка кириллицы
        String result = URLDecoder.decode(builder.toUriString(), StandardCharsets.UTF_8);
        return webClient.get()
                .uri(uri + result)
                .retrieve()
                .bodyToMono(CategorySearchResult.class)
                .block();
    }

    //Запрос на поиск родительских категорий
    @Override
    public List<CategoryDto> getParentCategoriesWithChildren(){
        //Get-запрос из БД
        return webClient.get()
                .uri(uri + "/parentCategoriesWithChildren")
                .retrieve()
                .bodyToFlux(CategoryDto.class)
                .collectList()
                .block();

    }

}
