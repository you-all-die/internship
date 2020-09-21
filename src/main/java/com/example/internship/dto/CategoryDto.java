package com.example.internship.dto;

import lombok.*;

@Data
public class CategoryDto {

    private Long id;
    private String name;
    private Long parentId;
    private String parentName;
}
