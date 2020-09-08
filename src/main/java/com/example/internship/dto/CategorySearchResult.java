package com.example.internship.dto;

import com.example.internship.entity.Category;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Aleksey Moiseychev
 */
@Data
@Component
public class CategorySearchResult {

    private List<Category> category;
    private Integer pageNumber;
    private Integer pageSize;

    public List<Category> getCategory() {
        return category;
    }

    public void setCategory(List<Category> category) {
        this.category = category;
    }

    public Integer getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(Integer pageNumber) {
        this.pageNumber = pageNumber;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }
}
