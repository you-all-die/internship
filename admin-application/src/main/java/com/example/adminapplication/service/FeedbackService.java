package com.example.adminapplication.service;

import com.example.adminapplication.dto.feedback.FeedbackSearchRequest;
import com.example.adminapplication.dto.feedback.FeedbackSearchResult;

public interface FeedbackService {
    //Поиск комментариев по критериям
    FeedbackSearchResult searchResult(FeedbackSearchRequest request);
    //Удаление комментария по ID
    void deleteFeedback(Long id);
}
