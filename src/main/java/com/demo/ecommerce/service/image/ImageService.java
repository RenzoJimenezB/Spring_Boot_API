package com.demo.ecommerce.service.image;

import com.demo.ecommerce.exception.ResourceNotFoundException;
import com.demo.ecommerce.model.Image;
import com.demo.ecommerce.model.Product;
import com.demo.ecommerce.repository.ImageRepository;
import com.demo.ecommerce.service.product.IProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialBlob;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ImageService implements IImageService {

    private final ImageRepository imageRepository;
    private final IProductService productService;

    @Override
    public Image addImage(List<MultipartFile> files, Long productId) {
        Product product = productService.getProductById(productId);
        
    }

    @Override
    public Image getImageById(Long id) {
        return imageRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Image not found"));
    }

    @Override
    public Image updateImage(MultipartFile file, Long imageId) {
        Image existingImage = getImageById(imageId);

        try {
            existingImage.setImageName(file.getOriginalFilename());
            existingImage.setImageType(file.getContentType());
            existingImage.setImage(new SerialBlob(file.getBytes()));
            imageRepository.save(existingImage);

        } catch (IOException | SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public void deleteImageById(Long id) {
        Image image = getImageById(id);
        imageRepository.delete(image);
    }
}
