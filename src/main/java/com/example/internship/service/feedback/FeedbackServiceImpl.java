package com.example.internship.service.feedback;

import com.example.internship.dto.FeedbackSearchResult;
import com.example.internship.entity.Feedback;
import com.example.internship.repository.FeedbackRepository;
import com.example.internship.specification.FeedbackSpecification;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.stream.Collectors;

/**
 * @author Romodin Aleksey
 */
@Service
@AllArgsConstructor
public class FeedbackServiceImpl implements FeedbackService {

    private final FeedbackRepository feedbackRepository;


    @Override
    public FeedbackSearchResult searchResult(Long productId, Long authorId, Integer pageSize, Integer pageNumber){
        FeedbackSearchResult feedbackSearchResult = new FeedbackSearchResult();
        Specification<Feedback> specification;

        // Формируем условия для запроса
        specification = draftSpecification(null,"productId", productId.toString());
        if (authorId!=null)
            specification = draftSpecification(specification ,"authorId", authorId.toString());

        feedbackSearchResult.setFeedbacks(feedbackRepository.findAll(specification,
                PageRequest.of(pageNumber, pageSize, Sort.by("datePublication").ascending()))
                .stream().collect(Collectors.toList()));

        feedbackSearchResult.setPageNumber(pageNumber);
        feedbackSearchResult.setPageSize(pageSize);
        feedbackSearchResult.setTotalFeedbacks(feedbackRepository.count(specification));

        return feedbackSearchResult;
    }

    @Override
    public void addFeedback(Long productId, Long authorId, String authorName, String feedbackText){
        Feedback feedback = new Feedback();
        feedback.setDatePublication(new Date());
        feedback.setProductId(productId);
        feedback.setAuthorId(authorId);
        feedback.setAuthorName(authorName);
        feedback.setFeedbackText(feedbackText);
        feedbackRepository.save(feedback);
    }

    @Override
    public  void deleteFeedback(Long id){
        if (feedbackRepository.findById(id).isPresent()) feedbackRepository.deleteById(id);
    }

    //Метод проверки поля и добавления условия в запрос
    private Specification<Feedback> draftSpecification(Specification<Feedback> specification, String columnName,
                                                       String optionalName ){
        if(optionalName !=null){
            if(specification == null){
                specification = Specification.where(new FeedbackSpecification(columnName, optionalName));
            }else {
                specification = specification.and(new FeedbackSpecification(columnName, optionalName));
            }
        }
        return specification;
    }
}
