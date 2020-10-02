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
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;

/**
 * @author Ivan Gubanov
 */

@Service
public class ProductImageServiceImpl implements ProductImageService {

    private static final Logger LOG = LoggerFactory.getLogger(ProductImageServiceImpl.class);

    private static final String UPLOAD_PATH = System.getProperty("user.dir") + File.separator + "img_product" + File.separator;

    @Override
    public boolean saveOrUpdate(Long productId, File image, String extension) {

        if (image != null && image.exists()) {
            if (!prepareDirectory(productId)) {

                return false;
            }
            String imagePath = UPLOAD_PATH + productId + File.separator;
            try {
                copyFileUsingChannel(image, new File(imagePath + "original." + extension));

                ImageIO.write(createThumbnail(image, 189, 189),
                        extension,
                        new File(imagePath + "catalog." + extension));

                ImageIO.write(createThumbnail(image, 520, 520),
                        extension,
                        new File(imagePath + "product." + extension));

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
            Files.walk(Path.of(UPLOAD_PATH + productId + File.separator))
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

    @Override
    public File createTempFile(MultipartFile image) {

        File file = null;
        try {
            file = File.createTempFile("upload_", ".tmp");
            image.transferTo(file);
        } catch (IOException e) {
            LOG.error(e.getMessage(), e);
        }

        return file;
    }

    private static void copyFileUsingChannel(File source, File dest) throws IOException {

        try (FileChannel sourceChannel = new FileInputStream(source).getChannel();
             FileChannel destChannel = new FileOutputStream(dest).getChannel()) {
            destChannel.transferFrom(sourceChannel, 0, sourceChannel.size());
        }
    }


    private BufferedImage createThumbnail(File image, int width, int height) throws IOException {

        Image thumbnail = ImageIO.read(image).getScaledInstance(width, height, BufferedImage.SCALE_SMOOTH);
        BufferedImage thumbnailImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        thumbnailImg.createGraphics().drawImage((thumbnail), 0, 0, null);

        return thumbnailImg;
    }

    private boolean prepareDirectory(Long productId) {

        File directory = new File(UPLOAD_PATH + productId + File.separator);

        if (directory.exists()) {
            delete(productId);
        }

        return directory.mkdir();
    }
}
