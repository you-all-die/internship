package com.example.internship.service.feedback;

import com.example.internship.dto.FeedbackSearchResult;


public interface FeedbackService {
    //Поиск по параметрам
    FeedbackSearchResult searchResult(Long productId, Long authorId, Integer pageSize, Integer pageNumber);
    //Сохранение нового комментария
    void addFeedback(Long productId, Long authorId, String authorName, String feedbackText);
    //Удаление комментария автором
    void deleteFeedback(Long id);
}
