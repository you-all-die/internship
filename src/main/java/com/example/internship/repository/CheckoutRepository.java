package com.example.internship.repository;

import com.example.internship.entity.Order;
import org.springframework.data.repository.CrudRepository;

/**
 * @author Sergey Lapshin
 */

public interface CheckoutRepository extends CrudRepository<Order, Long> {
}
