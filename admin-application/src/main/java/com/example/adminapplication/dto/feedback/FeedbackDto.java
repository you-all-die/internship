package com.example.adminapplication.dto.feedback;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.OffsetDateTime;

/**
 * @author Romodin Aleksey
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FeedbackDto {
    private Long id;
    private OffsetDateTime datePublication;
    @Size(max = 500)
    @NotNull
    private String feedbackText;
    private Long productId;
    private Long authorId;
    @Size(max = 64)
    @NotNull
    private String authorName;
}

