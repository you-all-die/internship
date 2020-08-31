package com.example.internship.service;

import com.example.internship.entity.ProductStatus;
import com.example.internship.repository.ProductStatusRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ProductStatusService {
    public final ProductStatusRepository productStatusRepository;

    public List<ProductStatus> findAll() {
        return (List<ProductStatus>) productStatusRepository.findAll();
    }

    public Optional<ProductStatus> findById(Long id) {
        return productStatusRepository.findById(id);
    }

}
