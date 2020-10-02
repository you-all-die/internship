package com.example.internship.dto;

import com.example.internship.refactoringdto.View;
import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
public class FeedbackSearchResult {
    @Schema(description = "Комментарии")
    @JsonView({View.Public.class})
    private List<FeedbackDto> feedbacks;
    @Schema(description = "Номер страницы")
    @JsonView({View.Public.class})
    private Integer pageNumber;
    @Schema(description = "Размер страницы")
    @JsonView({View.Public.class})
    private Integer pageSize;
    @Schema(description = "Количество найденных комментарием")
    @JsonView({View.Public.class})
    private Long totalFeedbacks;
}
