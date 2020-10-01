package com.example.internship.api;

import com.example.internship.dto.FeedbackDto;
import com.example.internship.dto.FeedbackSearchResult;
import com.example.internship.refactoringdto.View;
import com.example.internship.service.feedback.FeedbackService;
import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.Optional;

/*
* @author Romodin Aleksey
 */

@RestController
@RequestMapping("/api/feedbacks")
@RequiredArgsConstructor
public class FeedbackRestController {
    private final FeedbackService feedbackService;
    /**
     *
     * @param productId Идентификатор продукта
     * @param customerId Идентификатор пользователя
     * @param pageNumber номер страницы
     * @param pageSize размер страницы
     * @return комментарии по критериям
     */
    @GetMapping
    @Operation(description = "Поиск комментариев по параметрам")
    @Schema(implementation = FeedbackSearchResult.class)
    @ApiResponse(responseCode = "200", description = "Поиск успешен")
    @JsonView(View.Public.class)
    public ResponseEntity<FeedbackSearchResult> searchResult(@RequestParam(name = "productId", required = false)
                                                                 @Parameter(description = "Код продукта") Long productId,
                                                             @RequestParam(name = "customerId", required = false)
                                                                 @Parameter(description = "Код пользователя") Long customerId,
                                                             @RequestParam(name = "pageNumber", defaultValue = "0")
                                                                 @Parameter(description = "Номер страницы (0 - первая страница)") Integer pageNumber,
                                                             @RequestParam(name = "pageSize", defaultValue = "10")
                                                                 @Parameter(description = "Размер страницы") Integer pageSize,
                                                             @RequestParam(name = "startDate", required = false)
                                                                 @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                                                                 @Parameter(description = "Начальная дата (yyyy-MM-dd)") Date startDate,
                                                             @RequestParam(name = "endDate", required = false)
                                                                 @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                                                                 @Parameter(description = "Конечная дата (yyyy-MM-dd)") Date endDate) {
        return ResponseEntity.ok(feedbackService.searchResult(productId, customerId, pageSize, pageNumber, startDate, endDate));
    }

    /**
     *
     * @param id идентификатор комментария
     * @return http status 200 и комментарий или http status 404, если комментарий не найден
     */
    @GetMapping("/{id}")
    @Operation(description = "Поиск комментария по id.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Комментарий найден"),
            @ApiResponse(responseCode = "404", description = "Комментарий не найден")
    })
    @Schema(implementation = FeedbackDto.class)
    @JsonView(View.Public.class)
    public ResponseEntity<FeedbackDto> getFeedback(@Parameter(description = "Идентификатор комментария")
                                                       @PathVariable("id") Long id) {
        Optional<FeedbackDto> feedbackDto = feedbackService.getFeedbackById(id);
        return feedbackDto.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     *
     * @param id идентификатор комментария
     * @return  http status 200 или http status 404, если комментарий не найден
     */
    @DeleteMapping("/delete/{id}")
    @Operation(description = "Удаление комментария по ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Комментарий удален"),
            @ApiResponse(responseCode = "404", description = "Комментарий не найден")
    })
    @JsonView(View.Public.class)
    public ResponseEntity<?> deleteFeedbackById(@PathVariable("id") Long id) {
        if (feedbackService.getFeedbackById(id).isPresent()) {
            feedbackService.deleteFeedback(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
