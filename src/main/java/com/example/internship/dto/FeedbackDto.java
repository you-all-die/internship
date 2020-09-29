package com.example.internship.dto;


import com.sun.istack.NotNull;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Size;
import java.util.Date;

/**
 * @author Romodin Aleksey
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FeedbackDto {

    @Schema(description = "id комментария")
    private Long id;

    @Schema(description = "Дата публикации")
    private Date datePublication;

    @Schema(description = "Текст комментария")
    @Size(max = 500)
    @NotNull
    private String feedbackText;

    @Schema(description = "id продукта")
    private Long productId;

    @Schema(description = "id автора")
    private Long authorId;

    @Schema(description = "Имя автора")
    @Size(max = 64)
    @NotNull
    private String authorName;
}
