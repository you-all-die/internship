package com.example.adminapplication.service.impl;

import com.example.adminapplication.dto.ProductStatusDto;
import com.example.adminapplication.service.ProductStatusService;
import com.example.internship.client.api.ProductStatusRestControllerApi;
import com.example.internship.client.model.ProductStatus;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Ivan Gubanov
 */
@Service
@RequiredArgsConstructor
public class ProductStatusServiceImpl implements ProductStatusService {

    private final ModelMapper modelMapper;
    private final ProductStatusRestControllerApi productStatusApi;

    @Override
    public List<ProductStatusDto> findAll() {
        return productStatusApi.findAll2().map(this::convertToDto).collectList().block();
    }

    private ProductStatusDto convertToDto(ProductStatus productStatus) {
        return modelMapper.map(productStatus, ProductStatusDto.class);
    }
}
