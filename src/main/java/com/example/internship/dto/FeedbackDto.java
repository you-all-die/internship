package com.example.internship.dto;


import com.example.internship.refactoringdto.View;
import com.fasterxml.jackson.annotation.JsonView;
import com.sun.istack.NotNull;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Size;
import java.sql.Timestamp;

/**
 * @author Romodin Aleksey
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FeedbackDto {

    @Schema(description = "id комментария")
    @JsonView({View.Public.class})
    private Long id;

    @Schema(description = "Дата публикации")
    @JsonView({View.Public.class})
    private Timestamp datePublication;

    @Schema(description = "Текст комментария")
    @Size(max = 500)
    @NotNull
    @JsonView({View.Public.class})
    private String feedbackText;

    @Schema(description = "id продукта")
    @JsonView({View.Public.class})
    private Long productId;

    @Schema(description = "id автора")
    @JsonView({View.Public.class})
    private Long authorId;

    @Schema(description = "Имя автора")
    @Size(max = 64)
    @NotNull
    @JsonView({View.Public.class})
    private String authorName;
}
