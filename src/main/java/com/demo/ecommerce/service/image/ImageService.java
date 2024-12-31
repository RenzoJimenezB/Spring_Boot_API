package com.demo.ecommerce.service.image;

import com.demo.ecommerce.model.Image;
import org.springframework.web.multipart.MultipartFile;

public class ImageService implements IImageService {
    @Override
    public Image addImage(MultipartFile file, Long productId) {
        return null;
    }

    @Override
    public Image getImageById(Long id) {
        return null;
    }

    @Override
    public void updateImage(MultipartFile file, Long imageId) {

    }

    @Override
    public void deleteImageById(Long id) {

    }
}
