package com.example.adminapplication.dto.feedback;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Romodin Aleksey
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FeedbackSearchRequest {
    private Long productId;
    private Long authorId;
    private String startDate;
    private String endDate;
    private Integer pageNumber = 0;
    private Integer pageSize = 10;
}
