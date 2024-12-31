package com.demo.ecommerce.service.image;

import com.demo.ecommerce.exception.ResourceNotFoundException;
import com.demo.ecommerce.model.Image;
import com.demo.ecommerce.repository.ImageRepository;
import com.demo.ecommerce.service.product.IProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class ImageService implements IImageService {

    private final ImageRepository imageRepository;
    private final IProductService productService;

    @Override
    public Image addImage(MultipartFile file, Long productId) {
        return null;
    }

    @Override
    public Image getImageById(Long id) {
        return imageRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Image not found"));
    }

    @Override
    public void updateImage(MultipartFile file, Long imageId) {

    }

    @Override
    public void deleteImageById(Long id) {
        Image image = getImageById(id);
        imageRepository.delete(image);
    }
}
