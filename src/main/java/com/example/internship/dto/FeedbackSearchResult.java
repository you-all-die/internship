package com.example.internship.dto;

import com.example.internship.entity.Feedback;
import lombok.Data;

import java.util.List;

@Data
public class FeedbackSearchResult {
    private List<FeedbackDto> feedbacks;
    private Integer pageNumber;
    private Integer pageSize;
    private Long totalFeedbacks;
}
