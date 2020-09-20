package com.example.adminapplication.service.impl;

import com.example.adminapplication.dto.CategoryDto;
import com.example.adminapplication.dto.CategorySearchResult;
import com.example.adminapplication.dto.ParentCategoryDto;
import com.example.adminapplication.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Ivan Gubanov
 */
@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final WebClient webClient;

    private final String uri = "/category";


    //Показать все категории + новая строка "Без категории"
    @Override
    public List<CategoryDto> findAll() {
        List<CategoryDto> parentCategories =  webClient.get()
                .uri(uri + "/find-all")
                .retrieve()
                .bodyToFlux(CategoryDto.class)
                .collectList()
                .block();

        //Новая строка
        CategoryDto defaultParent = new CategoryDto((long) -1, "Без категории", null);
        if (parentCategories == null) parentCategories = new ArrayList<>();
        parentCategories.add(0, defaultParent);

        return parentCategories;
    }


    //Поиск категории по ID
    @Override
    public CategoryDto findById(Long id) {
        return webClient.post()
                .uri(uri + "/find-by-id")
                .bodyValue(id)
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
        webClient.post()
                .uri(uri +"/remove-category")
                .bodyValue(id)
                .retrieve()
                .toBodilessEntity()
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
        if(!name.isEmpty()) builder.queryParam("searchText", name);
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

    /*Запрос на поиск родительских категорий
     * Добавляем строку "Показать все"
     * Добавляем строку "Без родителя"
    */
    @Override
    public List<ParentCategoryDto> getParentCategory(){
        //Get-запрос из БД
        List<ParentCategoryDto> parentCategoryList =  webClient.get()
                .uri(uri + "/parentCategory")
                .retrieve()
                .bodyToFlux(ParentCategoryDto.class)
                .collectList()
                .block();

        if (parentCategoryList == null) {
            parentCategoryList = new ArrayList<>();
        }

        //Новые строки
        ParentCategoryDto findAllParentCategory = new ParentCategoryDto((long) -1, "Показать все");
        ParentCategoryDto notParentCategory = new ParentCategoryDto((long) 0, "Без родителя");
        parentCategoryList.add(notParentCategory);
        parentCategoryList.add(findAllParentCategory);

        return parentCategoryList;
    }

}
