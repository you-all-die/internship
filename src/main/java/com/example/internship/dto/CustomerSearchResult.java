package com.example.internship.dto;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.List;

/*
@author Romodin Aleksey
 */

@Data
@Component
public class CustomerSearchResult {
    private List<CustomerDto> customers;
    private Integer pageNumber;
    private Integer pageSize;
    private Integer totalCustomers;
}
