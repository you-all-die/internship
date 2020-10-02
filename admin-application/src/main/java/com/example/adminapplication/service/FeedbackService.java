package com.example.adminapplication.service;

import com.example.adminapplication.dto.feedback.FeedbackSearchRequest;
import com.example.adminapplication.dto.feedback.FeedbackSearchResult;

import java.text.ParseException;

public interface FeedbackService {
    //Поиск комментариев по критериям
    FeedbackSearchResult searchResult(FeedbackSearchRequest request) throws ParseException;
    //Удаление комментария по ID
    void deleteFeedback(Long id);
}
