package com.example.internship.dto.customer;

import lombok.Builder;
import lombok.Value;

import java.util.List;

@Value
@Builder
public class SearchResult<T> {
    List<T> customers;
    Integer pageNumber;
    Integer pageSize;
    Integer totalPages;
    Long totalCustomers;
    Boolean ascendingOrder;
}
