package com.example.internship.dto;

import lombok.Data;

import java.util.List;

/*
*@author Romodin Aleksey
 */

@Data
public class CustomerSearchResult {
    private List<CustomerDto> customers;
    private Integer pageNumber;
    private Integer pageSize;
    private Long totalCustomers;
}
