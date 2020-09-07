package com.example.adminapplication.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Size;

/**
 * @author Ivan Gubanov
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductStatusDto {
    private Long id;
    @Size(max = 64)
    private String description;
}
