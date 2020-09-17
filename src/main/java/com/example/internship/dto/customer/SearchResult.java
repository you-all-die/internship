package com.example.internship.dto.customer;

import lombok.Builder;
import lombok.Value;

import java.util.List;

@Value
@Builder
public class SearchResult {
    List<CustomerDto.Response.AllExceptPassword> customers;
    Integer pageNumber;
    Integer pageSize;
    Long total;
    Boolean ascendingOrder;
}
