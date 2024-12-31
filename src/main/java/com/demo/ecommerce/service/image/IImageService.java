package com.demo.ecommerce.service.image;

import com.demo.ecommerce.model.Image;
import org.springframework.web.multipart.MultipartFile;

public interface IImageService {
    Image addImage(MultipartFile file, Long productId);

    Image getImageById(Long id);

    void updateImage(MultipartFile file, Long imageId);

    void deleteImageById(Long id);
}
