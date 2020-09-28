package com.example.internship.repository;

import com.example.internship.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface CustomerRepository extends JpaRepository<Customer, Long>, JpaSpecificationExecutor<Customer> {
    Customer findByEmail(String email);

    /**
     * Проверка уникальности почты.
     *
     * @param email почта пользователя
     * @return возращает true если нашел, и false если не нашел.
     */
    boolean existsByEmail(String email);

    //обновляет поле последней активности пользователя, если с момента последнего действия прошло больше часа
    @Transactional
    @Modifying
    @Query(value = "update customers last_activity set last_activity = now() " +
            "where id = :id and EXTRACT(EPOCH FROM(now() - last_activity)) > 3600",
            nativeQuery = true)
    void setLastActivityForCustomers(@Param("id") Long customerId);

    //удаляет анонимных пользователей, неактивных более суток
    @Transactional
    @Modifying
    @Query(value = "delete FROM customers where email is null AND (last_activity < 'yesterday' OR last_activity is null)",
            nativeQuery = true)
    Integer deleteInactiveAnonymousUsers();
}
