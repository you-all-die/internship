package com.example.internship.service;

import com.example.internship.entity.ProductStatus;
import com.example.internship.repository.ProductStatusRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

//TODO: сервис + реализация
@Service
@AllArgsConstructor
public class ProductStatusService {
    public final ProductStatusRepository productStatusRepository;

    public List<ProductStatus> findAll() {
        //TODO: ненужное приведение типов
        return (List<ProductStatus>) productStatusRepository.findAll(Sort.by("id"));
    }

}
