package com.example.adminapplication.controller.feedbacks;


import com.example.adminapplication.dto.feedback.FeedbackSearchRequest;
import com.example.adminapplication.dto.feedback.FeedbackSearchResult;
import com.example.adminapplication.service.FeedbackService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


/*
*@author Romodin Aleksey
 */
@Controller
@AllArgsConstructor
@RequestMapping("/feedbacks")
public class FeedbackController {
    private final FeedbackService feedbackService;

    /**
     *
     * @param feedbackSearchRequest идентификатор продукта, идентификатор пользователя, размер страницы, номер страницы
     * @param model модель
     * @return комментарии по параметрам
     */
    @GetMapping
    public String getFeedbacks(
            @ModelAttribute("feedbackSearchRequest") FeedbackSearchRequest feedbackSearchRequest, Model model) {
        FeedbackSearchResult feedbackSearchResult = feedbackService.searchResult(feedbackSearchRequest);
        //Определяем количество страниц
        Long totalFeedbacks = feedbackSearchResult.getTotalFeedbacks();
        long totalPage = 0;
        if (feedbackSearchResult.getTotalFeedbacks() > feedbackSearchResult.getPageSize()) {
            totalPage = (long) Math.ceil(totalFeedbacks * 1.0 / feedbackSearchResult.getPageSize());
        }
        model.addAttribute("totalPage", totalPage);
        model.addAttribute("feedbacksList", feedbackSearchResult);
        return "feedbacks/feedbacks";
    }

    /**
     *
     * @param feedbackId идентификатор комментария
     * @return возврат на страницу с отзывами
     */
    @PostMapping("/delete")
    public  String deleteFeedback(@RequestParam("feedbackId") Long feedbackId) {
        feedbackService.deleteFeedback(feedbackId);
        return "redirect:/feedbacks";
    }
}
