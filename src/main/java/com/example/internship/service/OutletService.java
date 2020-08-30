package com.example.internship.service;

import com.example.internship.dto.outlet.OutletDto;
import com.example.internship.entity.Outlet;

import java.util.List;
import java.util.Optional;

/**
 * @author Самохвалов Юрий Алексеевич
 */
public interface OutletService {

    List<OutletDto.Response.Full> getAll();

    public Optional<Outlet> getById(long id);

    public void save(Outlet outlet);

    public void delete(long id);

    public List<String> getCities();

    public Iterable<Outlet> getOutlets(String city);

    public List<OutletDto.Response.OnlyCoordinates> getAllCoordinates();
}
