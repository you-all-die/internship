package com.example.adminapplication.service.impl;

import com.example.adminapplication.dto.feedback.FeedbackSearchRequest;
import com.example.adminapplication.dto.feedback.FeedbackSearchResult;
import com.example.adminapplication.service.FeedbackService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * @author Romodin Aleksey
 */
@Service
@RequiredArgsConstructor
public class FeedbackServiceImpl implements FeedbackService {
    private final WebClient webClient;
    private final String uri = "/feedbacks";
    //Запрос на поиск по критериям
    @Override
    public FeedbackSearchResult searchResult(FeedbackSearchRequest request) {
        //Конструктор запроса
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString("/");

        //Добавление параметра: поиск по ID товара
        if (request.getProductId() != null) {
            builder.queryParam("productId", request.getProductId());
        }
        //Добавление параметра: поиск по ID автора
        if (request.getAuthorId() != null) {
            builder.queryParam("customerId", request.getAuthorId());
        }
        //Добавление параметра: номер страницы
        builder.queryParam("pageNumber", request.getPageNumber());
        //Добавление параметра: размер страницы
        builder.queryParam("pageSize", request.getPageSize());
        return webClient.get()
                .uri(uri + builder.toUriString())
                .retrieve()
                .bodyToMono(FeedbackSearchResult.class)
                .block();
    }
    @Override
    public void deleteFeedback(Long id) {
        webClient.delete()
                .uri(uri + "/delete/" + id)
                .retrieve()
                .bodyToMono(Void.class)
                .block();
    }
}
