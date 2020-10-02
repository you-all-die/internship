package com.example.adminapplication.service.impl;

import com.example.adminapplication.service.ProductImageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;

/**
 * @author Ivan Gubanov
 */

@Service
public class ProductImageServiceImpl implements ProductImageService {

    private static final Logger LOG = LoggerFactory.getLogger(ProductImageServiceImpl.class);

    private static final String UPLOAD_PATH = System.getProperty("user.dir") + "/img_product/";

    @Override
    public boolean saveOrUpdate(Long productId, MultipartFile image) {

        if (!image.isEmpty()) {
            if (!createNewDirectory(productId)) {
                if (!delete(productId) || !createNewDirectory(productId)) {

                    return false;
                }
            }
            String imagePath = UPLOAD_PATH + productId + "/";
            String imageExtension = fileExtension(image);
            try {
                image.transferTo(new File(imagePath + "original." + imageExtension));

                ImageIO.write(createThumbnail(image, 189, 189),
                        imageExtension,
                        new File(imagePath + "catalog." + imageExtension));

                ImageIO.write(createThumbnail(image, 520, 520),
                        imageExtension,
                        new File(imagePath + "product." + imageExtension));

                return true;
            } catch (IOException e) {
                LOG.error(e.getMessage(), e);

                return false;
            }

        }

        return false;
    }

    @Override
    public boolean delete(Long productId) {

        try {
            Files.walk(Path.of(UPLOAD_PATH + productId + "/"))
                    .sorted(Comparator.reverseOrder())
                    .map(Path::toFile)
                    .forEach(File::delete);
        } catch (IOException e) {
            LOG.error(e.getMessage(), e);

            return false;
        }

        return true;
    }

    @Override
    public String fileExtension(MultipartFile image) {

        String extension = image.getOriginalFilename();
        if (extension != null) {
            int index = extension.lastIndexOf('.');

            return extension.substring(index + 1);
        }

        return "jpg";
    }

    private BufferedImage createThumbnail(MultipartFile image, int width, int height) throws IOException {

        Image thumbnail = ImageIO.read(image.getInputStream()).getScaledInstance(width, height, BufferedImage.SCALE_SMOOTH);
        BufferedImage thumbnailImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        thumbnailImg.createGraphics().drawImage((thumbnail), 0, 0, null);

        return thumbnailImg;
    }

    private boolean createNewDirectory(Long productId) {

        File directory = new File(UPLOAD_PATH + productId + "/");
        if (!directory.exists()) {
            return directory.mkdir();
        }

        return false;
    }
}
