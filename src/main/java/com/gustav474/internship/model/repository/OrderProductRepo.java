package com.gustav474.internship.model.repository;

import com.gustav474.internship.model.entity.OrderProduct;
import org.springframework.data.repository.CrudRepository;

public interface OrderProductRepo extends CrudRepository<OrderProduct, Long> {

}