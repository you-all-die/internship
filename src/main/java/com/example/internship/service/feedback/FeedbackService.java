package com.example.internship.service.feedback;

import com.example.internship.dto.FeedbackDto;
import com.example.internship.dto.FeedbackSearchResult;

import java.util.Optional;


public interface FeedbackService {
    //Поиск по параметрам
    FeedbackSearchResult searchResult(Long productId, Long authorId, Integer pageSize, Integer pageNumber);
    //Сохранение нового комментария
    void addFeedback(Long productId, Long authorId, String authorName, String feedbackText);
    //Удаление комментария автором
    void deleteFeedback(Long id);
    //Поиск по ID
    Optional<FeedbackDto> getFeedbackById(Long id);
}
