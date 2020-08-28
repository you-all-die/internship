package com.example.internship.service;

import com.example.internship.entity.Outlet;
import com.example.internship.repository.OutletRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Streamable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class OutletService {

    @Autowired
    private OutletRepository outletRepository;

    public Iterable<Outlet> getAll() {
        return outletRepository.findAll();
    }

    public Optional<Outlet> getById(long id) {
        return outletRepository.findById(id);
    }

    public void save(Outlet outlet) {
        outletRepository.save(outlet);
    }

    public void delete(long id) {
        outletRepository.deleteById(id);
    }

    public Iterable<String> getCities() {
        return outletRepository.findCities();
    }

    public Iterable<Outlet> getOutlets(String city) {
        if (null == city || city.isEmpty()) {
            return outletRepository.findAll();
        }
        return outletRepository.findAllByCity(city);
    }
}
