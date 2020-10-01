package com.example.internship.repository;

import com.example.internship.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Sergey Lapshin
 */

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findAllByCustomerId(Long customerId);

    Order findByIdAndCustomerId(Long orderId, Long customerId);

}