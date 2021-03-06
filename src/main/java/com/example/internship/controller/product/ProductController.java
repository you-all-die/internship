package com.example.internship.controller.product;

import com.example.internship.dto.CustomerDto;
import com.example.internship.dto.FeedbackSearchResult;
import com.example.internship.dto.category.CategoryDto;
import com.example.internship.dto.product.SearchResult;

import com.example.internship.service.ProductService;
import com.example.internship.service.category.GsCategoryService;
import com.example.internship.service.customer.CustomerService;
import com.example.internship.service.feedback.FeedbackService;
import com.example.internship.service.product.GsProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.List;



/**
 * @author Самохвалов Юрий Алексеевич
 * @author Каравашкин Роман Николаевич.
 */
@Controller
@RequestMapping(ProductController.BASE_URL)
@RequiredArgsConstructor
@Slf4j
public class ProductController {

    public static final String BASE_URL = "/product";


    private final ProductService productService;
    private final GsProductService gsProductService;
    private final CustomerService customerService;
    private final GsCategoryService categoryService;
    private final FeedbackService feedbackService;

    /**
     * @param productId код продукта
     * @param pageNumber номер страницы для отображения отзывов
     * @param authentication авторизация пользователя
     * @param model модель
     * @return переход на страницу товара
     */

    @GetMapping("/{id}")
    public String showProduct(@PathVariable("id") Long productId,
                          @RequestParam(value = "pageNumber", defaultValue = "0") Integer pageNumber,
                          Authentication authentication,
                          Model model) {

    //Получаем дерево предков категорий
    List<CategoryDto.Response.All> ancestors =
            categoryService.findAncestors(productService.getProductById(productId).getCategory().getId());
    model.addAttribute("breadcrumb", ancestors);

    /* Получаем пользователя из авторизации
     * - Если пользователь авторизирован, будет оставлять отзывы от своего имени.
     * - Получаем ID анонимного пользователя из cookie браузера.
     * - Получаем два ID, чтобы пользователь видел свои отзывы,
     *   оставленные под анонимным пользователем и в режиме авторизации.
    */
    Optional<CustomerDto> customer = customerService.getFromAuthentication(authentication);
    customer.ifPresent(customerDto -> model.addAttribute("customerId", customerDto.getId()));
    Long aCustomerId = customerService.customerIdFromCookie().orElse(null);
    model.addAttribute("aCustomerId", aCustomerId);

    /* Получаем отзывы о товаре из БД
     * - Без привязки к автору
     * - Отображение с пагинацией
     */
    FeedbackSearchResult feedbackSearchResult = feedbackService.searchResult(productId, null, 10, pageNumber, null, null);
    Long totalCategory = feedbackSearchResult.getTotalFeedbacks();
    long totalPage = 0;
    if (feedbackSearchResult.getTotalFeedbacks() > feedbackSearchResult.getPageSize()) {
        totalPage = (long) Math.ceil(totalCategory * 1.0 / feedbackSearchResult.getPageSize());
    }


    model.addAttribute("totalPage", totalPage);
    model.addAttribute("feedbacksList", feedbackSearchResult);
    model.addAttribute("product", productService.getProductById(productId));
    model.addAttribute("rate", productService.getProductRating(productId));
    return "products/product";
    }

    /**
     * @param productId код продукта
     * @param customerName имя пользователя, если он без авторизации
     * @param feedbackText текст комментария
     * @param authentication авторизация пользователя
     * @return возврат на страницу просматриваемого товара
     */
    @PostMapping("/{id}/addNewComment")
    public String addNewCommentProduct(@PathVariable("id") Long productId,
                                       @RequestParam(value = "customerName", required = false) String customerName,
                                       @RequestParam("textComment") String feedbackText,
                                       Authentication authentication
    ) {
        Optional<CustomerDto> customer = customerService.getFromAuthentication(authentication);
        if (customer.isPresent()) {
            feedbackService.addFeedback(productId, customer.get().getId(), customer.get().getFirstName(), feedbackText);
        } else {
            Long aCustomerId = customerService.customerIdFromCookie().orElse(null);
            feedbackService.addFeedback(productId, aCustomerId, customerName, feedbackText);
        }
        return "redirect:" + BASE_URL + "/" + productId;
    }

    /**
     * @param id код продукта
     * @param feedbackId код комментария
     * @return возврат на страницу просматриваемого товара
     */
    @PostMapping("/{id}/deleteFeedback")
    public String delFeedback(@PathVariable("id") Long id, @RequestParam("feedbackId") Long feedbackId) {
        feedbackService.deleteFeedback(feedbackId);
        return "redirect:" + BASE_URL + "/" + id;
    }


    @GetMapping
    public String viewProductPage(
            Model model,
            @RequestParam(required = false) String searchString,
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) BigDecimal lowerPriceLimit,
            @RequestParam(required = false) BigDecimal upperPriceLimit,
            @RequestParam(required = false) Integer pageNumber,
            @RequestParam(required = false) Integer pageSize,
            @RequestParam(required = false) Boolean descendingOrder
    ) {
        final SearchResult data = gsProductService.findByCriteria(
                searchString, categoryId, lowerPriceLimit, upperPriceLimit, pageNumber, pageSize, descendingOrder
        );
        model.addAttribute("data", data);
        return BASE_URL + "/index";
    }

    @PostMapping("/{id}/{rate}")
    public String addRate(@PathVariable("id") Long productId, @PathVariable("rate") Long rate,
                          Authentication authentication) {
        Optional<CustomerDto> customer = customerService.getFromAuthentication(authentication);
        if (customer.isPresent()) {
            productService.saveRating(productId, customer.get().getId(), rate);
            return "redirect:/login";
        } else {
            return "products/product";
        }
        }
    }

