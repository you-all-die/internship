package com.example.adminapplication.service;

import com.example.adminapplication.dto.ProductStatusDto;

import java.util.List;

/**
 * @author Ivan Gubanov
 */
public interface ProductStatusService {

    List<ProductStatusDto> findAll();
}
