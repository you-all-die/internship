package com.example.adminapplication.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * @author Ivan Gubanov
 */
public interface ProductImageService {

    boolean saveOrUpdate(Long productId, MultipartFile image);

    boolean delete(Long productId);

    String fileExtension(MultipartFile image);
}
