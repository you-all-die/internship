package com.example.internship.repository;

import com.example.internship.dto.outlet.OutletDto;
import com.example.internship.entity.Outlet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author Самохвалов Юрий Алексеевич
 */
public interface OutletRepository extends JpaRepository<Outlet, Long> {

    List<Outlet> findAllByCity(String city);

    @Query("select distinct o.city from Outlet o order by city")
    List<String> findCities();

    @Query("select o.longitude, o.latitude from Outlet o")
    List<OutletDto.Response.Coordinates> getCoordinates();
}
