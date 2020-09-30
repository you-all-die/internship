package com.example.internship.service.feedback;

import com.example.internship.dto.FeedbackDto;
import com.example.internship.dto.FeedbackSearchResult;
import com.example.internship.entity.Feedback;
import com.example.internship.repository.FeedbackRepository;
import com.example.internship.specification.FeedbackSpecification;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author Romodin Aleksey
 */
@Service
@AllArgsConstructor
public class FeedbackServiceImpl implements FeedbackService {

    private final FeedbackRepository feedbackRepository;
    private final ModelMapper modelMapper;

    /**
     * @param productId код продукта
     * @param authorId код автора отзыва
     * @param pageSize размер страницы с отзывами
     * @param pageNumber номер страницы отзывов
     * @return получение результата поиска
     */
    @Override
    public FeedbackSearchResult searchResult(Long productId, Long authorId, Integer pageSize, Integer pageNumber) {
        FeedbackSearchResult feedbackSearchResult = new FeedbackSearchResult();
        Specification<Feedback> specification = null;

        // Формируем условия для запроса
        if (productId != null) {
            specification = draftSpecification(null, "productId", productId.toString());
        }
        if (authorId != null) {
            specification = draftSpecification(specification, "authorId", authorId.toString());
        }
        feedbackSearchResult.setFeedbacks(feedbackRepository.findAll(specification,
                PageRequest.of(pageNumber, pageSize, Sort.by("datePublication").ascending()))
                .stream().map(this::convertToDto).collect(Collectors.toList()));

        feedbackSearchResult.setPageNumber(pageNumber);
        feedbackSearchResult.setPageSize(pageSize);
        feedbackSearchResult.setTotalFeedbacks(feedbackRepository.count(specification));

        return feedbackSearchResult;
    }

    /**
     * @param productId код продукта
     * @param authorId код автора
     * @param authorName имя автора
     * @param feedbackText текст отзыва
     */
    @Override
    public void addFeedback(Long productId, Long authorId, String authorName, String feedbackText) {
        Feedback feedback = new Feedback();
        feedback.setDatePublication(new Date());
        feedback.setProductId(productId);
        feedback.setAuthorId(authorId);
        feedback.setAuthorName(authorName);
        feedback.setFeedbackText(feedbackText);
        feedbackRepository.save(feedback);
    }

    /**
     * @param id код комментария
     */
    @Override
    public  void deleteFeedback(Long id) {
        if (feedbackRepository.findById(id).isPresent()) {
            feedbackRepository.deleteById(id);
        }
    }

    /**
     * @param specification спецификация для поиска
     * @param columnName имя столбца
     * @param optionalName значение переменной
     * @return получение спецификации
     */
    private Specification<Feedback> draftSpecification(
            Specification<Feedback> specification, String columnName, String optionalName) {
        if (optionalName != null) {
            if (specification == null) {
                specification = Specification.where(new FeedbackSpecification(columnName, optionalName));
            } else {
                specification = specification.and(new FeedbackSpecification(columnName, optionalName));
            }
        }
        return specification;
    }

    /**
     * @param feedback сущность Комментарий
     * @return возврат DTO
     */
    private FeedbackDto convertToDto(Feedback feedback) {
        return modelMapper.map(feedback, FeedbackDto.class);
    }
    @Override
    public Optional<FeedbackDto> getFeedbackById(Long id) {
        if (feedbackRepository.findById(id).isPresent()) {
            return Optional.of(convertToDto(feedbackRepository.findById(id).get()));
        }
        return Optional.empty();
    }

}
