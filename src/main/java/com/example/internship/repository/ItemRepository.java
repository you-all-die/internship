package com.example.internship.repository;

import com.example.internship.entity.Item;
import org.springframework.data.repository.CrudRepository;

/**
 * @author Sergey Lapshin
 */

public interface ItemRepository extends CrudRepository<Item, Long> {
}
