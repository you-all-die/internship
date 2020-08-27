package com.example.internship.repository;

import com.example.internship.entity.Outlet;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface OutletRepository extends CrudRepository<Outlet, Long> {
    Iterable<Outlet> findAllByCity(String city);
    @Query("select distinct o.city from Outlet o order by city")
    Iterable<String> findCities();
}
