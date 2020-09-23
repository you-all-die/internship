package com.example.internship.dto;

import com.example.internship.entity.Category;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Aleksey Moiseychev
 */
@Data
public class CategorySearchResult {

    private List<CategoryDto> category;
    private Integer pageNumber;
    private Integer pageSize;
    private Long totalCategory;
}
