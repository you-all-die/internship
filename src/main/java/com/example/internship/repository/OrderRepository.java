package com.example.internship.repository;

import com.example.internship.entity.Order;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Sergey Lapshin
 */

@Repository
public interface OrderRepository  extends CrudRepository<Order, Long> {
}