package com.example.internship.repository;

import com.example.internship.entity.Outlet;
import org.springframework.data.repository.CrudRepository;

public interface OutletRepository extends CrudRepository<Outlet, Long> {
    Iterable<Outlet> findAllByCity(String city);
}
