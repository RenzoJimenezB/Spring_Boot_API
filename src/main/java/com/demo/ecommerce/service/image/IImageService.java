package com.demo.ecommerce.service.image;

import com.demo.ecommerce.dto.ImageDto;
import com.demo.ecommerce.model.Image;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IImageService {
    List<ImageDto> addImages(List<MultipartFile> files, Long productId);

    Image getImageById(Long id);

    void updateImage(MultipartFile file, Long id);

    void deleteImageById(Long id);
}
