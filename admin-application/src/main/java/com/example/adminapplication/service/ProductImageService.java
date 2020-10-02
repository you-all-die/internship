package com.example.adminapplication.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;

/**
 * @author Ivan Gubanov
 */
public interface ProductImageService {

    boolean saveOrUpdate(Long productId, File image, String extension);

    boolean delete(Long productId);

    String fileExtension(MultipartFile image);

    File createTempFile(MultipartFile image);
}
