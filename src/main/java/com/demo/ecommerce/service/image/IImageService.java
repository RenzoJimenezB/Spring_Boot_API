package com.demo.ecommerce.service.image;

import com.demo.ecommerce.model.Image;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IImageService {
    Image addImage(List<MultipartFile> files, Long productId);

    Image getImageById(Long id);

    Image updateImage(MultipartFile file, Long imageId);

    void deleteImageById(Long id);
}
