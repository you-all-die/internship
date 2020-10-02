package com.example.adminapplication.dto.feedback;

import lombok.Data;

import java.util.List;

/**
 * @author Romodin Aleksey
 */
@Data
public class FeedbackSearchResult {
    private List<FeedbackDto> feedbacks;
    private Integer pageNumber;
    private Integer pageSize;
    private Long totalFeedbacks;
}
