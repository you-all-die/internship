package com.example.internship.repository;

import com.example.internship.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Modenov D.A
 */
@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {

}
