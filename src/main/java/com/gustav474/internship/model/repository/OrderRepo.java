package com.gustav474.internship.model.repository;

import com.gustav474.internship.model.entity.Order;
import org.springframework.data.repository.CrudRepository;

public interface OrderRepo extends CrudRepository<Order, Long> {

}