package com.example.internship.api;

import com.example.internship.dto.FeedbackDto;
import com.example.internship.dto.FeedbackSearchResult;
import com.example.internship.refactoringdto.View;
import com.example.internship.service.feedback.FeedbackService;
import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.v3.oas.annotations.media.Schema;
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
    @ApiOperation(value = "Поиск комментариев по параметрам")
    @Schema(implementation = FeedbackSearchResult.class)
    @ApiResponse(code = 200, message = "Поиск успешен")
    @JsonView(View.Public.class)
    public ResponseEntity<FeedbackSearchResult> searchResult(@RequestParam(name = "productId", required = false)
                                                                 @ApiParam(value = "Код продукта") Long productId,
                                                             @RequestParam(name = "customerId", required = false)
                                                                 @ApiParam(value = "Код пользователя") Long customerId,
                                                             @RequestParam(name = "pageNumber", defaultValue = "0")
                                                                 @ApiParam(value = "Номер страницы (0 - первая страница)") Integer pageNumber,
                                                             @RequestParam(name = "pageSize", defaultValue = "10")
                                                                 @ApiParam(value = "Размер страницы") Integer pageSize,
                                                             @RequestParam(name = "startDate", required = false)
                                                                 @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                                                                 @ApiParam(value = "Начальная дата (yyyy-MM-dd)") Date startDate,
                                                             @RequestParam(name = "endDate", required = false)
                                                                 @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                                                                 @ApiParam(value = "Конечная дата (yyyy-MM-dd)") Date endDate) {
        return ResponseEntity.ok(feedbackService.searchResult(productId, customerId, pageSize, pageNumber, startDate, endDate));
    }

    /**
     *
     * @param id идентификатор комментария
     * @return http status 200 и комментарий или http status 404, если комментарий не найден
     */
    @GetMapping("/{id}")
    @ApiOperation(value = "Поиск комментария по id.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Комментарий найден"),
            @ApiResponse(code = 404, message = "Комментарий не найден")
    })
    @Schema(implementation = FeedbackDto.class)
    @JsonView(View.Public.class)
    public ResponseEntity<FeedbackDto> getFeedback(@ApiParam(value = "Идентификатор комментария")
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
    @ApiOperation(value = "Удаление комментария по ID.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Комментарий удален"),
            @ApiResponse(code = 404, message = "Комментарий не найден")
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
