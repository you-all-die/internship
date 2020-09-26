package com.example.adminapplication.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategorySearchRequest {
    private String name = "";
    private Long parentCategoryId = -1L;
    private Integer pageNumber = 0;
    private Integer pageSize = 20;
}
