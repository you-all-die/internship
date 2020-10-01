package com.example.adminapplication.service.impl;

import com.example.adminapplication.dto.feedback.FeedbackSearchRequest;
import com.example.adminapplication.dto.feedback.FeedbackSearchResult;
import com.example.adminapplication.service.FeedbackService;
import com.example.internship.client.api.FeedbackRestControllerApi;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZoneOffset;


/**
 * @author Romodin Aleksey
 */
@Service
@RequiredArgsConstructor
public class FeedbackServiceImpl implements FeedbackService {
    private final FeedbackRestControllerApi feedbackApi;
    private final ModelMapper modelMapper;
    //Запрос на поиск по критериям
    @Override
    public FeedbackSearchResult searchResult(FeedbackSearchRequest request) throws ParseException {
        return modelMapper.map(feedbackApi.searchResult(request.getProductId(),
                request.getAuthorId(),
                request.getPageNumber(),
                request.getPageSize(),
                request.getStartDate().isBlank() ? null : new SimpleDateFormat("yyyy-MM-dd").parse(request.getStartDate()).
                        toInstant().atOffset(ZoneOffset.of("+04:00")),
                request.getEndDate().isBlank() ? null : new SimpleDateFormat("yyyy-MM-dd").parse(request.getEndDate()).
                        toInstant().atOffset(ZoneOffset.of("+04:00")))
                .block(), FeedbackSearchResult.class);

    }
    @Override
    public void deleteFeedback(Long id) {
        feedbackApi.deleteFeedbackById(id).block();
    }
}
