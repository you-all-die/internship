package com.example.internship.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
/** +=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=
 * Добавлен EqualsAndHashCode для корректного сравнения.
 +=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+= */
@EqualsAndHashCode
public class CategoryDto {

    private Long id;
    private String name;
    private CategoryDto parent;
}
