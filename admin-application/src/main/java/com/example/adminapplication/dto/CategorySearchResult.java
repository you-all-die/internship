package com.example.adminapplication.dto;

/*
 *@author Romodin Aleksey
 */

import lombok.Data;

import java.util.List;

@Data
public class CategorySearchResult {
    private List<CategoryDto> category;
    private Integer pageNumber;
    private Integer pageSize;
    private Long totalCategory;
}